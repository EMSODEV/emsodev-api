package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;

import eu.emsodev.observations.spark.livy.client.InteractiveSessionConf;
import eu.emsodev.observations.spark.livy.client.LivyException;
import eu.emsodev.observations.spark.livy.client.LivyInteractiveClient;
import eu.emsodev.observations.spark.livy.client.Session;
import eu.emsodev.observations.spark.livy.client.SessionKind;
import eu.emsodev.observations.spark.livy.client.StatementResult;
import eu.emsodev.observations.spark.livy.client.StatementResultListener;
import eu.emsodev.observations.utilities.EmsodevUtility;

@Controller
public class ODVgetFilesApiController implements ODVgetFilesApi {

	private String sparkResult = null;
	private boolean tryExecSparkJob = true;
	private int session_status = Session.STARTING;
	private LivyInteractiveClient client = null;

	// The URL of the Livy server to obtain a session
	@Value("${emsodev.global.setting.urlToCall.odvFilesGet}")
	private String livyUriTocall;
	// The path to the HDF where ODV files will be stored
	@Value("${emsodev.global.setting.urlToCall.odvFilesPath}")
	private String odvFilesPathPart;
	private String contentWithParameters;
	// The path to the file with the Scala code to run on spark via livy client
	@Value("${emsodev.global.scalacode.createodv.filepath}")
	private String scalacodeforodvpath;

	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public ResponseEntity<String> odvFilesGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

			,

			@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

			,
			@ApiParam(value = "The start time for the query. The Date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate

			,
			@ApiParam(value = "The end time for the query. The Date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		// Create a new Livy Client with a facke username and password (our
		// system does not require autentication)
		try {
			client = new LivyInteractiveClient(livyUriTocall, "username",
					"password");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Create a new interactive Livy session (kind : spark)
		// "Spark" session is for - Interactive Scala Spark session
		InteractiveSessionConf sc = new InteractiveSessionConf(
				SessionKind.SPARK);

		// I f required external jar add here like the following example
		// String[] path = new String[1];
		// path[0] = "/usr/hdp/2.5.0.0-1245/livy/jars/spark-csv_2.10-1.5.0.jar";
		// sc.setJars(path);

		// Create the Livy Session with kind Spark
		try {
			client.createSession(sc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LivyException e) {
			e.printStackTrace();
		}
		// The Session does not start immediately, for this reason the following
		// loop attempt to get the IDLE status that means "Session is waiting for input"
		while (true) {
			try {
				session_status = client.getSession().getState();
				if (session_status == Session.IDLE) {
					break;
				} else {
					System.out.println("Session is starting. Session ID: "
							+ client.getSession().getId());
				}
				Thread.sleep(1000);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		while (tryExecSparkJob) {
			String content = null;
			// Ready to read the scala source code to execute
			try {
				// Read the content of the passed file with the scala code
				// program that will create the ODV file
				File file = ResourceUtils.getFile(scalacodeforodvpath);

				content = FileUtils.readFileToString(file);

				// The session params are passed to the scala program to read
				// the correct data from HDF
				contentWithParameters = content
						.replace("egimNodeVar", observatory)
						.replace("instrumentIdVar", instrument)
						.replace(
								"startDateVar",
								EmsodevUtility
										.getDateToStringScalaFormat(startDate, "S"))
						.replace(
								"endDateVar",
								EmsodevUtility
										.getDateToStringScalaFormat(endDate, "E"));
			} catch (Exception e) {
				System.out.println("Input Failure: " + e.getMessage());
			}
			String statement = contentWithParameters;

			System.out.println("Scala code is [" + statement + "]");
			if (statement.equals("exit")) {
				try {
					client.deleteSession();
					System.out.println("Bye. The session is closed.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}

			// Call the statement submit via submitStatement method, the
			// execution could run for some minute
			// before release a StatementResulListener with the result, for this
			// reason a loop that test the result is
			// performed after the execution to avoid the exit without a result.
			try {

				System.out
						.println("Try to execute the statement: " + statement);
				client.submitStatement(statement, 1000,
						new StatementResultListener() {

							@Override
							public void update(StatementResult result) {
								sparkResult = result.getOutput();
							}
						});
				// Set the boolean to false to avoid another unnecessary loop
				tryExecSparkJob = false;
			} catch (LivyException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// loop to wait that the scala program get a result to the
			// ResultListener
			boolean loop = true;
			while (loop) {
				if (sparkResult == null) {
					continue;
				} else {
					loop = false;
				}
			}
		}
		System.out
				.println("Statement executed with result ID : " + sparkResult);

		// Compose the finally result with the complete path to download the ODV
		// file
		String odvFilePath = odvFilesPathPart + sparkResult;
		// Initialize the boolea for the first loop and clean the job result
		tryExecSparkJob = true;
		sparkResult = null;

		// Try to delete the livy session to avoid that unnecessary sessions
		// remains active on the livy server
		try {
			client.deleteSession();
			System.out.println("Session deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(odvFilePath, HttpStatus.OK);
	}

}

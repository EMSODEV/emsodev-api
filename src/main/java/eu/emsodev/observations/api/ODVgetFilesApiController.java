package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
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
import eu.emsodev.observations.spark.livy.client.SessionEventListener;
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
	
	@Value("${emsodev.global.setting.urlToCall.odvFilesGet}")
	private String livyUriTocall;
	@Value("${emsodev.global.setting.urlToCall.odvFilesPath}")
	private String odvFilesPathPart;
	private String contentWithParameters;
//	@Value("${emsodev.global.scalacode.createodv}")
//	private String scalacodeforodv;
	
	/**
     * Property placeholder configurer needed to process @Value annotations
     */
     @Bean
     public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
     }
	
	//public ResponseEntity<File> odvFilesGet(HttpServletResponse response) {
	public ResponseEntity<String> odvFilesGet(
	@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

	,

	@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

	,
	@ApiParam(value = "The start time for the query. The formast must be dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

	,
	@ApiParam(value = "The end time for the query. The formast must be dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate
    ){

		try {
			client = new LivyInteractiveClient(livyUriTocall, "username", "password");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		//*********************************
		
		  ClassLoader cl = ClassLoader.getSystemClassLoader();

	        URL[] urls = ((URLClassLoader)cl).getURLs();

	        for(URL url: urls){
	        	System.out.println(url.getFile());
	        }
		
		
		
		
		
		/////////**************************
		
		
		//Create a new interactive Livy session (kind : spark)
		InteractiveSessionConf sc = new InteractiveSessionConf(
				SessionKind.SPARK);

		// String[] path = new String[1];
		// path[0] = "/usr/hdp/2.5.0.0-1245/livy/jars/spark-csv_2.10-1.5.0.jar";
		// sc.setJars(path);

		//*********************************************************
		// Set session listener
		try {
			client.createSession(sc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LivyException e) {
			e.printStackTrace();
		}
//
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
		
		//*************************************************************************
		

		while (tryExecSparkJob) {
			String content = null;
			// Ready to read the scala source code to execute
			try {

				File file = ResourceUtils.getFile("src/main/resources/odvsparkcode.txt");
				
				content = FileUtils.readFileToString(file);
				
				//content = EmsodevUtility.getFileFromResourcesFolder("/odvsparkcode.txt");
				contentWithParameters = content.replace("egimNodeVar", observatory).replace("instrumentIdVar", instrument).replace("startDateVar", EmsodevUtility.getDateToStringScalaFormat(startDate)).replace("endDateVar", EmsodevUtility.getDateToStringScalaFormat(endDate));
				System.out.println(contentWithParameters);

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

			// Call the execution of the statement
			try {

				System.out.println("Try to execute the statement: " + statement);
				client.submitStatement(statement, 1000,
						new StatementResultListener() {

					@Override
					public void update(StatementResult result) {
						sparkResult = result.getOutput();
					}
				});
				
				tryExecSparkJob = false;
				
			} catch (LivyException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			boolean loop = true;
			while (loop) {
				if (sparkResult == null ) {
					continue;
				}else{
					loop = false;
				}
			}
		}
		System.out.println("Statement executed. The ODV file with the following id is mande : " + sparkResult);
		
		
		String odvFilePath = odvFilesPathPart + sparkResult;

		tryExecSparkJob = true;
		sparkResult = null;
		
		//Try to delete the livy session
		try {
			client.deleteSession();
			System.out.println("Session deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(odvFilePath, HttpStatus.OK);
	}

	
}

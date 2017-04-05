/**
 * 
 */
package eu.emsodev.observations.utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author leandro
 *
 */
public final class EmsodevUtility {

	protected static RestTemplate restTemplate;

	/**
	 * 
	 */
	public EmsodevUtility() {
		// TODO Auto-generated constructor stub
	}

	public static String test(String test) {

		// Do stuff
		test = test + " la riprova";

		return test;
	}

	public static RestTemplate istantiateRestTemplate(boolean enableProxy,
			String username, String password, String proxyUrl, String proxyPort) {
		// Setting for proxy, please modify proxy parameter into the
		// createRestTemplate() method
		if (enableProxy) {
			try {
				int port = Integer.valueOf(proxyPort).intValue();

				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(proxyUrl, port),
						new UsernamePasswordCredentials(username, password));

				HttpHost myProxy = new HttpHost(proxyUrl, port);
				HttpClientBuilder clientBuilder = HttpClientBuilder.create();

				clientBuilder.setProxy(myProxy)
						.setDefaultCredentialsProvider(credsProvider)
						.disableCookieManagement();

				HttpClient httpClient = clientBuilder.build();
				HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
				factory.setHttpClient(httpClient);

				restTemplate = new RestTemplate(factory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			restTemplate = new RestTemplate();
		}

		return restTemplate;

	}

	public static String replaceNull(String input) {
		return input == null ? "" : input;
	}

	// @fileName = Name of the file eg: "xml/test.xml" if the file is under
	// "test/resources/xml/test.xml" folder
	public static String getFileFromResourcesFolder(String fileName) {

		String result = "";
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		
		try {
			result = IOUtils
					.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static String getDateToStringScalaFormat (Date date){
				
		if (date == null){
			date = GregorianCalendar.getInstance().getTime();
		}
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    Integer year = cal.get(Calendar.YEAR);
	    Integer month = cal.get(Calendar.MONTH) + 1;
	    Integer day = cal.get(Calendar.DAY_OF_MONTH);
	    String dateToScalaFormat = year.toString() + "," + month.toString() + "," + day.toString() + ",0,0,0";
		
		
		return dateToScalaFormat; 
	}
	
	public static String getDateAsStringTimestampFormat (Date date){
		String dateAsStringTimestampFormat = "";
		
		if (date == null){
			date = GregorianCalendar.getInstance().getTime();
		}
	    
		dateAsStringTimestampFormat = String.valueOf(date.getTime());
		
		return dateAsStringTimestampFormat; 
	}
}

/**
 * 
 */
package eu.emsodev.observations.utilities;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
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

	public static RestTemplate istantiateRestTemplate(boolean enableProxy,String username,String password,String proxyUrl,String proxyPort) {
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
	
}

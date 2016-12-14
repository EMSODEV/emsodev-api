package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.emsodev.observations.model.Instrument;
import eu.emsodev.observations.model.Instruments;
import eu.emsodev.observations.model.Observation;
import eu.emsodev.observations.model.Observations;
import eu.emsodev.observations.model.ObservationsStats;
import eu.emsodev.observations.model.Observatories;
import eu.emsodev.observations.model.Observatory;
import eu.emsodev.observations.model.Parameter;
import eu.emsodev.observations.model.Parameters;

@Configuration
//@PropertySource("${api.properties.home}")
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class })
public class ObservationsController implements ObservationsApi {

	//Test	comment
	@Value("${emsodev.global.setting.urlToCall.observatoriesGet}")
	private String urlToCallObservatoriesGet;
	
	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersParameterGet}")
	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet;
	 
	 @Value("${emsodev.global.setting.proxyUser}")
	private String username;
	 
	 @Value("${emsodev.global.setting.proxyPassword}")
	private String password;
	 
	 @Value("${emsodev.global.setting.proxyUrl}")
    private String proxyUrl;
	 
	 @Value("${emsodev.global.setting.proxyPort}")
    private String proxyPort;
	 
	@Value("${emsodev.global.setting.proxy.enable}") 
	private boolean enableProxy; 

	protected RestTemplate restTemplate;
	
	public ObservationsController() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Property placeholder configurer needed to process @Value annotations
     */
     @Bean
     public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
     }
	
	public ResponseEntity<Observatories> observatoriesGet() {
		//Create the restTemplate object with or without proxy
		istantiateRestTemplate();

		String egimNode = "{EGIMNode=*}";
		// The response as string of the urlToCall
		String response = restTemplate.getForObject(urlToCallObservatoriesGet, String.class,
				egimNode);

		// Declare a list that not allow duplicate values
		Set<String> set = new HashSet<String>();
		try {
			// Create a JSONObject by the response
			JSONObject obj = new JSONObject(response);

			// Create a JSONArray that rapresent the "results" tag nested into
			// the JSON
			JSONArray arr = obj.getJSONArray("results");
			// The JSON object used into the loop to extract the value of the
			// "tags" tag
			JSONObject test = new JSONObject();

			// iterate the JSON array to read the value of the EGIMSnode
			for (int i = 0; i < arr.length(); i++) {
				test = arr.getJSONObject(i).getJSONObject("tags");
				// add the EGIMnode value to the list
				set.add(test.getString("EGIMNode"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// Istantiate the Observatories object
		Observatories obs = new Observatories();
		// For each value of the list create an Observatory object to add to the
		// Observatoriers object
		for (String s : set) {
			Observatory observatory = new Observatory();
			observatory.setName(s);
			obs.add(observatory);
		}

		return new ResponseEntity<Observatories>(obs, HttpStatus.OK);
	}

	

	public ResponseEntity<Observatory> observatoriesObservatoryGet(
			@ApiParam(value = "EGIM observatory name", required = true) @PathVariable("observatory") String observatory

			) {
		// do some magic!
		
		System.out.println("***************test parameters observatory passed: "+observatory+"**************");
		return new ResponseEntity<Observatory>(HttpStatus.OK);
	}

	public ResponseEntity<Instruments> observatoriesObservatoryInstrumentsGet(
			@ApiParam(value = "EGIM observatory name", required = true) @PathVariable("observatory") String observatory

			) {
		// do some magic!
		return new ResponseEntity<Instruments>(HttpStatus.OK);
	}

	public ResponseEntity<Instrument> observatoriesObservatoryInstrumentsInstrumentGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,

			@ApiParam(value = "EGIM observatory instrument name.", required = true) @PathVariable("instrument") String instrument

			) {
		// do some magic!
		return new ResponseEntity<Instrument>(HttpStatus.OK);
	}

	public ResponseEntity<Parameters> observatoriesObservatoryInstrumentsInstrumentParametersGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,

			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument

			) {
		// 
		

		
		System.out.println("***************test instrument parameters  observatory passed: "+observatory+"******instrument passed:" + instrument +"********");
		return new ResponseEntity<Parameters>(HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Observations> observatoriesObservatoryInstrumentsInstrumentParametersParameterGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,

			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument

			,

			@ApiParam(value = "EGIM parameter name.", required = true) @PathVariable("parameter") String parameter

			,
			@ApiParam(value = "The start time for the query. This may be an absolute or relative time. The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>-ago` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a start time of `1h-ago` and leave out the end time, our query will return data start at 1 hour ago to the current time.", required = true) @RequestParam(value = "startDate", required = true) String startDate

			,
			@ApiParam(value = "The end time for the query in Unix (or POSIX) style. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) String endDate

			) {
		// do some magic!
		
		//Create the restTemplate object with or without proxy
		istantiateRestTemplate();
		
//		MultiValueMap<String, String> variables = new LinkedMultiValueMap<String, String>();;
//		
//		variables.set("EGIMNode", observatory);
//		variables.set("SensorID", instrument);
//		
//		URI targetUrl= UriComponentsBuilder.fromUriString(urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet)
//			    .path(startDate +"&m=sum:" + parameter)
//			    .queryParams(variables)
//			    .build()
//			    .toUri();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		//String parametri  = params.toString().replace(" ", "");
		//String pulito = parametri.replace(" ", "");
		//System.out.println("Pulito : "+pulito);
		//System.out.println("Params : "+params.toString().trim());
		
		
		//String egimNode = "{EGIMNode="+observatory+",SensorID="+instrument+"}";
		//System.out.println("EgimNode : " + egimNode);
		//String egimNode = "{EGIMNode="+observatory;
		//String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet + startDate +"&m=sum:" + parameter+"{egimNode}";
		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet + startDate +"&m=sum:" + parameter+"{params}";
		// The response as string of the urlToCall																								
		String response = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));
		
		
		 	 
		 if (response.startsWith("[")){
			 response = response.substring(1);
		 }
		 if (response.endsWith("]")){
			 response = response.substring(0, (response.length() - 1));
		 }
		
		  Observations obts = new Observations();
		 try {
				// Create a JSONObject by the response
				JSONObject obj = new JSONObject(response);

				// Create a JSONArray that rapresent the "results" tag nested into
				// the JSON
				JSONObject dps = obj.getJSONObject("dps");

				String dpsCleaned = dps.toString().replace("\"", "").replace("{", "").replace("}", "");
				
				System.out.println(dpsCleaned);
			
				String[] array = dpsCleaned.split(",");
              
                Instrument inst = new Instrument();
                inst.setName(instrument);
                Parameter par = new Parameter(parameter);
                Observatory observ = new Observatory();
                observ.setName(observatory);
                
                ArrayList<Observation> list = new ArrayList<Observation>();
                
				for (int i = 0, n = array.length; i < n; i++) {
				    String c = array[i];
				    Observation obt = new Observation();
				    obt.setPhenomenonTime(Long.valueOf(c.substring(0, c.indexOf(":"))));
				    obt.setValue(Double.valueOf(c.substring((c.indexOf(":") + 1), c.length())));
				    list.add(obt);
				}
				
				obts.setObservations(list);
				obts.setParameter(par);
				obts.setInstrument(inst);
				obts.setObservatory(observ);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		return new ResponseEntity<Observations>(obts,HttpStatus.OK);
	}

	public ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterStatsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory

			,

			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument

			,

			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter

			,
			@ApiParam(value = "The start time for the query. This may be an absolute or relative time. The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>-ago` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a start time of `1h-ago` and leave out the end time, the query will return data start at 1 hour ago to the current time.", required = true) @RequestParam(value = "startDate", required = true) String startDate

			,
			@ApiParam(value = "The end time for the query in Unix (or POSIX) style. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) String endDate

			) {
		// do some magic!
		
		
		return new ResponseEntity<ObservationsStats>(HttpStatus.OK);
	}

	public String getPhoenixConnection() {

		/*
		 * 
		 * @SuppressWarnings("unused") Statement stmt = null; ResultSet rset =
		 * null;
		 * 
		 * try { Class.forName("org.apache.phoenix.jdbc.PhoenixDriver"); } catch
		 * (ClassNotFoundException e1) {
		 * System.out.println("Exception Loading Driver"); e1.printStackTrace();
		 * } try { //Connection con = DriverManager.getConnection(
		 * "jdbc:phoenix:90.147.170.237,90.147.170.236,90.147.170.241:2181:/hbase-unsecure"
		 * ); Connection con = DriverManager.getConnection(
		 * "jdbc:phoenix:90.147.170.241:8765:/hbase-unsecure"); stmt =
		 * con.createStatement(); System.out.println(
		 * "****************************************eccomi*****************************"
		 * ); PreparedStatement statement =
		 * con.prepareStatement("select * from javatest"); rset =
		 * statement.executeQuery(); while (rset.next()) {
		 * System.out.println(rset.getString("mycolumn")); } statement.close();
		 * con.close(); } catch(Exception e) {
		 * System.out.println(e.getMessage()); }
		 */

		return "only to save code";
	}
	
	
	private void istantiateRestTemplate(){
		//Setting for proxy, please modify proxy parameter into the createRestTemplate() method
		if (enableProxy) {		
			try {
				restTemplate = createRestTemplate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}else{
			  restTemplate = new RestTemplate();
		}
		//End setting for proxy
	}

	private RestTemplate createRestTemplate() throws Exception {
		
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

		return new RestTemplate(factory);
	}
	
}

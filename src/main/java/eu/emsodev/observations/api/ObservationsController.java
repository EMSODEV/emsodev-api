package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
@PropertySource("${api.properties.home}")
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class })
public class ObservationsController implements ObservationsApi {

	//Test	comment
	@Value("${emsodev.global.setting.urlToCall.observatoriesGet}")
	private String urlToCallObservatoriesGet;
	
	@Value("${emsodev.global.setting.urlToCall.observatoryInstrumentsGet}")
	private String urlToCallObservatoryInstrumentsGet;
	
	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersGet}")
	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersGet;
	
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
		//Create the restTemplate object with or without proxy
		istantiateRestTemplate();
		String params = "{SensorID=*,EGIMNode="+observatory+"}";

		String response = restTemplate.getForObject(urlToCallObservatoryInstrumentsGet, String.class, params);
		

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
						set.add(test.getString("SensorID"));
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				// Istantiate the Observatories object
				Instruments instrs = new Instruments();
				// For each value of the list create an Observatory object to add to the
				// Observatoriers object
				for (String s : set) {
					Instrument instrument = new Instrument();
					instrument.setName(s);
					instrs.addInstrumentsItem(instrument);
				}
		
		return new ResponseEntity<Instruments>(instrs,HttpStatus.OK);
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
		
		//Create the restTemplate object with or without proxy
		istantiateRestTemplate();
		String params = "{SensorID="+instrument+",EGIMNode="+observatory+"}";

		String response = restTemplate.getForObject(urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersGet, String.class, params);
		

		// Declare a list that not allow duplicate values
				Set<String> set = new HashSet<String>();
				Parameters parameters = new Parameters();
				try {
					// Create a JSONObject by the response
					JSONObject obj = new JSONObject(response);

					// Create a JSONArray that rapresent the "results" tag nested into
					// the JSON
					JSONArray arr = obj.getJSONArray("results");
					// The JSON object used into the loop to extract the value of the
					// "tags" tag
					
					String observatoryParameter = "";
					// iterate the JSON array to read the value of the EGIMSnode
					for (int i = 0; i < arr.length(); i++) {
						observatoryParameter = arr.getJSONObject(i).getString("metric");
						// add the EGIMnode value to the list				
						set.add(observatoryParameter);
						
					}
					
										
					// For each value of the list create an Observatory object to add to the
					// Observatoriers object
					for (String s : set) {
						Parameter parameter = new Parameter();
						parameter.setName(s);
						parameters.addParametersItem(parameter);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
		return new ResponseEntity<Parameters>(parameters,HttpStatus.OK);
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
		
		//Create the restTemplate object with or without proxy
		istantiateRestTemplate();
		
		//Create a map of params to pass add as placeholder after parameter value in the following compositeUrl
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet + startDate +"&m=sum:" + parameter+"{params}";
		// The response as string of the urlToCall - This Url do not allows blanck spaces beetwen the params, for this reason is trimmed																								
		String response = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));

		//Declare the final response object outside the loop
		Observations observations = new Observations();
		
		try {
			//Convert the response as string to a JSONArray
			JSONArray jarray = new JSONArray(response);
			//Declare a JSONObject for the timeseries 
			JSONObject jobjDps = new JSONObject();
			String egimNode = "";
			String sensorId= "";
			String metric = "" ;
			// iterate the JSON array to read the value of the Response
			for (int i = 0; i < jarray.length(); i++) {
				//Read the EGIMNode value
				egimNode = jarray.getJSONObject(i).getJSONObject("tags").getString("EGIMNode");
				//Read the SensorID value
				sensorId = jarray.getJSONObject(i).getJSONObject("tags").getString("SensorID");
				//Read the parameter (metric) value
				metric   = jarray.getJSONObject(i).getString("metric");
				//Populate the JSONOObject related the timeseries 
				jobjDps = jarray.getJSONObject(i).getJSONObject("dps");
				//Remove double " and brace from the string rapresentation of the object
				String dpsCleaned = jobjDps.toString().replace("\"", "").replace("{", "").replace("}", "");
				//Create an array with the value of the dpsCleaned string				
				String[] array = dpsCleaned.split(",");				
				//set the instrument name with the value previous saved
				Instrument inst = new Instrument();
                inst.setName(sensorId);
                //set the parameter name with the value previous saved
                Parameter par = new Parameter();
                par.setName(metric);
                //set the observatory name with the value previous saved
                Observatory observ = new Observatory();
                observ.setName(egimNode);
                
                ArrayList<Observation> list = new ArrayList<Observation>();
                
                Map<Long, Double> map = new TreeMap<Long, Double>();
                
				for (int index = 0, n = array.length; index < n; index++) {
				    String c = array[index];
				    map.put(Long.valueOf(c.substring(0, c.indexOf(":"))), Double.valueOf(c.substring((c.indexOf(":") + 1), c.length())));
				}
				
				
				for(Map.Entry<Long,Double> entry : map.entrySet()) {
					  Long key = entry.getKey();
					  Double value = entry.getValue();
					  System.out.println(key + " => " + value);
					  
					  Observation obt = new Observation();
					  obt.setPhenomenonTime(key);
					  obt.setValue(value);
					  list.add(obt);
					  
					}
				
				map.clear();

				observations.setObservations(list);
				observations.setParameter(par);
				observations.setInstrument(inst);
				observations.setObservatory(observ);
			}
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		 
		return new ResponseEntity<Observations>(observations,HttpStatus.OK);
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

		
	//Method to set the proxy if enable= true or false
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

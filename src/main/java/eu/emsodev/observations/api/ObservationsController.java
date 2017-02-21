package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eu.emsodev.observations.model.Instrument;
import eu.emsodev.observations.model.InstrumentMetadata;
import eu.emsodev.observations.model.InstrumentMetadataList;
import eu.emsodev.observations.model.Instruments;
import eu.emsodev.observations.model.Observation;
import eu.emsodev.observations.model.Observations;
import eu.emsodev.observations.model.Observatories;
import eu.emsodev.observations.model.Observatory;
import eu.emsodev.observations.model.Parameter;
import eu.emsodev.observations.model.Parameters;
import eu.emsodev.observations.utilities.EmsodevUtility;

@Configuration
@PropertySource("${api.properties.home}")
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class })
public class ObservationsController implements ObservationsApi {

	@Value("${emsodev.global.setting.urlToCall.observatoriesGet}")
	private String urlToCallObservatoriesGet;
	
	@Value("${emsodev.global.setting.urlToCall.observatoryInstrumentsGet}")
	private String urlToCallObservatoryInstrumentsGet;
	
	
	
	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentGet}")
	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentGet;              
	
	
	
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
		//istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		

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

	

	public ResponseEntity<Instruments> observatoriesObservatoryInstrumentsGet(
			@ApiParam(value = "EGIM observatory name", required = true) @PathVariable("observatory") String observatory

			) {
		//Create the restTemplate object with or without proxy
		//istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
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

	public ResponseEntity<InstrumentMetadataList> observatoriesObservatoryInstrumentsInstrumentGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,

			@ApiParam(value = "EGIM observatory instrument name.", required = true) @PathVariable("instrument") String instrument

			) {
		
		//Create the restTemplate object with or without proxy
		//istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		
		String url = "";
		url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet +observatory +"/" + instrument ;
		String response = restTemplate.getForObject(url + "?op=LISTSTATUS", String.class);
		System.out.println(response);
		
		String type = "";
		String nameDir = "";
		String dateValidity = "";
		InstrumentMetadataList instrMetadataList = new InstrumentMetadataList();
		instrMetadataList.setInstrumentName(instrument);
		//InstrumentMetadata instrMetadata = new InstrumentMetadata();
		// Create a JSONObject by the response
		try {
			JSONObject obj = new JSONObject(response);
			// Create a JSONArray that rapresent the "FileStatus" tag nested into
			// the JSON
			//obj.getJSONObject("FileStatuses").getString("type");
			JSONArray arr = obj.getJSONObject("FileStatuses").getJSONArray("FileStatus");
			
			for (int i = 0; i < arr.length(); i++) {
				type = arr.getJSONObject(i).getString("type");
				nameDir = arr.getJSONObject(i).getString("pathSuffix");
				dateValidity = arr.getJSONObject(i).getString("modificationTime");
				
				if (type != null && "DIRECTORY".equals(type)){
					String resp = restTemplate.getForObject(url + "/"+nameDir + "/metadata/metadata.json"+"?op=OPEN", String.class);
					System.out.println(resp);
					InstrumentMetadata instrMetadata = new InstrumentMetadata();
					instrMetadata.setValidityDate(dateValidity);				
					
					
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
					try {
						Object json = mapper.readValue(resp, Object.class);
						instrMetadata.setMetadata(json.toString());						
					} catch (Exception e) {
						e.printStackTrace();
					}					
					
					
//					JSONObject metaJson = new JSONObject(resp);
//					instrMetadata.setMetadataJson(metaJson);
					
					instrMetadataList.addInstrumentsMetadataItem(instrMetadata);
				}
			}
						
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
				
		return new ResponseEntity<InstrumentMetadataList>(instrMetadataList,HttpStatus.OK);
	}

	public ResponseEntity<Parameters> observatoriesObservatoryInstrumentsInstrumentParametersGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,

			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument

			) {
		
		//Create the restTemplate object with or without proxy
		//istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
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
					e.printStackTrace();
				}
				
		
		return new ResponseEntity<Parameters>(parameters,HttpStatus.OK);
	}

	//@SuppressWarnings("unchecked")
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
		//istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		
		//Create a map of params to pass add as placeholder after parameter value in the following compositeUrl
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet + startDate +"&m=sum:" + parameter+"{params}"+"&end="+EmsodevUtility.replaceNull(endDate);
		
		// The response as string of the urlToCall - This Url do not allows blanck spaces beetwen the params, for this reason is trimmed																							
		Object response = restTemplate.getForObject(compositeUrl, Object.class, params.toString().replace(" ", ""));
		//String response = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));

		//Declare the final response object outside the loop
		Observations observations = new Observations();
		
		try {
			//Properties to pass to the outpout object
			String egimNodeName = "";
			String sensorIdName = "";
			String metricName = "" ;			
			//Property that contain the readed timeseries without brackets and quotes and that will be used to create an array of timeseries
			String jobjectDpsCleaned = null;
			//Gson google object is used insteaf og JSONObject becouse  let the result sorted by timestamp
			Gson gson = new Gson();
			//Create a jelement that contains the response 
			JsonElement jelement = gson.fromJson (response.toString(), JsonElement.class);
			//The response is an array. Create an jsonArray that contain the response
			JsonArray jsonarray = jelement.getAsJsonArray();
			//Get the first and last item of the array
			JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
			//The value of metric attribute
			JsonObject  jobject = jarrayItem.getAsJsonObject();
			metricName = jobject.get("metric").getAsString();
						
			//Get the an jsonObject with that rapresent the "tags" branche
			jobject = jobject.getAsJsonObject("tags");
			//Get the value of attribute of SensorID and EGIMNode of the "tags" branche
			sensorIdName = jobject.get("SensorID").getAsString();
			egimNodeName = jobject.get("EGIMNode").getAsString();
			
			//set the instrument name with the previous extract value
			Instrument inst = new Instrument();
            inst.setName(sensorIdName);
            //set the parameter name with with the previous extract value
            Parameter par = new Parameter();
            par.setName(metricName);
            //set the observatory name with with the previous extract value
            Observatory observ = new Observatory();
            observ.setName(egimNodeName);
			
			//Get the an jsonObject with that rapresent the "dps" branche
			JsonObject  jobjectDps = jarrayItem.getAsJsonObject();
			jobjectDps = jobjectDps.getAsJsonObject("dps");
			jobjectDpsCleaned = jobjectDps.toString().replace("\"", "").replace("{", "").replace("}", "");
			//Array of timeseries key: timestamp, value: value
			String[] arrayDps = jobjectDpsCleaned.split(",");	
			//Declare a List of Observation 
            ArrayList<Observation> observationsList = new ArrayList<Observation>();
			//For each array item extract the key and value to set to a new Observation object in the loop
            for (int index = 0, n = arrayDps.length; index < n; index++) {
         		String item = arrayDps[index];
			    //
				Observation obs = new Observation();
				obs.setPhenomenonTime(Long.valueOf(item.substring(0, item.indexOf(":"))));
				obs.setValue(Double.valueOf(item.substring((item.indexOf(":") + 1), item.length())));
				observationsList.add(obs);
            }
            
            //Compose the final bean to return 
			observations.setObservations(observationsList);
			observations.setParameter(par);
			observations.setInstrument(inst);
			observations.setObservatory(observ);
            
			
			} catch (Exception e1) {
			       e1.printStackTrace();
		    }
		 
		return new ResponseEntity<Observations>(observations,HttpStatus.OK);
	}
	
}

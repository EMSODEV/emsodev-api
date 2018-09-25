package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.test.util.MetaAnnotationUtils;
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

import eu.emsodev.observations.model.AcousticObservationList;
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

	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet}")
	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet;

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

	//protected Integer limit;
	
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
		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		String egimNode = "{EGIMNode=*}";
		// The response as string of the urlToCall
		String response = restTemplate.getForObject(urlToCallObservatoriesGet,
				String.class, egimNode);
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
/*
	public ResponseEntity<Instruments> observatoriesObservatoryInstrumentsGet(
			@ApiParam(value = "EGIM observatory name", required = true) @PathVariable("observatory") String observatory

	) {
		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		String params = "{SensorID=*,EGIMNode=" + observatory + "}";
		String response = restTemplate.getForObject(
				urlToCallObservatoryInstrumentsGet, String.class, params);
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

		
		HashMap<String, String> sensorDetails = new HashMap<String, String>();  
		
		
		// Istantiate the Observatories object
		Instruments instrs = new Instruments();
		// For each value of the list create an Observatory object to add to the
		// Observatoriers object
		for (String s : set) {
			
			sensorDetails =	getInstrumentDetails(observatory, s);
			String sensorLongName = sensorDetails.get("sensorLongName");
			String sensorType = sensorDetails.get("sensorType");
			String sn = sensorDetails.get("sn");
			
			Instrument instrument = new Instrument();
			instrument.setName(s);
			instrument.setSensorLongName(sensorLongName);
			instrument.setSensorType(sensorType);
			instrument.setSn(sn);
			instrs.addInstrumentsItem(instrument);
			sensorDetails.clear();
		}

		return new ResponseEntity<Instruments>(instrs, HttpStatus.OK);
	}
	*/
	
	public ResponseEntity<Instruments> observatoriesObservatoryInstrumentsGet(
			@ApiParam(value = "EGIM observatory name", required = true) @PathVariable("observatory") String observatory

	) {
	
		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		
		String url = "";
		url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet
				+ observatory;
		String response = restTemplate.getForObject(url + "?op=LISTSTATUS",
				String.class);
		//System.out.println(response);

		String type = "";
		String nameDir = "";
		String nameJasonPart = ""; 
//		String dateValidity = "";
		
		// Istantiate the OInstruments object
		Instruments instrs = new Instruments();
		try {
			JSONObject obj = new JSONObject(response);
			// Create a JSONArray that rapresent the "FileStatus" tag nested
			// into the JSON
			JSONArray arr = obj.getJSONObject("FileStatuses").getJSONArray(
					"FileStatus");


			
			
			for (int i = 0; i < arr.length(); i++) {
				type = arr.getJSONObject(i).getString("type");
				nameDir = arr.getJSONObject(i).getString("pathSuffix");
//				dateValidity = arr.getJSONObject(i).getString(
//						"modificationTime");
				
				if (nameDir != null && nameDir.startsWith("acoustic_")){
					nameJasonPart = nameDir.replace("acoustic_", "");
				}else{
					nameJasonPart = nameDir;
				}
				

				if (type != null && "DIRECTORY".equals(type)) {
					String resp = restTemplate.getForObject(url + "/" + nameDir
							+ "/sensor_"+nameJasonPart+".json" + "?op=OPEN",
							String.class);
					//System.out.println(resp);
					
					JSONObject objFinal = new JSONObject(resp);
					
					String egimNode = objFinal.getString("EGIMNode");
					String sensorId = objFinal.getString("sensorID");
					String sensorLongName = objFinal.getString("sensorLongName");
					String sn = objFinal.getString("sn");
					String sensorType = objFinal.getString("sensorType");
					
					Instrument instrument = new Instrument();
					instrument.setName(sensorId);
					instrument.setSensorLongName(sensorLongName);
					instrument.setSensorType(sensorType);
					instrument.setSn(sn);
					instrs.addInstrumentsItem(instrument);
										
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	
		return new ResponseEntity<Instruments>(instrs, HttpStatus.OK);
	}
	
	

	public ResponseEntity<InstrumentMetadataList> observatoriesObservatoryInstrumentsInstrumentGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "EGIM observatory instrument name.", required = true) @PathVariable("instrument") String instrument) {
		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		String url = "";
		url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet
				+ observatory + "/" + instrument;
		String response = restTemplate.getForObject(url + "?op=LISTSTATUS",
				String.class);
		//System.out.println(response);

		String type = "";
		String nameDir = "";
		String dateValidity = "";
		InstrumentMetadataList instrMetadataList = new InstrumentMetadataList();
		instrMetadataList.setInstrumentName(instrument);
		// InstrumentMetadata instrMetadata = new InstrumentMetadata();
		// Create a JSONObject by the response
		try {
			JSONObject obj = new JSONObject(response);
			// Create a JSONArray that rapresent the "FileStatus" tag nested
			// into the JSON
			JSONArray arr = obj.getJSONObject("FileStatuses").getJSONArray(
					"FileStatus");

			for (int i = 0; i < arr.length(); i++) {
				type = arr.getJSONObject(i).getString("type");
				nameDir = arr.getJSONObject(i).getString("pathSuffix");
				dateValidity = arr.getJSONObject(i).getString(
						"modificationTime");

				if (type != null && "DIRECTORY".equals(type)) {
					String resp = restTemplate.getForObject(url + "/" + nameDir
							+ "/metadata/metadata.json" + "?op=OPEN",
							String.class);
					//System.out.println(resp);
					InstrumentMetadata instrMetadata = new InstrumentMetadata();
					instrMetadata.setValidityDate(dateValidity);
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
							false);
					//String metadatatoclear = null;
					try {
						Object json = mapper.readValue(resp, Object.class);
						//issue #1 json format output
						//if (json != null){
						//	metadatatoclear = json.toString();
						//	metadatatoclear = metadatatoclear.replaceFirst(Pattern.quote("{"), "{\"");
						//	metadatatoclear = metadatatoclear.replace("=", "\":\"");
						//	metadatatoclear = metadatatoclear.replace(", ", "\",\"");							
						//	metadatatoclear = metadatatoclear.replaceAll("\"", "\u201d");
						//}
						instrMetadata.setMetadata(json.toString());
						//instrMetadata.setMetadata(metadatatoclear);
					} catch (Exception e) {
						e.printStackTrace();
					}
					instrMetadataList.addInstrumentsMetadataItem(instrMetadata);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<InstrumentMetadataList>(instrMetadataList,
				HttpStatus.OK);
	}

	public ResponseEntity<Parameters> observatoriesObservatoryInstrumentsInstrumentParametersGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument) {

		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		String params = "{SensorID=" + instrument + ",EGIMNode=" + observatory
				+ "}";
		String response = restTemplate
				.getForObject(
						urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersGet,
						String.class, params);

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
			// For each value of the list create an Observatory object to add to
			// the Observatoriers object
			for (String s : set) {
				String uom = getUom(observatory, instrument, s);
				Parameter parameter = new Parameter();
				parameter.setName(s);
				parameter.setUom(uom);
				parameters.addParametersItem(parameter);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Parameters>(parameters, HttpStatus.OK);
	}

	// @SuppressWarnings("unchecked")
	public ResponseEntity<Observations> observatoriesObservatoryInstrumentsInstrumentParametersParameterGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "EGIM parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate
			){
			//,
			//@ApiParam(value = "The last x-measurements", required = false) @RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit) {
			//Integer limit){
		if (endDate != null) {
			if (startDate.equals(endDate)) {
				LocalDateTime endDateToIncrement = endDate.toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDateTime();
				endDateToIncrement = endDateToIncrement.plusDays(1)
						.minusMinutes(1);
				endDate = Date.from(endDateToIncrement.atZone(
						ZoneId.systemDefault()).toInstant());
			}
		}
		String uom = getUom(observatory, instrument, parameter);
		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		// Create a map of params to pass add as placeholder after parameter
		// value in the following compositeUrl
		Map<String, String> params = new HashMap<String, String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID", instrument);

		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate)
				+ "&m=sum:"
				+ parameter
				+ "{params}"
				+ "&end="
				+ EmsodevUtility.getDateAsStringTimestampFormat(endDate);
		// The response as string of the urlToCall - This Url do not allows
		// blanck spaces beetwen the params, for this reason is trimmed
		Object response = restTemplate.getForObject(compositeUrl, Object.class,
				params.toString().replace(" ", ""));
		// Declare the final response object outside the loop
		Observations observations = new Observations();

		try {
			// Properties to pass to the outpout object
			String egimNodeName = "";
			String sensorIdName = "";
			String metricName = "";
			// Property that contain the readed timeseries without brackets and
			// quotes and that will be used to create an array of timeseries
			String jobjectDpsCleaned = null;
			// Gson google object is used insteaf og JSONObject becouse let the
			// result sorted by timestamp
			Gson gson = new Gson();
			// Create a jelement that contains the response
			JsonElement jelement = gson.fromJson(response.toString(),
					JsonElement.class);
			// The response is an array. Create an jsonArray that contain the
			// response
			JsonArray jsonarray = jelement.getAsJsonArray();
			// Get the first and last item of the array
			JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
			// The value of metric attribute
			JsonObject jobject = jarrayItem.getAsJsonObject();
			metricName = jobject.get("metric").getAsString();
			// Get the an jsonObject with that rapresent the "tags" branche
			jobject = jobject.getAsJsonObject("tags");
			// Get the value of attribute of SensorID and EGIMNode of the "tags"
			// branche
			sensorIdName = jobject.get("SensorID").getAsString();
			egimNodeName = jobject.get("EGIMNode").getAsString();
			// set the instrument name with the previous extract value
			Instrument inst = new Instrument();
			HashMap<String, String> sensorDetails = getInstrumentDetails(
					observatory, instrument);
			String sensorLongName = sensorDetails.get("sensorLongName");
			String sensorType = sensorDetails.get("sensorType");
			String sn = sensorDetails.get("sn");
			inst.setName(sensorIdName);
			inst.setSensorLongName(sensorLongName);
			inst.setSensorType(sensorType);
			inst.setSn(sn);
			// set the parameter name with with the previous extract value
			Parameter par = new Parameter();
			par.setName(metricName);
			par.setUom(uom);
			// set the observatory name with with the previous extract value
			Observatory observ = new Observatory();
			observ.setName(egimNodeName);

			// Get the an jsonObject with that rapresent the "dps" branche
			JsonObject jobjectDps = jarrayItem.getAsJsonObject();
			jobjectDps = jobjectDps.getAsJsonObject("dps");
			jobjectDpsCleaned = jobjectDps.toString().replace("\"", "")
					.replace("{", "").replace("}", "");
			// Array of timeseries key: timestamp, value: value
			String[] arrayDps = jobjectDpsCleaned.split(",");
			// Declare a List of Observation
			ArrayList<Observation> observationsList = new ArrayList<Observation>();

			int startIndex = 0;
//			if (limit != null && limit > 0) {
//				if (limit < arrayDps.length) {
//					startIndex = arrayDps.length - limit;
//				}
//			}

			// For each array item extract the key and value to set to a new
			// Observation object in the loop
			for (int index = startIndex, n = arrayDps.length; index < n; index++) {
				String item = arrayDps[index];
				Observation obs = new Observation();
				obs.setPhenomenonTime(Long.valueOf(item.substring(0,
						item.indexOf(":"))));
				obs.setValue(Double.valueOf(item.substring(
						(item.indexOf(":") + 1), item.length())));
				observationsList.add(obs);
			}
			// Compose the final bean to return
			observations.setObservations(observationsList);
			observations.setParameter(par);
			observations.setInstrument(inst);
			observations.setObservatory(observ);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return new ResponseEntity<Observations>(observations, HttpStatus.OK);
	}

	
	public ResponseEntity<Observations> observatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "EGIM parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The last x-measurements", required = true) @RequestParam(value = "limit", required = true) Integer limitParam) {
			//@ApiParam(value = "The last x-measurements", required = true) @PathVariable(value = "limit") Integer limitParam) {
		     
 
		Date startEndDate = getLastDateFromDps(observatory, instrument, parameter);
		
		Date startDate = startEndDate;
		Date endDate = startEndDate;
		
		if (endDate != null) {
			if (startDate.equals(endDate)) {
				LocalDateTime endDateToIncrement = endDate.toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDateTime();
				endDateToIncrement = endDateToIncrement.plusDays(1)
						.minusMinutes(1);
				endDate = Date.from(endDateToIncrement.atZone(
						ZoneId.systemDefault()).toInstant());
			}
		}
		
		String uom = getUom(observatory, instrument, parameter);
		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		// Create a map of params to pass add as placeholder after parameter
		// value in the following compositeUrl
		Map<String, String> params = new HashMap<String, String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID", instrument);

		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate)
				+ "&m=sum:"
				+ parameter
				+ "{params}"
				+ "&end="
				+ EmsodevUtility.getDateAsStringTimestampFormat(endDate);
		// The response as string of the urlToCall - This Url do not allows
		// blanck spaces beetwen the params, for this reason is trimmed
		Object response = restTemplate.getForObject(compositeUrl, Object.class,
				params.toString().replace(" ", ""));
		// Declare the final response object outside the loop
		Observations observations = new Observations();

		try {
			// Properties to pass to the outpout object
			String egimNodeName = "";
			String sensorIdName = "";
			String metricName = "";
			// Property that contain the readed timeseries without brackets and
			// quotes and that will be used to create an array of timeseries
			String jobjectDpsCleaned = null;
			// Gson google object is used insteaf og JSONObject becouse let the
			// result sorted by timestamp
			Gson gson = new Gson();
			// Create a jelement that contains the response
			JsonElement jelement = gson.fromJson(response.toString(),
					JsonElement.class);
			// The response is an array. Create an jsonArray that contain the
			// response
			JsonArray jsonarray = jelement.getAsJsonArray();
			// Get the first and last item of the array
			JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
			// The value of metric attribute
			JsonObject jobject = jarrayItem.getAsJsonObject();
			metricName = jobject.get("metric").getAsString();
			// Get the an jsonObject with that rapresent the "tags" branche
			jobject = jobject.getAsJsonObject("tags");
			// Get the value of attribute of SensorID and EGIMNode of the "tags"
			// branche
			sensorIdName = jobject.get("SensorID").getAsString();
			egimNodeName = jobject.get("EGIMNode").getAsString();
			// set the instrument name with the previous extract value
			Instrument inst = new Instrument();
			HashMap<String, String> sensorDetails = getInstrumentDetails(
					observatory, instrument);
			String sensorLongName = sensorDetails.get("sensorLongName");
			String sensorType = sensorDetails.get("sensorType");
			String sn = sensorDetails.get("sn");
			inst.setName(sensorIdName);
			inst.setSensorLongName(sensorLongName);
			inst.setSensorType(sensorType);
			inst.setSn(sn);
			// set the parameter name with with the previous extract value
			Parameter par = new Parameter();
			par.setName(metricName);
			par.setUom(uom);
			// set the observatory name with with the previous extract value
			Observatory observ = new Observatory();
			observ.setName(egimNodeName);

			// Get the an jsonObject with that rapresent the "dps" branche
			JsonObject jobjectDps = jarrayItem.getAsJsonObject();
			jobjectDps = jobjectDps.getAsJsonObject("dps");
			jobjectDpsCleaned = jobjectDps.toString().replace("\"", "")
					.replace("{", "").replace("}", "");
			// Array of timeseries key: timestamp, value: value
			String[] arrayDps = jobjectDpsCleaned.split(",");
			// Declare a List of Observation
			ArrayList<Observation> observationsList = new ArrayList<Observation>();

			int startIndex = 0;
			if (limitParam != null && limitParam > 0) {
				if (limitParam < arrayDps.length) {
					startIndex = arrayDps.length - limitParam;
				}
			}

			// For each array item extract the key and value to set to a new
			// Observation object in the loop
			for (int index = startIndex, n = arrayDps.length; index < n; index++) {
				String item = arrayDps[index];
				Observation obs = new Observation();
				obs.setPhenomenonTime(Long.valueOf(item.substring(0,
						item.indexOf(":"))));
				obs.setValue(Double.valueOf(item.substring(
						(item.indexOf(":") + 1), item.length())));
				observationsList.add(obs);
			}
			// Compose the final bean to return
			observations.setObservations(observationsList);
			observations.setParameter(par);
			observations.setInstrument(inst);
			observations.setObservatory(observ);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return new ResponseEntity<Observations>(observations, HttpStatus.OK);		
		
		
		
		
		
//		
//		ResponseEntity<Observations> lastXTimeSerie = observatoriesObservatoryInstrumentsInstrumentParametersParameterGet(
//				observatory, instrument, parameter, startEndDate, startEndDate
//				//,				limit
//				);
//        limit = 0;
//		return lastXTimeSerie;
	}

	
	
//	public ResponseEntity<Observations> observatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet(
//			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
//			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument,
//			@ApiParam(value = "EGIM parameter name.", required = true) @PathVariable("parameter") String parameter,
//			@ApiParam(value = "The last x-measurements", required = true) @PathVariable(value = "limit") Integer limitParam) {
//
//		String sensorTemplateId = null;
//		//set the general variable limit to extract only the x measuraments from the api time series call.
//		limit = limitParam;
//		sensorTemplateId = getSensorTemplateID(observatory, instrument);
//		// Create the restTemplate object with or without proxy
//		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
//				username, password, proxyUrl, proxyPort);
//		// Create a map of params to pass add as placeholder after parameter
//		// value in the following compositeUrl
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("EGIMNode", observatory);
//		params.put("SensorID", instrument);
//		params.put("SensorTemplateID", sensorTemplateId);
//
//		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet
//				+ parameter + "{params}" + "&back_scan=2400&resolve=true";
//
//		// The response as string of the urlToCall - This Url do not allows
//		// blanck spaces beetwen the params, for this reason is trimmed
//		Object response = restTemplate.getForObject(compositeUrl, Object.class,
//				params.toString().replace(" ", ""));
//
//		// Gson google object is used insteaf og JSONObject becouse let the
//		// result sorted by timestamp
//		Gson gson = new Gson();
//		// Create a jelement that contains the response
//		JsonElement jelement = gson.fromJson(response.toString(),
//				JsonElement.class);
//		// The response is an array. Create an jsonArray that contain the
//		// response
//		JsonArray jsonarray = jelement.getAsJsonArray();
//		// Get the first and last item of the array
//		JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
//		// The value of metric attribute
//		JsonObject jobject = jarrayItem.getAsJsonObject();
//		String lastTimestamp = jobject.get("timestamp").getAsString();
//		// To get the correct xLastTimeSeries the last date is setted to the
//		// midnight
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(Long.valueOf(lastTimestamp));
//		calendar.set(Calendar.HOUR, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		Date startEndDate = calendar.getTime();
//
//		ResponseEntity<Observations> lastXTimeSerie = observatoriesObservatoryInstrumentsInstrumentParametersParameterGet(
//				observatory, instrument, parameter, startEndDate, startEndDate
//				//,				limit
//				);
//        limit = 0;
//		return lastXTimeSerie;
//	}

	// Method to retrieve the uom (unit of measuament) for a given parameter
	// The uom value is retrieved from a specific file into the HDFS of the DMP
	public String getUom(String observatory, String instrument, String parameter) {

		String uom = null;
		String type = "";
		String nameDir = "";
		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		String url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet
				+ observatory + "/" + instrument;
		;
		String response = restTemplate.getForObject(url + "?op=LISTSTATUS",
				String.class);
		// Create a JSONObject by the response
		try {
			JSONObject obj = new JSONObject(response);
			// Create a JSONArray that rapresent the "FileStatus" tag nested
			// into the JSON
			JSONArray arr = obj.getJSONObject("FileStatuses").getJSONArray(
					"FileStatus");
			// get the type and the pathSuffix to determinate if into the gient
			// path of HDFS are present
			// one ore more directory that contains the File with the uom
			// information.
			// only the last value the newest is setted to the uom parameter.
			for (int i = 0; i < arr.length(); i++) {
				type = arr.getJSONObject(i).getString("type");
				nameDir = arr.getJSONObject(i).getString("pathSuffix");

				if (type != null && "DIRECTORY".equals(type)) {
					String resp = restTemplate.getForObject(url + "/" + nameDir
							+ "/ODVHeader.txt" + "?op=OPEN", String.class);
					if (resp.contains(parameter.toUpperCase())) {
						uom = StringUtils.substringBetween(resp,
								parameter.toUpperCase() + " [", "]");
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// The expecial characters are converted into a user readeable format
		// for exampe the character "\\u00baC" bevame "°C" celsious degree
		uom = StringEscapeUtils.unescapeJava(uom);
		return uom;
	}

	// Method to retrieve dhe SonsorTemplateID value
	public String getSensorTemplateID(String observatory, String instrument) {

		String sensorTemplateID = "";
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		String params = "{SensorID=" + instrument + ",EGIMNode=" + observatory
				+ "}";
		String response = restTemplate
				.getForObject(
						urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersGet,
						String.class, params);
		try {
			// Create a JSONObject by the response
			JSONObject obj = new JSONObject(response);
			sensorTemplateID = obj.getJSONArray("results").getJSONObject(0)
					.getJSONObject("tags").getString("SensorTemplateID");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sensorTemplateID;
	}

	// Method to retrieve some additional values related to the Instrument.
	// This values are stored into HDFS into a special json file.
	public HashMap<String, String> getInstrumentDetails(String observatory,
			String instrument) {

		HashMap<String, String> instrumentDetails = new HashMap<String, String>();
		String sensorLongName = "";
		String sensorType = "";
		String sn = "";
		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		String url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet
				+ observatory + "/" + instrument + "/" + "sensor_" + instrument
				+ ".json";
		String response = restTemplate.getForObject(url + "?op=OPEN",
				String.class);
		// Create a JSONObject by the response
		try {
			JSONObject obj = new JSONObject(response);
			sensorLongName = obj.getString("sensorLongName");
			sensorType = obj.getString("sensorType");
			sn = obj.getString("sn");
			instrumentDetails.put("sensorLongName", sensorLongName);
			instrumentDetails.put("sensorType", sensorType);
			instrumentDetails.put("sn", sn);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return instrumentDetails;
	}
   //Get the last date of stored observation for the input parameters by openTsdb last API with back_scan
	public Date getLastDate(String observatory, String instrument, String parameter) {
		
		String sensorTemplateId = null;
		//set the general variable limit to extract only the x measuraments from the api time series call.
		sensorTemplateId = getSensorTemplateID(observatory, instrument);
		// Create the restTemplate object with or without proxy
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);
		// Create a map of params to pass add as placeholder after parameter
		// value in the following compositeUrl
		Map<String, String> params = new HashMap<String, String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID", instrument);
		params.put("SensorTemplateID", sensorTemplateId);

		String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet
				+ parameter + "{params}" + "&back_scan=2400&resolve=true";

		// The response as string of the urlToCall - This Url do not allows
		// blanck spaces beetwen the params, for this reason is trimmed
		Object response = restTemplate.getForObject(compositeUrl, Object.class,
				params.toString().replace(" ", ""));

		// Gson google object is used insteaf og JSONObject becouse let the
		// result sorted by timestamp
		Gson gsonFromResponse = new Gson();
		// Create a jelement that contains the response
		JsonElement jelementForTimestamp = gsonFromResponse.fromJson(response.toString(),
				JsonElement.class);
		// The response is an array. Create an jsonArray that contain the
		// response
		JsonArray jsonarrayForTimestamp = jelementForTimestamp.getAsJsonArray();
		// Get the first and last item of the array
		JsonObject jarrayItemForTimestamp = jsonarrayForTimestamp.get(0).getAsJsonObject();
		// The value of metric attribute
		JsonObject jobjectForTimestamp = jarrayItemForTimestamp.getAsJsonObject();
		String lastTimestamp = jobjectForTimestamp.get("timestamp").getAsString();
		// To get the correct xLastTimeSeries the last date is setted to the
		// midnight
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(lastTimestamp));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date startEndDate = calendar.getTime();
		
		return startEndDate;
	}

//Return the last date at midnight of stored observation for the specific input parameters	from the dps observation array
public Date getLastDateFromDps(String observatory, String instrument, String parameter) {
		
	//Date startDate = new Date("01/01/2018");
	
	Calendar startDate = Calendar.getInstance();
	startDate.set(2017,Calendar.JANUARY,01);
	System.out.println("Data = " + startDate.getTime());

	// Create the restTemplate object with or without proxy
	restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
			username, password, proxyUrl, proxyPort);
	// Create a map of params to pass add as placeholder after parameter
	// value in the following compositeUrl
	Map<String, String> params = new HashMap<String, String>();
	params.put("EGIMNode", observatory);
	params.put("SensorID", instrument);

	String compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet
			+ EmsodevUtility.getDateAsStringTimestampFormat(startDate.getTime())
			+ "&m=sum:"
			+ parameter
			+ "{params}"
			+ "&end=";
	// The response as string of the urlToCall - This Url do not allows
	// blanck spaces beetwen the params, for this reason is trimmed
	Object response = restTemplate.getForObject(compositeUrl, Object.class,
			params.toString().replace(" ", ""));

	Long lastTimestamp = new Long(0);
	try {
		String jobjectDpsCleaned = null;
		// Gson google object is used insteaf og JSONObject becouse let the
		// result sorted by timestamp
		Gson gson = new Gson();
		// Create a jelement that contains the response
		JsonElement jelement = gson.fromJson(response.toString(),
				JsonElement.class);
		// The response is an array. Create an jsonArray that contain the
		// response
		JsonArray jsonarray = jelement.getAsJsonArray();
		// Get the first and last item of the array
		JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
		// The value of metric attribute
		JsonObject jobject = jarrayItem.getAsJsonObject();

		// Get the an jsonObject with that rapresent the "dps" branche
		JsonObject jobjectDps = jarrayItem.getAsJsonObject();
		jobjectDps = jobjectDps.getAsJsonObject("dps");
		jobjectDpsCleaned = jobjectDps.toString().replace("\"", "")
				.replace("{", "").replace("}", "");
		// Array of timeseries key: timestamp, value: value
		String[] arrayDps = jobjectDpsCleaned.split(",");
		// Declare a List of Observation
		int arrayLength = 0;
		int startPoint = 0;
		arrayLength = arrayDps.length;
		startPoint = arrayLength - 1; 
		
		// For each array item extract the key and value to set to a new
		// Observation object in the loop
		//for (int index = startIndex, n = arrayDps.length; index < n; index++) {
		for (int index = startPoint, n = arrayDps.length; index < n; index++) {
			String item = arrayDps[index];
			lastTimestamp = Long.valueOf(item.substring(0,item.indexOf(":")));
		}

	} catch (Exception e1) {
		e1.printStackTrace();
	}
	
		// To get the correct xLastTimeSeries the last date is setted to the
		// midnight
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(Long.valueOf(lastTimestamp*1000));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date startEndDate = calendar.getTime();
		
		return startEndDate;
	}
	
	
}

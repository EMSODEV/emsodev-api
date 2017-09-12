package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import eu.emsodev.observations.model.AcousticObservation;
import eu.emsodev.observations.model.AcousticObservationDate;
import eu.emsodev.observations.model.AcousticObservationHourMinute;
import eu.emsodev.observations.model.AcousticObservationList;
import eu.emsodev.observations.model.Instrument;
import eu.emsodev.observations.model.InstrumentMetadataList;
import eu.emsodev.observations.model.Observatory;
import eu.emsodev.observations.utilities.EmsodevUtility;

@Configuration
@PropertySource("${api.properties.home}")
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class })
public class AcousticObservationsController implements AcousiticObservationApi {

//	@Value("${emsodev.global.setting.urlToCall.observatoriesGet}")
//	private String urlToCallObservatoriesGet;
//
//	@Value("${emsodev.global.setting.urlToCall.observatoryInstrumentsGet}")
//	private String urlToCallObservatoryInstrumentsGet;

	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentGet}")
	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentGet;

//	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersGet}")
//	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersGet;
//
//	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersParameterGet}")
//	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet;
//
//	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet}")
//	private String urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterLimitGet;

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
	
	public AcousticObservationsController() {
	}

	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}



	public ResponseEntity<AcousticObservationList> acousticObservationListGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "EGIM observatory instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "Date of Acoustic files. The date format is dd/MM/yyyy") @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date)
    {
		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		String formattedDate = "";
		if (date!=null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(date);
		}else{
			formattedDate = null;
		}
		
		AcousticObservationList acousticObservationList = new AcousticObservationList();
		
		//read instrument details to set instrument object fields
		String urlForInstrumentJson = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"
				+"acoustic_"+instrument + "/sensor_"+instrument+".json" + "?op=OPEN";
		String instrDetailResp = restTemplate.getForObject(urlForInstrumentJson,
				String.class);
		//System.out.println(resp);
		
		JSONObject objFinal;
		try {
			objFinal = new JSONObject(instrDetailResp);
			String egimNode = objFinal.getString("EGIMNode");
			String sensorId = objFinal.getString("sensorID");
			String sensorLongName = objFinal.getString("sensorLongName");
			String sn = objFinal.getString("sn");
			String sensorType = objFinal.getString("sensorType");
			
			Observatory obs = new Observatory();
			obs.setName(observatory);
			Instrument instr = new Instrument();
			instr.setName(sensorId);
			instr.setSensorLongName(sensorLongName);
			instr.setSensorType(sensorType);
			instr.setSn(sn);
			
			acousticObservationList.setInstrument(instr);
			acousticObservationList.setObservatory(obs);
		
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"+"acoustic_"+instrument;
		String response = restTemplate.getForObject(url + "?op=LISTSTATUS",
				String.class);
		//System.out.println(response);

		String type = "";
		String nameDir = "";
		String nameDirSave = "";
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
				nameDirSave = arr.getJSONObject(i).getString("pathSuffix");
				
				
				if (type != null && "DIRECTORY".equals(type)) {
					if (formattedDate == null){
						String urlForInstrumetDateList = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"
					                                     +"acoustic_"+instrument + "/" + nameDir +"/" + "insertAcoustic"; 
						String resp = restTemplate.getForObject(urlForInstrumetDateList + "?op=LISTSTATUS",		String.class);
						
						JSONObject objDateList = new JSONObject(resp);
						// Create a JSONArray that rapresent the "FileStatus" tag nested
						// into the JSON
						JSONArray arrDateList = objDateList.getJSONObject("FileStatuses").getJSONArray(
								"FileStatus");
						
						for (int y = 0; y < arrDateList.length(); y++) {
							type = arrDateList.getJSONObject(i).getString("type");
							nameDir = arrDateList.getJSONObject(y).getString("pathSuffix");
							
							if (type!=null && type.equals("DIRECTORY")){
								AcousticObservationDate acousticObservationDate = new AcousticObservationDate();
								acousticObservationDate.setAcousticObservationDate(nameDir);
								acousticObservationList.addAcousticObservationItem(acousticObservationDate);
								System.out.println(acousticObservationDate);
								
								
								
								String urlHourMinute = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"+"acoustic_"+instrument
										+ "/" + nameDirSave+"/" + "insertAcoustic"+ "/" + nameDir;
								String responseHourMinute = restTemplate.getForObject(urlHourMinute + "?op=LISTSTATUS",
										String.class);
								
								JSONObject objHourMinuteList = new JSONObject(responseHourMinute);
								// Create a JSONArray that rapresent the "FileStatus" tag nested
								// into the JSON
								JSONArray arrHourMinuteList = objHourMinuteList.getJSONObject("FileStatuses").getJSONArray(
										"FileStatus");
								
								for (int z = 0; z < arrHourMinuteList.length(); z++) {
									AcousticObservationHourMinute acousticObservationHourMinute = new AcousticObservationHourMinute();
									acousticObservationHourMinute.setAcousticObservationHourMinute( arrHourMinuteList.getJSONObject(z).getString("pathSuffix"));
									acousticObservationDate.addObservationsHourMinuteItem(acousticObservationHourMinute);
								}
								//QUA DENTRO CICLARE PER OGNI DATA E POPOLARE LA LISTA ORE E MINUTI E ADD SULLA LISTA DATE
								
							  }
						}
					}else{
						AcousticObservationDate acousticObservationDate = new AcousticObservationDate();
						acousticObservationDate.setAcousticObservationDate(formattedDate);
						acousticObservationList.addAcousticObservationItem(acousticObservationDate);
						//System.out.println(acousticObservationDate);
						
						
						
						String urlHourMinute = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"+"acoustic_"+instrument
								+ "/" + nameDirSave+"/" + "insertAcoustic"+ "/" + formattedDate;
						String responseHourMinute = restTemplate.getForObject(urlHourMinute + "?op=LISTSTATUS",
								String.class);
						
						JSONObject objHourMinuteList = new JSONObject(responseHourMinute);
						// Create a JSONArray that rapresent the "FileStatus" tag nested
						// into the JSON
						JSONArray arrHourMinuteList = objHourMinuteList.getJSONObject("FileStatuses").getJSONArray(
								"FileStatus");
						
						for (int z = 0; z < arrHourMinuteList.length(); z++) {
							AcousticObservationHourMinute acousticObservationHourMinute = new AcousticObservationHourMinute();
							acousticObservationHourMinute.setAcousticObservationHourMinute( arrHourMinuteList.getJSONObject(z).getString("pathSuffix"));
							acousticObservationDate.addObservationsHourMinuteItem(acousticObservationHourMinute);
						}
					}
		
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<AcousticObservationList>(acousticObservationList,
				HttpStatus.OK);
	}
	
	
	public ResponseEntity<String> acousticObservationFileGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "EGIM observatory instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "Date of Acoustic file. The date format is dd/MM/yyyy", required = true) @RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date,
			@ApiParam(value = "Hour and Minute of an Acoustic file. The Hour Minute format is HHMM", required = true) @RequestParam(value = "hourMinute", required = true) String hourMinute)
    {
		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		String formattedDate = "";
		if (date!=null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(date);
		}else{
			formattedDate = null;
		}
		
		AcousticObservation acousticObservation = new AcousticObservation();
		
		String url = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"+"acoustic_"+instrument;
		String response = restTemplate.getForObject(url + "?op=LISTSTATUS",
				String.class);
		//System.out.println(response);

		String type = "";
		String nameDir = "";
		String responseAcousticFile = "";
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
				
				if (type != null && "DIRECTORY".equals(type)) {
						
						String urlHourMinute = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"+"acoustic_"+instrument
								+ "/" + nameDir+"/" + "insertAcoustic"+ "/" + formattedDate + "/" + hourMinute;
						String responseHourMinute = restTemplate.getForObject(urlHourMinute + "?op=LISTSTATUS",
								String.class);
						
						JSONObject objHourMinuteList = new JSONObject(responseHourMinute);
						// Create a JSONArray that rapresent the "FileStatus" tag nested
						// into the JSON
						JSONArray arrHourMinuteList = objHourMinuteList.getJSONObject("FileStatuses").getJSONArray(
								"FileStatus");
						
						for (int z = 0; z < arrHourMinuteList.length(); z++) {
							
							String type2 = arrHourMinuteList.getJSONObject(z).getString("type");
							String name  = arrHourMinuteList.getJSONObject(z).getString("pathSuffix");
//							
							if (type2!=null && type2.equals("FILE")){
							
								String urlAcousticFIle = urlToCallObservatoriesObservatoryInstrumentsInstrumentGet + observatory +"/"+"acoustic_"+instrument
										+ "/" + nameDir+"/" + "insertAcoustic"+ "/" + formattedDate + "/" + hourMinute + "/" + name;
								 responseAcousticFile = restTemplate.getForObject(urlAcousticFIle + "?op=OPEN",
										String.class);
							
							    acousticObservation.setObservationBody(responseAcousticFile); 
							}    
						}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(responseAcousticFile,
				HttpStatus.OK);
	}

}

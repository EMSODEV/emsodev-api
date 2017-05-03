package eu.emsodev.observations.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.swagger.annotations.*;
import io.swagger.models.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eu.emsodev.observations.utilities.EmsodevUtility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import ucar.ma2.*; 
import ucar.nc2.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Controller
public class NetCDFgetFilesApiController implements NetCDFgetFilesApi {
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
	
	protected RestTemplate restTemplate;
				
	//public void netcdfFilesGet(@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory
    public ResponseEntity <String> netcdfFilesGet(@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

			,
			@ApiParam(value = "The start time for the query. The formast must be dd/MM/yyyy", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

			,
			@ApiParam(value = "The end time for the query. The formast must be dd/MM/yyyy. It is required") @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate
						,
			HttpServletResponse response
			
			)  {
        // do some magic!
    	//Variables definitions
    	String location = "Netcdf_File.nc";
    	NetcdfFileWriter writer = null;
    	NetcdfFileWriter ncfile = null;
    	Dimension lonDim = null;
    	Dimension latDim = null;
    	List<Dimension> dims = null;
    	Variable t = null;
    	Array data = null;
    	Dimension svar_len = null;
    	InputStream is = null;
    	String absolutePath= "ok";
    	JSONObject obj = null;
		JSONObject obj_2 = null;
		JSONObject result = null;
		JSONObject result_1 = null;
		String Data ="";
		String Data_2 ="";
		String response_1 ="";
		String response_2 ="";
		String response_3 = " ";
		String Url_3 = "";
		String response_4 = "";
		String response_5 = "";
		String strDate ="";
		String strDate_1 ="";
		String type = "";
		String nameDir ="";
		String dateValidity ="";
		String resp ="";
		String compositeUrl= "";
		String result_data_2= null;
		String[] element_1 = null;
		char lettera = ' ';
		int n=0;
		String header="{info: [ ";
		String ultimo_char="]}";
		String selection ="";
		JSONObject obj_6=null;
		Object respons;
		String egimNodeName = "";
		String sensorIdName = "";
		String metricName = "" ;
		String jobjectDpsCleaned = null;
		Gson gson=null;
		JsonObject  jobjectDps = null;
		String[] arrayDps = null;
		String test="pippo";
		Variable tx = null;
		Variable ts = null;
		Variable ta = null;
		ArrayList<Dimension> dimss = null;
		DataType app = null;
		int occurance=0;
		
		//la struttura del programma è questa: 
		//crei il file netcdf; ricevi le info e nei cicli for sulle stringhe del JSON object le scrivi
		try {
			writer= NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, location, null);
			//Add dimension
			svar_len = writer.addDimension(null, "svar_len", 80);
			writer.addVariable(null, "svar", DataType.CHAR, "svar_len");
			//Add Group Attributes
			writer.addGroupAttribute(null, new Attribute("yo", "face"));
		    writer.addGroupAttribute(null, new Attribute("versionD", 1.2));
		    writer.addGroupAttribute(null, new Attribute("versionF", (float) 1.2));
		    writer.addGroupAttribute(null, new Attribute("versionI", 1));
		    writer.addGroupAttribute(null, new Attribute("versionS", (short) 2));
		    writer.addGroupAttribute(null, new Attribute("versionB", (byte) 3));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//I create rest_template_object
    	restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
    	
    	String egimNode = "{EGIMNode=*}";
    	
    	// The response_rest as string of the urlToCall
    	String response_rest = restTemplate.getForObject(urlToCallObservatoriesGet, String.class,
    					egimNode);
    	
    	//I receive the information (Node name) by using JSON object from rest responce with for cycle. The received string will have field separated by ,  
    	try {
			 obj = new JSONObject(response_rest);	
			 JSONArray arr = obj.getJSONArray("results"); 
			 for (int i = 0; i < arr.length(); i++) {
				 	//ata_1=" "+ obj.getString("metric")+ ",";
					result = arr.getJSONObject(i).getJSONObject("tags");
					Data = Data+ " "+ arr.getJSONObject(i).getString("metric")+ ",";
					// add the EGIMnode value to the list				
					Data=Data+ " "+ result.getString("EGIMNode")+",";
					Data=Data+ " "+ result.getString("SensorID")+",";			
			 		}
			
    	} catch (JSONException e) {
					// TODO Auto-generate catch block
					e.printStackTrace();
				}
    	
    	restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
    	String URL= urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersGet;
		 //String URL="http://dmpnode5.emsodev.eu:9991/api/search/lookup?limit=0&m=*{params}"; 
		String paramss = "{SensorID="+instrument+",EGIMNode="+observatory+"}";
		 //response_1 = retTemplate.getForObject(URL, String.class);
		response_1 = restTemplate.getForObject(URL, String.class, paramss);
		try {
			obj = new JSONObject(response_1);
			JSONArray arr_1 = obj.getJSONArray("results");
			  for (int i = 0; i < arr_1.length(); i++) {
				  Data_2 = Data_2+ " "+ arr_1.getJSONObject(i).getString("metric")+ ",";
			  }
			  Data_2=Data_2.substring(1, Data_2.length()-1); //tolgo il primo e l'ultimo carattere
			  //To separe string's fields uncomment this 
			  /*
			  String [] splits = Data_2.split(",");
				for(String s:splits){
				//Do some operations on NETCDF File	
				}
				*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		//I receive the information (metadata) by using API
		Url_3=urlToCallObservatoriesObservatoryInstrumentsInstrumentGet +observatory +"/" +instrument ;
		  //Url_3 = "http://dmpnode1.emsodev.eu:50070/webhdfs/v1/emsodev/" +observatory +"/" + instrument ;
		 response_5= restTemplate.getForObject(Url_3 + "?op=LISTSTATUS", String.class);
		 try {
			obj_2 = new JSONObject(response_5);
			JSONArray arr_2 = obj_2.getJSONObject("FileStatuses").getJSONArray("FileStatus");
			for (int i = 0; i < arr_2.length(); i++) {
				type = arr_2.getJSONObject(i).getString("type");
				nameDir = arr_2.getJSONObject(i).getString("pathSuffix");
				dateValidity = arr_2.getJSONObject(i).getString("modificationTime");
				
				if (type != null && "DIRECTORY".equals(type)){
					//resp is the string you have to considerer to obtain the metadata for observatory
					 resp= restTemplate.getForObject(Url_3 + "/"+nameDir + "/metadata/metadata.json"+"?op=OPEN", String.class);
					//System.out.println(resp);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //To select the information for metadata use this code
		 
		 resp=header+resp+ultimo_char; // it becomes a JSON object
		 try {
			obj_6 = new JSONObject(resp);
			JSONArray arr_2 = obj_6.getJSONArray("info");
			for (int i = 0; i < arr_2.length(); i++) {
				selection = arr_2.getJSONObject(i).getString("EGIMLocation"); //se vuoi selezionare un campo: EgimLocation nel nostro caso
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	////Information for Time series for instrument
		// String Data_8="sea_water_temperature, pitch, Bin20_error_sea_water_speed, heading_of_device, roll, Bin20_N_S_sea_water_speed, Bin20_E_W_sea_water_speed, Bin20_vert_sea_water_speed, Bin3_E_W_sea_water_speed, Bin2_N_S_sea_water_speed, Bin3_error_sea_water_speed";
    	
		 for (String element:Data_2.split(",\\s")){
			  
			//////element_1=element.split(","); 
		  

		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		
		
		 compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate) +"&m=sum:" 
				+ element+"{params}"
				+"&end="
				+EmsodevUtility.getDateAsStringTimestampFormat(endDate);
		//response_3 = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));
		respons = restTemplate.getForObject(compositeUrl, Object.class, params.toString().replace(" ", ""));
		gson = new Gson();
		JsonElement jelement = gson.fromJson (respons.toString(), JsonElement.class);
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
		/*Uncomment this for writing NETCDF compliant file 
		 Per scrivere la variabile ho bisogno del nome e da cosa dipende (da quali dimensioni dipende). 
		 	dimss=new ArrayList<Dimension>();
		 	if(jobject.get("T").getAsString() == 1){
	    		dims.add(T);
				}
			if(jobject.get("D").getAsString() == 1){
		    	dims.add(D);
				}
			if(jobject.get("LA").getAsString() == 1){
		    	dims.add(LA);
				}
			if(jobject.get("LO").getAsString() == 1){
		    	dims.add(LO);
				}
			// Bisogna definire il tipo di dato	
			if (jobject.get("d").getAsString()== 1){
				 app=DataType.DOUBLE;
				}
			if (jobject.get("b").getAsString()== 1){
				 app=DataType.BYTE;
				}
			if (jobject.get("c").getAsString()== 1){
				 app=DataType.CHAR;
				}
			if (jobject.get("f").getAsString()== 1){
				 app=DataType.FLOAT;
				}
			if (jobject.get("i").getAsString()== 1){
				 app=DataType.INT;
				}
			//Scrittura variabile data
			ts = writer.addVariable(null, metricName, app, dimss);
		    ts.addAttribute(new Attribute("standard_name", jobject.get("standard_name").getAsString()));
		    ts.addAttribute(new Attribute("units", jobject.get("units").getAsString()));
		    ts.addAttribute(new Attribute("_FillValue", Float.parseFloat(jobject.get("_FillValue").getAsString())));
		    ts.addAttribute(new Attribute("long_name", jobject.get("long_name").getAsString()));
		    ts.addAttribute(new Attribute("QC_indicator", jobject.get("QC_indicator").getAsString()));
		    ts.addAttribute(new Attribute("valid_min", Double.parseDouble(jobject.get("valid_min").getAsString())));
		    ts.addAttribute(new Attribute("valid_max", Double.parseDouble(jobject.get("valid_max").getAsString()));
		    ts.addAttribute(new Attribute("sensor_depth", jobject.get("sensor_depth").getAsString()));
		    ts.addAttribute(new Attribute("sensor_name", jobject.get("sensor_name").getAsString()));
		    if(jobject.get("ancillary_variables_flag").getAsString()== 1){
		    	ts.addAttribute(new Attribute("ancillary_variables", jobject.get("ancillary_variables").getAsString()));
		    	//Aggiungo l'eventuale variabile ancillary
		    	 ta= writer.addVariable(null, "byte "+metricName, app, dimss);
		    	 ta.addAttribute(new Attribute("long_name", jobject.get("long_name").getAsString()));
		    	 ta.addAttribute(new Attribute("valid_min", Double.parseDouble(jobject.get("valid_min").getAsString())));
		    	 ta.addAttribute(new Attribute("valid_max", Double.parseDouble(jobject.get("valid_max").getAsString())));
		    	 ta.addAttribute(new Attribute("flag_values",  jobject.get("flag_values").getAsString()));
		    	 ta.addAttribute(new Attribute("flag_meanings", jobject.get("flag_meanings").getAsString()));
		         ta.addAttribute(new Attribute("conventions", jobject.get("conventions").getAsString()));
		    }
		    if(jobject.get("comment_flag").getAsString()== 1){
		    ts.addAttribute(new Attribute("comment", jobject.get("comment").getAsString()));
			}
		fine*/
		jobjectDps = jarrayItem.getAsJsonObject();
		jobjectDps = jobjectDps.getAsJsonObject("dps");
		jobjectDpsCleaned = jobjectDps.toString().replace("\"", "").replace("{", "").replace("}", "");
		//Vedo la lunghezza della stringa dei valori
		for( int i=0; i<jobjectDpsCleaned.length(); i++ ) {
		    if( jobjectDpsCleaned.charAt(i) == ',' ) {
		    	occurance++;
		    } 
		}
		writer.addGroupAttribute(null, new Attribute("lunghezza",(int)occurance ));
		//writer.addGroupAttribute(null, new Attribute("lunghezza",(int)jobjectDpsCleaned.length()));
		//arrayDps= jobjectDpsCleaned.split(",");
		 } 
		//} 
    	//qui poi per riordinare il file farai come sopra
		//try {
			//obj_7 = new JSONObject(response_3);
			//JSONArray arr_7 = ecc.... e poi il for. 
		
		 /* Uncomment this for NETCDF Compliant file
		writer.addGroupAttribute(null, new Attribute("site_code", "face"));
		writer.addGroupAttribute(null, new Attribute("platform_code", datas.time()));
		writer.addGroupAttribute(null, new Attribute("title", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("data_mode", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("principal_investigator", 1));
		writer.addGroupAttribute(null, new Attribute("principal_investigator_email", (short) 2));
		writer.addGroupAttribute(null, new Attribute("institution", "face"));
		writer.addGroupAttribute(null, new Attribute("geospatial_lat_min", 1.2));
		writer.addGroupAttribute(null, new Attribute("geospatial_lat_max", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("geospatial_lon_min", 1));
		writer.addGroupAttribute(null, new Attribute("geospatial_lon_max", (short) 2));
		writer.addGroupAttribute(null, new Attribute("geospatial_vertical_min", (byte) 3));
		writer.addGroupAttribute(null, new Attribute("geospatial_vertical_max", 1.2));
		writer.addGroupAttribute(null, new Attribute("time_coverage_start", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("time_coverage_end", 1));
		writer.addGroupAttribute(null, new Attribute("data_type", (short) 2));
		writer.addGroupAttribute(null, new Attribute("area", (short) 2));
		writer.addGroupAttribute(null, new Attribute("format_version", (short) 2));
		writer.addGroupAttribute(null, new Attribute("netcdf_version", (short) 2));
		writer.addGroupAttribute(null, new Attribute("publisher_name", (short) 2));
		writer.addGroupAttribute(null, new Attribute("publisher_email", (byte) 3));
		writer.addGroupAttribute(null, new Attribute("publisher_url", "face"));
		writer.addGroupAttribute(null, new Attribute("update_interval", (float) 1.2));
		writer.addGroupAttribute(null, new Attribute("license", (float) 1.2));
	    writer.addGroupAttribute(null, new Attribute("date_created", (float) 1.2));
	    writer.addGroupAttribute(null, new Attribute("QC_indicator", (float) 1.2));
        writer.addGroupAttribute(null, new Attribute("contributor_name", (float) 1.2));
	    writer.addGroupAttribute(null, new Attribute("contributor_role", (float) 1.2));
	    writer.addGroupAttribute(null, new Attribute("contributor_email", (float) 1.2));
		 */
		 try {
			writer.create();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//test esistenza file NETCDF
		  try {
			ncfile=NetcdfFileWriter.openExisting(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 return new ResponseEntity<String>("error", HttpStatus.OK);	
			//e.printStackTrace();
		}
		////
		  try {
			  java.nio.file.Path file = Paths.get(".", "Netcdf_File.nc");
			  if (Files.exists(file))
		        {
		            response.setContentType("application/x-netcdf");
		            response.addHeader("Content-Disposition", "attachment; filename=\"Netcdf_File.nc\"");
		        }
			  Files.copy(file, response.getOutputStream());
			  response.getOutputStream().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 return new ResponseEntity<String>("error_path", HttpStatus.OK);	
				//e.printStackTrace();
			} 
		  
    	
			  
			  
			
        return new ResponseEntity<String>(jobjectDpsCleaned, HttpStatus.OK);
    }

}
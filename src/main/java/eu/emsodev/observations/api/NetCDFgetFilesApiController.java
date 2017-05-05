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
			@ApiParam(value = "EGIM parameter name.", required = true) @RequestParam("parameter") String parameter
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
		int d=0;
		Dimension T=null;
		Dimension D=null;
		Dimension LA=null;
		Dimension LO=null;
		Variable TIME=null;
		Variable DEPTH=null;
		Variable LATITUDE=null;
		Variable LONGITUDE=null;
		int volte=0;
		Variable v=null;
		int[] shape= null;
		ArrayChar.D1 ac2=null;
		ArrayDouble.D2 A = null;
		Index ima = null;
		ArrayDouble.D1 datas = null;
		String[] f=null;
		int occ_max=0;
		Dimension POLLY;
		String tempo=null;
		int indice=0;
		Variable NEW_TIME=null;
		int dipendenze=0;
		ArrayDouble.D4 datass = null;
		//la struttura del programma è questa: 
		//crei il file netcdf; ricevi le info e nei cicli for sulle stringhe del JSON object le scrivi
		try {
			writer= NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, location, null);
			//Add dimension
			//writer.addDimension(null, "svar_len", 80);
			//writer.addVariable(null, "svar", DataType.CHAR, "svar_len");
			//svar_len = writer.addDimension(null, "svar_len", 80);
			//writer.addVariable(null, "svar", DataType.CHAR, "svar_len");
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
			/*Uncomment this for NETCDF File
			 * valid_min_1=
			 * valid_max_1=
			 * QC_indicator_1=
			 * Processing_level_1
			 * uncertainty_1=
			 * comment_1=
			 * coordinate_reference_frame_1= //for DEPTH
			 * valid_min_2=
			 * valid_max_2=
			 * QC_indicator_2=
			 * Processing_level_2=
			 * uncertainty_2=
			 * comment_2=
			 * coordinate_reference_frame_2= //for LATITUDE
			 * valid_min_3=
			 * valid_max_3=
			 * QC_indicator_3=
			 * Processing_level_3=
			 * uncertainty_3=
			 * comment_3=
			 * coordinate_reference_frame_3= //for LONGITUDE
			 * valid_min_4=
			 * valid_max_4=
			 * QC_indicator_4=
			 * Processing_level_4=
			 * uncertainty_4=
			 * comment_4=
			 * area
			 * institution=
			 * geospatial_lat_min=
			 * geospatial_lat_max=
			 * geospatial_lon_min=
			 * geospatial_lon_max=
			 * geospatial_vertical_min=
			 * geospatial_vertical_max=
			 * depth=
			 */
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //Modifica del 5/5/2017
		 //Trovo la massima dimensione della variabile TIME
		 /*
		 for (String element:Data_2.split(",\\s")){
			   
			  

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
				
				
				
				
				//Prendo le serie temporali
				jobjectDps = jarrayItem.getAsJsonObject();
				jobjectDps = jobjectDps.getAsJsonObject("dps");
				jobjectDpsCleaned = jobjectDps.toString().replace("\"", "").replace("{", "").replace("}", "");
				//Vedo la lunghezza della stringa dei valori
				
				occurance=0;
				for(String rep:jobjectDpsCleaned.split(",")){
					occurance++;
				}
				
				if(occurance>occ_max){
				
				occ_max=occurance;
				//occurance=jobjectDpsCleaned.length();
		 
		 }
		 
		 
		 }
		 */
		 
		 //Fine Modifica del 5/5/2017
		 
		 
		 
    	
		 
		// for (String element:Data_2.split(",\\s")){
	   
		  

		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		
		
		 compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate) +"&m=sum:" 
				+ parameter+"{params}"
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
		//Uncomment this for NETCDF file
		/*
		principal_investigator=
		principal_investigator_email
		publisher_name=
		publisher_e_mail=
		publisher_url=
		date_created=
		update_interval=
		license=
		QC_indicator=
		contributor_name
		contributor_role
		contributor_e_mail
		
		*/
		
		
		
		//Prendo le serie temporali
		jobjectDps = jarrayItem.getAsJsonObject();
		jobjectDps = jobjectDps.getAsJsonObject("dps");
		jobjectDpsCleaned = jobjectDps.toString().replace("\"", "").replace("{", "").replace("}", "");
		//Vedo la lunghezza della stringa dei valori
		/*Commento 5/5/2017
		  
		occurance=0;
		for( int i=0; i<jobjectDpsCleaned.length(); i++ ) {
		    if( jobjectDpsCleaned.charAt(i) == ',' ) {
		    	occurance++;
		    } 
		}
		
		occurance=occurance+2;
		occurance=jobjectDpsCleaned.length();
		*/
		occurance=0;
		for(String rep:jobjectDpsCleaned.split(",")){
			occurance++;
		}
		//Fine modifica 5/5/2017
		
		//writer.addGroupAttribute(null, new Attribute("lunghezza",(int)occurance ));
		if(volte==0){
		//Scrivo le dimensioni standard for Oceansites
		T=writer.addDimension(null, "TIME", occurance); //nome della dimensione e grandezza sono dati da metodi in Acquire 
	    D=writer.addDimension(null, "DEPTH", 1);
	    LA=writer.addDimension(null, "LATITUDE", 1);
	    LO=writer.addDimension(null, "LONGITUDE", 1);
	    
		//Scrivo le GLOBAL Variables di Oceansites (these are standard variables). 
	    //TIME
	    TIME=writer.addVariable(null, "TIME", DataType.DOUBLE, "TIME"); //funziona solo con il FLOAT
	    TIME.addAttribute(new Attribute("standard_name", "time")); 
	    TIME.addAttribute(new Attribute("units", "days since 1950-01-01T00:00:00Z")); 
	    TIME.addAttribute(new Attribute("axis", "T")); 
	    TIME.addAttribute(new Attribute("long_name", "time"));
	   //Uncomment this for NETCDF file
	  //  TIME.addAttribute(new Attribute("valid_min", (double) valid_min_1)); 
	   // TIME.addAttribute(new Attribute("valid_max", (double) valid_max_1)); 
	   // TIME.addAttribute(new Attribute("QC_indicator", QC_indicator_1)); 
	   // TIME.addAttribute(new Attribute("Processing_level", Processing_level_1)); 
	   // TIME.addAttribute(new Attribute("uncertainty", uncertainty_1)); 
	   // TIME.addAttribute(new Attribute("comment", comment_1));
	    //DEPTH
	    DEPTH=writer.addVariable(null, "DEPTH", DataType.DOUBLE, "DEPTH");
	    DEPTH.addAttribute(new Attribute("standard_name", "depth")); 
	    DEPTH.addAttribute(new Attribute("units", "meters"));
	    DEPTH.addAttribute(new Attribute("positive", "down")); 
	    DEPTH.addAttribute(new Attribute("axis", "Z")); 
	    DEPTH.addAttribute(new Attribute("reference", "sea_level"));
	    
	   //Uncomment this for NETCDF file
	    //DEPTH.addAttribute(new Attribute("coordinate_reference_frame", coordinate_reference_frame_1));
	  //  DEPTH.addAttribute(new Attribute("long_name", "Depth of measurement")); 
	   // DEPTH.addAttribute(new Attribute("_FillValue", (float) -99999.0)); 
	   // DEPTH.addAttribute(new Attribute("valid_min", (double) valid_min_2)); 
		   // DEPTH.addAttribute(new Attribute("valid_max", (double) valid_max_2)); 
		   // DEPTH.addAttribute(new Attribute("QC_indicator", QC_indicator_2)); 
		   // DEPTH.addAttribute(new Attribute("Processing_level", Processing_level_2)); 
		   // DEPTH.addAttribute(new Attribute("uncertainty", uncertainty_2)); 
		   // DEPTH.addAttribute(new Attribute("comment", comment_2));
	    
	   //LATITUDE
	    LATITUDE=writer.addVariable(null, "LATITUDE", DataType.DOUBLE, "LATITUDE");
	    LATITUDE.addAttribute(new Attribute("standard_name", "latitude")); 
	    LATITUDE.addAttribute(new Attribute("units", "degrees_north"));
	    LATITUDE.addAttribute(new Attribute("axis", "Y")); 
	    LATITUDE.addAttribute(new Attribute("long_name", "Latitude of measurement"));
	    LATITUDE.addAttribute(new Attribute("reference", "WGS84"));
	    //Uncomment this for NETCDF File
	    //LATITUDE.addAttribute(new Attribute("coordinate_reference_frame", coordinate_reference_frame_2));
	    //LATITUDE.addAttribute(new Attribute("valid_min", (double) valid_min_3)); 
		   // LATITUDE.addAttribute(new Attribute("valid_max", (double) valid_max_3)); 
		   // LATITUDE.addAttribute(new Attribute("QC_indicator", QC_indicator_3)); 
		   // LATITUDE.addAttribute(new Attribute("Processing_level", Processing_level_3)); 
		   // LATITUDE.addAttribute(new Attribute("uncertainty", uncertainty_3)); 
		   // LATITUDE.addAttribute(new Attribute("comment", comment_3));
	    
	    //LONGITUDE
	    LONGITUDE=writer.addVariable(null, "LONGITUDE", DataType.DOUBLE, "LONGITUDE");
	    LONGITUDE.addAttribute(new Attribute("standard_name", "latitude")); 
	    LONGITUDE.addAttribute(new Attribute("units", "degrees_east"));
	    LONGITUDE.addAttribute(new Attribute("axis", "X")); 
	    LONGITUDE.addAttribute(new Attribute("reference", "WGS84"));
	    LONGITUDE.addAttribute(new Attribute("long_name", "Longitude of each location"));
	    //Uncomment this for NETCDF file
	  //Uncomment this for NETCDF File
	    //LONGITUDE.addAttribute(new Attribute("coordinate_reference_frame", coordinate_reference_frame_3));
	    //LONGITUDE.addAttribute(new Attribute("valid_min", (double) valid_min_4)); 
		   // LONGITUDE.addAttribute(new Attribute("valid_max", (double) valid_max_4)); 
		   // LONGITUDE.addAttribute(new Attribute("QC_indicator", QC_indicator_4)); 
		   // LONGITUDE.addAttribute(new Attribute("Processing_level", Processing_level_4)); 
		   // LONGITUDE.addAttribute(new Attribute("uncertainty", uncertainty_4)); 
		   // LONGITUDE.addAttribute(new Attribute("comment", comment_4));
	    
	  	    
	       
	    
	    volte=1;
		}
		
		
		/*Uncomment this for writing NETCDF compliant file 
		 Per scrivere la variabile ho bisogno del nome e da cosa dipende (da quali dimensioni dipende). 
		 	dimss=new ArrayList<Dimension>();
		 	if(jobject.get("T").getAsString() == "1"){
	    		dimss.add(T);
	    		dipendenze++; 
				}
			if(jobject.get("D").getAsString() == "1"){
		    	dimss.add(D);
		    	dipendenze++;
				}
			if(jobject.get("LA").getAsString() == "1"){
		    	dimss.add(LA);
		    	dipendenze++;
				}
			if(jobject.get("LO").getAsString() == "1"){
		    	dimss.add(LO);
		    	dipendenze++;
				}	
			// Bisogna definire il tipo di dato	
			if (jobject.get("d").getAsString()== "1"){
				 app=DataType.DOUBLE;
				}
			if (jobject.get("b").getAsString()== "1"){
				 app=DataType.BYTE;
				}
			if (jobject.get("c").getAsString()== "1"){
				 app=DataType.CHAR;
				}
			if (jobject.get("f").getAsString()== "1"){
				 app=DataType.FLOAT;
				}
			if (jobject.get("i").getAsString()== "1"){
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
		
		 //} 
    	volte=0;
    	//Scrivo una variabile di prova per il NETCDF
    	dimss=new ArrayList<Dimension>();
    	dimss.add(T);
    	dimss.add(D);
    	dimss.add(LA);
    	dimss.add(LO);
    	ts = writer.addVariable(null, metricName, DataType.DOUBLE, dimss);
    	ts.addAttribute(new Attribute("standard_name", "testing"));
		//Fine variabile di prova
    	
    	writer.addGroupAttribute(null, new Attribute("site_code", "EMSODEV"));
    	writer.addGroupAttribute(null, new Attribute("platform_code", "EMSODEV"));
    	writer.addGroupAttribute(null, new Attribute("title", "Data_from_seafloor_observatory"+observatory+"related to this instrument"+instrument));
    	//messo D perchè i dati non sono in real time. Controllare. Se dovessero esserlo mettere la R al posto di D
    	writer.addGroupAttribute(null, new Attribute("data_mode", "D"));
    	
		 /* Uncomment this for NETCDF Compliant file
		
		writer.addGroupAttribute(null, new Attribute("principal_investigator", principal_investigator));
		writer.addGroupAttribute(null, new Attribute("principal_investigator_email", principal_investigator_email));
		writer.addGroupAttribute(null, new Attribute("institution", institution));
		writer.addGroupAttribute(null, new Attribute("geospatial_lat_min", geospatial_lat_min));
		writer.addGroupAttribute(null, new Attribute("geospatial_lat_max", geospatial_lat_max));
		writer.addGroupAttribute(null, new Attribute("geospatial_lon_min", geospatial_lon_min));
		writer.addGroupAttribute(null, new Attribute("geospatial_lon_max", geospatial_lon_max));
		writer.addGroupAttribute(null, new Attribute("geospatial_vertical_min", geospatial_vertical_min));
		writer.addGroupAttribute(null, new Attribute("geospatial_vertical_max", geospatial_vertical_max));
		*/
		writer.addGroupAttribute(null, new Attribute("time_coverage_start", EmsodevUtility.getDateAsStringTimestampFormat(startDate)));
		writer.addGroupAttribute(null, new Attribute("time_coverage_end", EmsodevUtility.getDateAsStringTimestampFormat(endDate)));
		
		writer.addGroupAttribute(null, new Attribute("data_type", "OceanSITES time-series data"));
		//Uncomment this line for NETCDF file
		//writer.addGroupAttribute(null, new Attribute("area", (short) 2));
		writer.addGroupAttribute(null, new Attribute("format_version", (float) 1.3));
		writer.addGroupAttribute(null, new Attribute("netcdf_version", (float) 3.5));
		/* Uncomment this for NETCDF Compliant file
		writer.addGroupAttribute(null, new Attribute("publisher_name", publisher_name));
		writer.addGroupAttribute(null, new Attribute("publisher_email", publisher_email));
		writer.addGroupAttribute(null, new Attribute("publisher_url", publisher_url));
		writer.addGroupAttribute(null, new Attribute("update_interval", update_interval));
		writer.addGroupAttribute(null, new Attribute("license", license));
	    writer.addGroupAttribute(null, new Attribute("date_created", date_created));
	    
	    writer.addGroupAttribute(null, new Attribute("QC_indicator", QC_indicator));
        writer.addGroupAttribute(null, new Attribute("contributor_name", contributor_name));
	    writer.addGroupAttribute(null, new Attribute("contributor_role", contributor_role));
	    writer.addGroupAttribute(null, new Attribute("contributor_email", contributor_email));
	    */
		
		 try {
			writer.create();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//Modifica 
		 try {
			arrayDps= jobjectDpsCleaned.split(",");
			//Scrivo i valori di TIME 
			v = writer.findVariable("TIME");
			shape = v.getShape();
			datas = new ArrayDouble.D1(shape[0]);
			ima=datas.getIndex();
			int hal=0;
			for(String rep:jobjectDpsCleaned.split(",")){
				f=rep.split(":");
			datas.setDouble(ima.set(hal), Double.parseDouble(f[0]));
			hal++;
			}
			writer.write(v, datas);
									
			//Scrivo i valori della DEPTH
			v = writer.findVariable("DEPTH");	
		  	shape = v.getShape();
		  	datas = new ArrayDouble.D1(shape[0]);
		  	ima=datas.getIndex();
		    //Uncomment this for NETCDF file 
		  	//datas.setDouble(ima.set(0), depth);
		  	//Comment the following line for NETCDF file
		  	datas.setDouble(ima.set(0), 2000.00);
		  	writer.write(v, datas);
			//Scrivo i valori della Latitudine
			v = writer.findVariable("LATITUDE");	
		  	shape = v.getShape();
		  	datas = new ArrayDouble.D1(shape[0]);
		  	ima=datas.getIndex();
		  	//Uncomment this for NETCDF file 
		  	//datas.setDouble(ima.set(0), geospatial_lat_min);
		  	//Comment the following line for NETCDF file
		  	datas.setDouble(ima.set(0), 36.681690);
		  	writer.write(v, datas);
		  	//Scrivo i valori della Longitudine
		  	v = writer.findVariable("LONGITUDE");	
		  	shape = v.getShape();
		  	datas = new ArrayDouble.D1(shape[0]);
		  	ima=datas.getIndex();
		  	//Uncomment this for NETCDF file 
		  	//datas.setDouble(ima.set(0), geospatial_lon_max);
		    //Comment the following line for NETCDF file
		  	datas.setDouble(ima.set(0), 15.133875);
		  	writer.write(v, datas);
		  	//prendo i valori del tempo
		  	//String[] appoggio=arrayDps[0].split(":");
		  	//converto la stringa in float
		  	//Float.parseFloat(appoggio[0]);
		  	//Array dei dati 
		  	//datas=new ArrayFloat.D0();
		  	//writer.write(v, ac2);
		  	//ac2.setString(ima.set(1) ,arrayDps[1]);
			//double a=1.111111111;
		  	//datas.set(a);
		  //Scrittura valori su variabile di prova
			v = writer.findVariable(metricName);
			shape = v.getShape();
			datass = new ArrayDouble.D4(shape[0], shape[1], shape[2], shape[3]);
			//Scrivo i dati sull'array quadridimensionale
			for(String rep:jobjectDpsCleaned.split(",")){
				f=rep.split(":");
			for (int record = 0; record < shape[0]; record++) {
		        for (int lvl = 0; lvl < shape[1]; lvl++)
		          for (int lat = 0; lat < shape[2]; lat++)
		            for (int lon = 0; lon < shape[3]; lon++) {
		              datass.set(record, lvl, lat, lon, Double.parseDouble(f[0]));
		            }
		      }
			}
			int[] origin = new int[4];
			writer.write(v, origin, datass);
			//Fine scrittura variabile di prova
			/*Uncomment this for NETCDF File
			//la varibile dipendenze mi dice che dimensioni ha la variabile. Il tipo di dato me lo dà la variabile app
			 * 
			switch(app){
				case Datatype.Double: 
						switch(dipendenze){
						 	case 1: 
						 	v = writer.findVariable(metricName);
							shape = v.getShape();
						 	dataD1=new ArrayDouble.D1(shape[0]);
						 	for (int lon = 0; lon < shape[0]; lon++) {
		              		dataD1.set(lon, Double.parseDouble("3000.00"));
		            		}
		            		int[] origin = new int[1];
		            		writer.write(v, origin, dataD1);
						 	break;
						 	case 2: 
						 	v = writer.findVariable(metricName);
							shape = v.getShape();
						 	dataD2=new ArrayDouble.D2(shape[0], shape[1]);
						 	for (int lat = 0; lat < shape[0]; lat++)
		            		for (int lon = 0; lon < shape[1]; lon++) {
		              		dataD2.set(lat, lon, Double.parseDouble("3000.00"));
		              		int[] origin = new int[2];
		              		writer.write(v, origin, dataD2);
		            		}
						 	}	
									//continuare con tutti i casi e dichiarare le varie tipologie di array (char, float double int e array di dimensione 1, 2, 3 ,4 ecc...)
			}
			*/
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidRangeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Fine modifica 
		 
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
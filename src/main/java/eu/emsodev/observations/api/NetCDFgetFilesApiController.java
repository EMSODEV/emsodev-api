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

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;
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
    @SuppressWarnings("null")
	public ResponseEntity <String> netcdfFilesGet(@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

			,
			@ApiParam(value = "EGIM parameter name. It is required for NETCDF file because one global TIME variable is allowed into this file in Oceansites flavour. Indeed each parameter for instruments installed in a seafloor observatory (node) could be sampled at different times than another one", required = true) @RequestParam("parameter") String parameter
			,
			@ApiParam(value = "The start time for the query. The format must be dd/MM/yyyy", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

			,
			@ApiParam(value = "The end time for the query. The format must be dd/MM/yyyy. It is required") @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate
						,
			HttpServletResponse response
			
			)  {
        //  do some magic!
    	//Variables definitions
    	String location = "NetcdfFile.nc";
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
		ArrayInt.D1 datas_T = null;
		int[] origin=null;
		//Variabili per tipologia di dato in netcdf
		//Double
		ArrayDouble.D1 dataD1=null;
		ArrayDouble.D2 dataD2=null;
		ArrayDouble.D3 dataD3=null;
		ArrayDouble.D4 dataD4=null;
		//Float
		ArrayFloat.D1 dataF1=null;
		ArrayFloat.D2 dataF2=null;
		ArrayFloat.D3 dataF3=null;
		ArrayFloat.D4 dataF4=null;
		//Char 
		ArrayChar.D1 dataC1=null;
		ArrayChar.D2 dataC2=null;
		ArrayChar.D3 dataC3=null;
		ArrayChar.D4 dataC4=null;
		//Int 
		ArrayInt.D1 dataI1=null;
		ArrayInt.D2 dataI2=null;
		ArrayInt.D3 dataI3=null;
		ArrayInt.D4 dataI4=null;
		//Byte
		ArrayByte.D1 dataB1=null;
		ArrayByte.D2 dataB2=null;
		ArrayByte.D3 dataB3=null;
		ArrayByte.D4 dataB4=null;
		
		
		
		
		try {
			writer= NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, location, null);
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
				selection = arr_2.getJSONObject(i).getString("EGIMLocation"); //Select a specific field. For instance EGIMLocation
			/*Uncomment this for NETCDF File
			 * valid_min_1=arr_2.getJSONObject(i).getString("valid_min_1");
			 * valid_max_1=arr_2.getJSONObject(i).getString("valid_max_1");
			 * QC_indicator_1=arr_2.getJSONObject(i).getString("QC_indicator_1");
			 * Processing_level_1=arr_2.getJSONObject(i).getString("Processing_level_1");
			 * uncertainty_1=arr_2.getJSONObject(i).getString("uncertainty_1");
			 * comment_1=arr_2.getJSONObject(i).getString("comment_1");
			 * //for DEPTH
			 * coordinate_reference_frame_1= arr_2.getJSONObject(i).getString("coordinate_reference_frame_1");
			 * valid_min_2=arr_2.getJSONObject(i).getString("valid_min_2");
			 * valid_max_2=arr_2.getJSONObject(i).getString("valid_max_2");
			 * QC_indicator_2=arr_2.getJSONObject(i).getString("QC_indicator_2");
			 * Processing_level_2=arr_2.getJSONObject(i).getString("Processing_level_2");
			 * uncertainty_2=arr_2.getJSONObject(i).getString("uncertainty_2");
			 * comment_2=arr_2.getJSONObject(i).getString("comment_2");
			 * //for LATITUDE
			 * coordinate_reference_frame_2=arr_2.getJSONObject(i).getString("coordinate_reference_frame_2"); 
			 * valid_min_3=arr_2.getJSONObject(i).getString("valid_min_3");
			 * valid_max_3=arr_2.getJSONObject(i).getString("valid_max_3");
			 * QC_indicator_3=arr_2.getJSONObject(i).getString("QC_indicator_3");
			 * Processing_level_3=arr_2.getJSONObject(i).getString("Processing_level_3");
			 * uncertainty_3=arr_2.getJSONObject(i).getString("uncertainty_3");
			 * comment_3=arr_2.getJSONObject(i).getString("comment_3");
			 * //for LONGITUDE
			 * coordinate_reference_frame_3= arr_2.getJSONObject(i).getString("coordinate_reference_frame_3");
			 * valid_min_4=arr_2.getJSONObject(i).getString("valid_min_4");
			 * valid_max_4=arr_2.getJSONObject(i).getString("valid_max_4");
			 * QC_indicator_4=arr_2.getJSONObject(i).getString("QC_indicator_4");
			 * Processing_level_4=arr_2.getJSONObject(i).getString("Processing_level_4");
			 * uncertainty_4=arr_2.getJSONObject(i).getString("uncertainty_4");
			 * comment_4=arr_2.getJSONObject(i).getString("comment_4");
			 * area=arr_2.getJSONObject(i).getString("area");
			 * institution=arr_2.getJSONObject(i).getString("institution");
			 * geospatial_lat_min=arr_2.getJSONObject(i).getString("geospatial_lat_min");
			 * geospatial_lat_max=arr_2.getJSONObject(i).getString("geospatial_lat_max");
			 * geospatial_lon_min=arr_2.getJSONObject(i).getString("geospatial_lon_min");
			 * geospatial_lon_max=arr_2.getJSONObject(i).getString("geospatial_lon_max");
			 * geospatial_vertical_min=arr_2.getJSONObject(i).getString("geospatial_vertical_min");
			 * geospatial_vertical_max=arr_2.getJSONObject(i).getString("geospatial_vertical_max");
			 * depth=arr_2.getJSONObject(i).getString("depth");
			 */
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	   
		  

		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		
		
		 compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate) +"&m=sum:" 
				+ parameter+"{params}"
				+"&end="
				+EmsodevUtility.getDateAsStringTimestampFormat(endDate);
		
		respons = restTemplate.getForObject(compositeUrl, Object.class, params.toString().replace(" ", ""));
		gson = new Gson();
		JsonElement jelement = gson.fromJson (respons.toString(), JsonElement.class);
		JsonArray jsonarray = jelement.getAsJsonArray();
		//Get the first and last item of the array
		JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
		//The value of metric attribute
		JsonObject  jobject = jarrayItem.getAsJsonObject();
		metricName = jobject.get("metric").getAsString();
					
		//Get the an jsonObject with that represents the "tags" branch
		jobject = jobject.getAsJsonObject("tags");
		//Get the value of attribute of SensorID and EGIMNode of the "tags" branch
		sensorIdName = jobject.get("SensorID").getAsString();
		egimNodeName = jobject.get("EGIMNode").getAsString();
		//Uncomment this for NETCDF file
		/*
		principal_investigator=jobject.get("principal_investigator").getAsString();
		principal_investigator_email=jobject.get("principal_investigator_email").getAsString();
		publisher_name=jobject.get("publisher_name").getAsString();
		publisher_e_mail=jobject.get("publisher_e_mail").getAsString();
		publisher_url=jobject.get("publisher_url").getAsString();
		update_interval=jobject.get("update_interval").getAsString();
		license=jobject.get("license").getAsString();
		QC_indicator=jobject.get("QC_indicator").getAsString();
		contributor_name=jobject.get("contributor_name").getAsString();
		contributor_role=jobject.get("contributor_role").getAsString();
		contributor_e_mail=jobject.get("contributor_e_mail").getAsString();
		
		*/
		
		
		
		//The json dps object is taken and adapted to string 
		jobjectDps = jarrayItem.getAsJsonObject();
		jobjectDps = jobjectDps.getAsJsonObject("dps");
		jobjectDpsCleaned = jobjectDps.toString().replace("\"", "").replace("{", "").replace("}", "");
		
		//I count the samples within the string 
		occurance=0;
		for(String rep:jobjectDpsCleaned.split(",")){
			occurance++;
		}
				

		if(volte==0){
		//Writing standard dimensions for OCENASITE NETCDF flavour
	
		T=writer.addDimension(null, "TIME", occurance); 
	    D=writer.addDimension(null, "DEPTH", 1);
	    LA=writer.addDimension(null, "LATITUDE", 1);
	    LO=writer.addDimension(null, "LONGITUDE", 1);
	    
		//Writing GLOBAL Variables di Oceansites (these are standard variables). 
	    //TIME
	    TIME=writer.addVariable(null, "TIME", DataType.INT, "TIME"); //funziona solo con il FLOAT
	    TIME.addAttribute(new Attribute("standard_name", "time")); 
	    TIME.addAttribute(new Attribute("units", "second since 1970-01-01T00:00:00Z")); 
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
		 //To write the variable I need to receive the information on the name and the variable dimensions. 
		 
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
				
			//Writing data variable (NETCDF Oceansites Flavour)
			 * 
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
		    	
		    	//Adding ancillary variable if it exists
		    	 
		    	 ta= writer.addVariable(null, "byte "+metricName, app, dimss);
		    	 ta.addAttribute(new Attribute("long_name", jobject.get("long_name_A").getAsString()));
		    	 ta.addAttribute(new Attribute("valid_min", Double.parseDouble(jobject.get("valid_min_A").getAsString())));
		    	 ta.addAttribute(new Attribute("valid_max", Double.parseDouble(jobject.get("valid_max_A").getAsString())));
		    	 ta.addAttribute(new Attribute("flag_values",  jobject.get("flag_values_A").getAsString()));
		    	 ta.addAttribute(new Attribute("flag_meanings", jobject.get("flag_meanings_A").getAsString()));
		         ta.addAttribute(new Attribute("conventions", jobject.get("conventions_A").getAsString()));
		    }
		    if(jobject.get("comment_flag").getAsString()== 1){
		    ts.addAttribute(new Attribute("comment", jobject.get("comment_A").getAsString()));
			}
		*/
		
		 //} 
    	volte=0;
    	//I write a test variable in NETCDF file
    	dimss=new ArrayList<Dimension>();
    	dimss.add(T);
    	dimss.add(D);
    	dimss.add(LA);
    	dimss.add(LO);
    	ts = writer.addVariable(null, metricName, DataType.DOUBLE, dimss);
    	ts.addAttribute(new Attribute("standard_name", "testing"));
		//Stop writing test variable
    	
    	writer.addGroupAttribute(null, new Attribute("site_code", "EMSODEV"));
    	writer.addGroupAttribute(null, new Attribute("platform_code", "EMSODEV"));
    	writer.addGroupAttribute(null, new Attribute("title", "Data_from_seafloor_observatory"+observatory+"related to this instrument"+instrument));
    	//I chose D because there aren't real-time data-sets. Otherwise choose R instead of D (check)
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
    	//Writing Data in ISO 8601 format
    	SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    	String Starting_DATE=dateformatyyyyMMdd.format(startDate);
    	String Ending_DATE=dateformatyyyyMMdd.format(endDate);
    	writer.addGroupAttribute(null, new Attribute("time_coverage_start", Starting_DATE+"T00:00:00Z"));
		writer.addGroupAttribute(null, new Attribute("time_coverage_end", Ending_DATE+"T23:59:59Z"));
		
		
		writer.addGroupAttribute(null, new Attribute("data_type", "OceanSITES time-series data"));
		//Uncomment this line for NETCDF file
		//writer.addGroupAttribute(null, new Attribute("area", (short) 2));
		writer.addGroupAttribute(null, new Attribute("format_version", (float) 1.3));
		writer.addGroupAttribute(null, new Attribute("netcdf_version", (float) 3));
		/* Uncomment this for NETCDF Compliant file
		writer.addGroupAttribute(null, new Attribute("publisher_name", publisher_name));
		writer.addGroupAttribute(null, new Attribute("publisher_email", publisher_email));
		writer.addGroupAttribute(null, new Attribute("publisher_url", publisher_url));
		writer.addGroupAttribute(null, new Attribute("update_interval", update_interval));
		writer.addGroupAttribute(null, new Attribute("license", license));
		*/
		
		String Current_DATE=dateformatyyyyMMdd.format(GregorianCalendar.getInstance().getTime());
		writer.addGroupAttribute(null, new Attribute("date_created", Current_DATE));
	    /*
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
		 
		//Writing numerical values into the variables previously declared
		 try {
			arrayDps= jobjectDpsCleaned.split(",");
			//TIME variable test
			v = writer.findVariable("TIME");
			shape = v.getShape();
			datas_T = new ArrayInt.D1(shape[0]);
			ima=datas_T.getIndex();
			int hal=0;
			for(String rep:jobjectDpsCleaned.split(",")){
				f=rep.split(":");
			datas_T.setInt(ima.set(hal), Integer.parseInt(f[0]));
			hal++;
			}
			writer.write(v, datas_T);
									
			//Writing values into DEPTH variable 
			v = writer.findVariable("DEPTH");	
		  	shape = v.getShape();
		  	datas = new ArrayDouble.D1(shape[0]);
		  	ima=datas.getIndex();
		    //Uncomment this for NETCDF file 
		  	//datas.setDouble(ima.set(0), depth);
		  	//Comment the following line for NETCDF file
		  	datas.setDouble(ima.set(0), 2000.00);
		  	writer.write(v, datas);
			//Writing values into LATITUDE variable
			v = writer.findVariable("LATITUDE");	
		  	shape = v.getShape();
		  	datas = new ArrayDouble.D1(shape[0]);
		  	ima=datas.getIndex();
		  	//Uncomment this for NETCDF file 
		  	//datas.setDouble(ima.set(0), geospatial_lat_min);
		  	//Comment the following line for NETCDF file
		  	datas.setDouble(ima.set(0), 36.681690);
		  	writer.write(v, datas);
		  	//Writing values into LONGITUDE variable
		  	v = writer.findVariable("LONGITUDE");	
		  	shape = v.getShape();
		  	datas = new ArrayDouble.D1(shape[0]);
		  	ima=datas.getIndex();
		  	//Uncomment this for NETCDF file 
		  	//datas.setDouble(ima.set(0), geospatial_lon_max);
		    //Comment the following line for NETCDF file
		  	datas.setDouble(ima.set(0), 15.133875);
		  	writer.write(v, datas);
		  	
		  //Writing values into 4D test variable 
		  	
			v = writer.findVariable(metricName);
			shape = v.getShape();
			datass = new ArrayDouble.D4(shape[0], shape[1], shape[2], shape[3]);
			//Loop for writing values in 4D test variable
			for(String rep:jobjectDpsCleaned.split(",")){
				f=rep.split(":");
			for (int record = 0; record < shape[0]; record++) {
		        for (int lvl = 0; lvl < shape[1]; lvl++)
		          for (int lat = 0; lat < shape[2]; lat++)
		            for (int lon = 0; lon < shape[3]; lon++) {
		              datass.set(record, lvl, lat, lon, Double.parseDouble(f[1]));
		            }
		      }
			}
			origin = new int[4];
			writer.write(v, origin, datass);
			//For each dataset I write the values into variable described by parameters in this API
			//Datatype Double
			if(app==DataType.DOUBLE){
				if(dipendenze == 1){
					v = writer.findVariable(metricName);
					shape = v.getShape();
				 	dataD1=new ArrayDouble.D1(shape[0]);
				 	for(String rep:jobjectDpsCleaned.split(",")){
						f=rep.split(":");
					 	for (int lon = 0; lon < shape[0]; lon++) {
					 		dataD1.set(lon, Double.parseDouble(f[1]));
					 		}
	            		}
	            		origin = new int[1];
	            		writer.write(v, origin, dataD1);
				}
				if(dipendenze == 2){
					v = writer.findVariable(metricName);
					shape = v.getShape();
				 	dataD2=new ArrayDouble.D2(shape[0], shape[1]);
				 	for(String rep:jobjectDpsCleaned.split(",")){
						f=rep.split(":");
						for (int lat = 0; lat < shape[0]; lat++){
						for (int lon = 0; lon < shape[1]; lon++) {
					 		dataD2.set(lat, lon, Double.parseDouble(f[1]));
					 		}
				 		}
	            		}
	            		origin = new int[2];
	            		writer.write(v, origin, dataD2);
				}
				
				if(dipendenze == 3){
					v = writer.findVariable(metricName);
					shape = v.getShape();
				 	dataD3=new ArrayDouble.D3(shape[0], shape[1], shape[2]);
				 	for(String rep:jobjectDpsCleaned.split(",")){
						f=rep.split(":");
						for (int lvl = 0; lvl < shape[0]; lvl++){
						for (int lat = 0; lat < shape[1]; lat++)
						for (int lon = 0; lon < shape[2]; lon++) {
					 		dataD3.set(lvl, lat, lon, Double.parseDouble(f[1]));
					 		}
							}
	            		}
	            		origin = new int[3];
	            		writer.write(v, origin, dataD3);
				}
				if(dipendenze == 4){
					v = writer.findVariable(metricName);
					shape = v.getShape();
				 	dataD4=new ArrayDouble.D4(shape[0], shape[1], shape[2], shape[3]);
				 	for(String rep:jobjectDpsCleaned.split(",")){
						f=rep.split(":");
						
						for (int record = 0; record < shape[0]; record++) {
					        for (int lvl = 0; lvl < shape[1]; lvl++)
					          for (int lat = 0; lat < shape[2]; lat++)
					            for (int lon = 0; lon < shape[3]; lon++) {
					            	dataD4.set(record, lvl, lat, lon, Double.parseDouble(f[1]));
					            }
					      }			
						
				}
				 	origin = new int[4];
					writer.write(v, origin, dataD4);	
			
				}
			}
			
			//Datatype Float
				if(app==DataType.FLOAT){
					if(dipendenze == 1){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataF1=new ArrayFloat.D1(shape[0]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
						 	for (int lon = 0; lon < shape[0]; lon++) {
						 		dataF1.set(lon, Float.parseFloat(f[1]));
						 		}
		            		}
		            		origin = new int[1];
		            		writer.write(v, origin, dataF1);
					}
					if(dipendenze == 2){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataF2=new ArrayFloat.D2(shape[0], shape[1]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lat = 0; lat < shape[0]; lat++){
							for (int lon = 0; lon < shape[1]; lon++) {
						 		dataF2.set(lat, lon, Float.parseFloat(f[1]));
						 		}
					 		}
		            		}
		            		origin = new int[2];
		            		writer.write(v, origin, dataF2);
					}
					
					if(dipendenze == 3){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataF3=new ArrayFloat.D3(shape[0], shape[1], shape[2]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lvl = 0; lvl < shape[0]; lvl++){
							for (int lat = 0; lat < shape[1]; lat++)
							for (int lon = 0; lon < shape[2]; lon++) {
						 		dataF3.set(lvl, lat, lon, Float.parseFloat(f[1]));
						 		}
								}
		            		}
		            		origin = new int[3];
		            		writer.write(v, origin, dataF3);
					}
					if(dipendenze == 4){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataF4=new ArrayFloat.D4(shape[0], shape[1], shape[2], shape[3]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							
							for (int record = 0; record < shape[0]; record++) {
						        for (int lvl = 0; lvl < shape[1]; lvl++)
						          for (int lat = 0; lat < shape[2]; lat++)
						            for (int lon = 0; lon < shape[3]; lon++) {
						            	dataF4.set(record, lvl, lat, lon, Float.parseFloat(f[1]));
						            }
						      }			
							
					}
					 	origin = new int[4];
						writer.write(v, origin, dataF4);	
				
					}	
			
				}
			//Datatype CHAR
				if(app==DataType.CHAR){
					if(dipendenze == 1){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataC1=new ArrayChar.D1(shape[0]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
						 	for (int lon = 0; lon < shape[0]; lon++) {
						 		dataC1.setString(lon, f[1]);
						 		}
		            		}
		            		origin = new int[1];
		            		writer.write(v, origin, dataC1);
					}
					if(dipendenze == 2){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataC2=new ArrayChar.D2(shape[0], shape[1]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lat = 0; lat < shape[0]; lat++){
							for (int lon = 0; lon < shape[1]; lon++) {
								for(int i = 0; i < f[1].length(); i++){
						 		dataC2.set(lat, lon, f[1].charAt(i));
								}
						 		}
					 		}
		            		}
		            		origin = new int[2];
		            		writer.write(v, origin, dataC2);
					}
					
					if(dipendenze == 3){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataC3=new ArrayChar.D3(shape[0], shape[1], shape[2]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lvl = 0; lvl < shape[0]; lvl++){
							for (int lat = 0; lat < shape[1]; lat++)
							for (int lon = 0; lon < shape[2]; lon++) {
								for(int i = 0; i < f[1].length(); i++){
							 		dataC3.set(lvl,lat, lon, f[1].charAt(i));
									}
						 		}
								}
		            		}
		            		origin = new int[3];
		            		writer.write(v, origin, dataC3);
					}
					if(dipendenze == 4){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataF4=new ArrayFloat.D4(shape[0], shape[1], shape[2], shape[3]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							
							for (int record = 0; record < shape[0]; record++) {
						        for (int lvl = 0; lvl < shape[1]; lvl++)
						          for (int lat = 0; lat < shape[2]; lat++)
						            for (int lon = 0; lon < shape[3]; lon++) {
						            	for(int i = 0; i < f[1].length(); i++){
									 		dataC4.set(record, lvl,lat, lon, f[1].charAt(i));
											}
						            }
						      }			
							
					}
					 	origin = new int[4];
						writer.write(v, origin, dataC4);
					}	
			
				}
				
				//Datatype Byte
				
				if(app==DataType.BYTE){
					if(dipendenze == 1){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataB1=new ArrayByte.D1(shape[0]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
						 	for (int lon = 0; lon < shape[0]; lon++) {
						 		dataB1.set(lon, Byte.parseByte(f[1]));
						 		}
		            		}
		            		origin = new int[1];
		            		writer.write(v, origin, dataB1);
					}
					if(dipendenze == 2){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataB2=new ArrayByte.D2(shape[0], shape[1]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lat = 0; lat < shape[0]; lat++){
							for (int lon = 0; lon < shape[1]; lon++) {
						 		dataB2.set(lat, lon, Byte.parseByte(f[1]));
						 		}
					 		}
		            		}
		            		origin = new int[2];
		            		writer.write(v, origin, dataB2);
					}
					
					if(dipendenze == 3){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataB3=new ArrayByte.D3(shape[0], shape[1], shape[2]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lvl = 0; lvl < shape[0]; lvl++){
							for (int lat = 0; lat < shape[1]; lat++)
							for (int lon = 0; lon < shape[2]; lon++) {
						 		dataB3.set(lvl, lat, lon, Byte.parseByte(f[1]));
						 		}
								}
		            		}
		            		origin = new int[3];
		            		writer.write(v, origin, dataB3);
					}
					if(dipendenze == 4){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataB4=new ArrayByte.D4(shape[0], shape[1], shape[2], shape[3]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							
							for (int record = 0; record < shape[0]; record++) {
						        for (int lvl = 0; lvl < shape[1]; lvl++)
						          for (int lat = 0; lat < shape[2]; lat++)
						            for (int lon = 0; lon < shape[3]; lon++) {
						            	dataB4.set(record, lvl, lat, lon, Byte.parseByte(f[1]));
						            }
						      }			
							
					}
					 	origin = new int[4];
						writer.write(v, origin, dataB4);	
				
					}
				}
				//Datatype INT 
				if(app==DataType.INT){
					if(dipendenze == 1){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataI1=new ArrayInt.D1(shape[0]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
						 	for (int lon = 0; lon < shape[0]; lon++) {
						 		dataI1.set(lon, Integer.parseInt(f[1]));
						 		}
		            		}
		            		origin = new int[1];
		            		writer.write(v, origin, dataI1);
					}
					if(dipendenze == 2){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataI2=new ArrayInt.D2(shape[0], shape[1]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lat = 0; lat < shape[0]; lat++){
							for (int lon = 0; lon < shape[1]; lon++) {
						 		dataI2.set(lat, lon, Integer.parseInt(f[1]));
						 		}
					 		}
		            		}
		            		origin = new int[2];
		            		writer.write(v, origin, dataI2);
					}
					
					if(dipendenze == 3){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataI3=new ArrayInt.D3(shape[0], shape[1], shape[2]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							for (int lvl = 0; lvl < shape[0]; lvl++){
							for (int lat = 0; lat < shape[1]; lat++)
							for (int lon = 0; lon < shape[2]; lon++) {
						 		dataI3.set(lvl, lat, lon, Integer.parseInt(f[1]));
						 		}
								}
		            		}
		            		origin = new int[3];
		            		writer.write(v, origin, dataI3);
					}
					if(dipendenze == 4){
						v = writer.findVariable(metricName);
						shape = v.getShape();
					 	dataI4=new ArrayInt.D4(shape[0], shape[1], shape[2], shape[3]);
					 	for(String rep:jobjectDpsCleaned.split(",")){
							f=rep.split(":");
							
							for (int record = 0; record < shape[0]; record++) {
						        for (int lvl = 0; lvl < shape[1]; lvl++)
						          for (int lat = 0; lat < shape[2]; lat++)
						            for (int lon = 0; lon < shape[3]; lon++) {
						            	dataI4.set(record, lvl, lat, lon, Integer.parseInt(f[1]));
						            }
						      }			
							
					}
					 	origin = new int[4];
						writer.write(v, origin, dataI4);	
				
					}
				}
			
	
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidRangeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 
		 try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//In order to pass as a API parameter the generated Oceansites netcdf file, I need to re-open this.
		  try {
			ncfile=NetcdfFileWriter.openExisting(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 return new ResponseEntity<String>("error", HttpStatus.OK);	
			//e.printStackTrace();
		}
		////Finding the file for adding the information and passing file and information to Output Stream
		  try {
			  java.nio.file.Path file = Paths.get(".", location );
			  if (Files.exists(file))
		        {
		            response.setContentType("application/x-netcdf");
		            response.addHeader("Content-Disposition", "attachment; filename="+location);
		        }
			  Files.copy(file, response.getOutputStream());
			  response.getOutputStream().flush();
			  //Erasing the generated file
			  Files.deleteIfExists(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 return new ResponseEntity<String>("error_path", HttpStatus.OK);	
				//e.printStackTrace();
			} 
		  
    	
			  
			  
			
        return new ResponseEntity<String>(jobjectDpsCleaned, HttpStatus.OK);
    }

}
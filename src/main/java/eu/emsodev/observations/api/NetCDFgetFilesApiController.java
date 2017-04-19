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
			/* uncomment this for using NETCDF file
			,
			HttpServletResponse response
			*/
			)  {
        // do some magic!
    	//Variables definitions
    	String location = "Umberto.nc";
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
		char lettera = ' ';
		int n=0;
		
		//la struttura del programma è questa: 
		//crei il file netcdf; ricevi le info e nei cicli for sulle stringhe del JSON object le scrivi
    	
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
    	
    	
    	////Information for Time series for instrument
    	
		 for(int i = 0; i < Data_2.length(); i++){
			  lettera= Data_2.charAt(i);
			  if(lettera ==' '){ //arrivo alla fine della sottostringa
				  result_data_2=Data_2.substring(n,i-1);
						  n=i+1;
						  
			  }
		}
		 
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
		
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID",instrument);
		
		 compositeUrl = urlToCallObservatoriesObservatoryInstrumentsInstrumentParametersParameterGet 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate) +"&m=sum:" 
				+ "sea_water_temperature"+"{params}"
				+"&end="
				+EmsodevUtility.getDateAsStringTimestampFormat(endDate);
		response_3 = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));
    	
    	//qui poi per riordinare il file farai come sopra
		//try {
			//obj_7 = new JSONObject(response_3);
			//JSONArray arr_7 = ecc.... e poi il for. 
			
		
		  
    	
		/* uncomment this for using NETCDF File
		 * 	  try {
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
				
			} catch (IOException e) {
				return new ResponseEntity<String>("error", HttpStatus.OK);
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	
			  try {
				writer.create();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<String>("error", HttpStatus.OK);
				//e.printStackTrace();
			}
			  try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return new ResponseEntity<String>("error", HttpStatus.OK);
				//e1.printStackTrace();
			}
			  //test esistenza file NETCDF
			  try {
				ncfile=NetcdfFileWriter.openExisting(location);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 return new ResponseEntity<String>("error", HttpStatus.OK);	
				//e.printStackTrace();
			}
			//
			  try {
				  java.nio.file.Path file = Paths.get(".", "Umberto.nc");
				  if (Files.exists(file))
			        {
			            response.setContentType("application/x-netcdf");
			            response.addHeader("Content-Disposition", "attachment; filename=\"Umberto.nc\"");
			        }
				  Files.copy(file, response.getOutputStream());
				  response.getOutputStream().flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					 return new ResponseEntity<String>("error_path", HttpStatus.OK);	
					//e.printStackTrace();
				}
			  
			*/			  
			  
			
        return new ResponseEntity<String>(result_data_2, HttpStatus.OK);
    }

}
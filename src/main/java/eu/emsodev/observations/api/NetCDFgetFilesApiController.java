package eu.emsodev.observations.api;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.swagger.annotations.*;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import ucar.ma2.*; 
//import ucar.nc2.*; 

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
	
	protected RestTemplate restTemplate;
				

	public ResponseEntity <String> netcdfFilesGet( 
			@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

			,
			@ApiParam(value = "The start time for the query. The formast must be dd/MM/yyyy", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

			,
			@ApiParam(value = "The end time for the query. The formast must be dd/MM/yyyy. It is required") @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate
			
			)  {
   		 
        // do some magic!
    	//reo l'oggetto restTemplate
    	restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
    	
    	String egimNode = "{EGIMNode=*}";
		// The response as string of the urlToCall
		String response = restTemplate.getForObject(urlToCallObservatoriesGet, String.class,
				egimNode);
		
	
		
		
		JSONObject obj = null;
		JSONObject obj_2 = null;
		JSONObject result = null;
		JSONObject result_1 = null;
		//inizio
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
		//NetcdfFileWriteable writer = null;
		String location = "gino.nc";
		
		
		//String Data_1 ="";
		Set<String> set = new HashSet<String>();
		
		try {
			 obj = new JSONObject(response);
			//Ricavo i metadati dal file di risposta: strumenti, nome del nodo e nome strumento nella stringa data dove ogni campo è separato da , e spazio
			 JSONArray arr = obj.getJSONArray("results"); //nuovo JSON solo con results
			 for (int i = 0; i < arr.length(); i++) {
				 	//Data_1=" "+ obj.getString("metric")+ ",";
					result = arr.getJSONObject(i).getJSONObject("tags");
					Data = Data+ " "+ arr.getJSONObject(i).getString("metric")+ ",";
					// add the EGIMnode value to the list				
					Data=Data+ " "+ result.getString("EGIMNode")+",";
					Data=Data+ " "+ result.getString("SensorID")+",";
					
		
			 
			 	}
		 //fine 
			 //Prendo i parametri del singolo strumento
			 String URL="http://dmpnode5.emsodev.eu:9991/api/search/lookup?limit=0&m=*{params}"; 
			 String paramss = "{SensorID="+instrument+",EGIMNode="+observatory+"}";
			 //response_1 = retTemplate.getForObject(URL, String.class);
			  response_1 = restTemplate.getForObject(URL, String.class, paramss);
			  obj = new JSONObject(response_1);
			  JSONArray arr_1 = obj.getJSONArray("results");
			  for (int i = 0; i < arr.length(); i++) {
				  Data_2 = Data_2+ " "+ arr_1.getJSONObject(i).getString("metric")+ ",";
			  }
			  //Per separarsi le strighe fai così per generarti variabili o cose così: fai. 
			  /*
			  String [] splits = Data_2.split(",");
				for(String s:splits){
				//fai operazioni su file netcdf	
				}
				*/
		  //metadati osserv
			  Url_3 = "http://dmpnode1.emsodev.eu:50070/webhdfs/v1/emsodev/" +observatory +"/" + instrument ;
			  response_5= restTemplate.getForObject(Url_3 + "?op=LISTSTATUS", String.class);
			  obj_2 = new JSONObject(response_5);
			  JSONArray arr_2 = obj_2.getJSONObject("FileStatuses").getJSONArray("FileStatus");
			  for (int i = 0; i < arr.length(); i++) {
					type = arr_2.getJSONObject(i).getString("type");
					nameDir = arr_2.getJSONObject(i).getString("pathSuffix");
					dateValidity = arr_2.getJSONObject(i).getString("modificationTime");
					
					if (type != null && "DIRECTORY".equals(type)){
						 resp= restTemplate.getForObject(Url_3 + "/"+nameDir + "/metadata/metadata.json"+"?op=OPEN", String.class);
						//System.out.println(resp);
					}
					
			  //response_4 = restTemplate.getForObject(Url_3 + "/" +"" + "/metadata/metadata.json"+"?op=OPEN", String.class);
			  }
			  
			  
			  
			  //Prendo i dati (series temporali) del singolo strumento per parametro
		  Map<String,String> params = new HashMap<String,String>();
				params.put("EGIMNode", observatory);
				params.put("SensorID", instrument);
				
				//DateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy hh:mm:ss z");
				//
				long unixTime = (long) startDate.getTime()/1000;
				 strDate= strDate.valueOf(unixTime);
				 long unixTime_1 = (long) endDate.getTime()/1000;
				 strDate_1= strDate.valueOf(unixTime_1);
				//Date date = dateFormat.parse(strDate);
				//long unixTime = (long) date.getTime()/1000;
				
				
			  String compositeUrl = "http://dmpnode5.emsodev.eu:9991/api/query?start=" + strDate  +"&m=sum:" + "sea_water_temperature"+"{params}"+"&end="+ strDate_1;
			   response_3 = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));
			 
			   
			    
			   
			   
		} catch (JSONException e) {
			// TODO Auto-geerate catch block
			e.printStackTrace();
		}
		
		//writer= writer.createNew(location);
		//Dimension latDim = writer.addDimension("lat", 64);
		//Dimension lonDim = writer.addDimension("lon", 128);
		//ArrayList dims = new ArrayList();
		  // dims.add( latDim);
		   //dims.add( lonDim);
		   //writer.addVariable("temperatures", DataType.DOUBLE, dims);
		   //writer.addVariableAttribute("temperatures", "units", "K");
		
		/*try {
			  writer.create();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
		*/
        	
        return new ResponseEntity<String>("pippo", HttpStatus.OK);
    }

}


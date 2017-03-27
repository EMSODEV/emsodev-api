package eu.emsodev.observations.api;

import java.io.File;


import io.swagger.annotations.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
				

    public ResponseEntity <String> netcdfFilesGet()  {
        // do some magic!
    	//reo l'oggettdo restTemplate
    	restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,username,password,proxyUrl,proxyPort);
    	
    	String egimNode = "{EGIMNode=*}";
		// The response as string of the urlToCall
		String response = restTemplate.getForObject(urlToCallObservatoriesGet, String.class,
				egimNode);
		JSONObject obj = null;
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
			 String paramss = "{SensorID="+"Workhorse_ADCP_21582"+",EGIMNode="+"EMSODEV-EGIM-node00001"+"}";
			 //response_1 = retTemplate.getForObject(URL, String.class);
			  response_1 = restTemplate.getForObject(URL, String.class, paramss);
			  obj = new JSONObject(response_1);
			  JSONArray arr_1 = obj.getJSONArray("results");
			  for (int i = 0; i < arr.length(); i++) {
				  Data_2 = Data_2+ " "+ arr_1.getJSONObject(i).getString("metric")+ ",";
			  }
			  
		  //metadati osserv
			  Url_3 = "http://dmpnode1.emsodev.eu:50070/webhdfs/v1/emsodev/" +"EMSODEV-EGIM-node00001" +"/" + "Workhorse_ADCP_21582" ;
			  response_5= restTemplate.getForObject(Url_3 + "?op=LISTSTATUS", String.class);
			  response_4 = restTemplate.getForObject(Url_3 + "/" +"" + "/metadata/metadata.json"+"?op=OPEN", String.class);
		  
			  
			  //Prendo i dati (series temporali) del singolo strumento per parametro
		  Map<String,String> params = new HashMap<String,String>();
				params.put("EGIMNode", "EMSODEV-EGIM-node00001");
				params.put("SensorID","Workhorse_ADCP_21582");
			  
			  String compositeUrl = "http://dmpnode5.emsodev.eu:9991/api/query?start=" + "1489763412" +"&m=sum:" + "sea_water_temperature"+"{params}"+"&end="+ "1490623812";
			   response_3 = restTemplate.getForObject(compositeUrl, String.class, params.toString().replace(" ", ""));
			 
			 
		} catch (JSONException e) {
			// TODO Auto-geerate catch block
			e.printStackTrace();
		}
		 
    	    	
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}

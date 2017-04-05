package eu.emsodev.observations.api;

import java.io.File;
import java.io.IOException;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	protected RestTemplate restTemplate;
				

    public ResponseEntity <NetcdfFileWriter> netcdfFilesGet(@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

			,
			@ApiParam(value = "The start time for the query. The formast must be dd/MM/yyyy", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

			,
			@ApiParam(value = "The end time for the query. The formast must be dd/MM/yyyy. It is required") @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate
			)  {
        // do some magic!
    	//reo l'oggetto restTemplate
    	String location = "Umberto.nc";
    	NetcdfFileWriter writer = null;
    	Dimension lonDim = null;
    	Dimension latDim = null;
    	List<Dimension> dims = null;
    	Variable t = null;
    	Array data = null;
    	//Dimension names= null;
    	Dimension svar_len = null;
			  try {
			writer= NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, location, null);
				//Add dimension
				svar_len = writer.addDimension(null, "svar_len", 2);
				writer.addVariable(null, "svar", DataType.CHAR, "svar_len");
				//Add Group Attributes
				writer.addGroupAttribute(null, new Attribute("yo", "face"));
			    writer.addGroupAttribute(null, new Attribute("versionD", 1.2));
			    writer.addGroupAttribute(null, new Attribute("versionF", (float) 1.2));
			    writer.addGroupAttribute(null, new Attribute("versionI", 1));
			    writer.addGroupAttribute(null, new Attribute("versionS", (short) 2));
			    writer.addGroupAttribute(null, new Attribute("versionB", (byte) 3));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
			  try {
				writer.create();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        return new ResponseEntity<NetcdfFileWriter>(writer, HttpStatus.OK);
    }

}

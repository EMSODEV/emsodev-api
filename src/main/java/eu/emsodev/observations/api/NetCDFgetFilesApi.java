package eu.emsodev.observations.api;

import java.io.File;


import io.swagger.annotations.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import ucar.ma2.*; 
import ucar.nc2.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Api(value = "files NETCDF", description = "the files API")







public interface NetCDFgetFilesApi {

	//@ApiOperation(value = "It represents the Time Series retrieved as NETCDF", notes = "Get NetCDF file of a specific Observatory`.", tags={ "Observations Time-series as NetCDF or ODV", })
	@ApiOperation(value = "It represents the Time Series retrieved as NETCDF", notes = "Get NetCDF file of a specific Observatory`.", response = String.class, tags={ "Observations Time-series as NetCDF or ODV", })
    @ApiResponses(value = { 
    		//@ApiResponse(code = 200, message = "Time series list.") })
    		@ApiResponse(code = 200, message = "Time series list.", response = String.class) })
    @RequestMapping(value = "/fileasnetcdf",
        produces = { "application/x-netcdf" }, 
        method = RequestMethod.GET)
	//inizio
	ResponseEntity <String> netcdfFilesGet(
	//void netcdfFilesGet(
    		@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

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
    		);
	
	// fine
	
	
	
	/*
	ResponseEntity<String> netcdfFilesGet(
    		@ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

			,
			@ApiParam(value = "The start time for the query. The formast must be dd/MM/yyyy", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

			,
			@ApiParam(value = "The end time for the query. The formast must be dd/MM/yyyy. It is required") @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate
    		
    		);
*/
    
    //Some example of spring method that return file
    // https://twilblog.github.io/java/spring/rest/file/stream/2015/08/14/return-a-file-stream-from-spring-rest.html
    // http://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
    // 
    // For batch mode see:  http://projects.spring.io/spring-batch/
    // 
    //
    
}

package eu.emsodev.observations.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.File;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Api(value = "files ODV", description = "the files API")
public interface ODVgetFilesApi {

    @ApiOperation(value = "It represents the Time Series retrieved as ODV", notes = "Get ODV representation of a dataset for a specific observatory,"
    		+ " instrument and time range. The output is a link which will start the download of the text file.", response = File.class, tags={ "Observations Time-series as NetCDF or ODV", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Time series list.", response = File.class) })
    @RequestMapping(value = "/fileasodv",  method = RequestMethod.GET)
    //ResponseEntity<File> odvFilesGet(HttpServletResponse response);

    
    ResponseEntity<String> odvFilesGet(
    @ApiParam(value = "EGIM observatory name.", required = true) @RequestParam("observatory") String observatory

	,
	@ApiParam(value = "EGIM instrument name.", required = true) @RequestParam("instrument") String instrument

	,
	@ApiParam(value = "The start time for the query. The Date format is dd/MM/yyyy", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern="dd/MM/yyyy") Date startDate

	,
	@ApiParam(value = "The end time for the query. The Date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="dd/MM/yyyy") Date endDate

	);
    //Some example of spring method that return file
    // https://twilblog.github.io/java/spring/rest/file/stream/2015/08/14/return-a-file-stream-from-spring-rest.html
    // http://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
    // 
    // For batch mode see:  http://projects.spring.io/spring-batch/
    // 
    //
    
}

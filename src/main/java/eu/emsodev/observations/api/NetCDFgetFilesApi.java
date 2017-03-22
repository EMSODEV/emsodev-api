package eu.emsodev.observations.api;

import java.io.File;

import io.swagger.annotations.*;
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

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Api(value = "files NETCDF", description = "the files API")
public interface NetCDFgetFilesApi {

    @ApiOperation(value = "I represnts the Time Series retrieved as NETCDF", notes = "Get NetCDF file of a specific Observatory`.", response = File.class, tags={ "Observations Time-series as NetCDF or ODV", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Time series list.", response = File.class) })
    @RequestMapping(value = "/fileasnetcdf",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<File> netcdfFilesGet();

    
    //Some example of spring method that return file
    // https://twilblog.github.io/java/spring/rest/file/stream/2015/08/14/return-a-file-stream-from-spring-rest.html
    // http://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
    // 
    // For batch mode see:  http://projects.spring.io/spring-batch/
    // 
    //
    
}

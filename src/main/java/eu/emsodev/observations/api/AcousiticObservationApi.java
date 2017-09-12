package eu.emsodev.observations.api;

import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.emsodev.observations.model.AcousticObservation;
import eu.emsodev.observations.model.AcousticObservationList;
import eu.emsodev.observations.model.Parameters;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Api(value = "Acoustic observation", description = "the acoustic observation API")
public interface AcousiticObservationApi {

	@ApiOperation(value = "It represents the date list of available acoustic files observed by an instrument.", notes = "Gets the date list of available acoustic files observed by a specific `EGIM instrument` of an `EGIM Observatory`.", response = Parameters.class, tags = { "Acoustic Observation", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Date list of available acoustic files for a specific EGIM Instrument of an EGIM Observatory.", response = AcousticObservationList.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/acousticfiledate", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<AcousticObservationList> acousticObservationListGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument
			,
			@ApiParam(value = "Date of Acoustic files. The date format is dd/MM/yyyy") @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date

			);
    
	@ApiOperation(value = "It represents a specific acoustic file observed by an instrument.", notes = "Gets an Acoustic file for a specific `EGIM instrument` of an `EGIM Observatory`.", response = Parameters.class, tags = { "Acoustic Observation", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Acoustic file for a specific EGIM Instrument of an EGIM Observatory.", response = String.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/acousticfile", produces = { "text/plain" }, method = RequestMethod.GET)
	ResponseEntity<String> acousticObservationFileGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument
			,
			@ApiParam(value = "Date of Acoustic file. The date format is dd/MM/yyyy",required = true) @RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date
			,
			@ApiParam(value = "Hour and Minute of an Acoustic file. The Hour Minute format is HHMM",required = true) @RequestParam(value = "hourMinute", required = true) String hourMinute

			);
}

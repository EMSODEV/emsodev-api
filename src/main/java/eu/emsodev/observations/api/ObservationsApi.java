package eu.emsodev.observations.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.emsodev.observations.model.Instrument;
import eu.emsodev.observations.model.InstrumentMetadataList;
import eu.emsodev.observations.model.Instruments;
import eu.emsodev.observations.model.Observations;
import eu.emsodev.observations.model.Observatories;
import eu.emsodev.observations.model.Parameters;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")
@Api(value = "observatories", description = "the observatories API")
public interface ObservationsApi {

	@ApiOperation(value = "It represents the EGIM observatories accessible through the EMSODEV Data Management Platform API.", notes = "Get a list of `EGIM observartories`.", response = Observatories.class, tags = { "Observatories", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "EGIM observatories list.", response = Observatories.class) })
	@RequestMapping(value = "/observatories", produces = { "application/json",
	"text/csv" }, method = RequestMethod.GET)
	ResponseEntity<Observatories> observatoriesGet();

	@ApiOperation(value = "It represents the instruments deployed in an EGIM observatory.", notes = "Get a list of `instruments` for an `EGIM observatory`.", response = Instruments.class, tags = { "Instruments", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of instruments for an EGIM observatory", response = Instruments.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Instruments> observatoriesObservatoryInstrumentsGet(
			@ApiParam(value = "EGIM observatory name", required = true) @PathVariable("observatory") String observatory

			);

	@ApiOperation(value = "An EGIM observatory instrument", notes = "Get `EGIM observatory instrument` resource.", response = Instrument.class, tags = { "Instruments", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Details of an EGIM observatory instrument", response = InstrumentMetadataList.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<InstrumentMetadataList> observatoriesObservatoryInstrumentsInstrumentGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,
			@ApiParam(value = "EGIM observatory instrument name.", required = true) @PathVariable("instrument") String instrument

			);

	@ApiOperation(value = "It represents the parametres observed by an instrument.", notes = "Gets the list of `EGIM parameters` for a specific `EGIM instrument` of an `EGIM Observatory`.", response = Parameters.class, tags = { "Parameters", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of parameters for a specific EGIM Instrument of an EGIM Observatory.", response = Parameters.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/parameters", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Parameters> observatoriesObservatoryInstrumentsInstrumentParametersGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument

			);

	@ApiOperation(value = "Time-series of a specific EGIM parameter.", notes = "Gets the time-series of a specific `EGIM parameter` in a certain time range for an `EGIM instrument` of an `EGIM observatory`.", response = Observations.class, tags = { "Observations Time-series", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Time-series of a specific EGIM parameter.", response = Observations.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/parameters/{parameter}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Observations> observatoriesObservatoryInstrumentsInstrumentParametersParameterGet(
			@ApiParam(value = "EGIM observatory name.", required = true) @PathVariable("observatory") String observatory

			,
			@ApiParam(value = "EGIM instrument name.", required = true) @PathVariable("instrument") String instrument

			,
			@ApiParam(value = "EGIM parameter name.", required = true) @PathVariable("parameter") String parameter

			,
			@ApiParam(value = "The start time for the query. This may be an absolute or relative time. The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>-ago` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a start time of `1h-ago` and leave out the end time, our query will return data start at 1 hour ago to the current time.", required = true) @RequestParam(value = "startDate", required = true) String startDate

			,
			@ApiParam(value = "The end time for the query in Unix (or POSIX) style. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) String endDate

			);

}

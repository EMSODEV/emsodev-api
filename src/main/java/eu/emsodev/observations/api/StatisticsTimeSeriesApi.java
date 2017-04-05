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

import eu.emsodev.observations.model.ObservationsStats;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")
@Api(value = "observatories", description = "the observatories API")
public interface StatisticsTimeSeriesApi {

	@ApiOperation(value = "Minimum values of time-series of a specific parameter.", notes = "Gets the minimum value over a selected time range from the available time-series of an EGIM parameter measured at an EGIM instrument deployed in a specific observatory", response = ObservationsStats.class, tags = { "Observations Time-series statistics", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Time-series of a specific parameter.", response = ObservationsStats.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/parameters/{parameter}/min", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterMinsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The downsample. This may be an absolute or relative time.The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a downsample time of `1h` the query will return data aggregated at 1 hour", required = true) @RequestParam(value = "downSample", required = true) String downSample,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate);

	
	
	@ApiOperation(value = "Maximum values of time-series of a specific parameter.", notes = "Gets the maximum value over a selected time range from the available time-series of an EGIM parameter measured at an EGIM instrument deployed in a specific observatory", response = ObservationsStats.class, tags = { "Observations Time-series statistics", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Time-series of a specific parameter.", response = ObservationsStats.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/parameters/{parameter}/max", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterMaxsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The downsample. This may be an absolute or relative time.The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a downsample time of `1h` the query will return data aggregated at 1 hour", required = true) @RequestParam(value = "downSample", required = true) String downSample,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate);

	
	
	@ApiOperation(value = "Average values of time-series of a specific parameter.", notes = "Gets the average value over a selected time range from the available time-series of an EGIM parameter measured at an EGIM instrument deployed in a specific observatory", response = ObservationsStats.class, tags = { "Observations Time-series statistics", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Time-series of a specific parameter.", response = ObservationsStats.class) })
	@RequestMapping(value = "/observatories/{observatory}/instruments/{instrument}/parameters/{parameter}/avg", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterAvgsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The downsample. This may be an absolute or relative time.The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a downsample time of `1h` the query will return data aggregated at 1 hour", required = true) @RequestParam(value = "downSample", required = true) String downSample,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate);
	
}

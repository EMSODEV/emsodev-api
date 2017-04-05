package eu.emsodev.observations.api;

import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eu.emsodev.observations.model.Instrument;
import eu.emsodev.observations.model.ObservationsStats;
import eu.emsodev.observations.model.ObservationsStatsStats;
import eu.emsodev.observations.model.Observatory;
import eu.emsodev.observations.model.Parameter;
//import org.codehaus.jackson.map.ObjectMapper;
import eu.emsodev.observations.utilities.EmsodevUtility;

@Configuration
@PropertySource("${api.properties.home}")
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		WebMvcAutoConfiguration.class })
public class StatisticsTimeSeriesController implements StatisticsTimeSeriesApi {

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

	@Value("${emsodev.global.setting.urlToCall.observatoriesObservatoryInstrumentsInstrumentParametersParameterGet}")
	private String statsRootUrl;

	protected RestTemplate restTemplate;

	public StatisticsTimeSeriesController() {
	}

	/**
	 * Property placeholder configurer needed to process @Value annotations
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterMinsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The downsample. This may be an absolute or relative time.The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a downsample time of `1h` the query will return data aggregated at 1 hour", required = true) @RequestParam(value = "downSample", required = true) String downSample,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		// Create a map of params to pass add as placeholder after parameter
		// value in the following compositeUrl
		Map<String, String> params = new HashMap<String, String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID", instrument);

		String compositeUrl = statsRootUrl 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate) 
				+ "&m=sum:" + downSample
				+ "-min:" + parameter + "{params}" + "&end="
				+ EmsodevUtility.getDateAsStringTimestampFormat(endDate);

		// The response as string of the urlToCall - This Url do not allows
		// blanck spaces beetwen the params, for this reason is trimmed
		Object response = restTemplate.getForObject(compositeUrl, Object.class,
				params.toString().replace(" ", ""));

		ObservationsStats statistics = getStatistics(response, "minimum");

		return new ResponseEntity<ObservationsStats>(statistics, HttpStatus.OK);
	}

	public ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterMaxsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The downsample. This may be an absolute or relative time.The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a downsample time of `1h` the query will return data aggregated at 1 hour", required = true) @RequestParam(value = "downSample", required = true) String downSample,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		// Create a map of params to pass add as placeholder after parameter
		// value in the following compositeUrl
		Map<String, String> params = new HashMap<String, String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID", instrument);

		String compositeUrl = statsRootUrl 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate)  
				+ "&m=sum:" + downSample
				+ "-max:" + parameter + "{params}" + "&end="
				+ EmsodevUtility.getDateAsStringTimestampFormat(endDate);

		// The response as string of the urlToCall - This Url do not allows
		// blanck spaces beetwen the params, for this reason is trimmed
		Object response = restTemplate.getForObject(compositeUrl, Object.class,
				params.toString().replace(" ", ""));

		ObservationsStats statistics = getStatistics(response, "maximum");

		return new ResponseEntity<ObservationsStats>(statistics, HttpStatus.OK);
	}

	public ResponseEntity<ObservationsStats> observatoriesObservatoryInstrumentsInstrumentParametersParameterAvgsGet(
			@ApiParam(value = "The observatory name.", required = true) @PathVariable("observatory") String observatory,
			@ApiParam(value = "The instrument name.", required = true) @PathVariable("instrument") String instrument,
			@ApiParam(value = "The parameter name.", required = true) @PathVariable("parameter") String parameter,
			@ApiParam(value = "The downsample. This may be an absolute or relative time.The **Absolute time** follows the Unix (or POSIX) style timestamp. The **Relative time** follows the format `<amount><time unit>` where `<amount>` is the number of time units and `<time unit>` is the unit of time *(ms->milliseconds, s->seconds, h->hours, d->days, w->weeks, n->months, y->years)*. For example, if we provide a downsample time of `1h` the query will return data aggregated at 1 hour", required = true) @RequestParam(value = "downSample", required = true) String downSample,
			@ApiParam(value = "Beginning date for the time series range. The date format is dd/MM/yyyy.", required = true) @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@ApiParam(value = "End date for the time series range. The date format is dd/MM/yyyy. If the end time is not supplied, the *current time* will be used.") @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		// Create the restTemplate object with or without proxy
		// istantiateRestTemplate();
		restTemplate = EmsodevUtility.istantiateRestTemplate(enableProxy,
				username, password, proxyUrl, proxyPort);

		// Create a map of params to pass add as placeholder after parameter
		// value in the following compositeUrl
		Map<String, String> params = new HashMap<String, String>();
		params.put("EGIMNode", observatory);
		params.put("SensorID", instrument);

		String compositeUrl = statsRootUrl 
				+ EmsodevUtility.getDateAsStringTimestampFormat(startDate) 
				+ "&m=sum:" + downSample
				+ "-avg:" + parameter + "{params}" + "&end="
				+ EmsodevUtility.getDateAsStringTimestampFormat(endDate);

		// The response as string of the urlToCall - This Url do not allows
		// blanck spaces beetwen the params, for this reason is trimmed
		Object response = restTemplate.getForObject(compositeUrl, Object.class,
				params.toString().replace(" ", ""));

		ObservationsStats statistics = getStatistics(response, "average");

		return new ResponseEntity<ObservationsStats>(statistics, HttpStatus.OK);
	}

	private ObservationsStats getStatistics(Object response, String statType) {

		// Declare the final response object outside the loop
		ObservationsStats observationsStats = new ObservationsStats();

		try {
			// Properties to pass to the outpout object
			String egimNodeName = "";
			String sensorIdName = "";
			String metricName = "";
			// Property that contain the readed timeseries without brackets and
			// quotes and that will be used to create an array of timeseries
			String jobjectDpsCleaned = null;
			// Gson google object is used insteaf og JSONObject becouse let the
			// result sorted by timestamp
			Gson gson = new Gson();
			// Create a jelement that contains the response
			JsonElement jelement = gson.fromJson(response.toString(),
					JsonElement.class);
			// The response is an array. Create an jsonArray that contain the
			// response
			JsonArray jsonarray = jelement.getAsJsonArray();
			// Get the first and last item of the array
			JsonObject jarrayItem = jsonarray.get(0).getAsJsonObject();
			// The value of metric attribute
			JsonObject jobject = jarrayItem.getAsJsonObject();
			metricName = jobject.get("metric").getAsString();

			// Get the an jsonObject with that rapresent the "tags" branche
			jobject = jobject.getAsJsonObject("tags");
			// Get the value of attribute of SensorID and EGIMNode of the "tags"
			// branche
			sensorIdName = jobject.get("SensorID").getAsString();
			egimNodeName = jobject.get("EGIMNode").getAsString();

			// set the instrument name with the previous extract value
			Instrument inst = new Instrument();
			inst.setName(sensorIdName);
			// set the parameter name with with the previous extract value
			Parameter par = new Parameter();
			par.setName(metricName);
			// set the observatory name with with the previous extract value
			Observatory observ = new Observatory();
			observ.setName(egimNodeName);

			// Get the an jsonObject with that rapresent the "dps" branche
			JsonObject jobjectDps = jarrayItem.getAsJsonObject();
			jobjectDps = jobjectDps.getAsJsonObject("dps");
			jobjectDpsCleaned = jobjectDps.toString().replace("\"", "")
					.replace("{", "").replace("}", "");
			// Array of timeseries key: timestamp, value: value
			String[] arrayDps = jobjectDpsCleaned.split(",");
			// Declare a List of Observation
			ArrayList<ObservationsStatsStats> statisticsList = new ArrayList<ObservationsStatsStats>();
			// For each array item extract the key and value to set to a new
			// Observation object in the loop
			for (int index = 0, n = arrayDps.length; index < n; index++) {
				String item = arrayDps[index];
				//
				ObservationsStatsStats stats = new ObservationsStatsStats();
				stats.setPhenomenonTime(Long.valueOf(item.substring(0,
						item.indexOf(":"))));
				stats.setStatValue(Double.valueOf(item.substring(
						(item.indexOf(":") + 1), item.length())));
				statisticsList.add(stats);
			}

			// Compose the final bean to return
			observationsStats.setStatistics(statisticsList);
			observationsStats.setStatisticType(statType);
			observationsStats.setParameter(par);
			observationsStats.setInstrument(inst);
			observationsStats.setNode(observ);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return observationsStats;
	}

}

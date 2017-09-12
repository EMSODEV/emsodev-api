package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONObject;

import com.google.gson.JsonObject;


/**
 * Definition of the Acoustic Observarion
 */
@ApiModel(description = "Definition of the Acoustic Observation date")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class AcousticObservationHourMinute  {

  
private String acousticObservationHourMinute;

/**
 * @return the acousticObservationHourMinute
 */
public String getAcousticObservationHourMinute() {
	return acousticObservationHourMinute;
}

/**
 * @param acousticObservationHourMinute the acousticObservationHourMinute to set
 */
public void setAcousticObservationHourMinute(
		String acousticObservationHourMinute) {
	this.acousticObservationHourMinute = acousticObservationHourMinute;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "AcousticObservationHourMinute ["
			+ (acousticObservationHourMinute != null ? "acousticObservationHourMinute="
					+ acousticObservationHourMinute
					: "") + "]";
}

public AcousticObservationHourMinute() {
	super();
	// TODO Auto-generated constructor stub
}

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime
			* result
			+ ((acousticObservationHourMinute == null) ? 0
					: acousticObservationHourMinute.hashCode());
	return result;
}

/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	AcousticObservationHourMinute other = (AcousticObservationHourMinute) obj;
	if (acousticObservationHourMinute == null) {
		if (other.acousticObservationHourMinute != null)
			return false;
	} else if (!acousticObservationHourMinute
			.equals(other.acousticObservationHourMinute))
		return false;
	return true;
}


}


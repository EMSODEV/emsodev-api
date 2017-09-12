package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONObject;

import com.google.gson.JsonObject;


/**
 * Definition of the Acoustic Observarion
 */
@ApiModel(description = "Definition of the Acoustic Observation date")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class AcousticObservationDate  {

  
private String acousticObservationDate;

private List<AcousticObservationHourMinute> observationsHourMinuteList = new ArrayList<AcousticObservationHourMinute>();

public AcousticObservationDate addObservationsHourMinuteItem(AcousticObservationHourMinute observationsHourMinuteItem) {
    this.observationsHourMinuteList.add(observationsHourMinuteItem);
    return this;
  }



public AcousticObservationDate() {
	super();
	// TODO Auto-generated constructor stub
}



/**
 * @return the acousticObservationDate
 */
public String getAcousticObservationDate() {
	return acousticObservationDate;
}

/**
 * @param acousticObservationDate the acousticObservationDate to set
 */
public void setAcousticObservationDate(String acousticObservationDate) {
	this.acousticObservationDate = acousticObservationDate;
}

/**
 * @return the observationsHourMinuteList
 */
public List<AcousticObservationHourMinute> getObservationsHourMinuteList() {
	return observationsHourMinuteList;
}

/**
 * @param observationsHourMinuteList the observationsHourMinuteList to set
 */
public void setObservationsHourMinuteList(
		List<AcousticObservationHourMinute> observationsHourMinuteList) {
	this.observationsHourMinuteList = observationsHourMinuteList;
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
			+ ((acousticObservationDate == null) ? 0 : acousticObservationDate
					.hashCode());
	result = prime
			* result
			+ ((observationsHourMinuteList == null) ? 0
					: observationsHourMinuteList.hashCode());
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
	AcousticObservationDate other = (AcousticObservationDate) obj;
	if (acousticObservationDate == null) {
		if (other.acousticObservationDate != null)
			return false;
	} else if (!acousticObservationDate.equals(other.acousticObservationDate))
		return false;
	if (observationsHourMinuteList == null) {
		if (other.observationsHourMinuteList != null)
			return false;
	} else if (!observationsHourMinuteList
			.equals(other.observationsHourMinuteList))
		return false;
	return true;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "AcousticObservationDate ["
			+ (acousticObservationDate != null ? "acousticObservationDate="
					+ acousticObservationDate + ", " : "")
			+ (observationsHourMinuteList != null ? "observationsHourMinuteList="
					+ observationsHourMinuteList
					: "") + "]";
}



}


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
@ApiModel(description = "Definition of the Acoustic Observation")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class AcousticObservation  {

  
private String observationBody;

/**
 * @return the observationBody
 */
public String getObservationBody() {
	return observationBody;
}

/**
 * @param observationBody the observationBody to set
 */
public void setObservationBody(String observationBody) {
	this.observationBody = observationBody;
}

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
			+ ((observationBody == null) ? 0 : observationBody.hashCode());
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
	AcousticObservation other = (AcousticObservation) obj;
	if (observationBody == null) {
		if (other.observationBody != null)
			return false;
	} else if (!observationBody.equals(other.observationBody))
		return false;
	return true;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "AcousticObservation ["
			+ (observationBody != null ? "observationBody=" + observationBody
					: "") + "]";
}


}


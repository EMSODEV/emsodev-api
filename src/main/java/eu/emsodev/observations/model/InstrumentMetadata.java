package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONObject;


/**
 * Definition of the Instrument Metadata
 */
@ApiModel(description = "Definition of the Instrument Metadata")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class InstrumentMetadata   {


private String validityDate;
  
private String metadata; 
 


public String getValidityDate() {
	return validityDate;
}


public void setValidityDate(String validityDate) {
	this.validityDate = validityDate;
}


 
@ApiModelProperty(value = "Metadata of the Instrument")
public String getMetadata() {
	return metadata;
}


public void setMetadata(String metadata) {
	this.metadata = metadata;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
	result = prime * result
			+ ((validityDate == null) ? 0 : validityDate.hashCode());
	return result;
}


@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	InstrumentMetadata other = (InstrumentMetadata) obj;
	if (metadata == null) {
		if (other.metadata != null)
			return false;
	} else if (!metadata.equals(other.metadata))
		return false;
	if (validityDate == null) {
		if (other.validityDate != null)
			return false;
	} else if (!validityDate.equals(other.validityDate))
		return false;
	return true;
}


@Override
public String toString() {
	return "InstrumentMetadata [validityDate=" + validityDate + ", metadata="
			+ metadata + "]";
}


/**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


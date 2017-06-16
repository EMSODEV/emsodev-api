package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;



/**
 * Definition of an Observation
 **/

/**
 * Definition of an Observation
 */
@ApiModel(description = "Definition of an Observation")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class Observation   {
  private Long phenomenonTime = null;

  private Double value = null;


  

   /**
   * UnixTime in seconds
   * @return phenomenonTime
  **/
  @ApiModelProperty(value = "UnixTime in seconds")
  public Long getPhenomenonTime() {
    return phenomenonTime;
  }

  public void setPhenomenonTime(Long phenomenonTime) {
    this.phenomenonTime = phenomenonTime;
  }

  public Observation value(Double value) {
    this.value = value;
    return this;
  }

   /**
   * observation value
   * @return value
  **/
  @ApiModelProperty(value = "observation value")
  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  

public Observation() {
	super();
	// TODO Auto-generated constructor stub
}



public Observation(Long phenomenonTime, Double value) {
	super();
	this.phenomenonTime = phenomenonTime;
	this.value = value;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "Observation ["
			+ (phenomenonTime != null ? "phenomenonTime=" + phenomenonTime
					+ ", " : "") + (value != null ? "value=" + value : "")
			+ "]";
}

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
			+ ((phenomenonTime == null) ? 0 : phenomenonTime.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
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
	Observation other = (Observation) obj;
	if (phenomenonTime == null) {
		if (other.phenomenonTime != null)
			return false;
	} else if (!phenomenonTime.equals(other.phenomenonTime))
		return false;
	if (value == null) {
		if (other.value != null)
			return false;
	} else if (!value.equals(other.value))
		return false;
	return true;
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


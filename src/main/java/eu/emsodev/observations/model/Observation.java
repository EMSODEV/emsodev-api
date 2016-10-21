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

  public Observation phenomenonTime(Long phenomenonTime) {
    this.phenomenonTime = phenomenonTime;
    return this;
  }

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Observation observation = (Observation) o;
    return Objects.equals(this.phenomenonTime, observation.phenomenonTime) &&
        Objects.equals(this.value, observation.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phenomenonTime, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Observation {\n");
    
    sb.append("    phenomenonTime: ").append(toIndentedString(phenomenonTime)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("}");
    return sb.toString();
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


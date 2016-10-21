package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * Array of Observations
 **/

/**
 * Array of Observations
 */
@ApiModel(description = "Array of Observations")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class Observations   {
  private Observatory observatory = null;

  private Instrument instrument = null;

  private Parameter parameter = null;

  private List<Observation> observations = new ArrayList<Observation>();

  public Observations observatory(Observatory observatory) {
    this.observatory = observatory;
    return this;
  }

   /**
   * Get observatory
   * @return observatory
  **/
  @ApiModelProperty(value = "")
  public Observatory getObservatory() {
    return observatory;
  }

  public void setObservatory(Observatory observatory) {
    this.observatory = observatory;
  }

  public Observations instrument(Instrument instrument) {
    this.instrument = instrument;
    return this;
  }

   /**
   * Get instrument
   * @return instrument
  **/
  @ApiModelProperty(value = "")
  public Instrument getInstrument() {
    return instrument;
  }

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  public Observations parameter(Parameter parameter) {
    this.parameter = parameter;
    return this;
  }

   /**
   * Get parameter
   * @return parameter
  **/
  @ApiModelProperty(value = "")
  public Parameter getParameter() {
    return parameter;
  }

  public void setParameter(Parameter parameter) {
    this.parameter = parameter;
  }

  public Observations observations(List<Observation> observations) {
    this.observations = observations;
    return this;
  }

  public Observations addObservationsItem(Observation observationsItem) {
    this.observations.add(observationsItem);
    return this;
  }

   /**
   * Get observations
   * @return observations
  **/
  @ApiModelProperty(value = "")
  public List<Observation> getObservations() {
    return observations;
  }

  public void setObservations(List<Observation> observations) {
    this.observations = observations;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Observations observations = (Observations) o;
    return Objects.equals(this.observatory, observations.observatory) &&
        Objects.equals(this.instrument, observations.instrument) &&
        Objects.equals(this.parameter, observations.parameter) &&
        Objects.equals(this.observations, observations.observations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(observatory, instrument, parameter, observations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Observations {\n");
    
    sb.append("    observatory: ").append(toIndentedString(observatory)).append("\n");
    sb.append("    instrument: ").append(toIndentedString(instrument)).append("\n");
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("    observations: ").append(toIndentedString(observations)).append("\n");
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


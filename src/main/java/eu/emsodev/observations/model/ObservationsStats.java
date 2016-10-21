package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;



/**
 * Obesrvations statistics
 **/

/**
 * Obesrvations statistics
 */
@ApiModel(description = "Obesrvations statistics")
//@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class ObservationsStats   {
  private Observatory node = null;

  private Instrument instrument = null;

  private Parameter parameter = null;

  private ObservationsStatsStats stats = null;

  public ObservationsStats node(Observatory node) {
    this.node = node;
    return this;
  }

   /**
   * Get node
   * @return node
  **/
  @ApiModelProperty(value = "")
  public Observatory getNode() {
    return node;
  }

  public void setNode(Observatory node) {
    this.node = node;
  }

  public ObservationsStats instrument(Instrument instrument) {
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

  public ObservationsStats parameter(Parameter parameter) {
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

  public ObservationsStats stats(ObservationsStatsStats stats) {
    this.stats = stats;
    return this;
  }

   /**
   * Get stats
   * @return stats
  **/
  @ApiModelProperty(value = "")
  public ObservationsStatsStats getStats() {
    return stats;
  }

  public void setStats(ObservationsStatsStats stats) {
    this.stats = stats;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ObservationsStats observationsStats = (ObservationsStats) o;
    return Objects.equals(this.node, observationsStats.node) &&
        Objects.equals(this.instrument, observationsStats.instrument) &&
        Objects.equals(this.parameter, observationsStats.parameter) &&
        Objects.equals(this.stats, observationsStats.stats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(node, instrument, parameter, stats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ObservationsStats {\n");
    
    sb.append("    node: ").append(toIndentedString(node)).append("\n");
    sb.append("    instrument: ").append(toIndentedString(instrument)).append("\n");
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("    stats: ").append(toIndentedString(stats)).append("\n");
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


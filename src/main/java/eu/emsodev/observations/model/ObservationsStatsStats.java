package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * ObservationsStatsStats
 */
//@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class ObservationsStatsStats   {
  private Double avg = null;

  private Double min = null;

  private Double max = null;

  public ObservationsStatsStats avg(Double avg) {
    this.avg = avg;
    return this;
  }

   /**
   * average value
   * @return avg
  **/
  @ApiModelProperty(value = "average value")
  public Double getAvg() {
    return avg;
  }

  public void setAvg(Double avg) {
    this.avg = avg;
  }

  public ObservationsStatsStats min(Double min) {
    this.min = min;
    return this;
  }

   /**
   * minimum value
   * @return min
  **/
  @ApiModelProperty(value = "minimum value")
  public Double getMin() {
    return min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  public ObservationsStatsStats max(Double max) {
    this.max = max;
    return this;
  }

   /**
   * maximum value
   * @return max
  **/
  @ApiModelProperty(value = "maximum value")
  public Double getMax() {
    return max;
  }

  public void setMax(Double max) {
    this.max = max;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ObservationsStatsStats observationsStatsStats = (ObservationsStatsStats) o;
    return Objects.equals(this.avg, observationsStatsStats.avg) &&
        Objects.equals(this.min, observationsStatsStats.min) &&
        Objects.equals(this.max, observationsStatsStats.max);
  }

  @Override
  public int hashCode() {
    return Objects.hash(avg, min, max);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ObservationsStatsStats {\n");
    
    sb.append("    avg: ").append(toIndentedString(avg)).append("\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
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


package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * Array of instruments
 **/

/**
 * Array of instruments
 */
@ApiModel(description = "Array of instruments")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class Instruments   {
  private List<Instrument> instruments = new ArrayList<Instrument>();

  public Instruments instruments(List<Instrument> instruments) {
    this.instruments = instruments;
    return this;
  }

  public Instruments addInstrumentsItem(Instrument instrumentsItem) {
    this.instruments.add(instrumentsItem);
    return this;
  }

   /**
   * Get instruments
   * @return instruments
  **/
  @ApiModelProperty(value = "")
  public List<Instrument> getInstruments() {
    return instruments;
  }

  public void setInstruments(List<Instrument> instruments) {
    this.instruments = instruments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Instruments instruments = (Instruments) o;
    return Objects.equals(this.instruments, instruments.instruments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instruments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Instruments {\n");
    
    sb.append("    instruments: ").append(toIndentedString(instruments)).append("\n");
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


package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;



/**
 * Definition of an Observatory
 **/

/**
 * Definition of an Observatory
 */
@ApiModel(description = "Definition of an Observatory")
//@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class Observatory   {
  private String name = null;



public Observatory name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the observatory
   * @return name
  **/
  @ApiModelProperty(value = "Name of the observatory")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Observatory observatory = (Observatory) o;
    return Objects.equals(this.name, observatory.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Observatory {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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


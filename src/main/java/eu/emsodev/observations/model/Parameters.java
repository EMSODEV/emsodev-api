package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * Array of Parameters
 **/

/**
 * Array of Parameters
 */
@ApiModel(description = "Array of Parameters")
//@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")
public class Parameters   {
  private List<Parameter> parameters = new ArrayList<Parameter>();

  public Parameters(String parameter) {
	// TODO Auto-generated constructor stub
}

public Parameters parameters(List<Parameter> parameters) {
    this.parameters = parameters;
    return this;
  }

  public Parameters addParametersItem(Parameter parametersItem) {
    this.parameters.add(parametersItem);
    return this;
  }

   /**
   * Get parameters
   * @return parameters
  **/
  @ApiModelProperty(value = "")
  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Parameters parameters = (Parameters) o;
    return Objects.equals(this.parameters, parameters.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Parameters {\n");
    
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
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


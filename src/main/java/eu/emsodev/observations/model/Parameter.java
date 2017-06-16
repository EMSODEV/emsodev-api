package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;



/**
 * Definition of a Parameter
 **/

/**
 * Definition of a Parameter
 */
@ApiModel(description = "Definition of a Parameter")
//@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class Parameter   {
  private String name = null;
  private String uom = null;

 

public Parameter name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of a Parameter
   * @return name
  **/
  @ApiModelProperty(value = "Name of a Parameter")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  

  /**
 * @return the uom
 */
public String getUom() {
	return uom;
}

/**
 * @param uom the uom to set
 */
public void setUom(String uom) {
	this.uom = uom;
}


  /* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((uom == null) ? 0 : uom.hashCode());
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
	Parameter other = (Parameter) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (uom == null) {
		if (other.uom != null)
			return false;
	} else if (!uom.equals(other.uom))
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


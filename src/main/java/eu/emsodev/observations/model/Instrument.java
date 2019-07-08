package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONObject;



/**
 * Definition of an Instrument
 **/

/**
 * Definition of an Instrument
 */
@ApiModel(description = "Definition of an Instrument")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class Instrument   {
  
private String name = null;  
private String sensorLongName = null;
private String sn = null;
private String sensorType = null;

private String sensorShortName = null;
private String sensorModelName = null;
private String sensorManifacturerName = null;
private String sensorUUID = null;
//private ArrayList<String> metadataList = null; 
  
 
public Instrument name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of an Instrument
   * @return name
  **/
  @ApiModelProperty(value = "Name of an Instrument")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

 

// @ApiModelProperty(value = "Metadata of an Instrument")
//public ArrayList<String> getMetadataList() {
//		return metadataList;
//	}
//
//	public void setMetadataList(ArrayList<String> metadataList) {
//		this.metadataList = metadataList;
//	}

/**
 * @return the sensorLongName
 */
public String getSensorLongName() {
	return sensorLongName;
}

/**
 * @param sensorLongName the sensorLongName to set
 */
public void setSensorLongName(String sensorLongName) {
	this.sensorLongName = sensorLongName;
}

/**
 * @return the sn
 */
public String getSn() {
	return sn;
}

/**
 * @param sn the sn to set
 */
public void setSn(String sn) {
	this.sn = sn;
}

/**
 * @return the sensorType
 */
public String getSensorType() {
	return sensorType;
}

/**
 * @param sensorType the sensorType to set
 */
public void setSensorType(String sensorType) {
	this.sensorType = sensorType;
}




/**
 * @return the sensorShortName
 */
public String getSensorShortName() {
	return sensorShortName;
}

/**
 * @param sensorShortName the sensorShortName to set
 */
public void setSensorShortName(String sensorShortName) {
	this.sensorShortName = sensorShortName;
}

/**
 * @return the sensorModelName
 */
public String getSensorModelName() {
	return sensorModelName;
}

/**
 * @param sensorModelName the sensorModelName to set
 */
public void setSensorModelName(String sensorModelName) {
	this.sensorModelName = sensorModelName;
}

/**
 * @return the sensorManifacturerName
 */
public String getSensorManifacturerName() {
	return sensorManifacturerName;
}

/**
 * @param sensorManifacturerName the sensorManifacturerName to set
 */
public void setSensorManifacturerName(String sensorManifacturerName) {
	this.sensorManifacturerName = sensorManifacturerName;
}

/**
 * @return the sensorUUID
 */
public String getSensorUUID() {
	return sensorUUID;
}

/**
 * @param sensorUUID the sensorUUID to set
 */
public void setSensorUUID(String sensorUUID) {
	this.sensorUUID = sensorUUID;
}

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result
			+ ((sensorLongName == null) ? 0 : sensorLongName.hashCode());
	result = prime
			* result
			+ ((sensorManifacturerName == null) ? 0 : sensorManifacturerName
					.hashCode());
	result = prime * result
			+ ((sensorModelName == null) ? 0 : sensorModelName.hashCode());
	result = prime * result
			+ ((sensorShortName == null) ? 0 : sensorShortName.hashCode());
	result = prime * result
			+ ((sensorType == null) ? 0 : sensorType.hashCode());
	result = prime * result
			+ ((sensorUUID == null) ? 0 : sensorUUID.hashCode());
	result = prime * result + ((sn == null) ? 0 : sn.hashCode());
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
	Instrument other = (Instrument) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (sensorLongName == null) {
		if (other.sensorLongName != null)
			return false;
	} else if (!sensorLongName.equals(other.sensorLongName))
		return false;
	if (sensorManifacturerName == null) {
		if (other.sensorManifacturerName != null)
			return false;
	} else if (!sensorManifacturerName.equals(other.sensorManifacturerName))
		return false;
	if (sensorModelName == null) {
		if (other.sensorModelName != null)
			return false;
	} else if (!sensorModelName.equals(other.sensorModelName))
		return false;
	if (sensorShortName == null) {
		if (other.sensorShortName != null)
			return false;
	} else if (!sensorShortName.equals(other.sensorShortName))
		return false;
	if (sensorType == null) {
		if (other.sensorType != null)
			return false;
	} else if (!sensorType.equals(other.sensorType))
		return false;
	if (sensorUUID == null) {
		if (other.sensorUUID != null)
			return false;
	} else if (!sensorUUID.equals(other.sensorUUID))
		return false;
	if (sn == null) {
		if (other.sn != null)
			return false;
	} else if (!sn.equals(other.sn))
		return false;
	return true;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "Instrument ["
			+ (name != null ? "name=" + name + ", " : "")
			+ (sensorLongName != null ? "sensorLongName=" + sensorLongName
					+ ", " : "") + (sn != null ? "sn=" + sn + ", " : "")
			+ (sensorType != null ? "sensorType=" + sensorType : "") + "]";
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


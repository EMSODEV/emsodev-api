package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * Array of instruments
 **/

/**
 * Array of instruments
 */
@ApiModel(description = "Array of instrument metadata")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class InstrumentMetadataList  implements Serializable {
  private List<InstrumentMetadata> metadataList = new ArrayList<InstrumentMetadata>();
  private String instrumentName;
  
  public InstrumentMetadataList instrumentMetadataList(List<InstrumentMetadata> metadataList) {
    this.metadataList = metadataList;
    return this;
  }

  public InstrumentMetadataList addInstrumentsMetadataItem(InstrumentMetadata metadataItem) {
    this.metadataList.add(metadataItem);
    return this;
  }


  
  
  public String getInstrumentName() {
	return instrumentName;
}

public void setInstrumentName(String name) {
	this.instrumentName = name;
}

public List<InstrumentMetadata> getMetadataList() {
	return metadataList;
}

public void setMetadataList(
		List<InstrumentMetadata> metadataList) {
	this.metadataList = metadataList;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime
			* result
			+ ((metadataList == null) ? 0 : metadataList
					.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	InstrumentMetadataList other = (InstrumentMetadataList) obj;
	if (metadataList == null) {
		if (other.metadataList != null)
			return false;
	} else if (!metadataList.equals(other.metadataList))
		return false;
	return true;
}



@Override
public String toString() {
	return "MetadataList [metadataList="
			+ metadataList + "]";
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


package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONObject;

import com.google.gson.JsonObject;


/**
 * Definition of the Acoustic Observarion
 */
@ApiModel(description = "Definition of the Acoustic Observation")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-10-04T09:41:08.096Z")

public class AcousticObservationList  {

  
	  private Observatory observatory = null;

	  private Instrument instrument = null;

	  private List<AcousticObservationDate> acousticOobservationDate = new ArrayList<AcousticObservationDate>();

	  
	  public AcousticObservationList addAcousticObservationItem(AcousticObservationDate acousticObservationDateItem) {
		    this.acousticOobservationDate.add(acousticObservationDateItem);
		    return this;
		  }
	  
	/**
	 * @return the observatory
	 */
	public Observatory getObservatory() {
		return observatory;
	}

	/**
	 * @param observatory the observatory to set
	 */
	public void setObservatory(Observatory observatory) {
		this.observatory = observatory;
	}

	/**
	 * @return the instrument
	 */
	public Instrument getInstrument() {
		return instrument;
	}

	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the acousticOobservationDate
	 */
	public List<AcousticObservationDate> getAcousticOobservationDate() {
		return acousticOobservationDate;
	}

	/**
	 * @param acousticOobservationDate the acousticOobservationDate to set
	 */
	public void setAcousticOobservationDate(
			List<AcousticObservationDate> acousticOobservationDate) {
		this.acousticOobservationDate = acousticOobservationDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((acousticOobservationDate == null) ? 0
						: acousticOobservationDate.hashCode());
		result = prime * result
				+ ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result
				+ ((observatory == null) ? 0 : observatory.hashCode());
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
		AcousticObservationList other = (AcousticObservationList) obj;
		if (acousticOobservationDate == null) {
			if (other.acousticOobservationDate != null)
				return false;
		} else if (!acousticOobservationDate
				.equals(other.acousticOobservationDate))
			return false;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (observatory == null) {
			if (other.observatory != null)
				return false;
		} else if (!observatory.equals(other.observatory))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AcousticObservationList ["
				+ (observatory != null ? "observatory=" + observatory + ", "
						: "")
				+ (instrument != null ? "instrument=" + instrument + ", " : "")
				+ (acousticOobservationDate != null ? "acousticOobservationDate="
						+ acousticOobservationDate
						: "") + "]";
	}
	  
	  

}


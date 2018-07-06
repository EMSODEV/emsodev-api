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

	  private List<AcousticObservationDate> acousticObservationDate = new ArrayList<AcousticObservationDate>();

	  
	  public AcousticObservationList addAcousticObservationItem(AcousticObservationDate acousticObservationDateItem) {
		    this.acousticObservationDate.add(acousticObservationDateItem);
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
	 * @return the acousticObservationDate
	 */
	public List<AcousticObservationDate> getAcousticObservationDate() {
		return acousticObservationDate;
	}

	/**
	 * @param acousticObservationDate the acousticObservationDate to set
	 */
	public void setAcousticObservationDate(
			List<AcousticObservationDate> acousticObservationDate) {
		this.acousticObservationDate = acousticObservationDate;
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
				+ ((acousticObservationDate == null) ? 0
						: acousticObservationDate.hashCode());
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
		if (acousticObservationDate == null) {
			if (other.acousticObservationDate != null)
				return false;
		} else if (!acousticObservationDate
				.equals(other.acousticObservationDate))
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
				+ (acousticObservationDate != null ? "acousticObservationDate="
						+ acousticObservationDate : "") + "]";
	}



	

	

	  
	  

}


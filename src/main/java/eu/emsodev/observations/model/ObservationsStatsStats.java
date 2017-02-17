package eu.emsodev.observations.model;

/**
 * ObservationsStatsStats
 */
// @javax.annotation.Generated(value =
// "class io.swagger.codegen.languages.SpringCodegen", date =
// "2016-10-04T09:41:08.096Z")

public class ObservationsStatsStats {

	private Double statValue = null;
	private Long phenomenonTime = null;

	/**
	 * @return the statValue
	 */
	public Double getStatValue() {
		return statValue;
	}

	/**
	 * @param statValue
	 *            the statValue to set
	 */
	public void setStatValue(Double statValue) {
		this.statValue = statValue;
	}

	/**
	 * @return the phenomenonTime
	 */
	public Long getPhenomenonTime() {
		return phenomenonTime;
	}

	/**
	 * @param phenomenonTime
	 *            the phenomenonTime to set
	 */
	public void setPhenomenonTime(Long phenomenonTime) {
		this.phenomenonTime = phenomenonTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((phenomenonTime == null) ? 0 : phenomenonTime.hashCode());
		result = prime * result
				+ ((statValue == null) ? 0 : statValue.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		ObservationsStatsStats other = (ObservationsStatsStats) obj;
		if (phenomenonTime == null) {
			if (other.phenomenonTime != null)
				return false;
		} else if (!phenomenonTime.equals(other.phenomenonTime))
			return false;
		if (statValue == null) {
			if (other.statValue != null)
				return false;
		} else if (!statValue.equals(other.statValue))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ObservationsStatsStats ["
				+ (statValue != null ? "statValue=" + statValue + ", " : "")
				+ (phenomenonTime != null ? "phenomenonTime=" + phenomenonTime
						: "") + "]";
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

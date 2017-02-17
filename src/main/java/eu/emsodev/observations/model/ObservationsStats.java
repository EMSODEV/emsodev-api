package eu.emsodev.observations.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Obesrvations statistics
 */
@ApiModel(description = "Obesrvations statistics")
// @javax.annotation.Generated(value =
// "class io.swagger.codegen.languages.SpringCodegen", date =
// "2016-10-04T09:41:08.096Z")
public class ObservationsStats {
	private Observatory node = null;

	private Instrument instrument = null;

	private Parameter parameter = null;

	private String statisticType;

	private List<ObservationsStatsStats> statistics = new ArrayList<ObservationsStatsStats>();

	public ObservationsStats node(Observatory node) {
		this.node = node;
		return this;
	}

	/**
	 * @return the statisticType
	 */
	public String getStatisticType() {
		return statisticType;
	}

	/**
	 * @param statisticType
	 *            the statisticType to set
	 */
	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}

	/**
	 * Get node
	 * 
	 * @return node
	 **/
	@ApiModelProperty(value = "")
	public Observatory getNode() {
		return node;
	}

	public void setNode(Observatory node) {
		this.node = node;
	}

	public ObservationsStats instrument(Instrument instrument) {
		this.instrument = instrument;
		return this;
	}

	/**
	 * Get instrument
	 * 
	 * @return instrument
	 **/
	@ApiModelProperty(value = "")
	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public ObservationsStats parameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}

	/**
	 * Get parameter
	 * 
	 * @return parameter
	 **/
	@ApiModelProperty(value = "")
	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public ObservationsStats(Observatory node, Instrument instrument,
			Parameter parameter, List<ObservationsStatsStats> statistics) {
		super();
		this.node = node;
		this.instrument = instrument;
		this.parameter = parameter;
		this.statistics = statistics;
	}

	public ObservationsStats() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ObservationsStatsStats> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<ObservationsStatsStats> statistics) {
		this.statistics = statistics;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result
				+ ((statistics == null) ? 0 : statistics.hashCode());
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
		ObservationsStats other = (ObservationsStats) obj;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (statistics == null) {
			if (other.statistics != null)
				return false;
		} else if (!statistics.equals(other.statistics))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ObservationsStats {\n");

		sb.append("    node: ").append(toIndentedString(node)).append("\n");
		sb.append("    instrument: ").append(toIndentedString(instrument))
				.append("\n");
		sb.append("    parameter: ").append(toIndentedString(parameter))
				.append("\n");
		sb.append("    stats: ").append(toIndentedString(statistics))
				.append("\n");
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

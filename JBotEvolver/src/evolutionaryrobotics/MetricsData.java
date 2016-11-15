package evolutionaryrobotics;

import java.util.List;

public abstract class MetricsData {
	protected int generation = -1;

	public MetricsData(int generation) {
		this.generation = generation;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	/**
	 * This method merges the given MetricsData with the current metrics data.
	 * The different values are combined Using a mean value for each field
	 * 
	 * @param metricsData
	 *            Are the metrics data instances to merge with the current
	 *            Instance
	 **/
	public abstract void combineMetricsData(List<MetricsData> metricsData);
}
package gui.utils;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import evolutionaryrobotics.FormationTaskMetricsCodex;
import evolutionaryrobotics.FormationTaskMetricsData;
import gui.util.Graph;
import gui.util.GraphPlotter;

public class FormationsMetricsGraphPlotter extends GraphPlotter {
	private static final long serialVersionUID = 6178820332224992218L;

	public enum MetricsType {
		TIME_INSIDE_FORMATION, TIME_TO_FIRST_OCCUPATION, PERMUTATION_METRICS, REOCUPATION_TIME;
	}

	public FormationsMetricsGraphPlotter(String[] files, MetricsType type) {
		super();

		JPanel graphPanel = new JPanel(new BorderLayout());
		Graph graph = new Graph();
		getContentPane().add(graphPanel);
		graphPanel.add(graph);

		int totalGenerations = 0;
		for (String filePath : files) {
			File file = new File(filePath);
			ArrayList<FormationTaskMetricsData> metricsData = FormationTaskMetricsCodex.decodeMetricsDataFile(file);
			totalGenerations = Math.max(metricsData.size(), totalGenerations);

			loadMetricsDataOnGraph(graph, metricsData, file, type);
		}

		graph.setxLabel("Generations (" + (totalGenerations) + ")");
		switch (type) {
		case TIME_INSIDE_FORMATION:
			graph.setyLabel("Robot time in spot");
			break;
		case TIME_TO_FIRST_OCCUPATION:
			graph.setyLabel("Time to first full occupation");
			break;
		case PERMUTATION_METRICS:
			graph.setyLabel("Different spots occupied per robot");
			break;
		case REOCUPATION_TIME:
			graph.setyLabel("Time until faulty robot spot is occupied");
			break;
		}

		graph.setShowLast(totalGenerations);

		setSize(800, 500 + graph.getHeaderSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void loadMetricsDataOnGraph(Graph graph, ArrayList<FormationTaskMetricsData> metricsData, File file,
			MetricsType type) {
		// Instead of differentiate, just create three arrays and use only the
		// needed ones
		metricsData.sort(new Comparator<FormationTaskMetricsData>() {
			@Override
			public int compare(FormationTaskMetricsData o1, FormationTaskMetricsData o2) {
				if (o1.getGeneration() < o2.getGeneration()) {
					return -1;
				} else {
					if (o1.getGeneration() > o2.getGeneration()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		});

		Double[] dataList_0 = new Double[metricsData.size()];
		Double[] dataList_1 = new Double[metricsData.size()];
		Double[] dataList_2 = new Double[metricsData.size()];

		for (int i = 0; i < metricsData.size(); i++) {
			FormationTaskMetricsData data = metricsData.get(i);
			switch (type) {
			case TIME_INSIDE_FORMATION:
				dataList_0[i] = data.getTimeInside_min();
				dataList_1[i] = data.getTimeInside_avg();
				dataList_2[i] = data.getTimeInside_max();
				break;
			case TIME_TO_FIRST_OCCUPATION:
				dataList_0[i] = (double) data.getTimeFirstTotalOccup();
				break;
			case PERMUTATION_METRICS:
				dataList_0[i] = data.getNumberDiffSpotsOccupied_min();
				dataList_1[i] = data.getNumberDiffSpotsOccupied_avg();
				dataList_2[i] = data.getNumberDiffSpotsOccupied_max();
				break;
			case REOCUPATION_TIME:
				dataList_0[i] = data.getReocupationTime_min();
				dataList_1[i] = data.getReocupationTime_avg();
				dataList_2[i] = data.getReocupationTime_max();
				break;
			}
		}

		String name = file.getParentFile().getParentFile().getName();
		name += File.separatorChar + file.getParentFile().getName();
		name += File.separatorChar + file.getName();
		name += "_" + type.name();

		switch (type) {
		case TIME_INSIDE_FORMATION:
		case PERMUTATION_METRICS:
		case REOCUPATION_TIME:
			graph.addDataList(dataList_0);
			graph.addDataList(dataList_1);
			graph.addDataList(dataList_2);

			graph.addLegend(name + "_min");
			graph.addLegend(name + "_avg");
			graph.addLegend(name + "_max");
			break;
		case TIME_TO_FIRST_OCCUPATION:
			graph.addDataList(dataList_0);

			graph.addLegend(name);
			break;
		}
	}
}

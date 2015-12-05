package ch.uzh.sweng15.filmvisualizer.client;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.corechart.PieChart;

/** 
 * This class displays the distribution of an given attribute in a {@link MovieCollection} in a pie chart
 * 
 * @author 	Jenny S.
 */

public abstract class PieChartVisualizer implements Visualizer {
	protected FocusPanel focusPanel;
	protected PieChart pieChart;
	protected MovieCollection mc;

	/**
	 * Constructor with parameters
	 * @param movieCollection Collection of movies that should be visualized
	 */
	public PieChartVisualizer(MovieCollection mc) {
		this.mc = mc;
	}

	/** 
	 * Creates the pie chart visualization and returns it in a Widget
	 * @return Widget The movie collection visualized in pie chart form
	 */
	public Widget createVisualization() {
		focusPanel = new FocusPanel();

		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				pieChart = new PieChart();
				focusPanel.setWidget(pieChart);
				draw();
			}
		});
		return focusPanel;
	}

	/**
	 * A different draw method is implemented depending on the movie criteria to be visualized in a pie chart
	 */
	protected abstract void draw();
}

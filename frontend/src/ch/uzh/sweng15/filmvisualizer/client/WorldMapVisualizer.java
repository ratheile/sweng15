package ch.uzh.sweng15.filmvisualizer.client;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.geochart.GeoChart;

/** 
 * This class displays the content of a {@link MovieCollection} or a {@link Movie} in a world map visualization
 * 
 * @author 	Dzmitry K.
 */

public abstract class WorldMapVisualizer implements Visualizer {
	protected FocusPanel focusPanel;
	protected GeoChart geoChart;
	
	/**
	 * Default constructor
	 */
	public WorldMapVisualizer() {
	}

	/** 
	 * Creates the world map visualization and returns it in a Widget
	 * @return Widget The movie collection visualized in world map form
	 */
	public Widget createVisualization() {
		focusPanel = new FocusPanel();
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				geoChart = new GeoChart();
				focusPanel.setWidget(geoChart);
				draw();
			}
		});
		return focusPanel;
	}
	
	/**
	 * A different draw method is implemented depending on whether a single or multiple movies ought to be visualized
	 */
	protected abstract void draw();
}

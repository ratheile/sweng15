package ch.uzh.sweng15.filmvisualizer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.Histogram;
import com.googlecode.gwt.charts.client.corechart.HistogramOptions;
import com.googlecode.gwt.charts.client.options.ChartArea;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;

/**
 * This class displays the lengths of all {@link Movie} in a {@link MovieCollection} in a histogram.
 * 
 * @author 	Dzmitry K.
 */

public class HistogramVisualizer implements Visualizer {
	private Histogram histogram;
	private FocusPanel focusPanel;
	private MovieCollection movieCollection;
	
	/**
	 * Constructor with parameter
	 * @param passedCollection Collection of movies that should be visualized
	 */
	public HistogramVisualizer(MovieCollection passedCollection) {
		this.movieCollection = passedCollection;
	}

	/** 
	 * Creates the histogram visualization of movie lengths and returns it in a Widget
	 * @return Widget The movie collection visualized in histogram form
	 */
	public Widget createVisualization() {
		focusPanel = new FocusPanel();
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				histogram = new Histogram();
				focusPanel.setWidget(histogram);
				draw();
			}
		});
		return focusPanel;
	}

	/** 
	 * Creates the histogram visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	private void draw() {
		// Create DataTable
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Title");
		dataTable.addColumn(ColumnType.NUMBER, "Length");
	
		// Populate DataTable
		ArrayList<Movie> movieList = movieCollection.getMovies();
		
		dataTable.addRows(movieList.size());
		
        int i = 0;
        for (Movie m : movieList) {
        	if (m.getLength() != -1) {
        		dataTable.setValue(i, 0, m.getTitle());
        		dataTable.setValue(i, 1, m.getLength());
        		i++;
        	}
        }

        // Histogram options
		HistogramOptions options = HistogramOptions.create();
		options.setTitle("Duration of films, in minutes");
		options.setLegend(Legend.create(LegendPosition.NONE));
		options.setColors("#505050");
		options.setHeight(425);
		ChartArea area = ChartArea.create();
        area.setTop(55);
        area.setLeft(50);
        area.setHeight(300);
        options.setChartArea(area);

		// Draw visualization
		histogram.draw(dataTable, options);
	}
}
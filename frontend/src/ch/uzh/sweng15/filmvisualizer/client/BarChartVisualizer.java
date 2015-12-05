package ch.uzh.sweng15.filmvisualizer.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.SortColumn;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.ChartArea;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.TextStyle;


/** 
 * This class displays the distribution of genres in a {@link MovieCollection} in a bar chart
 * 
 * @author 	Dzmitry K.
 */

public class BarChartVisualizer implements Visualizer{
	private FocusPanel focusPanel;
	private ColumnChart columnChart;
	private MovieCollection mc;

	/**
	 * Constructor with parameters
	 * @param mc Collection of movies that should be visualized
	 */
	public BarChartVisualizer (MovieCollection mc) {
		this.mc = mc;
	}

	/** 
	 * Creates the bar chart visualization and returns it in a Widget
	 * @return Widget The movie collection visualized in bar chart form
	 */
	public Widget createVisualization() {
		focusPanel = new FocusPanel();

		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				columnChart = new ColumnChart();
				focusPanel.setWidget(columnChart);
				draw();
			}
		});
		return focusPanel;
	}

	/** 
	 * Creates the bar chart visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	private void draw() {
		// Determine movie count of every country
		HashMap<String, Integer> countryMap = new HashMap<String, Integer>();
		for (Movie m : mc.getMovies()) {
			for (String country : m.getCountry()) {
				if (countryMap.containsKey(country)) {
					int tmpCount = countryMap.get(country);
					tmpCount++;
					countryMap.put(country, tmpCount);
				} else {
					countryMap.put(country, 1);
				}
			}
		}
		
		// Prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Count");
		dataTable.addRows(countryMap.size());
		
		// Fill DataTable
		Set<String> countryNames = countryMap.keySet();
		int i = 0;
        Iterator<String> iter = countryNames.iterator();
        while(iter.hasNext()) {
			String country = iter.next();
			dataTable.setValue(i, 0, country);
			dataTable.setValue(i, 1, countryMap.get(country));
			i++;
        }
        
        // Sort DataTable in descending order
        SortColumn sc = SortColumn.create(1);
        sc.setDesc(true);
        dataTable.sort(sc);
        
        // The framework automatically, and correctly, reduces the number of shown countries below 20 if necessary
        dataTable.removeRows(20, countryMap.size()-20);
        
        // ColumnChart options
        ColumnChartOptions options = ColumnChartOptions.create();
        options.setTitle("Movie Count per Country (Top 20)");
        // Axis label settings
        HAxis ha = HAxis.create();
        ha.setShowTextEvery(1);
        ha.setSlantedText(true);
        ha.setSlantedTextAngle(45);
        TextStyle ts = TextStyle.create();
        ts.setFontSize(13);
        ts.setFontName("Arial");
        ha.setTextStyle(ts);
        options.setHAxis(ha);
        // Legend options
        Legend legend = Legend.create();
        legend.setPosition(LegendPosition.NONE);
        options.setLegend(legend);
        // Display options
        options.setColors("#505050");
        options.setHeight(500);
        ChartArea area = ChartArea.create();
        area.setTop(55);
        area.setLeft(85);
        options.setChartArea(area);

		// Draw the chart
		columnChart.draw(dataTable, options);
	}
}

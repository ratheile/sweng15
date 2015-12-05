package ch.uzh.sweng15.filmvisualizer.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;

/** 
 * This class displays the content of an {@link MovieCollection} in a world map visualization
 * 
 * @author 	Jenny S.
 */

public class MoviesWorldMapVisualizer extends WorldMapVisualizer {
	private MovieCollection movieCollection;

	/**
	 * Constructor with parameters
	 * @param movieCollection Collection of movies that should be visualized
	 */
	public MoviesWorldMapVisualizer(MovieCollection movieCollection) {
		super();
		this.movieCollection = movieCollection;
	}

	/** 
	 * Creates the world map visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	protected void draw() {			
		// Determine movie count of every country
		HashMap<String, Integer> countryMap = new HashMap<String, Integer>();
		for (Movie m : movieCollection.getMovies()) {
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

		// Create DataTable
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Movie Count");
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

		// GeoChart options
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		// If no movies are to be visualized, change color scheme
		if (countryMap.size() != 0) {
			geoChartColorAxis.setColors("#ffffcc", "orange", "red");
			options.setDatalessRegionColor("#EEEEEE");
		} else {
			geoChartColorAxis.setColors("#EEEEEE");
			options.setDatalessRegionColor("#EEEEEE");
		}
		options.setColorAxis(geoChartColorAxis);
		options.setHeight(570);
		options.setKeepAspectRatio(true);

		// Draw the chart
		geoChart.draw(dataTable, options);
	}
}




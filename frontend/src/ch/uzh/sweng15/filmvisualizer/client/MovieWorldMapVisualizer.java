package ch.uzh.sweng15.filmvisualizer.client;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.geochart.GeoChartColorAxis;
import com.googlecode.gwt.charts.client.geochart.GeoChartOptions;

/** 
 * This class displays the content of a {@link Movie} in a world map visualization
 * 
 * @author 	Dzmitry K.
 */

public class MovieWorldMapVisualizer extends WorldMapVisualizer {
	private Movie movie;
	
	/**
	 * Constructor with parameter
	 * @param movie The movie to be visualized
	 */
	public MovieWorldMapVisualizer(Movie movie) {
		super();
		this.movie = movie;
	}

	/** 
	 * Creates the world map visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	protected void draw() {
		// Create DataTable
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.STRING, "Info");
		dataTable.addColumn(ColumnType.NUMBER, "Movie Count");
		dataTable.addRows(movie.getCountry().size());
		
		// Fill DataTable
		int i = 0;
		for (String country : movie.getCountry()) {
			dataTable.setValue(i, 0, country);
			// The printString() method produces all the information of an individual film, which is displayed 
			// if the user moves the cursor over the appropriate country
			dataTable.setValue(i, 1, movie.getRelevantMovieInfo());
			dataTable.setValue(i, 2, 1);
			i++;
		}

		// GeoChart options
		GeoChartOptions options = GeoChartOptions.create();
		GeoChartColorAxis geoChartColorAxis = GeoChartColorAxis.create();
		geoChartColorAxis.setColors("red");
		options.setDatalessRegionColor("#EEEEEE");
		options.setColorAxis(geoChartColorAxis);
		options.setHeight(570);

		// Draw visualization
		geoChart.draw(dataTable, options);
	}
}
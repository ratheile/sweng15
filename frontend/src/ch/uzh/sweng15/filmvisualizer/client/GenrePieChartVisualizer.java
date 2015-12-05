package ch.uzh.sweng15.filmvisualizer.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.options.ChartArea;

/** 
 * This class displays the distribution of genres in a {@link MovieCollection} in a pie chart
 * 
 * @author 	Jenny S.
 */

public class GenrePieChartVisualizer extends PieChartVisualizer {
	
	/**
	 * Constructor with parameters
	 * @param mc Collection of movies that should be visualized
	 */
	public GenrePieChartVisualizer(MovieCollection mc) {
		super(mc);
	}

	/** 
	 * Creates the pie chart visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	protected void draw() {
		// Determine movie count of every genre
		HashMap<String, Integer> genreMap = new HashMap<String, Integer>();
		for (Movie m : mc.getMovies()) {
			for (String s : m.getGenre()) {
				if (genreMap.containsKey(s)) {
					int tmpCount = genreMap.get(s);
					tmpCount++;
					genreMap.put(s, tmpCount);
				} else {
					genreMap.put(s, 1);
				}
			}
		}
		
		// Create DataTable
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Genre");
		dataTable.addColumn(ColumnType.NUMBER, "Genre Percentage");
		dataTable.addRows(genreMap.size());

		// Fill DataTable
		Set<String> genreNames = genreMap.keySet();
		int i = 0;
		Iterator<String> iter = genreNames.iterator();
		while(iter.hasNext()) {
			String genre = iter.next();
			dataTable.setValue(i, 0, genre);	
			// The percentage of the genre is automatically calculated by the framework
			dataTable.setValue(i, 1, genreMap.get(genre));
			i++;
		}

		// PieChart options
		PieChartOptions options = PieChartOptions.create();
		options.setTitle("Genre Distribution by Percent");
		// Languages with less than 2.5 percent are collected under the category "Other"
		options.setPieResidueSliceColor("#505050");
		options.setPieResidueSliceLabel("Other");
		options.setSliceVisibilityThreshold(0.025);
		// Display options
		options.setFontName("Arial");
		options.setHeight(400);
        ChartArea area = ChartArea.create();
        area.setLeft(0);
		area.setTop(55);
		area.setHeight(300);
        options.setChartArea(area);
        
		// Draw the chart
		pieChart.draw(dataTable, options);		
	}
}



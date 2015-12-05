package ch.uzh.sweng15.filmvisualizer.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.options.ChartArea;

/** 
 * This class displays the distribution of languages in a {@link MovieCollection} in a pie chart
 * 
 * @author 	Jenny S.
 */

public class LanguagePieChartVisualizer extends PieChartVisualizer {

	/**
	 * Constructor with parameters
	 * @param movieCollection Collection of movies that should be visualized
	 * @param languageSet TreeSet<String> containing all languages in the movie collection
	 */
	public LanguagePieChartVisualizer(MovieCollection mc) {
		super(mc);
	}

	/** 
	 * Creates the pie chart visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	protected void draw() {
		// Determine movie count of every language
		HashMap<String, Integer> languageMap = new HashMap<String, Integer>();
		for (Movie m : mc.getMovies()) {
			for (String s : m.getLanguage()) {
				if (languageMap.containsKey(s)) {
					int tmpCount = languageMap.get(s);
					tmpCount++;
					languageMap.put(s, tmpCount);
				} else {
					languageMap.put(s, 1);
				}
			}
		}

		// Create DataTable
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Language");
		dataTable.addColumn(ColumnType.NUMBER, "Language Percentage");
		dataTable.addRows(languageMap.size());

		// Fill DataTable
		Set<String> languageNames = languageMap.keySet();
		int i = 0;
		Iterator<String> iter = languageNames.iterator();
		while(iter.hasNext()) {
			String language = iter.next();
			dataTable.setValue(i, 0, language);			
			// The percentage of the genre is automatically calculated by the framework
			dataTable.setValue(i, 1, languageMap.get(language));
			i++;
		}

		// PieChart options
		PieChartOptions options = PieChartOptions.create();
		options.setTitle("Language Distribution by Percent");
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



package ch.uzh.sweng15.filmvisualizer.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
import com.googlecode.gwt.charts.client.table.TablePage;

/** 
 * The TableVisualizer class displays the content of an {@link MovieCollection} in the form a table
 * 
 * @author 	Dzmitry K.
 */

public class TableVisualizer implements Visualizer {
	private Table table;
	private FocusPanel returnPanel;
	private MovieCollection mc;

	/**
	 * Constructor with parameter
	 * @param mc Collection of movies that should be visualized
	 */
	public TableVisualizer(MovieCollection mc) {
		this.mc = mc;
	}

	/** 
	 * Creates a Widget, in which the table will be drawn, and calls the draw() method to visualize the table.
	 * @return Widget The movie collection visualized in table form
	 */
	public Widget createVisualization() {
		returnPanel = new FocusPanel();
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.TABLE);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				// Add to-be-drawn table to a Widget, here a FocusPanel, and draw the table
				table = new Table();
				returnPanel.setWidget(table);
				draw();
			}
		});
		
		return returnPanel;
	}

	/** 
	 * Creates the table visualization, using the Google Charts framework. See Google Charts API for more detail.
	 */
	private void draw() {
		// Create DataTable
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Title");
		dataTable.addColumn(ColumnType.NUMBER, "Year");
		dataTable.addColumn(ColumnType.NUMBER, "Length");
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.STRING, "Genre");
		dataTable.addColumn(ColumnType.STRING, "Language");

		// Populate DataTable
		ArrayList<Movie> movieList = mc.getMovies();
		
		dataTable.addRows(movieList.size());
		
		int i = 0;
		for (Movie m : movieList) {
			dataTable.setValue(i, 0, m.getTitle());
			dataTable.setValue(i, 1, m.getYear());
			dataTable.setValue(i, 2, m.getLength());
			dataTable.setValue(i, 3, m.getCountry().toString());
			dataTable.setValue(i, 4, m.getGenre().toString());
			dataTable.setValue(i, 5, m.getLanguage().toString());
			i++;
		}

		// Table options
		TableOptions options = TableOptions.create();
		// Enable row numbering
		options.setShowRowNumber(true);
		// Enable paging to improve the performance of displaying large tables
		options.setAlternatingRowStyle(true);
		options.setPage(TablePage.ENABLE);
		// Limit the number of displayed movies per page to 100
		options.setPageSize(100);

		// Draw visualization
		table.draw(dataTable, options);
	}
}
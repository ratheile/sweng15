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
 * The TableVisualizer class displays the content of an {@link MovieCollection} in a table
 * 
 * @author 	Dzmitry K.
 */

public class TableVisualizer implements Visualizer {
	private Table table;
	private FocusPanel returnPanel;
	private MovieCollection movieCollection;

	/**
	 * Constructor with parameter
	 * @param movieCollection Collection of movies that should be visualized
	 */
	public TableVisualizer(MovieCollection movieCollection) {
		this.movieCollection = movieCollection;
	}

	/** 
	 * Creates a table visualization and returns it in a Widget.
	 * @return Widget The movie collection visualized in table form
	 */
	public Widget createVisualization() {
		returnPanel = new FocusPanel();
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.TABLE);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				table = new Table();
				returnPanel.setWidget(table);
				draw();
			}
		});
		
		return returnPanel;
	}

	/** 
	 * Creates the table visualization, using the Google Charts framework.
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
		
		ArrayList<Movie> movieList = movieCollection.getMovies();
		dataTable.addRows(movieList.size());
		
		// Populate DataTable
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
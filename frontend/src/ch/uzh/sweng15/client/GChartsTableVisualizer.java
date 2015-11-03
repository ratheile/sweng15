package ch.uzh.sweng15.client;

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

public class GChartsTableVisualizer {
	private Table table;
	private FocusPanel focusPanel;
	private MovieCollection mc;

	public GChartsTableVisualizer(MovieCollection mc) {
		this.mc = mc;
	}

	public Widget createVisualization() {
		focusPanel = new FocusPanel();
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.TABLE);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create visualization and add to return Panel
				table = new Table();
				focusPanel.setWidget(table);
				draw();
			}
		});
		
		return focusPanel;
	}

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
		ArrayList<Movie> movieList = mc.getMovieArrayList();
		
		dataTable.addRows(movieList.size());
		
		int i = 0;
		for (Movie m : movieList) {
			String genre = "";
			ArrayList<String> tmpList = m.getGenre();
	    	if (tmpList.size() > 0) {
	            genre = tmpList.get(0);
	    	} else {
	    		genre = "No Genre";
	    	}
			dataTable.setValue(i, 0, m.getTitle());
			dataTable.setValue(i, 1, m.getYear());
			dataTable.setValue(i, 2, m.getLength());
			dataTable.setValue(i, 3, m.getCountry());
			dataTable.setValue(i, 4, genre);
			dataTable.setValue(i, 5, m.getLanguage());
			i++;
		}

		// Table options
		TableOptions options = TableOptions.create();
		options.setAlternatingRowStyle(true);
		options.setPage(TablePage.ENABLE);
		options.setPageSize(100);
		//options.setShowRowNumber(true);

		// Draw visualization
		table.draw(dataTable, options);
	}
}
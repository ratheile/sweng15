package ch.uzh.sweng15.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.ListDataProvider;

public class TableVisualizer {
	
	public TableVisualizer() {
	}

	public CellTable<Movie> createVisualization(MovieCollection selection) { 
	
	// Create a CellTable.
    CellTable<Movie> table = new CellTable<Movie>();
    
    table.setVisibleRange(0, selection.getMovieCollectionSize());
    
    // Create title column.
    TextColumn<Movie> titleColumn = new TextColumn<Movie>() {
      @Override
      public String getValue(Movie movie) {
        return movie.getTitle();
      }
    };
    
    // Create year column.
    TextColumn<Movie> yearColumn = new TextColumn<Movie>() {
      @Override
      public String getValue(Movie movie) {
        return movie.getYear().toString();
      }
    };
    
    // Create length column.
    TextColumn<Movie> lengthColumn = new TextColumn<Movie>() {
      @Override
      public String getValue(Movie movie) {
        return movie.getLength().toString();
      }
    };

    // Create genre column.
    TextColumn<Movie> genreColumn = new TextColumn<Movie>() {
      @Override
      public String getValue(Movie movie) {
    	ArrayList<String> tmpList = movie.getGenre();
    	if (tmpList.size() > 0) {
            return tmpList.get(0);
    	} else {
    		return "No Genre";
    	}
      }
    };
    
    // Create country column.
    TextColumn<Movie> countryColumn = new TextColumn<Movie>() {
      @Override
      public String getValue(Movie movie) {
        return movie.getCountry();
      }
    };
    
    // Create language column.
    TextColumn<Movie> languageColumn = new TextColumn<Movie>() {
      @Override
      public String getValue(Movie movie) {
        return movie.getLanguage();
      }
    };
    
    // Make the name column sortable.
    titleColumn.setSortable(true);
    yearColumn.setSortable(true);
    lengthColumn.setSortable(true);
    genreColumn.setSortable(true);
    countryColumn.setSortable(true);
    languageColumn.setSortable(true);
    
    // Add the columns.
    table.addColumn(titleColumn, "Title");
    table.addColumn(yearColumn, "Year");
    table.addColumn(lengthColumn, "Length");
    table.addColumn(genreColumn, "Genre");
    table.addColumn(countryColumn, "Country");
    table.addColumn(languageColumn, "Language");
    
    // Create a data provider.
    ListDataProvider<Movie> dataProvider = new ListDataProvider<Movie>();

    // Connect the table to the data provider.
    dataProvider.addDataDisplay(table);

    // Add the data to the data provider, which automatically pushes it to the
    // widget.
    List<Movie> list = dataProvider.getList();
    for (Movie m : selection.getMovieArrayList()) {
    	list.add(m);
    }

    // Add a ColumnSortEvent.ListHandler to connect sorting to the java.util.List.
    ListHandler<Movie> titleSortHandler = new ListHandler<Movie>(list);
    titleSortHandler.setComparator(titleColumn, new Comparator<Movie>() {
          public int compare(Movie o1, Movie o2) {
            if (o1 == o2) {
              return 0;
            }

            // Compare the title columns.
            if (o1 != null) {
              return (o2 != null) ? o1.getTitle().compareTo(o2.getTitle()) : 1;
            }
            return -1;
          }
        });
    table.addColumnSortHandler(titleSortHandler);
    
    // Add a ColumnSortEvent.ListHandler to connect sorting to the java.util.List.
    ListHandler<Movie> yearSortHandler = new ListHandler<Movie>(list);
    yearSortHandler.setComparator(yearColumn, new Comparator<Movie>() {
          public int compare(Movie o1, Movie o2) {
            if (o1 == o2) {
              return 0;
            }

            // Compare the year columns.
            if (o1 != null) {
              return (o2 != null) ? o1.getYear().compareTo(o2.getYear()) : 1;
            }
            return -1;
          }
        });
    table.addColumnSortHandler(yearSortHandler);
    
    // Add a ColumnSortEvent.ListHandler to connect sorting to the java.util.List.
    ListHandler<Movie> lengthSortHandler = new ListHandler<Movie>(list);
    lengthSortHandler.setComparator(lengthColumn, new Comparator<Movie>() {
          public int compare(Movie o1, Movie o2) {
            if (o1 == o2) {
              return 0;
            }

            // Compare the length columns.
            if (o1 != null) {
              return (o2 != null) ? o1.getLength().compareTo(o2.getLength()) : 1;
            }
            return -1;
          }
        });
    table.addColumnSortHandler(lengthSortHandler);
    
    // Add a ColumnSortEvent.ListHandler to connect sorting to the java.util.List.
    ListHandler<Movie> genreSortHandler = new ListHandler<Movie>(list);
    genreSortHandler.setComparator(genreColumn, new Comparator<Movie>() {
          public int compare(Movie o1, Movie o2) {
            if (o1 == o2) {
              return 0;
            }

            // Compare the genre columns.
            if (o1 != null) {
              return (o2 != null) ? o1.getGenre().toString().compareTo(o2.getGenre().toString()) : 1;
            }
            return -1;
          }
        });
    table.addColumnSortHandler(genreSortHandler);
    
    // Add a ColumnSortEvent.ListHandler to connect sorting to the java.util.List.
    ListHandler<Movie> countrySortHandler = new ListHandler<Movie>(list);
    countrySortHandler.setComparator(countryColumn, new Comparator<Movie>() {
          public int compare(Movie o1, Movie o2) {
            if (o1 == o2) {
              return 0;
            }

            // Compare the country columns.
            if (o1 != null) {
              return (o2 != null) ? o1.getCountry().toString().compareTo(o2.getCountry().toString()) : 1;
            }
            return -1;
          }
        });
    table.addColumnSortHandler(countrySortHandler);
    
    // Add a ColumnSortEvent.ListHandler to connect sorting to the java.util.List.
    ListHandler<Movie> languageSortHandler = new ListHandler<Movie>(list);
    languageSortHandler.setComparator(languageColumn, new Comparator<Movie>() {
          public int compare(Movie o1, Movie o2) {
            if (o1 == o2) {
              return 0;
            }

            // Compare the language columns.
            if (o1 != null) {
              return (o2 != null) ? o1.getLanguage().toString().compareTo(o2.getLanguage().toString()) : 1;
            }
            return -1;
          }
        });
    table.addColumnSortHandler(languageSortHandler);

    table.getColumnSortList().push(yearColumn);
    table.getColumnSortList().push(lengthColumn);
    table.getColumnSortList().push(genreColumn);
    table.getColumnSortList().push(countryColumn);
    table.getColumnSortList().push(languageColumn);
    table.getColumnSortList().push(titleColumn);
    
    return table;
	}
}

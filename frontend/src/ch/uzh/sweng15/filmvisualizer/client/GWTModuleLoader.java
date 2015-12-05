package ch.uzh.sweng15.filmvisualizer.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import br.com.freller.tool.client.Print;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * The class GWTModuleLoader creates the graphical user interface (GUI) for our film data visualizer application
 * 
 * @author 	Joel H., Dzmitry K., Jenny S.
 */

public class GWTModuleLoader implements EntryPoint, SliderListener {

	private MovieCollection movieCollection;
	private Filter filter;
	private VerticalPanel mainVerPanel;
	private Widget printWidget;
	private TextBox titleTextBox;
	private TreeSet<String> countrySet;
	private TreeSet<String> genreSet;
	private TreeSet<String> languageSet;
	private RangeSlider rangeSlider;
	private Label rangeSliderLabel;
	private VisualizationType toBeVisualizedAs;
	private boolean isDisplayed = false;
	private boolean isAdvertised = false;
	private boolean isLoading = false;
	private boolean dataIsUpToDate = false;
	final int bottomWidgetIndex = 4;
	private FilmLoaderAsync filmLoaderSvc = GWT.create(FilmLoader.class);
	
	
	/**
	 * Entry point method: Creates a new movie collection and filter, builds the loading screen 
	 * and gets all the relevant names to populate the various ListBoxes.
	 */
	public void onModuleLoad() {
		movieCollection = new MovieCollection();
		filter = new Filter();
		buildLoadScreen();
		loadListBoxItemNames();
	}


	/**
	 * Create main VerticalPanel, add it to the RootPanel and display a loading data message
	 */
	private void buildLoadScreen() {
		// Create new main vertical panel to which all other GUI panels shall be added
		mainVerPanel = new VerticalPanel();

		// Add header image
		Image image = new Image();
		image.setUrl("/images/header.png");
		mainVerPanel.add(image);

		// Add data loading label
		Label loadingLabel = new Label("Loading Application. Please wait...");
		mainVerPanel.add(loadingLabel);

		// Vertical Panel Settings
		mainVerPanel.setSpacing(25);

		// Insert the main vertical panel into a scroll panel to allow for scrolling
		ScrollPanel sp = new ScrollPanel();
		sp.add(mainVerPanel);

		// Add main vertical panel inside scroll panel to root panel
		RootPanel.get().add(sp);
	}

	
	/**
	 * Loads the names of all countries, genres and languages for display in GUI ListBoxes
	 */
	private void loadListBoxItemNames() {
		if (filmLoaderSvc == null) {
			filmLoaderSvc = GWT.create(FilmLoader.class);
		}

		// Set up the callback object.
		AsyncCallback<ArrayList<TreeSet<String>>> callback = new AsyncCallback<ArrayList<TreeSet<String>>>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}

			// On successful callback, call populateTreeSets(ArrayList<TreeSet<String>> passedItemNames) method 
			public void onSuccess(ArrayList<TreeSet<String>> result) {
				populateTreeSets(result);
			}
		};

		// ArrayList containing all the files to be searched for names
		ArrayList<String> fileLocations = new ArrayList<String>();
		fileLocations.add("movies_80000.tsv");
		fileLocations.add("movies_1471.tsv");
		
		filmLoaderSvc.getListBoxItemNames(fileLocations, callback);
	}

	/**
	 * Populate TreeSet<String> data structures for later GUI use; then, build GUI
	 */
	private void populateTreeSets(ArrayList<TreeSet<String>> passedItemNames) {
		countrySet = passedItemNames.get(0);
		genreSet = passedItemNames.get(1);
		languageSet = passedItemNames.get(2);
		buildGUI();
	}


	/**
	 * Create graphical user interface (GUI)
	 */
	private void buildGUI() {
		// Remove data loading label
		mainVerPanel.remove(1);

		// Add horizontal panels to main vertical panel
		buildButtonPanel();
		buildFilterPanel();
		buildSearchPanel();
		addBannerAd();
	}


	/**
	 * Create a panel of buttons, which generate various visualizations
	 */
	private void buildButtonPanel() {	    
		// Table visualization button
		Button tableButton = new Button("Visualize in Table", new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Set selected visualization type
				toBeVisualizedAs = VisualizationType.TABLE;
				
				// Check if data is up-to-date. If yes, visualize; if no, retrieve a new data set for updating purposes.
				if (dataIsUpToDate) {
					visualizeInTable();
				} else {
					getDataSet();
				}
			}
		});
		tableButton.setStyleName("blueButton");

		// World map visualization button
		Button worldMapButton = new Button("World Map", new ClickHandler() {
			public void onClick(ClickEvent event) {
				toBeVisualizedAs = VisualizationType.WORLDMAP;
				if (dataIsUpToDate) {
					visualizeWorldMap();
				} else {
					getDataSet();
				}
			}
		});
		worldMapButton.setStyleName("greenButton");

		// Bar graph visualization button
		Button barGraphButton = new Button("Country Bar Graph", new ClickHandler() {
			public void onClick(ClickEvent event) {
				toBeVisualizedAs = VisualizationType.BARGRAPH;
				if (dataIsUpToDate) {
					visualizeBarChart();
				} else {
					getDataSet();
				}
			}
		});
		barGraphButton.setStyleName("purpleButton");

		// Genre pie chart visualization button
		Button countryPieChartButton = new Button("Genre Pie Chart", new ClickHandler() {
			public void onClick(ClickEvent event) {
				toBeVisualizedAs = VisualizationType.GENRE_PIECHART;
				if (dataIsUpToDate) {
					visualizeGenrePieChart();
				} else {
					getDataSet();
				}
			}
		});
		countryPieChartButton.setStyleName("purpleButton");

		// Language pie chart visualization button
		Button languagePieChartButton = new Button("Language Pie Chart", new ClickHandler() {
			public void onClick(ClickEvent event) {
				toBeVisualizedAs = VisualizationType.LANGUAGE_PIECHART;
				if (dataIsUpToDate) {
					visualizeLanguagePieChart();
				} else {
					getDataSet();
				}
			}
		});
		languagePieChartButton.setStyleName("purpleButton");

		// Length histogram visualization button
		Button histogramButton = new Button("Length Histogram", new ClickHandler() {
			public void onClick(ClickEvent event) {
				toBeVisualizedAs = VisualizationType.HISTOGRAM;
				if (dataIsUpToDate) {
					visualizeHistogram();
				} else {
					getDataSet();
				}
			}
		});
		histogramButton.setStyleName("purpleButton");
		
		// Print (Export PDF) button
		Button printButton = new Button("Print (Save as PDF)", new ClickHandler() {
			public void onClick(ClickEvent event) {
				printOrSaveAsPDF();
			}
		});
		printButton.setStyleName("grayButton");

		// Export CSV button
		Button exportCSVButton = new Button("Export CSV", new ClickHandler() {
			public void onClick(ClickEvent event) {
				getExportCSVLink();
			}
		});
		exportCSVButton.setStyleName("grayButton");


		// Add all buttons to a horizontal panel
		HorizontalPanel horButtonPanel = new HorizontalPanel();

		horButtonPanel.add(tableButton);
		horButtonPanel.add(worldMapButton);
		horButtonPanel.add(barGraphButton);
		horButtonPanel.add(countryPieChartButton);
		horButtonPanel.add(languagePieChartButton);
		horButtonPanel.add(histogramButton);
		horButtonPanel.add(printButton);
		horButtonPanel.add(exportCSVButton);

		// Add horizontal panel with all button to main vertical panel
		mainVerPanel.add(horButtonPanel);  
	}


	/**
	 * Get film data set from server
	 */
	private void getDataSet() {
		// Inform user of data loading process
		addLoadingLabel();
		
		if (filmLoaderSvc == null) {
			filmLoaderSvc = GWT.create(FilmLoader.class);
		}

		// Set up the callback object.
		AsyncCallback<MovieCollection> callback = new AsyncCallback<MovieCollection>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}

			// The getDataSet() method is called by a visualizing GUI event (e.g. user clicks on the "World Map" button).
			// The toBeVisualizedAs variable stores what kind of visualization was chosen at that time, so that the correct
			// type is displayed after the asynchronous callback.
			public void onSuccess(MovieCollection result) {
				if (toBeVisualizedAs == VisualizationType.TABLE) {
					loadDataSet(result);
					visualizeInTable();
				} else if (toBeVisualizedAs == VisualizationType.WORLDMAP) {
					loadDataSet(result);
					visualizeWorldMap();
				} else if (toBeVisualizedAs == VisualizationType.HISTOGRAM) {
					loadDataSet(result);
					visualizeHistogram();
				} else if (toBeVisualizedAs == VisualizationType.BARGRAPH) {
					loadDataSet(result);
					visualizeBarChart();
				} else if (toBeVisualizedAs == VisualizationType.GENRE_PIECHART) {
					loadDataSet(result);
					visualizeGenrePieChart();
				} else if (toBeVisualizedAs == VisualizationType.LANGUAGE_PIECHART) {
					loadDataSet(result);
					visualizeLanguagePieChart();
				} else if (toBeVisualizedAs == VisualizationType.HISTOGRAM) {
					loadDataSet(result);
					visualizeHistogram();
				}
			}
		};

		filmLoaderSvc.getRemoteMovieCollection(filter, callback);
	}

	/**
	 * Called on successful callback, loads the movie collection
	 * @param passedCollection The MovieCollection retrieved from the server
	 */
	private void loadDataSet(MovieCollection passedCollection) {
		movieCollection = passedCollection;
		dataIsUpToDate = true;
	}


	/**
	 * Create filter panel, allowing the user to filter according to various criteria
	 */
	private void buildFilterPanel() {
		// Country box
		final ListBox countryBox = new ListBox() {
			// Override is necessary for proper later retrieval of the ListBox's selected index
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};

		// Populate genre ListBox using the country set
		countryBox.addItem("Country");
		Iterator<String> countryIterator = countrySet.iterator();
		while(countryIterator.hasNext()) {
			countryBox.addItem(countryIterator.next());
		}

		countryBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (countryBox.getValue(countryBox.getSelectedIndex()).equals("Country")) {
					filter.setCountry(null);
					dataIsUpToDate = false;
				} else {
					filter.setCountry(countryBox.getValue(countryBox.getSelectedIndex()));
					dataIsUpToDate = false;
				}
			}
		});
		countryBox.setVisibleItemCount(1);
		countryBox.getElement().getStyle().setWidth(150, Unit.PX);


		// Genre box
		final ListBox genreBox = new ListBox() {
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};

		// Populate genre ListBox using the genre set
		genreBox.addItem("Genre");
		Iterator<String> genreIterator = genreSet.iterator();
		while(genreIterator.hasNext()) {
			genreBox.addItem(genreIterator.next());
		}

		genreBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (genreBox.getValue(genreBox.getSelectedIndex()).equals("Genre")) {
					filter.setGenre(null);
					dataIsUpToDate = false;
				} else {
					filter.setGenre(genreBox.getValue(genreBox.getSelectedIndex()));
					dataIsUpToDate = false;
				}
			}
		});
		genreBox.setVisibleItemCount(1);
		genreBox.getElement().getStyle().setWidth(150, Unit.PX);

		// Language box
		final ListBox languageBox = new ListBox() {
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};

		// Populate genre ListBox using the language set
		languageBox.addItem("Language");
		Iterator<String> languageIterator = languageSet.iterator();
		while(languageIterator.hasNext()) {
			languageBox.addItem(languageIterator.next());
		}

		languageBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (languageBox.getValue(languageBox.getSelectedIndex()).equals("Language")) {
					filter.setLanguage(null);
					dataIsUpToDate = false;
				} else {
					filter.setLanguage(languageBox.getValue(languageBox.getSelectedIndex()));
					dataIsUpToDate = false;
				}
			}
		});
		languageBox.setVisibleItemCount(1);
		languageBox.getElement().getStyle().setWidth(150, Unit.PX);

		// Length range boxes
		HorizontalPanel horLengthRangePanel = new HorizontalPanel();

		Label lengthLabel = new Label("Length:");

		// From length box
		final ListBox fromLength = new ListBox() {
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};
		fromLength.addItem("From");
		for (int i=0; i<50; i++) {
			int j = i*10;
			fromLength.addItem(Integer.toString(j) + " min");
		}
		// Currently, there exist only 9 feature films longer than 500 minutes, 
		// so this seemed to be a reasonable limit in the ListBox selection 
		fromLength.addItem("500+ min");
		fromLength.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (fromLength.getValue(fromLength.getSelectedIndex()).equals("From")) {
					filter.setFromLength(0);
					dataIsUpToDate = false;
				} else if (fromLength.getValue(fromLength.getSelectedIndex()).equals("500+ min")) {
					filter.setFromLength(500);
					dataIsUpToDate = false;
				} else {
					String tmpStr = fromLength.getValue(fromLength.getSelectedIndex());
					tmpStr = tmpStr.substring(0, tmpStr.indexOf("min")-1);
					filter.setFromLength(Integer.parseInt(tmpStr));
					dataIsUpToDate = false;
				}
			}
		});
		fromLength.setVisibleItemCount(1);

		// To length box
		final ListBox toLength = new ListBox() {
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};
		toLength.addItem("To");
		for (int i=0; i<50; i++) {
			int j = i*10;
			toLength.addItem(Integer.toString(j) + " min");
		}
		toLength.addItem("500+ min");
		toLength.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (toLength.getValue(toLength.getSelectedIndex()).equals("To")) {
					filter.setToLength(0);
					dataIsUpToDate = false;
				} else if (toLength.getValue(toLength.getSelectedIndex()).equals("500+ min")) {
					filter.setToLength(0);
					dataIsUpToDate = false;
				} else {
					String tmpStr = toLength.getValue(toLength.getSelectedIndex());
					tmpStr = tmpStr.substring(0, tmpStr.indexOf("min")-1);
					filter.setToLength(Integer.parseInt(tmpStr));
					dataIsUpToDate = false;
				}
			}
		});
		toLength.setVisibleItemCount(1);

		// Construct length panel out of label and ListBoxes
		VerticalPanel verLengthListBoxes = new VerticalPanel();	    
		horLengthRangePanel.add(lengthLabel);
		verLengthListBoxes.add(fromLength);
		verLengthListBoxes.add(toLength);
		horLengthRangePanel.add(verLengthListBoxes);


		// Year range slider
		Label rangeLabel = new Label("Year:");
		rangeLabel.setStyleName("rangeLabel");

		rangeSliderLabel = new Label("From To");
		rangeSliderLabel.setStyleName("sliderValues");

		// The first film ever made was in 1888. We assume our application will have a life-span of
		// five years, and thus, 2020 is the current upper year limit. This could easily be changed
		// by an IT-specialist.
		rangeSlider = new RangeSlider("range", 1888, 2020, 1888, 2020);

		SimplePanel sliderContainer = new SimplePanel();
		sliderContainer.setWidth("180px");
		sliderContainer.add(rangeSlider);

		// Add all slider elements to a horizontal panel
		HorizontalPanel horSliderPanel = new HorizontalPanel();
		horSliderPanel.setWidth("250px");

		horSliderPanel.add(rangeLabel);
		horSliderPanel.add(rangeSliderLabel);
		horSliderPanel.add(sliderContainer);
		// Add listener to slider in order to react to onSlide events
		rangeSlider.addListener(this);


		// Button to reset all filter choices made by user
		Button resetFieldsButton = new Button("Reset Filters", new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Set all ListBoxes back to their default value
				countryBox.setItemSelected(0, true);
				genreBox.setItemSelected(0, true);
				languageBox.setItemSelected(0, true);
				fromLength.setItemSelected(0, true);
				toLength.setItemSelected(0, true);

				// Reset slider to its default values
				int[] resetArray = {1888, 2020};
				rangeSlider.setValues(resetArray);
				rangeSliderLabel.setText("From To");

				// Empty search box
				titleTextBox.setText("");

				// Reset actual filters
				filter.resetFilter();
				dataIsUpToDate = false;
			}
		});
		resetFieldsButton.setStyleName("yellowButton");


		// Add all ListBoxes to a horizontal panel
		HorizontalPanel horFilterPanel = new HorizontalPanel();

		horFilterPanel.add(countryBox);
		horFilterPanel.add(genreBox);
		horFilterPanel.add(languageBox);
		horFilterPanel.add(horLengthRangePanel);
		horFilterPanel.add(horSliderPanel);
		horFilterPanel.add(resetFieldsButton);


		// Add the horizontal ListBox panel to the main vertical panel
		mainVerPanel.add(horFilterPanel);
	}
	

	/**
	 * Search for an individual movie by title
	 */
	private void buildSearchPanel() {
		HorizontalPanel horSearchPanel = new HorizontalPanel();
		titleTextBox = new TextBox();

		// Press enter in TextBox to search
		titleTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					filter.setTitle(titleTextBox.getText());
					dataIsUpToDate = false;
					toBeVisualizedAs = VisualizationType.TABLE;
					getDataSet();
					
				// The following allows for a user to reset the title filter by manually deleting (via the keyboard) the text written in the TextBox.
				// For whatever reason, only the pen-ultimate KeyDownEvent in GWT TextBoxes is returned by the getText() method, 
				// i.e. the method does not return what is visible in the TextBox. 
				// This does not affect normal searching as an enter press or a button click makes the getText() method return the correct text, 
				// for the pen-ultimate has now become ultimate. 
				// In order to support emptying the title field without having to hit the reset button, we simply set the filter to "" 
				// if either the delete or the backspace keys are used (using the getText() method would result in otherwise buggy behavior).
				// This has no other negative effects for our application.
				} else if ((event.getNativeKeyCode() == KeyCodes.KEY_DELETE) || (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE)) {
					filter.setTitle("");
					dataIsUpToDate = false;
				}
			}
		});
		
		// Click button to search
		Button searchButton = new Button("Search by Title", new ClickHandler() {
			public void onClick(ClickEvent event) {
				filter.setTitle(titleTextBox.getText());
				dataIsUpToDate = false;
				toBeVisualizedAs = VisualizationType.TABLE;
				getDataSet();
			}
		});
		searchButton.setStyleName("yellowButton");

		horSearchPanel.add(titleTextBox);
		horSearchPanel.add(searchButton);

		// Add to main vertical panel
		mainVerPanel.add(horSearchPanel);
	}


	/**
	 * Remove old visualization, source, ad banner, or loading label
	 */
	private void removeOldPanels() {
		if (isAdvertised && !isDisplayed && !isLoading) {
			// Remove ad
			mainVerPanel.remove(bottomWidgetIndex);
		} else {
			// Remove ad
			mainVerPanel.remove(bottomWidgetIndex);
			// Remove source
			mainVerPanel.remove(bottomWidgetIndex);
			// Remove graphic or loading label
			mainVerPanel.remove(bottomWidgetIndex);
		}
		isLoading = false;
		isAdvertised = false;
		isDisplayed = false;
	}


	/**
	 * Visualize in table.
	 */  
	private void visualizeInTable() {
		removeOldPanels();
		TableVisualizer table = new TableVisualizer(movieCollection);
		mainVerPanel.add(table.createVisualization());
		isDisplayed = true;
		addSource();
		addBannerAd();
	}

	/**
	 * Visualize movies on a world map.
	 */  
	private void visualizeWorldMap() {
		removeOldPanels();
		// If the user searched for a specific movie, or only one movie applies to the selected filter criteria
		if (movieCollection.getMovieCollectionSize() == 1) {
			MovieWorldMapVisualizer worldMap = new MovieWorldMapVisualizer(movieCollection.getMovies().get(0));
			printWidget = worldMap.createVisualization();
			mainVerPanel.add(printWidget);
			isDisplayed = true;
		// Visualize more than one movie
		} else {
			WorldMapVisualizer worldMap = new MoviesWorldMapVisualizer(movieCollection);
			printWidget = worldMap.createVisualization();
			mainVerPanel.add(printWidget);
			isDisplayed = true;
		}
		addSource();
		addBannerAd();
	}
	
	/**
	 * Visualize distribution of countries in a bar chart. Countries can be visualized this way, even though they
	 * may no longer exist such as the Soviet Union. 
	 */  
	private void visualizeBarChart() {
		removeOldPanels();
		BarChartVisualizer barChart = new BarChartVisualizer(movieCollection);
		printWidget = barChart.createVisualization();
		mainVerPanel.add(printWidget);
		isDisplayed = true;
		addSource();
		addBannerAd();
	}
	
	/**
	 * Visualize distribution of genres in a pie chart
	 */  
	private void visualizeGenrePieChart() {
		removeOldPanels();
		GenrePieChartVisualizer pieChart = new GenrePieChartVisualizer(movieCollection);
		printWidget = pieChart.createVisualization();
		mainVerPanel.add(printWidget);
		isDisplayed = true;
		addSource();
		addBannerAd();
	}
	
	/**
	 * Visualize distribution of languages in a pie chart
	 */  
	private void visualizeLanguagePieChart() {
		removeOldPanels();
		LanguagePieChartVisualizer pieChart = new LanguagePieChartVisualizer(movieCollection);
		printWidget = pieChart.createVisualization();
		mainVerPanel.add(printWidget);
		isDisplayed = true;
		addSource();
		addBannerAd();
	}

	/**
	 * Visualize movie lengths in histogram.
	 */  
	private void visualizeHistogram() {
		removeOldPanels();
		HistogramVisualizer histogram = new HistogramVisualizer(movieCollection);
		printWidget = histogram.createVisualization();
		mainVerPanel.add(printWidget);
		isDisplayed = true;
		addSource();
		addBannerAd();
	}
	

	/**
	 * Prints current graphical visualization. If browser supports a print-to-PDF function,
	 * the visualization can also be saved as a PDF file. 
	 */
    private void printOrSaveAsPDF() {
    	// If a graphical visualization is displayed on the user screen
    	if ((printWidget != null) && (toBeVisualizedAs != VisualizationType.TABLE) && (toBeVisualizedAs != VisualizationType.NONE)) {
    		Print.it(printWidget);
    	} else {
    		Window.alert("Please select a graphical visualization.");
    	}
	}
    
    
    /**
	 * Get a link to a CSV File containing all movies corresponding to the filter criteria
	 */
	private void getExportCSVLink() {
		// Inform user of data loading process
		addLoadingLabel();
		
		if (filmLoaderSvc == null) {
			filmLoaderSvc = GWT.create(FilmLoader.class);
		}

		// Set up the callback object.
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}

			// On successful callback, call displayExportLink() method 
			public void onSuccess(String result) {
				displayExportLink(result);
			}
		};

		filmLoaderSvc.getExportCSVLink(filter, callback);
	}
    
    /**
	 * Display link for CSV file of movie selection
	 */
    private void displayExportLink(String CSVLink) {
    	removeOldPanels();
    	// A link and not a visualization is displayed. 
    	toBeVisualizedAs = VisualizationType.NONE;
    	Anchor exportLink = new Anchor("Right-Click to Download Movie Selection as CSV File", CSVLink);
    	mainVerPanel.add(exportLink);
		isDisplayed = true;
		addSource();
		addBannerAd();
    }
	
    
    /**
	 * Add source as footnote to all visualizations
	 */  
	private void addSource() {		
		Label licenseTitleLabel = new Label("License:");
		licenseTitleLabel.setStyleName("sourceLabel");

		Label licenseLabel = new Label("Published under Creative Commons Attribution-ShareAlike License (http://creativecommons.org/licenses/by-sa/4.0/).");
		licenseLabel.setStyleName("sourceLabel");

		Label attributionTitleLabel = new Label("Attribution:");
		attributionTitleLabel.setStyleName("sourceLabel");

		Label attributionLabel = new Label("David Bamman, Brendan O'Connor and Noah Smith, \"Learning Latent Personas of Film Characters,\" "
				+ "in: Proceedings of the Annual Meeting of the Association for Computational Linguistics (ACL 2013), Sofia, Bulgaria, August 2013.");
		attributionLabel.setStyleName("sourceLabel");

		VerticalPanel sourcePanel = new VerticalPanel();

		sourcePanel.add(licenseTitleLabel);
		sourcePanel.add(licenseLabel);
		sourcePanel.add(attributionTitleLabel);
		sourcePanel.add(attributionLabel);

		mainVerPanel.add(sourcePanel);
	}


	/**
	 * Add banner ad
	 */  
	private void addBannerAd() {
		Image ad = new Image();
		ad.setUrl("/images/banana_2.gif");
		mainVerPanel.add(ad);
		isAdvertised = true;
	}
	
	
	/**
	 * Display a label to inform user of data loading process
	 */  
	private void addLoadingLabel() {
		removeOldPanels();
		Label loadingLabel = new Label("Loading film data. Please wait...");
		mainVerPanel.add(loadingLabel);
		isLoading = true;
		addSource();
		addBannerAd();
	}
	

	/**
	 * React to a slide event by setting the filter accordingly
	 */
	@Override
	public boolean onSlide(SliderEvent e) {
		Slider source = e.getSource();
		if (source == rangeSlider) {
			rangeSliderLabel.setText(e.getValues()[0] + " " + e.getValues()[1]);
			filter.setFromYear(e.getValues()[0]);
			filter.setToYear(e.getValues()[1]);
			dataIsUpToDate = false;
		} 
		return true;
	}

	/**
	 * Slider onChange method
	 */
	@Override
	public void onChange(SliderEvent e) {
		// No implementation necessary; we only use the onSlide Event
	}

	/**
	 * Slider onStart method
	 */
	@Override
	public void onStart(SliderEvent e) {
		// No implementation necessary; we only use the onSlide Event
	}

	/**
	 * Slider onStop method
	 */
	@Override
	public void onStop(SliderEvent e) {
		// No implementation necessary; we only use the onSlide Event        
	}

}
package ch.uzh.sweng15.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

public class GWTModuleLoader implements EntryPoint {
	
	private VerticalPanel mainVerPanel;
	private MovieCollection mc;
    private boolean isVisualized = false;
    final int bottomWidgetIndex = 3;
        
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		mc = new MovieCollection();
		buildGUI();
	}
	
	
	/**
	 * Create graphical user interface (GUI)
	 */
	private void buildGUI() {
		// Create new main vertical panel to which all other panels shall be added
		mainVerPanel = new VerticalPanel();
		
		// Add header image
		Image image = new Image();
		image.setUrl("/images/header4.png");
		mainVerPanel.add(image);

		// Add horizontal panels to main vertical panel
		constructButtonPanel();
		constructFilterPanel();

		// Vertical Panel Settings
		mainVerPanel.setSpacing(25);

		// Insert the main vertical panel into a scroll panel to allow for scrolling
		ScrollPanel sp = new ScrollPanel();
		sp.add(mainVerPanel);

		// Add main vertical panel inside scroll panel to root panel
		RootLayoutPanel.get().add(sp);
	}
	
	
	/**
	 * Create button panel.
	 */
	private void constructButtonPanel() {
	    // Table button
	    Button tableButton = new Button("Visualize in GWT Table", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        visualizeInTable();
	      }
	    });
	    tableButton.setStyleName("blueButton");
	    
	    
	    // Table button
	    Button gTableButton = new Button("Visualize in GCharts Table", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  visualizeInGChartsTable();
	      }
	    });
	    gTableButton.setStyleName("blueButton");
	    
	    
	    // Pie Chart Button
	    Button pieChartButton = new Button("Pie Chart", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  //visualizePieChart();
	      }
	    });
	    //pieChartButton.setStyleName("purpleButton");
	    pieChartButton.setStyleName("disabledButton");
	    pieChartButton.setEnabled(false);
	    
	    
	    // Spider Web Button
	    Button spiderWebButton = new Button("Spider Web", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  //visualizeSpiderWeb();
	      }
	    });
	    //spiderWebButton.setStyleName("purpleButton");
	    spiderWebButton.setStyleName("disabledButton");
	    spiderWebButton.setEnabled(false);
	    
	    
	    // Bar Graph Button
	    Button barGraphButton = new Button("Bar Graph", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  //visualizeBarGraph();
	      }
	    });
	    //barGraphButton.setStyleName("purpleButton");
	    barGraphButton.setStyleName("disabledButton");
	    barGraphButton.setEnabled(false);
	    
	    
	    // World Map Button
	    Button worldMapButton = new Button("World Map", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  //visualizeWorldMap();
	      }
	    });
	    //worldMapButton.setStyleName("greenButton");
	    worldMapButton.setStyleName("disabledButton");
	    worldMapButton.setEnabled(false);
	    
	    
	    // Length Histogram Button
	    Button histogramButton = new Button("Histogram", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  //visualizeHistogram();
	      }
	    });
	    //histogramButton.setStyleName("purpleButton");
	    histogramButton.setStyleName("disabledButton");
	    histogramButton.setEnabled(false);
	    
	    
	    // Add all buttons to a horizontal panel
	    HorizontalPanel horButtonPanel = new HorizontalPanel();

	    horButtonPanel.add(tableButton);
	    horButtonPanel.add(gTableButton);
	    horButtonPanel.add(worldMapButton);
	    horButtonPanel.add(barGraphButton);
	    horButtonPanel.add(spiderWebButton);
	    horButtonPanel.add(pieChartButton);
	    horButtonPanel.add(histogramButton);
	    
	    // Add horizontal panel with all button to main vertical panel
	    mainVerPanel.add(horButtonPanel);  
	}
	
	
	/**
	 * Create filter panel
	 */
	private void constructFilterPanel() {
		
		// Country box
	    final ListBox countryBox = new ListBox() {
	        @Override
	        public void setSelectedIndex(int index) {
	            super.setSelectedIndex(index);
	            // If you want to call a function here, use "this" for ListBox reference
	        }
	    };
	    
	    countryBox.addItem("Country");

		
	    countryBox.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	        }
	    });
	    countryBox.setVisibleItemCount(1);
		
		
	    
	    // Genre box
	    final ListBox genreBox = new ListBox() {
	        @Override
	        public void setSelectedIndex(int index) {
	            super.setSelectedIndex(index);
	        }
	    };
	    
	    genreBox.addItem("Genre");

		genreBox.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	        	// Do something with selected list box item
	        }
	    });
	    genreBox.setVisibleItemCount(1);
		
		
	    
		// Language box
	    final ListBox languageBox = new ListBox() {
	        @Override
	        public void setSelectedIndex(int index) {
	            super.setSelectedIndex(index);
	        }
	    };
	    
	    languageBox.addItem("Language");

		languageBox.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	        	// Do something with selected list box item
	        }
	    });
		languageBox.setVisibleItemCount(1);
		
		
		
		// Year range boxes
	    HorizontalPanel horYearRangePanel = new HorizontalPanel();
	    
	    Label yearLabel = new Label("Year:");
	    
		// From year box
	    final ListBox fromYear = new ListBox() {
	        @Override
	        public void setSelectedIndex(int index) {
	            super.setSelectedIndex(index);
	        }
	    };   
	    fromYear.addItem("From");
		for (int i=2015; i>1887; i--) {
			fromYear.addItem(Integer.toString(i));
		}
		fromYear.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	        	// Do something with selected list box item
	        }
	    });
	    fromYear.setVisibleItemCount(1);
		
		// To year box
	    final ListBox toYear = new ListBox() {
	        @Override
	        public void setSelectedIndex(int index) {
	            super.setSelectedIndex(index);
	        }
	    };
	    toYear.addItem("To");
		for (int i=2015; i>1887; i--) {
			toYear.addItem(Integer.toString(i));
		}

		toYear.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	        	// Do something with selected list box item
	        }
	    });
		toYear.setVisibleItemCount(1);

		VerticalPanel verYearListBoxes = new VerticalPanel();
		horYearRangePanel.add(yearLabel);
		horYearRangePanel.add(verYearListBoxes);
		verYearListBoxes.add(fromYear);
		verYearListBoxes.add(toYear);

		
		
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
	    fromLength.addItem("500+ min");
	    fromLength.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	        	// Do something with selected list box item
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
	        	// Do something with selected drop box item
	        }
	    });
	    toLength.setVisibleItemCount(1);
	    
	    VerticalPanel verLengthListBoxes = new VerticalPanel();	    
	    horLengthRangePanel.add(lengthLabel);
	    verLengthListBoxes.add(fromLength);
	    verLengthListBoxes.add(toLength);
	    horLengthRangePanel.add(verLengthListBoxes);
	    
  
	    
	    // Reset filter and fields button
	    Button resetFieldsButton = new Button("Reset Filters", new ClickHandler() {
		    public void onClick(ClickEvent event) {
				// Reset UI
			    countryBox.setItemSelected(0, true);
			    genreBox.setItemSelected(0, true);
			    languageBox.setItemSelected(0, true);
			    fromYear.setItemSelected(0, true);
		    	toYear.setItemSelected(0, true);
			    fromLength.setItemSelected(0, true);
		    	toLength.setItemSelected(0, true);
		    	
		    	//removeOldPanels();
		      }
		    });
	    resetFieldsButton.setStyleName("yellowResetButton");

	    
	    
	    // Add all filters to a horizontal panel
	    HorizontalPanel horFilterPanel = new HorizontalPanel();
	    
	    horFilterPanel.add(countryBox);
	    horFilterPanel.add(genreBox);
	    horFilterPanel.add(languageBox);
	    horFilterPanel.add(horYearRangePanel);
	    horFilterPanel.add(horLengthRangePanel);
	    horFilterPanel.add(resetFieldsButton);
	   
	    		
	    // Add the horizontal filter panel to the main vertical panel
	    mainVerPanel.add(horFilterPanel);
	}
	
	
	/**
	 * Remove current visualization
	 */
	private void removeOldPanels() {
		if (isVisualized) {
			// Remove graphic
			mainVerPanel.remove(bottomWidgetIndex);
		}
		isVisualized = false;
	}
	
	
	/**
	 * Visualize in table method. Called by corresponding button.
	 */  
	private void visualizeInTable() {
		removeOldPanels();
		TableVisualizer table = new TableVisualizer();
		mainVerPanel.add(table.createVisualization(mc));
	    isVisualized = true;
	}
	
	private void visualizeInGChartsTable() {
		removeOldPanels();
		GChartsTableVisualizer table = new GChartsTableVisualizer(mc);
		mainVerPanel.add(table.createVisualization());
	    isVisualized = true;
	}
	
	// TODO
	private void visualizePieChart() {
	}
	
	// TODO
	private void visualizeSpiderWeb() {
	}
	
	// TODO
	private void visualizeBarGraph() {
	}
	
	// TODO
	private void visualizeWorldMap() {
	}
	
	// TODO
	private void visualizeHistogram() {
	}
	
}


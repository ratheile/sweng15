package ch.uzh.sweng15.filmvisualizer.client;

import com.google.gwt.user.client.ui.Widget;

/** 
 * Interface for visualizing a {@link MovieCollection} 
 * 
 * @author 	Dzmitry K.
 */

public interface Visualizer {
	
	/** 
	 * Creates a {@link Widget} that contains the visualization of film data and returns it
	 * @return Widget A Widget containing a visualization
	 */
	public Widget createVisualization();
}

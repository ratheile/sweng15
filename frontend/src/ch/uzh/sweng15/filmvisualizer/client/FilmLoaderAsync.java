package ch.uzh.sweng15.filmvisualizer.client;

import java.util.ArrayList;
import java.util.TreeSet;

import com.google.gwt.user.client.rpc.AsyncCallback;

/** 
 * Interface defining available RPC services with AsyncCallback parameter
 * 
 * @author 	Joel H.
 */

public interface FilmLoaderAsync {
	
	/** 
	 * Retrieves movie data from a remote server for the client and passes it back to the client as a {@link MovieCollection}.
	 * The movies contained in the movie collection correspond to the selected filter criteria.
	 * @param filter Filter containing the selected filter criteria
	 * @param callback AsyncCallback<MovieCollection> that is returned upon successful method completion
	 */
	void getRemoteMovieCollection(Filter filter, AsyncCallback<MovieCollection> callback);
	
	/** 
	 * Returns the names of all countries, genres and languages so that the ListBoxes in the GUI can be populated appropriately.
	 * @param fileLocations ArrayList<String> that indicates where the film data files are located
	 * @param itemNames AsyncCallback<ArrayList<TreeSet<String>>> that is returned upon successful method completion
	 */
	void getListBoxItemNames(ArrayList<String> fileLocations, AsyncCallback<ArrayList<TreeSet<String>>> itemNames);	
	
	/** 
	 * Retrieves the location of a CSV file containing all movies that correspond to the selected filter criteria
	 * @param Filter containing the selected filter criteria
	 * @param AsyncCallback<String> that is returned upon successful method completion
	 */
	void getExportCSVLink(Filter filter, AsyncCallback<String> callback);
}

package ch.uzh.sweng15.filmvisualizer.client;

import java.util.ArrayList;
import java.util.TreeSet;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/** 
 * Interface defining available RPC services 
 * 
 * @author 	Joel H.
 */

@RemoteServiceRelativePath("filmData")
public interface FilmLoader extends RemoteService 	{
	
	/** 
	 * Retrieves movie data from a remote server for the client and passes it back to the client as a {@link MovieCollection}.
	 * The movies contained in the movie collection correspond to the selected filter criteria.
	 * @param filter Filter containing the selected filter criteria
	 * @return MovieCollection A movie collection containing a filtered selection of movies
	 */
	MovieCollection getRemoteMovieCollection(Filter filter);
	
	/** 
	 * Returns the names of all countries, genres and languages so that the ListBoxes in the GUI can be populated appropriately.
	 * @param fileLocations ArrayList<String> that indicates where the film data files are located
	 * @return ArrayList<TreeSet<String>> Contains the names of all countries, genres and languages
	 */
	ArrayList<TreeSet<String>> getListBoxItemNames(ArrayList<String> fileLocations);
	
	/** 
	 * Retrieves the location of a CSV file containing all movies that correspond to the selected filter criteria
	 * @param Filter containing the selected filter criteria
	 * @return String containing the link to the CSV file
	 */
	String getExportCSVLink(Filter filter);
	
}

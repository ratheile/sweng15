package ch.uzh.sweng15.backend.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import ch.uzh.sweng15.backend.db.DatabaseAdapter;
import ch.uzh.sweng15.backend.db.DatabaseMovieImporter;
import ch.uzh.sweng15.backend.pojo.Filter;
import ch.uzh.sweng15.backend.pojo.Movie;

/**
 * This class does handle movie api of the server. it is registered as a servlet
 * in the web.xml file. (this is very important, otherwise it its not possible
 * to access the endpoints)
 * 
 * @author Raffael Theiler
 *
 */
@Path("/")
public class MovieVisualizerAPI {

	@Inject
	private DatabaseAdapter adapter;

	/**
	 * This constructor is called by the dependency injection system of the
	 * webserver
	 */
	public MovieVisualizerAPI() {
		//default constructor
	}

	/**
	 * Return a random movie in JSON format at the endpoint
	 * 
	 * @return the JSON string
	 */
	@GET
	@Path("/movie/random")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRandomMovie() {
		return adapter.getRandomMovie();
	}

	/**
	 * This method handles the upload of a TSV movie file and adds it to the
	 * database
	 * 
	 * @param fileInputStream	the inputstream of the data (formdata)
	 * @param contentDispositionHeader	Information about the transfered file
	 * @return Status information if the import was successful
	 */
	@POST
	@Path("/db/add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String appendToDatabase(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		try {
			List<Movie> movies = DatabaseMovieImporter.importToDatabase(fileInputStream, adapter);
			return String.format("Success, inserted %d movies", movies.size());
		} catch (IOException e) {
			// TODO: do not return the error message (security)
			return e.getMessage();
		}

	}

	/**
	 * This method handles the upload of a new TSV movie database
	 * 
	 * @param fileInputStream	the inputstream of the data (formdata)
	 * @param contentDispositionHeader	Information about the transfered file
	 * @return Status information if the import was successful
	 */
	@POST
	@Path("/db/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String importDatabase(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();

		try {
			List<Movie> movies = DatabaseMovieImporter.importToDatabase(fileInputStream, adapter);
			return String.format("Success, deleted database and inserted %d movies", movies.size());
		} catch (IOException e) {
			// TODO: do not return the error message (security)
			return e.getMessage();
		}
	}

	/**
	 * This function looks for the movies defined in a filter received trough
	 * the api.
	 * 
	 * @param filter	the Filter containing all parameters
	 * @return A JSON array of movies
	 */
	@POST
	@Path("/movie/filtered")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String postRequestFilteredMovies(Filter filter) {
		return adapter.getFilteredListOfMovies(filter);
	}

	/**
	 * This function looks for the movies defined in a filter received trough
	 * the api and returns a link to a csv containing the data.
	 * 
	 * @param filter	the Filter containing all parameters
	 * @return A JSON with a link to the csv document
	 */
	@POST
	@Path("/movie/filtered/csv")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String postRequestFilteredMoviesAsCSV(Filter filter) {
		return adapter.getFilteredListOfMovies(filter);
	}

}

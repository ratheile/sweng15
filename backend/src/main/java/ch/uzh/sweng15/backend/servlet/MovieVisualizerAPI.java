package ch.uzh.sweng15.backend.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import ch.uzh.sweng15.backend.db.DatabaseAdapter;
import ch.uzh.sweng15.backend.db.TSVParser;
import ch.uzh.sweng15.backend.pojo.Movie;


/**
 * This class does handle movie api of the server. it is registered
 * as a servlet in the web.xml file. (this is very important, otherwise
 * it its not possible to access the endpoints)
 * @author Raffael Theiler
 *
 */
@Path("/")
public class MovieVisualizerAPI {

	private DatabaseAdapter adapter;

	/**
	 * This constructor is called by the dependency injection system
	 * of the webserver 
	 */
	public MovieVisualizerAPI() {
		// TODO: add this to config;
		adapter = new DatabaseAdapter("localhost", 27017, "test");
	}

	/**
	 * Return a random movie in JSON format at the endpoint
	 * @return the JSON string
	 */
	@GET
	@Path("/random")
	public String getRandomMovie(){
		return adapter.getRandomMovie();
	}

	/**
	 * This method handles the upload of a new TSV movie database
	 * @param fileInputStream the inputstream of the data (formdata)
	 * @param contentDispositionHeader Information about the transfered file
	 * @return Status information if the import was successful
	 */
	@POST 
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
	public String importDatabase(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		List<Movie> movies = new ArrayList<>();
		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();

		try {
			/*
			 * utility call to retrieve the whole file from the stream
			 * this was necessary because we receive a binary stream but
			 * we want to separate by line to parse the input
			 * 
			 * has to be optimized eventually when uploading very large files
			 * because of the server heap size.
			 */
			String fileAsString = IOUtils.toString(fileInputStream, "UTF-8");
			Scanner scanner = new Scanner(fileAsString);
			
			//each line of the received file is parsed line by line
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Movie movie = TSVParser.parseMovieLine(line);
				movies.add(movie);
				System.out.println(String.format("inserting Movie: %s", movie.toString()));
				adapter.insertMovie(movie);
			}
			scanner.close();
		} catch (IOException e) {
			//TODO: do not return the error message (security)
			return e.getMessage();
		}

		return String.format("success, parsed %d Movies", movies.size());

	}

}

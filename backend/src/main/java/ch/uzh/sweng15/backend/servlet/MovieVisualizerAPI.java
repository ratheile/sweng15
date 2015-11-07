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
import ch.uzh.sweng15.backend.temp.Movie;

@Path("/")
public class MovieVisualizerAPI {

	private DatabaseAdapter adapter;

	public MovieVisualizerAPI() {
		// TODO: add this to config;
		adapter = new DatabaseAdapter("localhost", 27017, "test");
	}


	@GET
	@Path("/random")
	public String getRandomMovie(){
		return adapter.getRandomMovie();
	}

	@POST @Path("/upload") @Consumes(MediaType.MULTIPART_FORM_DATA) public String importDatabase(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		List<Movie> movies = new ArrayList<>();
		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();

		try {
			String fileAsString = IOUtils.toString(fileInputStream, "UTF-8");
			Scanner scanner = new Scanner(fileAsString);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Movie movie = TSVParser.parseMovieLine(line);
				movies.add(movie);
				System.out.println(String.format("inserting Movie: %s", movie.toString()));
				adapter.insertMovie(movie);
			}
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

		return String.format("success, parsed %d Movies", movies.size());

	}

}

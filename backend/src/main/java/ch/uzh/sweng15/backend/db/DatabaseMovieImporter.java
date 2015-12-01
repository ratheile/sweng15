package ch.uzh.sweng15.backend.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import ch.uzh.sweng15.backend.pojo.Movie;

public class DatabaseMovieImporter {

	/**
	 * Adds movies from an {@link InputStream}
	 * (received trough an api most of the time). to a database.
	 * The movies have to be in tsv format
	 * 
	 * @param fileInputStream the input stream containing all the movies
	 * @param adapter the database Adapter
	 * @return a list of all the movies added to the database.
	 * @throws IOException exception thrown in case of an invalid stream
	 */
	public static List<Movie> importToDatabase(
			InputStream fileInputStream, DatabaseAdapter adapter) throws IOException {
		
		List<Movie> movies = new ArrayList<>();


		/*
		 * utility call to retrieve the whole file from the stream this was
		 * necessary because we receive a binary stream but we want to
		 * separate by line to parse the input
		 * 
		 * has to be optimized eventually when uploading very large files
		 * because of the server heap size.
		 */
		String fileAsString = IOUtils.toString(fileInputStream, "UTF-8");
		Scanner scanner = new Scanner(fileAsString);

		// each line of the received file is parsed line by line
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Movie movie = TSVParser.parseMovieLine(line);
			movies.add(movie);
			System.out.println(String.format("Parsing movie: %s", movie.toString()));
		}

		System.out.println(String.format("Success, parsed %d movies", movies.size()));
		System.out.println(String.format("Adding movies to the database"));
		adapter.bulkInsertMovies(movies);
		System.out.println(String.format("Done, success"));

		scanner.close();
		
		return movies;

	}

}

package ch.uzh.sweng15.backend.db;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoCursor;

import ch.uzh.sweng15.backend.pojo.Filter;
import ch.uzh.sweng15.backend.pojo.Movie;

public class DabaseFilteringTest {

	private static DatabaseAdapter adapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		adapter = new DatabaseAdapter("localhost", 27017, "movies");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		adapter.deleteDatabase();
	}

	/*
	 * We test the following conditions: a empty filter should return all movies
	 * a filter for the exact movie should return this movie
	 */
	
	
	
	@Test
	public void testEmptyFilter() {

		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();
		int nMoviesInTest = fillDBWithMovies();


		Filter emptyFilter = new Filter();

		//filter empty
		int count = 0;
		MongoCursor<Document> filteredListOfMovieDocuments = 
				adapter.getFilteredListOfMovieDocuments(emptyFilter);

		while (filteredListOfMovieDocuments.hasNext()) {
			Document next = filteredListOfMovieDocuments.next();
			count++;
		}

		assertEquals(nMoviesInTest, count);
	}
	
	@Test
	public void testFilterForAll(){
		
		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();
		fillDBWithMovies();
		
		Filter allFilter = new Filter();
		allFilter.setTitle("Testmovie2");
		allFilter.setCountry("Switzerland");
		allFilter.setGenre("Action");
		allFilter.setFromLength(150);
		allFilter.setToLength(150);
		allFilter.setFromYear(1950);
		allFilter.setToLength(1950);
		
		int count = 0;
		
		//filter for all
		count = 0;
		MongoCursor<Document> filteredListOfMovieDocuments2 = 
				adapter.getFilteredListOfMovieDocuments(allFilter);

		while (filteredListOfMovieDocuments2.hasNext()) {
			Document next = filteredListOfMovieDocuments2.next();
			count++;
		}

		assertEquals(1, count);
	}
	
	

	private int fillDBWithMovies() {
		ArrayList<String> country = new ArrayList<>();
		country.add("Switzerland");
		ArrayList<String> genres = new ArrayList<>();
		genres.add("Action");
		ArrayList<String> languages = new ArrayList<>();
		languages.add("German");
		Movie movie = new Movie("Testmovie", 1922, 100, country, genres, languages);
		Movie movie2 = new Movie("Testmovie2", 1950, 150, country, genres, languages);

		adapter.insertMovie(movie);
		adapter.insertMovie(movie2);
		
		return 2; //return n movies in this db
	}
	
}

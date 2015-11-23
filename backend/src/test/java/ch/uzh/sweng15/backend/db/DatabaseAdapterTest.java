/**
 * 
 */
package ch.uzh.sweng15.backend.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.uzh.sweng15.backend.pojo.Movie;

/**
 * Thest of  all database second lvl api functions
 * @author Raffael Theiler
 *
 */
public class DatabaseAdapterTest {

	private static DatabaseAdapter adapter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		adapter = new DatabaseAdapter("localhost",27017, "movies");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		adapter.deleteDatabase();
	}

	/*
	 * test a simple insert into an empty database,
	 * then read back the result and compare it to the expected value
	 */
	@Test
	public void testSimpleInsertAndRetrieve() {
		
		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();
		
		ArrayList<String> country = new ArrayList<>();
		ArrayList<String> genres = new ArrayList<>();
		ArrayList<String> languages = new ArrayList<>();
		Movie movie = new Movie("testmovie", 1922, 1000, country, genres, languages);
		System.out.println(movie.toString());
		System.out.println(movie.getNewBSONRepresentation().toJson());
		adapter.insertMovie(movie);
		List<Document> allMoviesAsJSON = adapter.getAllMoviesAsBSON();
		Document first = allMoviesAsJSON.get(0);
		boolean compare = movie.compareTo(first);
		assertEquals(true,compare);
	}
	
	
	/*
	 * Test the bulk insert feature together with the delete feature
	 * (the size after the db was deleted and refilled must be = 2)
	 */
	@Test
	public void testBulkInsert(){
		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();
		
		ArrayList<String> country = new ArrayList<>();
		country.add("Switzerlad");
		ArrayList<String> genres = new ArrayList<>();
		genres.add("Action");
		ArrayList<String> languages = new ArrayList<>();
		languages.add("German");
		
		Movie movie = new Movie("Testmovie", 1922, 100, country, genres, languages);
		Movie movie2 = new Movie("Testmovie2", 1950, 150, country, genres, languages);
		
		List <Movie> bulk = new ArrayList<>();
		bulk.addAll(Arrays.asList(movie, movie2));
		
		adapter.bulkInsertMovies(bulk);
		List<Document> allMoviesAsBSON = adapter.getAllMoviesAsBSON();
		
		assertEquals(2, allMoviesAsBSON.size());
		
	}

}

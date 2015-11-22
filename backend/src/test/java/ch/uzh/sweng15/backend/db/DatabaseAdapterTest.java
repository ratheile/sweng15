/**
 * 
 */
package ch.uzh.sweng15.backend.db;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
		adapter = new DatabaseAdapter("mongodb://test:test@ds057204.mongolab.com:57204/sweng15");
		adapter.deleteDatabase();
		adapter.setupEmptyDatabase();
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

}

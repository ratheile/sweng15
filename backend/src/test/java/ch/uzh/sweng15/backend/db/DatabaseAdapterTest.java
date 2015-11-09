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
		adapter = new DatabaseAdapter("localhost", 27017, "test");
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
		ArrayList<String> genres = new ArrayList<>();
		Movie movie = new Movie("testmovie", 1922, 1000, "testcountry", genres, "testlang");
		adapter.insertMovie(movie);
		List<Document> allMoviesAsJSON = adapter.getAllMoviesAsBSON();
		Document first = allMoviesAsJSON.get(0);
		boolean compare = movie.compareTo(first);
		assertEquals(true,compare);
	}

}

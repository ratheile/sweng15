/**
 * 
 */
package ch.uzh.sweng15.backend.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.uzh.sweng15.backend.temp.Movie;

/**
 * @author shafall
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

	@Test
	public void testSimpleInsertAndRetrieve() {
		ArrayList<String> genres = new ArrayList<>();
		Movie movie = new Movie("testmovie", 1922, 1000, "testcountry", genres, "testlang");
		adapter.insertMovie(movie);
		List<Document> allMoviesAsJSON = adapter.getAllMoviesAsBSON();
		Document first = allMoviesAsJSON.get(0);
		boolean compare = movie.compareTo(first);
		assert(compare);
	}

}

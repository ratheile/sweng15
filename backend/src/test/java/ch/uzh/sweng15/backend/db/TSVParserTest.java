package ch.uzh.sweng15.backend.db;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.uzh.sweng15.backend.pojo.Movie;

public class TSVParserTest {

	
	private static String testMovie = "975900	/m/03vyhn	Ghosts of Mars	2001-08-24	14010832	98.0"
			+ "	{\"/m/02h40lc\": \"English Language\"}"
			+ "	{\"/m/09c7w0\": \"United States of America\"}"
			+ "	{\"/m/01jfsb\": \"Thriller\", \"/m/06n90\": \"Science Fiction\","
			+ " \"/m/03npn\": \"Horror\", \"/m/03k9fj\": \"Adventure\","
			+ " \"/m/0fdjb\": \"Supernatural\", \"/m/02kdv5l\":"
			+ " \"Action\", \"/m/09zvmj\": \"Space western\"}";
	
	private static String testMovieDisplayString = "Title: Ghosts of Mars, Year: 2001, Length: 98 min,"
			+ " Country: United States of America, Genre: [Thriller],"
			+ " Language: English Language";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * Test if the movie class content is equal to the string
	 */
	@Test
	public void testParseMovieLine() {
		Movie parseMovieLine = TSVParser.parseMovieLine(testMovie);
		assertEquals(testMovieDisplayString, parseMovieLine.getDisplayString());
	}

}

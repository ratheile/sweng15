package ch.uzh.sweng15.backend.pojo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MovieTest {

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
	 * W are testing if every atomic compare is working inside the testCompare function
	 */
	@Test
	public void testCompareTo(){
		ArrayList<String> genres = new ArrayList<>();
		ArrayList<String> alteredGenres = new ArrayList<>();
		alteredGenres.addAll(Arrays.asList("test1"));
		genres.addAll(Arrays.asList("test1", "test2", "test3"));
		
		Movie movie = new Movie("testmovie", 1922, 1000, "testcountry", genres, "testlang");
		Movie movie_alteredTitle = new Movie("t3stmovie", 1922, 1000, "testcountry", genres, "testlang");
		Movie movie_alteredYear = new Movie("testmovie", 1923, 1000, "testcountry", genres, "testlang");
		Movie movie_alteredLength = new Movie("testmovie", 1922, 1001, "testcountry", genres, "testlang");
		Movie movie_alteredCountry = new Movie("testmovie", 1922, 1000, "t3stcountry", genres, "testlang");
		Movie movie_alteredGenre = new Movie("testmovie", 1922, 1000, "testcountry", alteredGenres, "testlang");
		Movie movie_alteredLang = new Movie("testmovie", 1922, 1000, "testcountry", genres, "t3stlang");
		
		assertEquals(true, movie.compareTo(movie.getNewBSONRepresentation()));
		assertEquals(false, movie.compareTo(movie_alteredTitle.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredYear.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredLength.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredCountry.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredGenre.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredLang.getNewBSONRepresentation()));
	}
	
	/*
	 * We are testing if the BSON representation is equal to the movie object
	 */
	@Test
	public void testGetNewBSONRepresentation() {
		ArrayList<String> genres = new ArrayList<>();
		genres.addAll(Arrays.asList("test1", "test2", "test3"));
		Movie movie = new Movie("testmovie", 1922, 1000, "testcountry", genres, "testlang");
		//this test depends on the correctness of the compare test
		boolean compare = movie.compareTo(movie.getNewBSONRepresentation());
		assertEquals(true,compare);
	}
	

}

package ch.uzh.sweng15.filmvisualizer.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.sweng15.filmvisualizer.client.Movie;
import ch.uzh.sweng15.filmvisualizer.client.MovieCollection;

/**
 * Unit tests for the {@link MovieCollection} class.
 * 
 * @author 	Joel H.
 */

public class MovieCollectionTest {
	private MovieCollection testMovieCollection;
	private Movie testMovie;
	
	/** 
	 * Set up before each unit test is to create a new MovieCollection and a {@link Movie} object.
	 */
	@Before
	public void setup() {
		testMovieCollection = new MovieCollection();
		
		ArrayList<String> genreList = new ArrayList<String>();
		genreList.add("Horror");
		ArrayList<String> countryList = new ArrayList<String>();
		countryList.add("Canada");
		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("English");
		languageList.add("French");
		languageList.add("Armenian");
		testMovie = new Movie("Pontypool", 2009, 93, countryList, genreList, languageList);
	}
	
	/** 
	 * Tests adding a movie to a MovieCollection
	 */
	@Test
	public void testAddMovie() {
		testMovieCollection.addMovie(testMovie);
		int testMovieLocation = testMovieCollection.getMovies().indexOf(testMovie);
		assertTrue(testMovieCollection.getMovies().get(testMovieLocation).getTitle().equals("Pontypool"));
		assertTrue(testMovieCollection.getMovies().get(testMovieLocation).getLanguage().get(2).equals("Armenian"));
	}
	
	/** 
	 * Tests removing a movie from a MovieCollection
	 */
	@Test
	public void testRemoveMovie() {
		testMovieCollection.addMovie(testMovie);
		testMovieCollection.removeMovie(testMovie);
		assertFalse(testMovieCollection.getMovies().contains(testMovie));
	}
	
	/** 
	 * Tests getting the size of the MovieCollection
	 */
	@Test
	public void testGetMovieCollectionSize() {
		assertTrue(testMovieCollection.getMovieCollectionSize() == 0);
		ArrayList<Movie> testMovieList = new ArrayList<Movie>();
		testMovieList.add(testMovie);
		testMovieCollection.setMovies(testMovieList);
		assertTrue(testMovieCollection.getMovieCollectionSize() == 1);
	}
	
	/** 
	 * Tests getter method for the ArrayList<Movie> movies instance variable
	 */
	@Test
	public void testGetMovies() {
		assertTrue(testMovieCollection.getMovies() instanceof ArrayList<?>);
		testMovieCollection.addMovie(testMovie);
		assertTrue(testMovieCollection.getMovies().get(0).getTitle().equals("Pontypool"));
		assertTrue(testMovieCollection.getMovies().get(0).getLanguage().get(2).equals("Armenian"));
	}
	
	/** 
	 * Tests setter method for the ArrayList<Movie> movies instance variable
	 */
	@Test
	public void testSetMovies() {
		ArrayList<Movie> testMovieList = new ArrayList<Movie>();
		testMovieList.add(testMovie);
		testMovieCollection.setMovies(testMovieList);
		assertTrue(testMovieCollection.getMovies().get(0).getTitle().equals("Pontypool"));
		assertTrue(testMovieCollection.getMovies().get(0).getLanguage().get(2).equals("Armenian"));
	}
	
}
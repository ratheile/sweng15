package ch.uzh.sweng15.filmvisualizer.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ch.uzh.sweng15.filmvisualizer.client.Movie;

/**
 * Unit tests for the {@link Movie} class.
 * 
 * @author 	Joel H.
 */

public class MovieTest {
	Movie testMovie;

	/** 
	 * Create the following Movie object before each unit test
	 */
	@Before
	public void setUp() {
		ArrayList<String> genreList = new ArrayList<String>();
		genreList.add("Action");
		genreList.add("Sci-Fi");
		ArrayList<String> countryList = new ArrayList<String>();
		countryList.add("USA");
		countryList.add("Australia");
		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("English");
		testMovie = new Movie("The Matrix", 1999, 136, countryList, genreList, languageList);
	}
	
	/** 
	 * Tests constructor with parameters
	 */
	@Test
	public void testConstructorWithParameters() {
		assertTrue(testMovie.getTitle().equals("The Matrix"));
		assertTrue(testMovie.getYear().equals(1999));
		assertTrue(testMovie.getLength().equals(136));
		assertTrue(testMovie.getCountry().get(0).equals("USA"));
		assertTrue(testMovie.getCountry().get(1).equals("Australia"));
		assertTrue(testMovie.getGenre().get(0).equals("Action"));
		assertTrue(testMovie.getGenre().get(1).equals("Sci-Fi"));
		assertTrue(testMovie.getLanguage().get(0).equals("English"));
	}
	
	/** 
	 * Test getRelevantMovieInfo() method
	 */
	@Test
	public void testGetRelevantMovieInfo() {
		ArrayList<String> countryList = new ArrayList<String>();
		countryList.add("Germany");
		countryList.add("United States of America");
		countryList.add("Denmark");
		countryList.add("Sweden");
		countryList.add("Norway");
		
		ArrayList<String> genreList = new ArrayList<String>();
		genreList.add("War film");
		genreList.add("Family & Personal Relationships");
		genreList.add("Drama");
		genreList.add("Political Drama");
		genreList.add("World Cinema");
		genreList.add("Biopic [feature]");

		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("Australian Aboriginal Pidgin English");
		languageList.add("Swedish");
		languageList.add("German");
		languageList.add("Norwegian");
		languageList.add("Danish");
		
		Movie complexTestMovie = new Movie("Hamsun", 1996, 160, countryList, genreList, languageList);
		
		assertTrue(testMovie.getRelevantMovieInfo().equals("Title: The Matrix, Year: 1999, Length: 136 min, "
				+ "Genre: [Action, Sci-Fi], Language: [English]"));
		assertTrue(complexTestMovie.getRelevantMovieInfo().equals("Title: Hamsun, Year: 1996, Length: 160 min, "
				+ "Genre: [War film, Family & Personal Relationships, ...], Language: [Australian Aboriginal Pidgin English, Swedish, ...]"));
	}
	
	/** 
	 * Test all getter and setter methods.
	 */
	@Test
	public void testGetTitle() {
		assertTrue(testMovie.getTitle().equals("The Matrix"));
	}
	
	@Test
	public void testSetTitle() {
		testMovie.setTitle("The Matrix Reloaded");
		assertTrue(testMovie.getTitle().equals("The Matrix Reloaded"));
	}
	
	@Test
	public void testGetYear() {
		assertTrue(testMovie.getYear().equals(1999));
	}
	
	@Test
	public void testSetYear() {
		testMovie.setYear(2000);
		assertTrue(testMovie.getYear().equals(2000));
	}
	
	@Test
	public void testGetLength() {
		assertTrue(testMovie.getLength().equals(136));
	}
	
	@Test
	public void testSetLength() {
		testMovie.setLength(150);
		assertTrue(testMovie.getLength().equals(150));
	}
	
	@Test
	public void testGetCountry() {
		assertTrue(testMovie.getCountry().get(0).equals("USA"));
	}
	
	@Test
	public void testSetCountry() {
		ArrayList<String> countryList = new ArrayList<String>();
		countryList.add("Russia");
		testMovie.setCountry(countryList);
		assertTrue(testMovie.getCountry().get(0).equals("Russia"));
	}
	
	@Test
	public void testGetGenre() {
		assertTrue(testMovie.getGenre().get(0).equals("Action"));
		assertTrue(testMovie.getGenre().get(1).equals("Sci-Fi"));
	}
	
	@Test
	public void testSetGenre() {
		ArrayList<String> genreList = new ArrayList<String>();
		genreList.add("Romance");
		testMovie.setGenre(genreList);
		assertTrue(testMovie.getGenre().get(0).equals("Romance"));
	}
	
	@Test
	public void testGetLanguage() {
		assertTrue(testMovie.getLanguage().get(0).equals("English"));
	}
	
	@Test
	public void testSetLanguage() {
		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("Russian");
		testMovie.setLanguage(languageList);
		assertTrue(testMovie.getLanguage().get(0).equals("Russian"));
	}
}

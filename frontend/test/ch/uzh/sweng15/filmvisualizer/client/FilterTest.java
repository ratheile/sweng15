package ch.uzh.sweng15.filmvisualizer.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link Filter} class.
 * 
 * @author 	Joel H.
 */

public class FilterTest {

	Filter testFilter;

	/** 
	 * Create the a Filter object before each unit test
	 */
	@Before
	public void setUp() {
		testFilter = new Filter();
	}
	
	/** 
	 * Tests that instance variables are corrected initialized
	 */
	@Test
	public void testInstanceVariableInitializers() {
		assertTrue(testFilter.getTitle().equals(""));
		assertTrue(testFilter.getCountry().equals(""));
		assertTrue(testFilter.getGenre().equals(""));
		assertTrue(testFilter.getLanguage().equals(""));
		assertTrue(testFilter.getFromYear() == 0);
		assertTrue(testFilter.getToYear() == 0);
		assertTrue(testFilter.getFromLength() == 0);
		assertTrue(testFilter.getToLength() == 0);
	}
	
	/** 
	 * Tests that the filter is correctly reset to its default values
	 */
	@Test
	public void testResetFilter() {
		testFilter.setTitle("Casablanca");
		testFilter.setCountry("USA");
		testFilter.setGenre("Drama");
		testFilter.setLanguage("English");
		testFilter.setFromYear(1900);
		testFilter.setToYear(2000);
		testFilter.setFromLength(30);
		testFilter.setToLength(100);
		
		testFilter.resetFilter();
		
		assertTrue(testFilter.getTitle().equals(""));
		assertTrue(testFilter.getCountry().equals(""));
		assertTrue(testFilter.getGenre().equals(""));
		assertTrue(testFilter.getLanguage().equals(""));
		assertTrue(testFilter.getFromYear() == 0);
		assertTrue(testFilter.getToYear() == 0);
		assertTrue(testFilter.getFromLength() == 0);
		assertTrue(testFilter.getToLength() == 0);
	}
	
	/** 
	 * Test all getter and setter methods.
	 */
	@Test
	public void testGetTitle() {
		assertTrue(testFilter.getTitle().equals(""));
	}
	
	@Test
	public void testSetTitle() {
		testFilter.setTitle("The Matrix Reloaded");
		assertTrue(testFilter.getTitle().equals("The Matrix Reloaded"));
	}
	
	@Test
	public void testGetCountry() {
		assertTrue(testFilter.getCountry().equals(""));
	}
	
	@Test
	public void testSetCountry() {
		testFilter.setCountry("Russia");
		assertTrue(testFilter.getCountry().equals("Russia"));
	}
	
	@Test
	public void testGetGenre() {
		assertTrue(testFilter.getGenre().equals(""));
	}
	
	@Test
	public void testSetGenre() {
		testFilter.setGenre("Action");
		assertTrue(testFilter.getGenre().equals("Action"));
	}
	
	@Test
	public void testGetLanguage() {
		assertTrue(testFilter.getLanguage().equals(""));
	}
	
	@Test
	public void testSetLanguage() {
		testFilter.setLanguage("Russian");
		assertTrue(testFilter.getLanguage().equals("Russian"));
	}
	
	@Test
	public void testGetFromYear() {
		assertTrue(testFilter.getFromYear() == 0);
	}
	
	@Test
	public void testSetFromYear() {
		testFilter.setFromYear(2000);
		assertTrue(testFilter.getFromYear() == 2000);
	}
	
	@Test
	public void testGetToYear() {
		assertTrue(testFilter.getToYear() == 0);
	}
	
	@Test
	public void testSetToYear() {
		testFilter.setToYear(1900);
		assertTrue(testFilter.getToYear() == 1900);
	}
	
	@Test
	public void testGetFromLength() {
		assertTrue(testFilter.getFromLength() == 0);
	}
	
	@Test
	public void testSetFromLength() {
		testFilter.setFromLength(150);
		assertTrue(testFilter.getFromLength() == 150);
	}
	
	@Test
	public void testGetToLength() {
		assertTrue(testFilter.getToLength() == 0);
	}
	
	@Test
	public void testSetToLength() {
		testFilter.setToLength(100);
		assertTrue(testFilter.getToLength() == 100);
	}
}

package ch.uzh.sweng15.backend.pojo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MovieTest {


	/*
	 * W are testing if every atomic compare is working inside the testCompare function
	 */
	@Test
	public void testCompareTo(){
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> alteredList = new ArrayList<>();
		ArrayList<String> alteredList2 = new ArrayList<>();
		
		alteredList.addAll(Arrays.asList("test1"));
		
		alteredList2.addAll(Arrays.asList("test2"));
		list.addAll(Arrays.asList("test1", "test2", "test3"));
		
		Movie movie = new Movie("testmovie", 1922, 1000, list, list, list);
		
		//alter each column and test if change is detected
		Movie movie_alteredTitle = new Movie("t3stmovie", 1922, 1000, list, list, list);
		Movie movie_alteredYear = new Movie("testmovie", 1923, 1000, list, list, list);
		Movie movie_alteredLength = new Movie("testmovie", 1922, 1001, list, list, list);
		
		Movie movie_alteredCountry = new Movie("testmovie", 1922, 1000, alteredList, list, list);
		Movie movie_alteredGenre = new Movie("testmovie", 1922, 1000, list, alteredList,list);
		Movie movie_alteredLang = new Movie("testmovie", 1922, 1000, list, list, alteredList);
		
		Movie movie_alteredListAttrib = new Movie("testmovie", 1922, 1000, alteredList2, list, list);
		
		assertEquals(true, movie.compareTo(movie.getNewBSONRepresentation()));
		assertEquals(false, movie.compareTo(movie_alteredTitle.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredYear.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredLength.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredCountry.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredGenre.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredLang.getNewBSONRepresentation()));
		assertEquals(false,movie.compareTo(movie_alteredLang.getNewBSONRepresentation()));
		
		
		assertEquals(false,movie.compareTo(movie_alteredListAttrib.getNewBSONRepresentation()));
	}
	
	/*
	 * We are testing if the BSON representation is equal to the movie object
	 */
	@Test
	public void testGetNewBSONRepresentation() {
		ArrayList<String> list = new ArrayList<>();
		list.addAll(Arrays.asList("test1", "test2", "test3"));
		Movie movie = new Movie("testmovie", 1922, 1000, list, list,list);
		//this test depends on the correctness of the compare test
		boolean compare = movie.compareTo(movie.getNewBSONRepresentation());
		assertEquals(true,compare);
	}
	
	
	
	/*
	 * Test if the csv string generated from a movie is ok
	 */
	@Test
	public void testCSVStringArray(){
		ArrayList<String> genres = new ArrayList<>();
		ArrayList<String> languages = new ArrayList<>();
		ArrayList<String> countries = new ArrayList<>();
		
		genres.addAll(Arrays.asList("genrestest1", "genrestest2", "genrestest3"));
		languages.addAll(Arrays.asList("languagestest1", "languagestest2", "languagestest3"));
		countries.addAll(Arrays.asList("countriestest1", "countriestest2", "countriestest3"));
		
		Movie movie = new Movie("testmovie", 1922, 1000, genres, languages,countries);
		
		
		Document newBSONRepresentation = movie.getNewBSONRepresentation();
		
		List<String> stringlist = Movie.documentToCSV(newBSONRepresentation);
		
		assertEquals("[testmovie, 1000, 1922, countriestest1#countriestest2#countriestest3, "
				+ "languagestest1#languagestest2#languagestest3, genrestest1#genrestest2#genrestest3]", stringlist.toString());
		
		
		
	}

}

package ch.uzh.sweng15.filmvisualizer.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeSet;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ch.uzh.sweng15.filmvisualizer.client.Filter;
import ch.uzh.sweng15.filmvisualizer.client.Movie;
import ch.uzh.sweng15.filmvisualizer.client.MovieCollection;

/**
 * Unit tests for the {@link FilmLoaderImpl} class.
 * 
 * @author 	Joel H.
 */

public class FilmLoaderImplTest {
	FilmLoaderImpl testloader;
	
	/** 
	 * Create a film loader object before each unit test
	 */
	@Before
	public void setUp() {
		testloader = new FilmLoaderImpl();
	}

	/** 
	 * Test parsing a JSON string containing the information of a single movie
	 */
	@Test
	public void testParseMovieJSON() {
		String testString = "{ \"_id\" : { \"$oid\" : \"56508acde8939d31e6f7ee6f\" }, \"title\" : \"Whity\", \"language\" : [\"German\"], \"genre\" : [\"Western\", \"Drama\", \"World cinema\"], \"country\" : [\"Germany\"], \"length\" : 95, \"year\" : 1971 }";
		MovieCollection testCollection = new MovieCollection();
		
		testCollection = testloader.parseMovieJSON(testString);
		
		assertTrue(testCollection.getMovies().get(0).getTitle().equals("Whity"));
		assertTrue(testCollection.getMovies().get(0).getYear().equals(1971));
		assertTrue(testCollection.getMovies().get(0).getLength().equals(95));
		ArrayList<String> countryList = new ArrayList<String>();
		countryList.add("Germany");
		ArrayList<String> genreList = new ArrayList<String>();
		genreList.add("Western");
		genreList.add("Drama");
		genreList.add("World cinema");
		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("German");
		assertTrue(testCollection.getMovies().get(0).getCountry().equals(countryList));
		assertTrue(testCollection.getMovies().get(0).getGenre().equals(genreList));
		assertTrue(testCollection.getMovies().get(0).getLanguage().equals(languageList));
	}
	
	/** 
	 * Test parsing a JSON string containing the information of an array of movies
	 */
	@Test
	public void testParseMovieJSONArray() {
		String testArray = "[{ \"_id\" : { \"$oid\" : \"56508acde8939d31e6f7cca0\" }, \"title\" : \"Murphy's War\", \"language\" : [\"German\", \"English\"], \"genre\" : [\"Action\", \"Drama\", \"War film\"], \"country\" : [\"United Kingdom\"], \"length\" : 106, \"year\" : 1971 },{ \"_id\" : { \"$oid\" : \"56508acde8939d31e6f7e262\" }, \"title\" : \"Now and Forever\", \"language\" : [\"English\"], \"genre\" : [\"Thriller\", \"Drama\"], \"country\" : [\"United Kingdom\"], \"length\" : 90, \"year\" : 1956 }]";
		MovieCollection testCollection = new MovieCollection();
		
		testCollection = testloader.parseMovieJSONArray(testArray);
		
		assertTrue(testCollection.getMovies().get(0).getTitle().equals("Murphy's War"));
		assertTrue(testCollection.getMovies().get(0).getYear().equals(1971));
		assertTrue(testCollection.getMovies().get(0).getLength().equals(106));
		ArrayList<String> countryList = new ArrayList<String>();
		countryList.add("United Kingdom");
		ArrayList<String> genreList = new ArrayList<String>();
		genreList.add("Action");
		genreList.add("Drama");
		genreList.add("War film");
		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("German");
		languageList.add("English");
		assertTrue(testCollection.getMovies().get(0).getCountry().equals(countryList));
		assertTrue(testCollection.getMovies().get(0).getGenre().equals(genreList));
		assertTrue(testCollection.getMovies().get(0).getLanguage().equals(languageList));
		
		assertTrue(testCollection.getMovies().get(1).getTitle().equals("Now and Forever"));
		assertTrue(testCollection.getMovies().get(1).getYear().equals(1956));
		assertTrue(testCollection.getMovies().get(1).getLength().equals(90));
		countryList = new ArrayList<String>();
		countryList.add("United Kingdom");
		genreList = new ArrayList<String>();
		genreList.add("Thriller");
		genreList.add("Drama");
		languageList = new ArrayList<String>();
		languageList.add("English");
		assertTrue(testCollection.getMovies().get(1).getCountry().equals(countryList));
		assertTrue(testCollection.getMovies().get(1).getGenre().equals(genreList));
		assertTrue(testCollection.getMovies().get(1).getLanguage().equals(languageList));
	}
	
	/** 
	 * Test converting the filter into a JSONObject
	 */
	@Test
	public void testConvertFilterToJSON() {
		Filter filter = new Filter();
		filter.setCountry("Switzerland");
		filter.setFromYear(2000);
		JSONObject testObject = testloader.convertFilterToJSON(filter);
		assertTrue(testObject instanceof JSONObject);
		String testString = "{\"genre\":\"\",\"title\":\"\",\"toLength\":0,\"fromLength\":0,\"toYear\":0,\"language\":\"\",\"fromYear\":2000,\"country\":\"Switzerland\"}";
		assertTrue(testObject.toString().equals(testString));
	}
	
	/** 
	 * Test getListBoxItemNames() method, storing the names of all genres, countries, and languages
	 */
	@Test
	public void testGetListBoxItemNames() {
		ArrayList<String> fileLocations = new ArrayList<String>();
		fileLocations.add("/Users/Shared/FilmVisualizer/movies_80000.tsv");
		fileLocations.add("/Users/Shared/FilmVisualizer/movies_1471.tsv");
		ArrayList<TreeSet<String>> testNames = testloader.getListBoxItemNames(fileLocations);
		assertTrue(testNames instanceof ArrayList<?>);
		assertTrue(testNames.size() == 3);
		assertTrue(testNames.get(0) instanceof TreeSet<?>);
		assertTrue(testNames.get(1) instanceof TreeSet<?>);
		assertTrue(testNames.get(2) instanceof TreeSet<?>);
		assertTrue(testNames.get(0).contains("Afghanistan"));
		assertTrue(testNames.get(0).contains("Isle of Man"));
		assertTrue(testNames.get(0).contains("New Zealand"));
		assertTrue(testNames.get(0).contains("Zimbabwe"));
		assertTrue(testNames.get(1).contains("Absurdism"));
		assertTrue(testNames.get(1).contains("Heavenly Comedy"));
		assertTrue(testNames.get(1).contains("Silhouette animation"));
		assertTrue(testNames.get(1).contains("Zombie Film"));
		assertTrue(testNames.get(2).contains("Aboriginal Malay"));
		assertTrue(testNames.get(2).contains("French Sign"));
		assertTrue(testNames.get(2).contains("Latin"));
		assertTrue(testNames.get(2).contains("Zulu"));
	}
	
	/** 
	 * Test getExportCSVLink() method, sending an non-empty filter to the remote server and receiving back a link to a CSV File.
	 */
	@Test
	public void testgetExportCSVLink() {
		Filter filter = new Filter();
		filter.setCountry("Zambia");
		String exportCSVLink = testloader.getExportCSVLink(filter);
		assertTrue(exportCSVLink.startsWith("http"));
		assertTrue(exportCSVLink.endsWith(".txt"));
	}

	
//	/** 
//	 * Test getRemoteMovieCollection() method, which tests the interaction between the front- and backend,
//	 * sending an empty filter to the remote server and receiving back the whole, or a part of the (depending on server settings), database
//	 */
//	@Test
//	public void testGetRemoteMovieCollection() {
//		Filter filter = new Filter();
//		MovieCollection testCollection = testloader.getRemoteMovieCollection(filter);
//		assertTrue(testCollection.getMovieCollectionSize()>0);
//	}
//	
//	/** 
//	 * Test getRemoteMovieCollection() method, which tests the interaction between the front- and backend,
//	 * sending a title filter to the remote server and receiving back the correct movie
//	 */
//	@Test
//	public void testTitleFilter() {
//		Filter filter = new Filter();
//		filter.setTitle("Pontypool");
//		MovieCollection testCollection = testloader.getRemoteMovieCollection(filter);
//		assertTrue(testCollection.getMovieCollectionSize()>0);
//		
//		assertTrue(testCollection.getMovies().get(0).getTitle().equals("Pontypool"));
//		assertTrue(testCollection.getMovies().get(0).getYear().equals(2008));
//		assertTrue(testCollection.getMovies().get(0).getLength().equals(96));
//		ArrayList<String> countryList = new ArrayList<String>();
//		countryList.add("Canada");
//		ArrayList<String> genreList = new ArrayList<String>();
//		genreList.add("Zombie Film");
//		genreList.add("Drama");
//		genreList.add("Horror");
//		genreList.add("Psychological thriller");
//		genreList.add("Film adaptation");
//		ArrayList<String> languageList = new ArrayList<String>();
//		languageList.add("English");
//		assertTrue(testCollection.getMovies().get(0).getCountry().equals(countryList));
//		assertTrue(testCollection.getMovies().get(0).getGenre().equals(genreList));
//		assertTrue(testCollection.getMovies().get(0).getLanguage().equals(languageList));
//	}
//	
//	/** 
//	 * Test getRemoteMovieCollection() method, which tests the interaction between the front- and backend,
//	 * sending a country filter to the remote server and receiving back the correct set of movies
//	 */
//	@Test
//	public void TestCountryFilter() {
//		Filter filter = new Filter();
//		filter.setCountry("Zambia");
//		MovieCollection testCollection = testloader.getRemoteMovieCollection(filter);
//		assertTrue(testCollection.getMovieCollectionSize()==3);
//		
//		ArrayList<String> titles = new ArrayList<String>();
//		titles.add("Slipstream");
//		titles.add("The Grass Is Singing");
//		titles.add("Mwansa the Great");
//		
//		for (Movie m : testCollection.getMovies()) {
//			assertTrue(titles.contains(m.getTitle()));
//		}
//	}
//	
//	/** 
//	 * Test getRemoteMovieCollection() method, which tests the interaction between the front- and backend,
//	 * sending a multitude of filters to the remote server and receiving back the correct movie
//	 */
//	@Test
//	public void TestGenreFilters() {
//		Filter filter = new Filter();
//		filter.setCountry("United States of America");
//		filter.setGenre("Drama");
//		filter.setLanguage("Russian");
//		filter.setFromYear(2010);
//		filter.setToLength(100);
//		MovieCollection testCollection = testloader.getRemoteMovieCollection(filter);
//		
//		assertTrue(testCollection.getMovies().get(0).getTitle().equals("Bobby Fischer Against The World"));
//	}
	
}

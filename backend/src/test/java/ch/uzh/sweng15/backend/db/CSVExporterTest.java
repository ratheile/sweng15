package ch.uzh.sweng15.backend.db;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.uzh.sweng15.backend.pojo.Filter;
import ch.uzh.sweng15.backend.pojo.Movie;

public class CSVExporterTest {
	
	
	private static DatabaseAdapter adapter;
	private static CSVExporter exporter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		adapter = new DatabaseAdapter("localhost", 27017, "movies");
		exporter = new CSVExporter(adapter);
		fillDBWithMovies();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		adapter.deleteDatabase();
	}

	
	
	/*
	 * test if we get a result from a csv key
	 */
	@Test
	public void testGetFromKey() {
		Filter emptyFilter = new Filter();
		String id = exporter.generateCSV(emptyFilter);
		String csv = exporter.getCSV(id);
		assertNotEquals("", csv);
		System.out.println(csv);
	}
	
	
	
	/*
	 * test what happens if the maximum ammount of csvs is generated
	 * it should then delete the first one
	 */
	@Test
	public void testMaxDeleted(){
		Filter emptyFilter = new Filter();
		String firstID = exporter.generateCSV(emptyFilter);
		String csv = exporter.getCSV(firstID);
		assertNotEquals("", csv);
		
		for(int i = 0; i < CSVExporter.MAX_CSV_STORED+1; i++){
			exporter.generateCSV(emptyFilter);
		}
		csv = exporter.getCSV(firstID);
		assertEquals("",csv);
	}
	
	
	private static int fillDBWithMovies() {
		ArrayList<String> country = new ArrayList<>();
		country.add("Switzerland");
		ArrayList<String> genres = new ArrayList<>();
		genres.add("Action");
		ArrayList<String> languages = new ArrayList<>();
		languages.add("German");
		Movie movie = new Movie("Testmovie", 1922, 100, country, genres, languages);
		Movie movie2 = new Movie("Testmovie2", 1950, 150, country, genres, languages);

		adapter.insertMovie(movie);
		adapter.insertMovie(movie2);
		
		return 2; //return n movies in this db
	}

}

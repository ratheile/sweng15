package ch.uzh.sweng15.backend.db;

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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		adapter.deleteDatabase();
	}


	@Test
	public void test() {
		Filter emptyFilter = new Filter();
		String id = exporter.generateCSV(emptyFilter);
		System.out.println("ID is: "+ id);
		String csv = exporter.getCSV(id);
		System.out.println(csv);
		
	}
	
	
	private int fillDBWithMovies() {
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

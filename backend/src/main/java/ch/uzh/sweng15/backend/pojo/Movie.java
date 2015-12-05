package ch.uzh.sweng15.backend.pojo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

/**
 * Simple wrapper class for a movie
 * Allows to convert the movie into its different representations
 * (JSON, BSON) 
 * @author Raffael Theiler
 *
 */
public class Movie {
	
	private static final String BSON_ID_YEAR = "year";
	private static final String BSON_ID_LENGTH = "length";
	private static final String BSON_ID_COUNTRY = "country";
	private static final String BSON_ID_GENRE = "genre";
	private static final String BSON_ID_LANGUAGE = "language";
	private static final String BSON_ID_TITLE = "title";
	
	
	
	private String title;
	private Integer year;
	private Integer length;
	private ArrayList<String> country;
	private ArrayList<String> genre;
	private ArrayList<String> language;

	/**
	 * Default constructor
	 */
	public Movie() {
	}
	
	/** 
	 * Constructor with parameters
	 * @param title The title of the movie
	 * @param year  The year of the movie
	 * @param length The length of the movie (in minutes)
	 * @param country The production countries of the movie
	 * @param genre The genres of the movie
	 * @param language The languages of the movie
	 */
	public Movie(String title, Integer year, Integer length, 
			ArrayList<String> country, 
			ArrayList<String> genre, 
			ArrayList<String> language) {
		this.title = title;
		this.year = year;
		this.length = length;
		this.country = country;
		this.genre = genre;
		this.language = language;
	}
	
	/** 
	 * Returns a string containing all the information of a movie.
	 * It is used to display the information of a single film in a GeoChart.
	 * @return String containing all the information of a movie
	 */
	public String printString() {
		String tmp = "Title: " + this.getTitle() + ", Year: " + this.getYear() + ", Length: " + 
				this.getLength() + " min, Country: " + this.getCountry().toString() + ", Genre: " + 
				this.getGenre().toString() + ", Language: " + this.getLanguage().toString();
		return tmp;
	}
	
	/** 
	 * Gets the title of the movie
	 * @return String title of the movie
	 */
	public String getTitle() {
		return title;
	}

	/** 
	 * Sets the title of the movie
	 * @param title New title of the movie.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 
	 * Gets the year of the movie
	 * @return int Year of the movie
	 */
	public Integer getYear() {
		return year;
	}

	/** 
	 * Sets the year of the movie
	 * @param year New year of the movie.
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/** 
	 * Gets the length of the movie
	 * @return int Length of the movie
	 */
	public Integer getLength() {
		return length;
	}

	/** 
	 * Sets the length of the movie
	 * @param length New length of the movie
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/** 
	 * Gets an ArrayList with all the genres of the movie
	 * @return ArrayList<String> New genres of the movie 
	 */
	public ArrayList<String> getGenre() {
		return genre;
	}

	/** 
	 * Sets the genres of the movie
	 * @param genre ArrayList of genres of the movie
	 */
	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}

	/** 
	 * Gets the country or countries, where the movie was produced
	 * @return ArrayList<String> Country or countries of movie production 
	 */
	public ArrayList<String> getCountry() {
		return country;
	}

	/** 
	 * Sets the country or countries of movie production
	 * @param country New country or countries of movie production
	 */
	public void setCountry(ArrayList<String> country) {
		this.country = country;
	}

	/** 
	 * Gets the language or languages of the movie
	 * @return ArrayList<String> Language or languages of the movie
	 */
	public ArrayList<String> getLanguage() {
		return language;
	}

	/** 
	 * Sets the language or languages of the movie
	 * @param language New value of the movie's language or languages
	 */
	public void setLanguage(ArrayList<String> language) {
		this.language = language;
	}	
	
	
	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + 
				", length=" + length + ", country=" + country + ", genre="
				+ genre + ", language=" + language + "]";
	}

	/**
	 * Returns a BSON document representation of this movie
	 * @return the BSON document, careful, returns a new instance everytimes!
	 */
	public Document getNewBSONRepresentation(){
		Document document = new Document(BSON_ID_TITLE, this.getTitle())
	               .append(BSON_ID_LANGUAGE, this.getLanguage())
	               .append(BSON_ID_GENRE, this.getGenre())
	               .append(BSON_ID_COUNTRY, this.getCountry())
	               .append(BSON_ID_LENGTH, this.getLength())
	               .append(BSON_ID_YEAR, this.getYear());
		return document;
	}
	
	/**
	 * Compares this movie to a BSON representation
	 * @param bson the BSON representation to be compared to
	 * @return status if the BSON representation is representing this movie
	 */
	@SuppressWarnings("unchecked")
	public boolean compareTo(Document bson){
		
		List<String> bsonLanguages = (List<String>) bson.get(BSON_ID_LANGUAGE, List.class);
		List<String> bsonGenres = (List<String>)bson.get(BSON_ID_GENRE, List.class);
		List<String> bsonCountries = (List<String>) bson.get(BSON_ID_COUNTRY, List.class);
		
		if(bsonLanguages.size() != this.language.size())return false;
		if(bsonGenres.size() != this.genre.size())return false;
		if(bsonCountries.size() != this.country.size())return false;
		
		for(String s : bsonLanguages){
			if(!this.language.contains(s)) return false;
		}
		
		for(String s : bsonGenres){
			if(!this.genre.contains(s)) return false;
		}
		
		for(String s : bsonCountries){
			if(!this.country.contains(s)) return false;
		}
		
		if(!bson.getString(BSON_ID_TITLE).equals(this.getTitle()))return false;	
		if(bson.getInteger(BSON_ID_LENGTH).intValue() != this.getLength())return false;
		if(bson.getInteger(BSON_ID_YEAR).intValue() != this.getYear())return false;
		return true;
	}
	
	
	/**
	 * Generates a CSV String list from the movie BSON Document without an instance of Movie.
	 * @param document the BSON document
	 * @return ordered List of elements to be in the CSV
	 */
	public static List<String> documentToCSV(Document bson){
		
		String innerSeparator = "#";
		
		List<String> csvStrings = new ArrayList<>();
		
		List<String> bsonLanguages = (List<String>) bson.get(BSON_ID_LANGUAGE, List.class);
		List<String> bsonGenres = (List<String>)bson.get(BSON_ID_GENRE, List.class);
		List<String> bsonCountries = (List<String>) bson.get(BSON_ID_COUNTRY, List.class);
		
		String title = bson.getString(BSON_ID_TITLE);
		Integer length = bson.getInteger(BSON_ID_LENGTH);
		Integer year = bson.getInteger(BSON_ID_YEAR);
		
		csvStrings.add(title);
		csvStrings.add(String.valueOf(length));
		csvStrings.add(String.valueOf(year));
		
		//Nice Java8 method, does not work on server :(
		//csvStrings.add(bsonLanguages.stream().collect(Collectors.joining(innerSeparator)));
		//csvStrings.add(bsonGenres.stream().collect(Collectors.joining(innerSeparator)));
		//csvStrings.add(bsonCountries.stream().collect(Collectors.joining(innerSeparator)));
		
		
		//fallback to java 7
		csvStrings.add(arrayJoinToString(innerSeparator, bsonLanguages));
		csvStrings.add(arrayJoinToString(innerSeparator, bsonGenres));
		csvStrings.add(arrayJoinToString(innerSeparator, bsonCountries));
		
		return csvStrings;
	}

	private static String arrayJoinToString(String separator, List<String> list) {
		StringBuilder string = new StringBuilder();
		for(String s : list){
			string.append(s);
			string.append(separator);
		}
		//remove last inner separator or return "" when empty
		return ((string.length()>0)?string.substring(0, string.length() - 1).toString():"");
	}
	
	
}
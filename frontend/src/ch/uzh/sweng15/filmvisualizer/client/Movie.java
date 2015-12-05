package ch.uzh.sweng15.filmvisualizer.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the information of an individual film.
 * 
 * @author 	Joel H.
 */

public class Movie implements Serializable {
	
	private static final long serialVersionUID = -674707847237827519L;
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
	public Movie(String title, Integer year, Integer length, ArrayList<String> country, ArrayList<String> genre, ArrayList<String> language) {
		this.title = title;
		this.year = year;
		this.length = length;
		this.country = country;
		this.genre = genre;
		this.language = language;
	}
	
	/** 
	 * Returns a string containing only the relevant information of a movie for better, i.e., shorter presentation in the world map visualization.
	 * It is used to display the information of a single film in a GeoChart.
	 * @return String containing the relevant information of a movie
	 */
	public String getRelevantMovieInfo() {
		// Limit the number of displayed genres
		List<String> genreList = (List<String>)this.getGenre().clone();
		if (genreList.size() > 2) {
			genreList = genreList.subList(0, 2);
			genreList.add("...");
		}
		
		// Limit the number of displayed languages
		List<String> languageList = (List<String>)this.getLanguage().clone();
		if (languageList.size() > 2) {
			languageList = languageList.subList(0, 2);
			languageList.add("...");
		}
		
		// Further, to shorten string, there is no need to display country names as the countries are already visible on the map
		String returnString = "Title: " + this.getTitle() + ", Year: " + this.getYear() + ", Length: " + 
				this.getLength() + " min" + ", Genre: " + genreList + ", Language: " + languageList;
		return returnString;
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
}

package ch.uzh.sweng15.filmvisualizer.client;

import java.io.Serializable;

/**
 * This class stores the filter criteria chosen by the user
 * 
 * @author  Joel H.
 */

public class Filter implements Serializable{

	private static final long serialVersionUID = -7314163964927753715L;
	private String title = "";
	private String country = "";
	private String genre = "";
	private String language = "";
	private int fromYear = 0;
	private int toYear = 0;
	private int fromLength = 0;
	private int toLength = 0;
	
	/**
	 * Default constructor
	 */
	public Filter() {
	}
	
	/** 
	 * Resets the filter criteria
	 */
	public void resetFilter() {
		title = "";
		country = "";
		genre = "";
		language = "";
		fromYear = 0;
		toYear = 0;
		fromLength = 0;
		toLength = 0;
	}
	
	/** 
	 * Gets filter title
	 * @return String Filter title
	 */
	public String getTitle() {
		return title;
	}

	/** 
	 * Sets filter title
	 * @param title New filter title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 
	 * Gets the filter country
	 * @return String Filter country 
	 */
	public String getCountry() {
		return country;
	}

	/** 
	 * Sets filter country
	 * @param country New filter country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/** 
	 * Gets the filter genre
	 * @return String Filter genre 
	 */
	public String getGenre() {
		return genre;
	}

	/** 
	 * Sets filter genre
	 * @param genre New filter genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/** 
	 * Gets the filter language
	 * @return String Filter language 
	 */
	public String getLanguage() {
		return language;
	}

	/** 
	 * Sets filter language
	 * @param language New filter language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/** 
	 * Gets the lower bound of the filter year range
	 * @return String Lower bound of the filter year range 
	 */
	public int getFromYear() {
		return fromYear;
	}

	/** 
	 * Sets the lower bound of the filter year range
	 * @param fromYear New lower bound of the filter year range 
	 */
	public void setFromYear(int fromYear) {
		this.fromYear = fromYear;
	}

	/** 
	 * Gets the upper bound of the filter year range
	 * @return String Upper bound of the filter year range 
	 */
	public int getToYear() {
		return toYear;
	}

	/** 
	 * Sets the upper bound of the filter year range
	 * @param toYear New upper bound of the filter year range 
	 */
	public void setToYear(int toYear) {
		this.toYear = toYear;
	}
	
	/** 
	 * Gets the lower bound of the filter length range
	 * @return String Lower bound of the filter length range 
	 */
	public int getFromLength() {
		return fromLength;
	}

	/** 
	 * Sets the lower bound of the filter length range
	 * @param fromLength New lower bound of the filter length range 
	 */
	public void setFromLength(int fromLength) {
		this.fromLength = fromLength;
	}

	/** 
	 * Gets the upper bound of the filter length range
	 * @return String Upper bound of the filter length range 
	 */
	public int getToLength() {
		return toLength;
	}
	
	/** 
	 * Sets the upper bound of the filter length range
	 * @param toLength New upper bound of the filter length range 
	 */
	public void setToLength(int toLength) {
		this.toLength = toLength;
	}
}

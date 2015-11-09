package ch.uzh.sweng15.backend.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import org.bson.Document;

/**
 * Simple wrapper class for a movie
 * Allows to convert the movie into its different representations
 * (JSON, BSON) 
 * @author Raffael Theiler
 *
 */
public class Movie implements Serializable {
	
	private static final long serialVersionUID = -5171017094923715593L;
	private String title;
	private Integer year;
	private Integer length;
	private String country;
	private ArrayList<String> genre;
	private String language;
	
	public Movie() {
	}
	
	public Movie(String title, Integer year, Integer length, String country, ArrayList<String> genre, String language) {
		this.title = title;
		this.year = year;
		this.length = length;
		this.country = country;
		this.genre = genre;
		this.language = language;
	}
	
	// This string is used to display information of a single film in a GeoChart
	public String getDisplayString() {
		String tmp = "Title: " + this.getTitle() + ", Year: " + this.getYear() + ", Length: " + 
				this.getLength() + " min, Country: " + this.getCountry() + ", Genre: " + 
				this.getGenre() + ", Language: " + this.getLanguage();
		return tmp;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ArrayList<String> getGenre() {
		return genre;
	}

	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", length=" + length + ", country=" + country + ", genre="
				+ genre + ", language=" + language + "]";
	}
	
	
	public Document getNewBSONRepresentation(){
		Document document = new Document("title", this.getTitle())
	               .append("language", this.getLanguage())
	               .append("displayString", this.getDisplayString())
	               .append("genre", this.getGenre())
	               .append("country", this.getCountry())
	               .append("length", this.getLength())
	               .append("year", this.getYear());
		return document;
	}
	
	
	public boolean compareTo(Document bson){
		if(!bson.getString("title").equals(this.getTitle()))return false;	
		if(!bson.getString("language").equals(this.getLanguage()))return false;	
		if(!bson.getString("displayString").equals(this.getDisplayString()))return false;	
		if(!bson.getString("country").equals(this.getCountry()))return false;	
		if(bson.getInteger("length").intValue() != this.getLength())return false;
		if(bson.getInteger("year").intValue() != this.getYear())return false;
		return true;
	}
	
	
	
	
}
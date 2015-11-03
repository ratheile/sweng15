package ch.uzh.sweng15.client;

import java.util.ArrayList;

public class Movie {
	
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
	
}

package ch.uzh.sweng15.client;

import java.util.ArrayList;

public class MovieCollection {
	private ArrayList<Movie> movies;
	
	public MovieCollection() {
		movies = new ArrayList<Movie>();
		populate();
	}
	
	public void addMovie(Movie passedMovie) {
		movies.add(passedMovie);
	}
	
	public void removeMovie(Movie passedMovie) {
		movies.remove(passedMovie);
	}
	
	public ArrayList<Movie> getMovieArrayList() {
		return movies;
	}
	
	public int getMovieCollectionSize() {
		return movies.size();
	}
	
	
	private void populate() {
		ArrayList<String> listDrama = new ArrayList<String>();
		listDrama.add("Drama");
		ArrayList<String> listAction = new ArrayList<String>();
		listAction.add("Action");
		ArrayList<String> listSciFi = new ArrayList<String>();
		listSciFi.add("Sci Fi");
		ArrayList<String> listThriller = new ArrayList<String>();
		listThriller.add("Thriller");
		ArrayList<String> listHorror = new ArrayList<String>();
		listHorror.add("Horror");
		ArrayList<String> listDocumentary = new ArrayList<String>();
		listDocumentary.add("Documentary");
		ArrayList<String> listComedy = new ArrayList<String>();
		listComedy.add("Comedy");
		
		Movie m1 = new Movie();
		m1.setTitle("Casablanca");
		m1.setYear(1942);
		m1.setLength(102);
		m1.setCountry("USA");
		m1.setGenre(listDrama);
		m1.setLanguage("English");
		
		Movie m2 = new Movie("The Matrix", 1999, 136, "USA", listAction, "English");
		Movie m3 = new Movie("Wild Strawberries", 1957, 91, "Sweden", listDrama, "Swedish");
		Movie m4 = new Movie("2001: A Space Odyssey", 1968, 160, "USA", listSciFi, "English");
		Movie m5 = new Movie("The Silence of the Lambs", 1991, 118, "USA", listThriller, "English");
		Movie m6 = new Movie("Chungking Express", 1994, 102, "China", listDrama, "Cantonese");
		Movie m7 = new Movie("Bicycle Thieves", 1948, 93, "Italy", listDrama, "Italian");
		Movie m8 = new Movie("In the Mood for Love", 2000, 98, "China", listDrama, "Cantonese");
		Movie m9 = new Movie("Hour of the Wolf", 1968, 90, "Sweden", listHorror, "Swedish");
		Movie m10 = new Movie("The Great Beauty", 2013, 141, "Italy", listDrama, "Italian");
		Movie m11 = new Movie("The Seventh Seal", 1957, 96, "Sweden", listDrama, "Swedish");
		Movie m12 = new Movie("Fargo", 1996, 98, "USA", listDrama, "English");
		Movie m13 = new Movie("Scream", 1996, 111, "USA", listHorror, "English");
		Movie m14 = new Movie("The Remains of the Day", 1993, 134, "Great Britain", listDrama, "English");
		Movie m15 = new Movie("La Dolce Vita", 1960, 174, "Italy", listDrama, "Italian");
		Movie m16 = new Movie("Lessons of Darkness", 1992, 54, "Great Britain", listDocumentary, "English");
		Movie m17 = new Movie("Metropolis", 1927, 153, "Germany", listDrama, "German");
		Movie m18 = new Movie("Onibaba", 1964, 103, "Japan", listHorror, "Japanese");
		Movie m19 = new Movie("3 Idiots", 2009, 170, "India", listComedy, "Hindi");
		Movie m20 = new Movie("Let the Right One In", 2008, 115, "Sweden", listHorror, "Swedish");
		Movie m21 = new Movie("Pan's Labyrinth", 2006, 118, "Spain", listDrama, "Spanish");
		Movie m22 = new Movie("Kung Fu Hustle", 2004, 99, "China", listAction, "Cantonese");
		Movie m23 = new Movie("Grey Gardens", 1975, 94, "USA", listDocumentary, "English");
		Movie m24 = new Movie("Suzhou River", 2000, 83, "China", listDrama, "Mandarin");
		Movie m25 = new Movie("High Tension", 2003, 91, "France", listHorror, "French");
		
		addMovie(m2);
		addMovie(m1);
		addMovie(m3);
		addMovie(m4);
		addMovie(m5);
		addMovie(m6);
		addMovie(m7);
		addMovie(m8);
		addMovie(m9);
		addMovie(m10);
		addMovie(m11);
		addMovie(m12);
		addMovie(m13);
		addMovie(m14);
		addMovie(m15);
		addMovie(m16);
		addMovie(m17);
		addMovie(m18);
		addMovie(m19);
		addMovie(m20);
		addMovie(m21);
		addMovie(m22);
		addMovie(m23);
		addMovie(m24);
		addMovie(m25);
	}
	
}

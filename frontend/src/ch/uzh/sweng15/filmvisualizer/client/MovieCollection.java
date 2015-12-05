package ch.uzh.sweng15.filmvisualizer.client;

import java.io.Serializable;
import java.util.ArrayList;

/** 
 * This class stores a collection of {@link Movie} objects
 * 
 * @author 	Joel H.
 */

public class MovieCollection implements Serializable {
	
	private static final long serialVersionUID = 8570687569992090083L;
	private ArrayList<Movie> movies;

	/**
	 * Constructs a new MovieCollection object that populates itself
	 */
	public MovieCollection() {
		movies = new ArrayList<Movie>();
	}

	/**
	 * Adds a new Movie object to the movie collection. 
	 * @param passedMovie The movie to be added to the movie collection
	 */
	public void addMovie(Movie passedMovie) {
		movies.add(passedMovie);
	}

	/**
	 * Removes the passed Movie object from the movie collection 
	 * @param passedMovie The movie to be removed from the movie collection
	 */
	public void removeMovie(Movie passedMovie) {
		movies.remove(passedMovie);
	}

	/**
	 * Returns the underlying ArrayList containing all Movie object within the collection
	 * @return ArrayList<Movie> The ArrayList containing all Movie objects
	 */
	public ArrayList<Movie> getMovies() {
		return movies;
	}

	/**
	 * Sets the ArrayList containing all Movie object within the collection
	 * @param passedMovies The new ArrayList containing Movie objects
	 */
	public void setMovies(ArrayList<Movie> passedMovies) {
		this.movies = passedMovies;
	}

	/**
	 * Returns the number of movies contained in the collection
	 * @return int The size of the MovieCollection
	 */
	public int getMovieCollectionSize() {
		return movies.size();
	}
}

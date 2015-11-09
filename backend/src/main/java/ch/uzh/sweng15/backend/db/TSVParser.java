package ch.uzh.sweng15.backend.db;

import java.util.ArrayList;
import java.util.Scanner;

import ch.uzh.sweng15.backend.pojo.Movie;

/**
 * This class does parse lines from a tsv movie file. The goal
 * is to efficiently convert those lines into new {@link Movie} elements.
 * @author Raffael Theiler
 *
 */
public class TSVParser {

	/**
	 * Parser function to parse a string line into a movie object.
	 * @param strLine One line of the csv file, (use {@link Scanner} or similar classes)
	 * @return a new {@link Movie} instance containing the data in the line string
	 */
	public static Movie parseMovieLine(String strLine) {
			
			/*
			 * File information:
			 * The file is "row"-separated with '\t'
			 * We split it into rows and handle each row sequentially
			 * 
			 * The current rows are:
			 * -Title
			 * -Year
			 * -Length
			 * -Language
			 * -Country
			 * -Genre
			 */
		
			//TODO: Remove default values
			String[] row = strLine.split("\t", -1);

			int tmpIndex;
			
		
			String title = row[2];
			if (title.equals("")) {
				title = "No Title";
			}

			String year = row[3];
			if (year.equals("")) {
				year = "0";
			} else {
				String[] tmpYear = year.split("-");
				year = tmpYear[0];
			}

			String length = row[5];
			if (length.equals("")) {
				length = "0";
			} else {
				length = length.substring(0, length.indexOf("."));
			}
			
			String language = row[6];
			// Either no information or empty braces
			if (language.equals("{}") || language.equals("")) {
				language = "No Language";
			} else {
				// Skip past meta tag
				tmpIndex = language.indexOf(":");
				language = language.substring(tmpIndex + 3);
				tmpIndex = language.indexOf("\"");
				language = language.substring(0, tmpIndex);
			}

			String country = row[7];
			if (country.equals("{}") || country.equals("")) {
				country = "No Country";
			} else {
				tmpIndex = country.indexOf(":");
				country = country.substring(tmpIndex + 3);
				tmpIndex = country.indexOf("\"");
				country = country.substring(0, tmpIndex);
			}

			//TODO: parse all genres, currently only the first is added to the database
			ArrayList<String> tmpList = new ArrayList<String>();
			String genre = row[8];
			if (genre.equals("{}") || genre.equals("")) {
				genre = "No Genre";
			} else {
				tmpIndex = genre.indexOf(":");
				genre = genre.substring(tmpIndex + 3);
				tmpIndex = genre.indexOf("\"");
				genre = genre.substring(0, tmpIndex);
			}
			tmpList.add(genre);

			Movie tmpMovie = new Movie(title, Integer.parseInt(year),
					Integer.parseInt(length), country, tmpList,
					language);
			
		return tmpMovie;
	}
}
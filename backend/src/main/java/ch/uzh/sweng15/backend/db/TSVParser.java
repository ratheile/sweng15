package ch.uzh.sweng15.backend.db;

import java.io.IOException;
import java.util.ArrayList;

import ch.uzh.sweng15.backend.temp.Movie;

public class TSVParser {

	public static Movie parseMovieLine(String strLine) throws IOException {

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
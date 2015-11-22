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
        
        String title = row[2];
        if (title.equals("")) {
            title = "No Value";
        }
        
        
        String year = row[3];
        if (year.equals("")) {
            year = "-1";
        } else {
            String[] tmpYear = year.split("-");
            year = tmpYear[0];
        }
        
        
        String length = row[5];
        if (length.equals("")) {
            length = "-1";
        } else {
            length = length.substring(0, length.indexOf("."));
        }
        
        int tmpIndex;
        
        ArrayList<String> languageList = new ArrayList<String>();
        String language = row[6];
        if (language.equals("{}") || language.equals("")) {
            language = "No Value";
        } else {
            while (language.indexOf(":") != -1) {
                tmpIndex = language.indexOf(":");
                language = language.substring(tmpIndex + 3);
                tmpIndex = language.indexOf("\"");
                String languageToAdd = language.substring(0, tmpIndex);
                int languageLoc = languageToAdd.indexOf(" Language");
                if (languageLoc != -1) {
                    languageToAdd = languageToAdd.substring(0, languageLoc);
                }
                languageList.add(languageToAdd);
                language = language.substring(tmpIndex);
            }
        }
        
        ArrayList<String> countryList = new ArrayList<String>();
        String country = row[7];
        if (country.equals("{}") || country.equals("")) {
            country = "No Value";
        } else {
            while (country.indexOf(":") != -1) {
                tmpIndex = country.indexOf(":");
                country = country.substring(tmpIndex + 3);
                tmpIndex = country.indexOf("\"");
                String countryToAdd = country.substring(0, tmpIndex);
                countryList.add(countryToAdd);
                country = country.substring(tmpIndex);
            }
        }
        
        ArrayList<String> genreList = new ArrayList<String>();
        String genre = row[8];
        if (genre.equals("{}") || genre.equals("")) {
            genre = "No Value";
        } else {
            while (genre.indexOf(":") != -1) {
                tmpIndex = genre.indexOf(":");
                genre = genre.substring(tmpIndex + 3);
                tmpIndex = genre.indexOf("\"");
                String genreToAdd = genre.substring(0, tmpIndex);
                genreList.add(genreToAdd);
                genre = genre.substring(tmpIndex);
            }
        }		
        
        Movie tmpMovie = new Movie(title, Integer.parseInt(year), Integer.parseInt(length), countryList, genreList, languageList);
			
		return tmpMovie;
	}
}
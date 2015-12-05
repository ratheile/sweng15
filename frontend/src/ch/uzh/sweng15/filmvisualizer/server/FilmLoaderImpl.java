package ch.uzh.sweng15.filmvisualizer.server;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.uzh.sweng15.filmvisualizer.client.FilmLoader;
import ch.uzh.sweng15.filmvisualizer.client.Filter;
import ch.uzh.sweng15.filmvisualizer.client.Movie;
import ch.uzh.sweng15.filmvisualizer.client.MovieCollection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This class contains all RPC services available to the client. This includes retrieving movie data from a remote server,
 * getting the names of countries, genres and languages to populate ListBoxes and 
 * retrieving the link location of a CSV file containing a movie selection.
 * 
 * @author 	Joel H.
 */

public class FilmLoaderImpl extends RemoteServiceServlet implements FilmLoader {

	private static final long serialVersionUID = 955456895529707255L;

	/** 
	 * Retrieves movie data from a remote server for the client and passes it back to the client as a {@link MovieCollection}.
	 * The movies contained in the movie collection correspond to the selected filter criteria.
	 * @param filter Filter containing the selected filter criteria
	 * @return MovieCollection A movie collection containing a filtered selection of movies
	 */
	public MovieCollection getRemoteMovieCollection(Filter filter) {
		String inputLine = "";

		JSONObject JSONRequest = convertFilterToJSON(filter);

		try {
			URL url = new URL("http://backend-sweng15.rhcloud.com/backend/api/movie/filtered");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);

			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(JSONRequest.toString());
			writer.close();

			// The "UTF-8" charset argument is critical for the correct character encoding in our web application
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			inputLine = in.readLine();
			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace(System.out);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}

		MovieCollection returnCollection = parseMovieJSONArray(inputLine);
		return returnCollection;

		// For local data retrieval comment the above and uncomment what follows

		//		MovieCollection returnCollection = new MovieCollection();
		//
		//		// Open the file
		//		FileInputStream fstream = null;
		//		try {
		//			fstream = new FileInputStream("movies_80000.tsv");
		//		} catch (FileNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		//
		//		String strLine = null;
		//
		//		//Read File Line By Line
		//		//while ((strLine = br.readLine()) != null)   {
		//		for (int i=0; i<1000; i++) {
		//			try {
		//				strLine = br.readLine();
		//			} catch (IOException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//
		//			String[] row = strLine.split("\t", -1);
		//
		//			String title = row[2];
		//			if (title.equals("")) {
		//				title = "No Value";
		//			}
		//
		//
		//			String year = row[3];
		//			if (year.equals("")) {
		//				year = "-1";
		//			} else {
		//				String[] tmpYear = year.split("-");
		//				year = tmpYear[0];
		//			}
		//
		//
		//			String length = row[5];
		//			if (length.equals("")) {
		//				length = "-1";
		//			} else {
		//				length = length.substring(0, length.indexOf("."));
		//			}
		//
		//			int tmpIndex;
		//
		//			ArrayList<String> languageList = new ArrayList<String>();
		//			String language = row[6];
		//			if (language.equals("{}") || language.equals("")) {
		//				language = "No Value";
		//			} else {
		//				while (language.indexOf(":") != -1) {
		//					tmpIndex = language.indexOf(":");
		//					language = language.substring(tmpIndex + 3);
		//					tmpIndex = language.indexOf("\"");
		//					String languageToAdd = language.substring(0, tmpIndex);
		//					int languageLoc = languageToAdd.indexOf(" Language");
		//					if (languageLoc != -1) {
		//						languageToAdd = languageToAdd.substring(0, languageLoc);
		//					}
		//					languageLoc = languageToAdd.indexOf(" language");
		//					if (languageLoc != -1) {
		//						languageToAdd = languageToAdd.substring(0, languageLoc);
		//					} 
		//					languageList.add(languageToAdd);
		//					language = language.substring(tmpIndex);
		//				}
		//			}
		//
		//			ArrayList<String> countryList = new ArrayList<String>();
		//			String country = row[7];
		//			if (country.equals("{}") || country.equals("")) {
		//				country = "No Value";
		//			} else {
		//				while (country.indexOf(":") != -1) {
		//					tmpIndex = country.indexOf(":");
		//					country = country.substring(tmpIndex + 3);
		//					tmpIndex = country.indexOf("\"");
		//					String countryToAdd = country.substring(0, tmpIndex);
		//					countryList.add(countryToAdd);
		//					country = country.substring(tmpIndex);
		//				}
		//			}
		//
		//			ArrayList<String> genreList = new ArrayList<String>();
		//			String genre = row[8];
		//			if (genre.equals("{}") || genre.equals("")) {
		//				genre = "No Value";
		//			} else {
		//				while (genre.indexOf(":") != -1) {
		//					tmpIndex = genre.indexOf(":");
		//					genre = genre.substring(tmpIndex + 3);
		//					tmpIndex = genre.indexOf("\"");
		//					String genreToAdd = genre.substring(0, tmpIndex);
		//					genreList.add(genreToAdd);
		//					genre = genre.substring(tmpIndex);
		//				}
		//			}		
		//
		//			Movie tmpMovie = new Movie(title, Integer.parseInt(year), Integer.parseInt(length), countryList, genreList, languageList);
		//			returnCollection.addMovie(tmpMovie);
		//		}
		//		return returnCollection;

	}

	/** 
	 * Parses a JSON encoded String that contains the information of an array of movies
	 * @param input A JSON encoded String
	 * @return MovieCollection A movie collection of the movies whose information was encoded in JSON
	 */
	protected MovieCollection parseMovieJSONArray(String input) {
		MovieCollection returnCollection = new MovieCollection();

		try {
			JSONArray movieArray = new JSONArray(input);

			for (int i=0; i<movieArray.length(); i++) {
				JSONObject movieObject = (JSONObject)movieArray.get(i);

				String title = movieObject.getString("title");
				int year = movieObject.getInt("year");
				int length = movieObject.getInt("length");


				ArrayList<String> countryList = new ArrayList<String>();
				JSONArray countryArray = movieObject.getJSONArray("country");
				for (int j=0; j<countryArray.length(); j++) {
					String country = (String)countryArray.get(j);
					countryList.add(country);
				}

				ArrayList<String> genreList = new ArrayList<String>();
				JSONArray genreArray = movieObject.getJSONArray("genre");
				for (int k=0; k<genreArray.length(); k++) {
					String genre = (String)genreArray.get(k);
					genreList.add(genre);
				}

				ArrayList<String> languageList = new ArrayList<String>();
				JSONArray languageArray = movieObject.getJSONArray("language");
				for (int l=0; l<languageArray.length(); l++) {
					String language = (String)languageArray.get(l);
					languageList.add(language);
				}

				Movie newMovie = new Movie(title, year, length, countryList, genreList, languageList);
				returnCollection.addMovie(newMovie);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return returnCollection;
	}


	/** 
	 * Parses a JSON encoded String that contains the information of a single movie
	 * @param input A JSON encoded String
	 * @return MovieCollection A movie collection containing the movie whose information was encoded in JSON
	 */
	protected MovieCollection parseMovieJSON(String input) {
		MovieCollection returnCollection = new MovieCollection();

		try {
			JSONObject movieObject = new JSONObject(input);

			String title = movieObject.getString("title");
			int year = movieObject.getInt("year");
			int length = movieObject.getInt("length");


			ArrayList<String> countryList = new ArrayList<String>();
			JSONArray countryArray = movieObject.getJSONArray("country");
			for (int i=0; i<countryArray.length(); i++) {
				String country = (String)countryArray.get(i);
				countryList.add(country);
			}

			ArrayList<String> genreList = new ArrayList<String>();
			JSONArray genreArray = movieObject.getJSONArray("genre");
			for (int i=0; i<genreArray.length(); i++) {
				String genre = (String)genreArray.get(i);
				genreList.add(genre);
			}

			ArrayList<String> languageList = new ArrayList<String>();
			JSONArray languageArray = movieObject.getJSONArray("language");
			for (int i=0; i<languageArray.length(); i++) {
				String language = (String)languageArray.get(i);
				languageList.add(language);
			}

			Movie newMovie = new Movie(title, year, length, countryList, genreList, languageList);
			returnCollection.addMovie(newMovie);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return returnCollection;
	}


	/** 
	 * Gets the JSON representation of a filter object
	 * @return JSONObject Containing the information of the filter object
	 */
	protected JSONObject convertFilterToJSON(Filter filter) {
		JSONObject filterObject = new JSONObject();

		try {
			filterObject.put("title", filter.getTitle());
			filterObject.put("country", filter.getCountry());
			filterObject.put("genre", filter.getGenre());
			filterObject.put("language", filter.getLanguage());
			filterObject.put("fromYear", filter.getFromYear());
			filterObject.put("toYear", filter.getToYear());
			filterObject.put("fromLength", filter.getFromLength());
			filterObject.put("toLength", filter.getToLength());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return filterObject;
	}


	/** 
	 * Returns the names of all countries, genres and languages so that the ListBoxes in the GUI can be populated appropriately.
	 * @param fileLocations ArrayList<String> that indicates where the film data files are located
	 * @return ArrayList<TreeSet<String>> Contains the names of all countries, genres and languages
	 */
	public ArrayList<TreeSet<String>> getListBoxItemNames(ArrayList<String> fileLocations) {
		// Create data structures
		ArrayList<TreeSet<String>> itemNames = new ArrayList<TreeSet<String>>();
		// Sets are used because we don't want to allow duplicates. 
		// Further, we use TreeSets as they do not require alphabetic sorting, since they are continuously sorted.
		TreeSet<String> countrySet = new TreeSet<String>();
		TreeSet<String> genreSet = new TreeSet<String>();
		TreeSet<String> languageSet = new TreeSet<String>();

		// Parse all movie files for the names of countries, genres and languages
		for (String fileLocation : fileLocations) {
			// Open the file
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream(fileLocation);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine = null;
			// Parse file line by line
			try {
				while ((strLine = br.readLine()) != null)   {

					// We are parsing tab-separated files; thus, we split on tabs
					String[] row = strLine.split("\t", -1);

					int tmpIndex;

					String country = row[7];
					if (country.equals("{}") || country.equals("")) {
						country = "No Value";
					} else {
						while (country.indexOf(":") != -1) {
							tmpIndex = country.indexOf(":");
							country = country.substring(tmpIndex + 3);
							tmpIndex = country.indexOf("\"");
							String countryToAdd = country.substring(0, tmpIndex);
							countrySet.add(countryToAdd);
							country = country.substring(tmpIndex);
						}
					}

					String genre = row[8];
					if (genre.equals("{}") || genre.equals("")) {
						genre = "No Value";
					} else {
						while (genre.indexOf(":") != -1) {
							tmpIndex = genre.indexOf(":");
							genre = genre.substring(tmpIndex + 3);
							tmpIndex = genre.indexOf("\"");
							String genreToAdd = genre.substring(0, tmpIndex);
							genreSet.add(genreToAdd);
							genre = genre.substring(tmpIndex);
						}
					}

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
							languageLoc = languageToAdd.indexOf(" language");
							if (languageLoc != -1) {
								languageToAdd = languageToAdd.substring(0, languageLoc);
							} 
							languageSet.add(languageToAdd);
							language = language.substring(tmpIndex);
						}
					}
				}
			} catch (IOException readException) {
				readException.printStackTrace();
			}

			// After reading file close BufferedReader
			try {
				br.close();
			} catch (IOException closeException) {
				closeException.printStackTrace();
			}
		}

		// Add TreeSets to ArrayList
		itemNames.add(countrySet);
		itemNames.add(genreSet);
		itemNames.add(languageSet);

		return itemNames;
	}


	/** 
	 * Retrieves the location of a CSV file containing all movies that correspond to the selected filter criteria
	 * @param Filter containing the selected filter criteria
	 * @return String containing the link to the CSV file
	 */
	public String getExportCSVLink(Filter filter) {
		String inputLine = "https://www.census.gov/econ/cbp/download/noise_layout/County_Layout.txt";

//				JSONObject JSONRequest = convertFilterToJSON(filter);
//				//System.out.println("My Filter Request");
//				//System.out.println(JSONRequest.toString());
//		
//				try {
//					// CHANGE API NAME
//					URL url = new URL("http://backend-sweng15.rhcloud.com/backend/api/movie/filtered");
//					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//					connection.setDoOutput(true);
//					connection.setRequestMethod("POST");
//					connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
//					connection.setConnectTimeout(60000);
//					connection.setReadTimeout(60000);
//		
//					OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
//					writer.write(JSONRequest.toString());
//					writer.close();
//					
//					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//					inputLine = in.readLine();
//					//System.out.println("Read line: " + inputLine);
//					in.close();
//		
//				} catch (MalformedURLException e) {
//					e.printStackTrace(System.out);
//				} catch (IOException e) {
//					e.printStackTrace(System.out);
//				}
//				
//				System.out.println("CSV Response from server: " + inputLine);
//				
//				// Call parseExportLink??????

		return inputLine;
	}

	/** 
	 * Parses a JSON encoded String that contains a URL
	 * @param input A JSON encoded String
	 * @return String The URL whose information was encoded in JSON
	 */
	protected String parseExportLink(String input) {
		String returnString = "";

		try {
			JSONObject movieObject = new JSONObject(input);
			returnString = movieObject.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return returnString;
	}
	
}

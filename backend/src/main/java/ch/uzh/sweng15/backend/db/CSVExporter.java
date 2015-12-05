package ch.uzh.sweng15.backend.db;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import ch.uzh.sweng15.backend.pojo.Filter;
import ch.uzh.sweng15.backend.pojo.Movie;


/**
 * This class generates CSVs and handles the distibution of access keys
 * to the different CSVs.
 * @author Raffael Theiler
 *
 */
@Singleton
public class CSVExporter {
	
	
	public static int MAX_CSV_STORED = 2;
	public static String SEPARATOR_STR = ",  ";
	
	Queue<String> keyQueue;
	HashMap<String, String> csvStore;
	
	@Inject
	DatabaseAdapter adapter;
	
	private SecureRandom randomStringGen;
	
	
	/**
	 * CDI Constructor
	 */
	public CSVExporter() {
		System.out.println("New instance of CSV Exporter created");
		this.keyQueue  =new LinkedList<>();
		this.csvStore = new HashMap<>();
		 randomStringGen = new SecureRandom();
	}

	/**
	 * Constructor for non CDI applications
	 * @param adapter an instance of the {@link DatabaseAdapter}
	 */
	public CSVExporter(DatabaseAdapter adapter) {
		this();
		this.adapter = adapter;
	}
	
	
	/**
	 * Generates a CSV file id from a given filter.
	 * The ID can then be used to download the file
	 * @param filter the input filter
	 * @return the ID
	 */
	public String generateCSV(Filter filter){
		if(csvStore.size() >=MAX_CSV_STORED){
			removeOneCSV();
		}
		
		MongoCursor<Document> cursor = 
				adapter.getFilteredListOfMovieDocuments(filter);
		
		StringBuilder csv = new StringBuilder();

		try {
			while (cursor.hasNext()) {
				Document next = cursor.next();
				
				List<String> csvStrings = Movie.documentToCSV(next);
				
				//Java 8
				//csv.append(csvStrings.stream().collect(Collectors.joining(SEPARATOR_STR)));
				//csv.append("\n");
				
				//Java 7
				//newline integrated in arrayJoinToString
				csv.append(arrayJoinToString(SEPARATOR_STR, csvStrings));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		
		finally { 
			cursor.close();
		}
		
		return storeCSV(csv.toString());
	}
	
	
	/**
	 * Returns a CSV string from a given ID generated 
	 * by this {@link CSVExporter}
	 * @param key the key
	 * @return the csv string
	 */
	public String getCSV(String key){
		if(csvStore.containsKey(key)){
			return csvStore.get(key);
		}else return "";
	}
	
	
	/*
	 * handles the deletion of a csv file string
	 */
	private void removeOneCSV(){
		String poll = keyQueue.poll();
		this.csvStore.remove(poll);
	}
	
	
	/*
	 * insertion of a csv file string
	 */
	private String storeCSV(String csv){
		String hashCodeString =  (new BigInteger(130, randomStringGen)).toString(32);
		this.keyQueue.add(hashCodeString);
		this.csvStore.put(hashCodeString, csv);
		return hashCodeString;
		
	}
	
	private  String arrayJoinToString(String separator, List<String> list) {
		StringBuilder string = new StringBuilder();
		for(String s : list){
			string.append(s);
			string.append(separator);
		}
		//remove last inner separator or  "" when empty
		String out = ((string.length()>0)?string.substring(0, string.length() - separator.length()).toString():"");
		return out + "\n";
	}
	
}

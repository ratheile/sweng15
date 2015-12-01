package ch.uzh.sweng15.backend.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import ch.uzh.sweng15.backend.pojo.Filter;

public class CSVExporter {
	
	public static int MAX_CSV_STORED = 2;
	Queue<String> keyQueue;
	HashMap<String, String> csvStore;
	
	@Inject
	DatabaseAdapter adapter;
	
	
	public CSVExporter() {
		this.keyQueue  =new LinkedList<>();
		this.csvStore = new HashMap<>();
	}
	
	
	public CSVExporter(DatabaseAdapter adapter) {
		this();
		this.adapter = adapter;
	}
	
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
				System.out.println(next);
			}
		} finally {
			cursor.close();
		}
		
		return storeCSV(csv.toString());
	}
	
	
	public String getCSV(String key){
		if(csvStore.containsKey(key)){
			return csvStore.get(key);
		}else return "";
	}
	
	

	private void removeOneCSV(){
		String poll = keyQueue.poll();
		this.csvStore.remove(poll);
	}
	
	
	private String storeCSV(String csv){
		int hashCode = csv.hashCode();
		String hashCodeString = String.valueOf(hashCode);
		this.keyQueue.add(hashCodeString);
		this.csvStore.put(hashCodeString, csv);
		return hashCodeString;
		
	}
	
}

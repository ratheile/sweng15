package ch.uzh.sweng15.backend.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Singleton;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;

import ch.uzh.sweng15.backend.pojo.Filter;
import ch.uzh.sweng15.backend.pojo.Movie;
import javafx.scene.chart.PieChart.Data;

/**
 * This class is used to access the mongodb databse.
 * All interaction has to be in here.
 * @author Raffael Theiler
 *
 */
@Singleton
public class DatabaseAdapter {

	private static final int MAX_FETCH = 100000;
	private static final String MOVIES_COLLECTION = "movies";
	private static final String MOVIES_DATABASE = "sweng15";

	MongoClient mongoClient;
	MongoDatabase database;

	
	/**
	 * CDI Constructor 
	 */
	public DatabaseAdapter(){
		this("mongodb://test:test@ds057204.mongolab.com:57204/sweng15");
	}
	
	
	
	/**
	 * Create a database Adapter from a Connection String
	 * @param connectionString the connection String
	 */
	public DatabaseAdapter(String connectionString){
		MongoClientURI tempConStrObj = new MongoClientURI(connectionString);
		setupDB(new MongoClient(tempConStrObj));
	}
	
	
	/**
	 * Create a new Database Adapter
	 * @param host the Mongodb host address
	 * @param port the Mongodb host port
	 * @param database the Mongodb database name
	 */
	public DatabaseAdapter(String host, int port, String database) {
		setupDB(new MongoClient(host, port));
	}


	/**
	 * Create all collections in the database, then setup necessary environment parameters.
	 */
	public void setupEmptyDatabase() {
		this.database.createCollection(MOVIES_COLLECTION, new CreateCollectionOptions().capped(false));
	}

	
	/**
	 * Delete all data in the database
	 */
	public void deleteDatabase() {
		
	MongoIterable<String> collectionNames = this.database.listCollectionNames();
		
		//check if MOVIES_COLLECTION exists
		for(String name:collectionNames){
			if(name.equals(MOVIES_COLLECTION)){
				//drop if exists
				MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
				collection.drop();
			}
		}
		this.database.getCollection(MOVIES_COLLECTION).drop();
	}

	
	/**
	 * Inserts the movie into the database depending on its BSON representation
	 * @param movie the new movie
	 */
	public void insertMovie(Movie movie) {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		collection.insertOne(movie.getNewBSONRepresentation());
	}
	
	/**
	
	
	
	/**
	 * Inserts the movie into the database depending on its BSON representation
	 * @param movie the new movie
	 */
	public void bulkInsertMovies(List<Movie> movies) {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		
		List<Document> docs = new ArrayList<>(movies.size());
		for(Movie movie:movies){
			Document bsonRepresentation = movie.getNewBSONRepresentation();
			docs.add(bsonRepresentation);
		}
		
		collection.insertMany(docs);
	}
	
	
	
	/**
	 * Get all movies in the collection as BSON documents, be careful using this when performance
	 * is needed
	 * @return The List of BSON documents
	 */
	public List<Document> getAllMoviesAsBSON() {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		FindIterable<Document> find = collection.find();
		List<Document> jsonArray = new ArrayList<>();

		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				jsonArray.add(cursor.next());
			}
		} finally {
			cursor.close();
		}

		return jsonArray;
	}

	/**
	 * Return a random movie from the collection
	 * @return The JSON String of the random movie
	 */
	public String getRandomMovie() {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		int index = (new Random()).nextInt((int) collection.count());
		
		//because we cannot access the index directly we skip n entries
		FindIterable<Document> skip = collection.find().skip(index);
		return skip.first().toJson();

	}
	
	
	/**
	 * Return a filtered set of movies as string, limited to 50 entries
	 * @param filter the filter applied on the set of movies
	 * @return The JSON string of the array of movies
	 */
	public String getFilteredListOfMovies(Filter filter){
		
		//get the movies
		MongoCursor<Document> cursor = getFilteredListOfMovieDocuments(filter);
		
		//convert to JSON string
		return cursorToJSON(cursor);
	}


	/**
	 * Return a filtered set of movies as string, limited to 50 entries
	 * @param filter the filter applied on the set of movies
	 * @return The JSON string of the array of movies
	 */
	public MongoCursor<Document> getFilteredListOfMovieDocuments(Filter filter) {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		
		Document filterAsBson = new Document();
		
		if(!filter.getTitle().equals(""))filterAsBson.append("title",filter.getTitle());
		if(!filter.getCountry().equals(""))filterAsBson.append("country", filter.getCountry());
		if(!filter.getGenre().equals(""))filterAsBson.append("genre", filter.getGenre());
		if(!filter.getLanguage().equals(""))filterAsBson.append("language", filter.getLanguage());
		
		
		//year
		if(filter.getFromYear() != 0 || filter.getToYear() != 0) {
			Document yearFilter = new Document();
			if(filter.getToYear() != 0)yearFilter.append("$lte", filter.getToYear());
			if(filter.getFromYear() != 0)yearFilter.append("$gte",filter.getFromYear());
			filterAsBson.append("year",yearFilter);
		}
		
		if(filter.getFromLength() != 0 || filter.getToLength() != 0) {
			Document yearFilter = new Document();
			if(filter.getToLength() != 0)yearFilter.append("$lte", filter.getToLength());
			if(filter.getFromLength() != 0)yearFilter.append("$gte",filter.getFromLength());
			filterAsBson.append("length",yearFilter);
		}
		
		MongoCursor<Document> cursor = collection.find(filterAsBson).limit(MAX_FETCH).iterator();
		return cursor;
	}
	
	
	
	/*
	 * Centralized database Setup Function
	 * @param mongoClient The client connection to the database
	 */
	private void setupDB(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
		this.database = mongoClient.getDatabase(MOVIES_DATABASE);
	}
	
	/*
	 *Converts the cursor of Documents to json using bson.toJson()
	 *@param cursor Mongodb Cursor retrieved from the database driver
	 *@return the JSON String
	 */
	private String cursorToJSON(MongoCursor<Document> cursor) {
		StringBuilder sb = new StringBuilder();
		while(cursor.hasNext()){
			Document doc = cursor.next();
			sb.append(doc.toJson());
			sb.append(",");
		}
		
		return "[" +((sb.length()>0)?sb.substring(0, sb.length() - 1).toString():"") + "]";
	}

}

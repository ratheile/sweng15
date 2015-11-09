package ch.uzh.sweng15.backend.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;

import ch.uzh.sweng15.backend.pojo.Movie;

/**
 * This class is used to access the mongodb databse.
 * All interaction has to be in here.
 * @author Raffael Theiler
 *
 */
public class DatabaseAdapter {

	private static final String MOVIES_COLLECTION = "movies";

	MongoClient mongoClient;
	MongoDatabase database;

	/**
	 * Create a new Database Adapter
	 * @param host the Mongodb host address
	 * @param port the Mongodb host port
	 * @param database the Mongodb database name
	 */
	public DatabaseAdapter(String host, int port, String database) {
		this.mongoClient = new MongoClient(host, port);
		this.database = mongoClient.getDatabase(database);
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

}

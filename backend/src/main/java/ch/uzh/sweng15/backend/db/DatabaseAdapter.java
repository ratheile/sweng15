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

import ch.uzh.sweng15.backend.temp.Movie;

public class DatabaseAdapter {

	private static final String MOVIES_COLLECTION = "movies";

	MongoClient mongoClient;
	MongoDatabase database;

	public DatabaseAdapter(String host, int port, String database) {
		this.mongoClient = new MongoClient(host, port);
		this.database = mongoClient.getDatabase(database);
	}

	public void setupEmptyDatabase() {
		this.database.createCollection(MOVIES_COLLECTION, new CreateCollectionOptions().capped(false));
	}

	public void deleteDatabase() {
		this.database.getCollection(MOVIES_COLLECTION).drop();
	}

	public void insertMovie(Movie movie) {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		collection.insertOne(movie.getNewBSONRepresentation());
	}

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

	public String getRandomMovie() {
		MongoCollection<Document> collection = database.getCollection(MOVIES_COLLECTION);
		int index = (new Random()).nextInt((int) collection.count());
		FindIterable<Document> skip = collection.find().skip(index);
		return skip.first().toJson();

	}

}

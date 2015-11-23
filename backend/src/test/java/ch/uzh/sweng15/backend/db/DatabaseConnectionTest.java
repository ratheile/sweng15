package ch.uzh.sweng15.backend.db;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * Test the connection to the production database.
 * Test admin privileges of account
 * @author shafall
 *
 */
public class DatabaseConnectionTest {

	@Test
	public void testAdminPrivileges(){
		String testCollectionName = "test";
		
		MongoClientURI tempConStrObj = new MongoClientURI
				("mongodb://test:test@ds057204.mongolab.com:57204/sweng15");
		MongoClient mongoClient = new MongoClient(tempConStrObj);
		MongoDatabase database = mongoClient.getDatabase("sweng15");
		
		MongoIterable<String> collectionNames = database.listCollectionNames();
		
		//check if testcollection exists
		for(String name:collectionNames){
			if(name.equals(testCollectionName)){
				//drop if exists
				MongoCollection<Document> collection = database.getCollection(testCollectionName);
				collection.drop();
			}
		}
		
		//create collection
		database.createCollection(testCollectionName);
		
		//drop again
		MongoCollection<Document> collection = database.getCollection(testCollectionName);
		collection.drop();
	}
}

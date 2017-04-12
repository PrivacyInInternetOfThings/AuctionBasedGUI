import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Aggregates;


public class DatabaseController {

	private MongoClient mongoClient;
	private DB trafficDatabase;
	private String dbName = "deneme";
	private Set<String> collectionSet;
	private String vehiclesCollectionName = "Vehicles";
	private String accidentsCollectionName = "Accidents";
	

	public DatabaseController() throws UnknownHostException {
		mongoClient = new MongoClient();
		trafficDatabase = mongoClient.getDB(dbName);
		collectionSet = trafficDatabase.getCollectionNames();
		System.out.println(trafficDatabase.getCollectionNames());
	}

	public List<String> getAccidentIndexes() {
		
//		db.student.aggregate([     
//		                      {"$group": {"_id": "$rollNo", "count": { "$sum": 1 }}}, 
//		                      {"$match": {count: {"$gt": 1}}} 
//		                  ])
		
		List<String> result = new ArrayList<String>();
		
		DBCollection accidentCollection = trafficDatabase.getCollection(vehiclesCollectionName);
		
		DBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$Accident_Index").append("count", new BasicDBObject("$sum",1)));
		DBObject match = new BasicDBObject("$match", new BasicDBObject("count",new BasicDBObject("$gt", 1)));
		DBObject sort  = new BasicDBObject("$sort", new BasicDBObject("count", -1)); 
		Iterable<DBObject> o = accidentCollection.aggregate(Arrays.asList(group,match,sort)).results();
		
		
		
		for (DBObject dbo : o) {
			
			System.out.println("Accident Index: " + dbo.get("_id").toString() + ", Number Of Vehicles: " + dbo.get("count").toString());
			result.add("Accident Index: " + dbo.get("_id").toString() + ", Number Of Vehicles: " + dbo.get("count").toString());
			
		}
		
		return result;
	}
	
	public void getVehiclesByAccidentIndex(String accidentIndex){
		DBCollection vehiclesCollection = trafficDatabase.getCollection("Vehicles");
		DBCursor c = vehiclesCollection.find(new BasicDBObject("Accident_Index",accidentIndex));
		while(c.hasNext()){
			BasicDBObject o = (BasicDBObject) c.next();
			System.out.println(o.toString());
		}
	}
}

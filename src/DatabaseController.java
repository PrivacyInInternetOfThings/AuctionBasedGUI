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

	public DatabaseController() throws UnknownHostException {
		mongoClient = new MongoClient();
		trafficDatabase = mongoClient.getDB(dbName);
		collectionSet = trafficDatabase.getCollectionNames();
		System.out.println(collectionSet);
	}

	public List<String> getAccidentIndexes() {

		List<String> result = new ArrayList<String>();

		DBCollection accidentCollection = trafficDatabase.getCollection(vehiclesCollectionName);

		DBObject group = new BasicDBObject("$group",
				new BasicDBObject("_id", "$Accident_Index").append("count", new BasicDBObject("$sum", 1)));
		DBObject match = new BasicDBObject("$match", new BasicDBObject("count", new BasicDBObject("$gt", 1)));
		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1));
		Iterable<DBObject> o = accidentCollection.aggregate(Arrays.asList(group, match, sort)).results();

		for (DBObject dbo : o) {

//			System.out.println("Accident Index: " + dbo.get("_id").toString() + ", Number Of Vehicles: "
//					+ dbo.get("count").toString());
			result.add("Accident Index: " + dbo.get("_id").toString() + ", Number Of Vehicles: "
					+ dbo.get("count").toString());

		}

		return result;
	}

	public ArrayList<Vehicle> getVehiclesByAccidentIndex(String accidentIndex){
		ArrayList<Vehicle> result = new ArrayList<>();
		
		DBCollection vehiclesCollection = trafficDatabase.getCollection("Vehicles");
		DBCursor c = vehiclesCollection.find(new BasicDBObject("Accident_Index",accidentIndex),new BasicDBObject("Accident_Index",1).append("Vehicle_Reference", 1).append("_id", 0).append("Vehicle_Type", 1).append("Journey_Purpose_of_Driver", 1).append("Age_of_Vehicle", 1));
		while(c.hasNext()){
			BasicDBObject o = (BasicDBObject) c.next();
			Vehicle v = new Vehicle(
					VEHICLETYPE.getById(o.getInt("Vehicle_Type")), 
					JOURNEYPURPOSE.getJourneyPurposeById(o.getInt("Journey_Purpose_of_Driver")), 
					MALFUNCTIONTYPE.getMalfunctionById((int)Math.random()*3), 
					(o.getInt("Age_of_Vehicle") == -1 ? 50 :  o.getInt("Age_of_Vehicle")));
			result.add(v);
		}
		
		return result;
	}

	public void getVehicleByAccidentIdAndReference(String accidentIndex, int reference) {
		DBCollection vehiclesCollection = trafficDatabase.getCollection("Vehicles");
		DBCursor c = vehiclesCollection
				.find(new BasicDBObject("Accident_Index", accidentIndex).append("Vehicle_Reference", reference));
	}
}

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class InsertData {
    public InsertData(ArrayList<String> data){
        System.out.println(1);
        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.3.0");
        System.out.println(2);
        MongoDatabase db = mongoClient.getDatabase("sampleDB");
        System.out.println(3);
        MongoCollection col = db.getCollection("users");
        System.out.println(4);
        Document sampleDoc = new Document("name", data.get(0)).append("email", data.get(1)).append("password",data.get(2));
        db.createCollection(data.get(0));
        System.out.println(4);
        col.insertOne(sampleDoc);
        System.out.println(4);

    }
}

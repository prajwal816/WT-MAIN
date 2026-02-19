package wt.lab7;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

/**
 * Lab-7: Establish connection with MongoDB using Java Driver
 * a) Send various MongoDB statements
 * b) Retrieve and process the results received from the database
 */
public class MongoDBDriverDemo {

    private static final String DEFAULT_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "lab7_db";
    private static final String COLLECTION = "students";

    public static void main(String[] args) {
        String uri = args.length > 0 ? args[0] : DEFAULT_URI;
        System.out.println("=== Lab-7: MongoDB Java Driver Demo ===\n");
        System.out.println("Connecting to: " + uri);

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION);

            System.out.println("Connected to database: " + DB_NAME + ", collection: " + COLLECTION + "\n");

            // a) Send various MongoDB statements
            insertDocuments(collection);
            findDocuments(collection);
            updateDocuments(collection);
            aggregateDocuments(collection);
            deleteDocuments(collection);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Make sure MongoDB is running at " + uri);
        }
    }

    /**
     * a) Send INSERT statements and retrieve results
     */
    private static void insertDocuments(MongoCollection<Document> collection) {
        System.out.println("--- 1. INSERT ---");
        List<Document> docs = Arrays.asList(
                new Document("name", "Alice").append("age", 22).append("course", "Computer Science"),
                new Document("name", "Bob").append("age", 21).append("course", "Mathematics"),
                new Document("name", "Carol").append("age", 23).append("course", "Physics")
        );
        collection.insertMany(docs);
        System.out.println("Inserted " + docs.size() + " documents.");
    }

    /**
     * a) Send FIND statements and b) retrieve & process results
     */
    private static void findDocuments(MongoCollection<Document> collection) {
        System.out.println("\n--- 2. FIND (Retrieve & Process Results) ---");

        // Find all
        System.out.println("All documents:");
        FindIterable<Document> cursor = collection.find();
        for (Document doc : cursor) {
            processDocument(doc);
        }

        // Find with filter
        System.out.println("\nDocuments where age >= 22:");
        Bson filter = Filters.gte("age", 22);
        for (Document doc : collection.find(filter)) {
            processDocument(doc);
        }

        // Find one
        System.out.println("\nFirst document where course = 'Mathematics':");
        Document one = collection.find(Filters.eq("course", "Mathematics")).first();
        if (one != null) {
            processDocument(one);
        }
    }

    /**
     * b) Process and display a document (retrieve & process results)
     */
    private static void processDocument(Document doc) {
        String name = doc.getString("name");
        Integer age = doc.getInteger("age");
        String course = doc.getString("course");
        String id = doc.getObjectId("_id").toString();
        System.out.println("  [id=" + id.substring(id.length() - 6) + "] " + name + ", " + age + ", " + course);
    }

    /**
     * a) Send UPDATE statements and retrieve results
     */
    private static void updateDocuments(MongoCollection<Document> collection) {
        System.out.println("\n--- 3. UPDATE ---");
        Bson filter = Filters.eq("name", "Bob");
        Bson update = Updates.set("course", "Data Science");
        UpdateResult result = collection.updateOne(filter, update);
        System.out.println("Matched: " + result.getMatchedCount() + ", Modified: " + result.getModifiedCount());

        // Retrieve and process updated result
        Document updated = collection.find(filter).first();
        if (updated != null) {
            System.out.println("Updated document: " + updated.toJson());
        }
    }

    /**
     * a) Send AGGREGATION pipeline and b) retrieve & process results
     */
    private static void aggregateDocuments(MongoCollection<Document> collection) {
        System.out.println("\n--- 4. AGGREGATION ---");
        List<Bson> pipeline = Arrays.asList(
                Aggregates.group("$course", Accumulators.sum("count", 1), Accumulators.avg("avgAge", "$age")),
                Aggregates.sort(Sorts.descending("count"))
        );
        for (Document doc : collection.aggregate(pipeline)) {
            String course = doc.getString("_id");
            int count = doc.getInteger("count", 0);
            double avgAge = doc.getDouble("avgAge");
            System.out.println("  " + course + ": count=" + count + ", avgAge=" + String.format("%.1f", avgAge));
        }
    }

    /**
     * a) Send DELETE statements and retrieve results
     */
    private static void deleteDocuments(MongoCollection<Document> collection) {
        System.out.println("\n--- 5. DELETE ---");
        Bson filter = Filters.eq("name", "Carol");
        DeleteResult result = collection.deleteOne(filter);
        System.out.println("Deleted: " + result.getDeletedCount() + " document(s)");

        System.out.println("\nRemaining documents after delete:");
        for (Document doc : collection.find()) {
            processDocument(doc);
        }
    }
}

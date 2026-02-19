# Lab-7: MongoDB Java Driver

Establish a connection with MongoDB using the Java Driver and:
- **a)** Send various MongoDB statements (Insert, Find, Update, Aggregation, Delete)
- **b)** Retrieve and process the results received from the database

## Prerequisites

- Java 17+
- Maven
- MongoDB running locally (default: `mongodb://localhost:27017`)

## Build & Run

```bash
cd Lab-7
mvn compile exec:java -Dexec.mainClass="wt.lab7.MongoDBDriverDemo"
```

Or using `mvn exec-maven-plugin` (add to pom.xml) or run the JAR:

```bash
mvn clean package
java -cp target/mongodb-java-driver-1.0-SNAPSHOT.jar:target/dependency/* wt.lab7.MongoDBDriverDemo
```

For custom URI: `mvn exec:java -Dexec.mainClass="wt.lab7.MongoDBDriverDemo" -Dexec.args="mongodb://localhost:27017"`

## MongoDB Operations Demonstrated

| Operation | Statement Type | Result Processing |
|-----------|----------------|-------------------|
| Insert | `insertMany()` | Confirms documents inserted |
| Find | `find()`, `find(filter)` | Iterates cursor, processes each document |
| Update | `updateOne()` | Gets matched/modified counts, fetches updated doc |
| Aggregation | `aggregate(pipeline)` | Groups by course, sums count, averages age |
| Delete | `deleteOne()` | Gets deleted count, lists remaining docs |

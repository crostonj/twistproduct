package com.techtwist.services;

import com.techtwist.models.Product;
import com.techtwist.services.interfaces.IProductService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service("MongoProductService") // Matches the value in application.properties
public class MongoProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(MongoProductService.class);

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> productCollection;

    //get mongo password
    String mongoPassword = System.getenv("MONGOPASSWORD");

    // Zero-argument constructor
    public MongoProductService() {
        // Default constructor for frameworks or tools that require it
    }

    @PostConstruct
    public void initialize() {
        String connectionString = String.format(
            "mongodb+srv://TechTwist:%s@techtwist.c1msawb.mongodb.net/?retryWrites=true&w=majority&appName=TechTwist",
            mongoPassword
        );

        logger.info("Initializing MongoDB client...");
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try {
            this.mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase("TechTwist");
            this.productCollection = database.getCollection("products");
            this.database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException e) {
            System.err.println("An error occurred while connecting to MongoDB: " + e.getMessage());
            throw new RuntimeException("Failed to initialize MongoDB client", e);
        }
    }

    @PreDestroy
    public void closeMongoClient() {
        if (this.mongoClient != null) {
            System.out.println("Closing MongoDB client.");
            this.mongoClient.close();
        }
    }
    @Override
    public Product create(Product product) {
        Document doc = new Document("partitionKey", product.getPartitionKey()).append("rowKey", product.getRowKey())
                .append("name", product.getName()).append("description", product.getDescription());
        productCollection.insertOne(doc);
        product.setId(doc.getObjectId("_id").toString());
        return product;
    }

    @Override
    public Product get(String partitionKey, String rowKey) {
        Document query = new Document("partitionKey", partitionKey).append("rowKey", rowKey);
        Document doc = productCollection.find(query).first();
        return doc != null ? documentToProduct(doc) : null;
    }

    @Override
    public Product getByName(String name) {
        Document query = new Document("name", name);
        Document doc = productCollection.find(query).first();
        return doc != null ? documentToProduct(doc) : null;
    }

    @Override
    public Product update(Product product) {
        Document query = new Document("_id", new ObjectId(product.getId()));
        Document update = new Document("$set",
                new Document("name", product.getName()).append("description", product.getDescription()));
        productCollection.updateOne(query, update);
        return product;
    }

    @Override
    public void delete(Product product) {
        Document query = new Document("_id", new ObjectId(product.getId()));
        productCollection.deleteOne(query);
    }

    @Override
    public List<Product> List() {
        List<Product> products = new ArrayList<>();
        for (Document doc : productCollection.find()) {
            products.add(documentToProduct(doc));
        }
        return products;
    }

    private Product documentToProduct(Document doc) {
        Product product = new Product();
        product.setId(doc.getObjectId("_id").toString());
        product.setPartitionKey(doc.getString("partitionKey"));
        product.setRowKey(doc.getString("rowKey"));
        product.setName(doc.getString("name"));
        product.setDescription(doc.getString("description"));
        return product;
    }
}
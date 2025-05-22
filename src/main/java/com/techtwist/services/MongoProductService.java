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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Primary
@Service("MongoProductService") // Matches the value in application.properties
public class MongoProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(MongoProductService.class);

    private MongoClient mongoClient;

    private MongoCollection<Document> productCollection;


    @Autowired
    public MongoProductService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @PostConstruct
    public void initialize() {
        logger.info("Initializing MongoProductService with Spring-managed MongoClient.");
        try {
            // Spring Boot typically configures the database from the URI.
            // If your URI includes '/TechTwist', it should connect to that database.
            // Otherwise, you can specify spring.data.mongodb.database or get it here.
            MongoDatabase database = mongoClient.getDatabase("techtwist"); // Or derive from config
            this.productCollection = database.getCollection("products");

            // Ping to confirm (Spring Boot's health indicator often does this too)
            database.runCommand(new Document("ping", 1));
            logger.info("Successfully pinged MongoDB via Spring-managed client!");
        } catch (Exception e) {
            logger.error("Failed to initialize productCollection or ping MongoDB", e);
            throw new RuntimeException("Failed to initialize MongoDB components in MongoProductService", e);
        }
    }

    @Override
    public Product create(Product product) {
        try {
            Document doc = new Document("partitionKey", product.getPartitionKey())
                    .append("rowKey", product.getRowKey())
                    .append("name", product.getName())
                    .append("description", product.getDescription());
            productCollection.insertOne(doc);
            product.setId(doc.getObjectId("_id").toString());
            return product;
        } catch (Exception e) {
            logger.error("Error creating product", e);
            throw new RuntimeException("Failed to create product", e);
        }
    }

    @Override
    public Product get(String partitionKey, String rowKey) {
        try {
            Document query = new Document("partitionKey", partitionKey).append("rowKey", rowKey);
            Document doc = productCollection.find(query).first();
            return doc != null ? documentToProduct(doc) : null;
        } catch (Exception e) {
            logger.error("Error getting product by partitionKey and rowKey", e);
            throw new RuntimeException("Failed to get product", e);
        }
    }

    @Override
    public Product getByName(String name) {
        try {
            Document query = new Document("name", name);
            Document doc = productCollection.find(query).first();
            return doc != null ? documentToProduct(doc) : null;
        } catch (Exception e) {
            logger.error("Error getting product by name", e);
            throw new RuntimeException("Failed to get product by name", e);
        }
    }

    @Override
    public Product update(Product product) {
        try {
            Document query = new Document("_id", new ObjectId(product.getId()));
            Document update = new Document("$set",
                    new Document("name", product.getName()).append("description", product.getDescription()));
            productCollection.updateOne(query, update);
            return product;
        } catch (Exception e) {
            logger.error("Error updating product", e);
            throw new RuntimeException("Failed to update product", e);
        }
    }

    @Override
    public void delete(Product product) {
        try {
            Document query = new Document("_id", new ObjectId(product.getId()));
            productCollection.deleteOne(query);
        } catch (Exception e) {
            logger.error("Error deleting product", e);
            throw new RuntimeException("Failed to delete product", e);
        }
    }

    @Override
    public List<Product> List() {
        try {
            List<Product> products = new ArrayList<>();
            for (Document doc : productCollection.find()) {
                products.add(documentToProduct(doc));
            }
            return products;
        } catch (Exception e) {
            logger.error("Error listing products", e);
            throw new RuntimeException("Failed to list products", e);
        }
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
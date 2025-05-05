package com.techtwist.services;

import com.techtwist.models.Product;
import com.techtwist.services.interfaces.IProductService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service("MongoProductService") // Matches the value in application.properties
public class MongoProductService implements IProductService {

    private MongoClient mongoClient;
    private MongoDatabase database;

    private final MongoCollection<Document> productCollection;

    public void initialize() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("myDatabase");
    }

    public MongoProductService() {
        this.productCollection = database.getCollection("products");
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
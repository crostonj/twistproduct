package com.techtwist.services;

import com.techtwist.services.interfaces.IProductService;
import com.techtwist.models.Product;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("InMemoryProductService") // Matches the value in application.properties
public class InMemoryProductService implements IProductService {

    private final Map<String, Product> productStore = new HashMap<>();

    public InMemoryProductService() {
        createSampleProducts();
    }

    @Override
    public Product create(Product product) {
        String key = generateKey(product.getPartitionKey(), product.getRowKey());
        productStore.put(key, product);
        return product;
    }

    @Override
    public Product get(String partitionKey, String rowKey) {
        String key = generateKey(partitionKey, rowKey);
        return productStore.get(key);
    }

    @Override
    public Product getByName(String name) {
        return productStore.values().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Product update(Product product) {
        String key = generateKey(product.getPartitionKey(), product.getRowKey());
        if (productStore.containsKey(key)) {
            productStore.put(key, product);
            return product;
        }
        return null;
    }

    @Override
    public void delete(Product product) {
        String key = generateKey(product.getPartitionKey(), product.getRowKey());
        productStore.remove(key);
    }

    public Map<String, Product> getProductStore() {
        return productStore; // Expose internal store for testing
    }

    private String generateKey(String partitionKey, String rowKey) {
        return partitionKey + ":" + rowKey;
    }

    //Need a function to create 10 products
    public void createSampleProducts() {
        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            //convert integer to string
            String id = Integer.toString(i);
            product.setId(id);    
            product.setPartitionKey("partition" + i);
            product.setRowKey("row" + i);
            product.setName("Product" + i);
            product.setImageUrl("ttp" + i + ".png");
            product.setCategory("Category" + i);
            product.setBrand("Brand" + i);
            product.setDescription("Description for Product " + i);
            product.setPrice(10.0 * i);
            create(product);
        }
    }

    @Override
    public java.util.List<Product> List() {
        return new java.util.ArrayList<>(productStore.values());
    }

    //List override
    
}

package com.techtwist.helpers;

import com.techtwist.models.Product;
import com.techtwist.services.InMemoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductServiceTestHelper {

    @Autowired
    private InMemoryProductService inMemoryProductService;

    public void addProduct(Product product) {
        inMemoryProductService.create(product);
    }

    public Map<String, Product> getProductStore() {
        return inMemoryProductService.getProductStore(); // Expose internal store for testing
    }
}

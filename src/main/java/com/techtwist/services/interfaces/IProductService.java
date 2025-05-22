package com.techtwist.services.interfaces;

import java.util.List;

import com.techtwist.models.Product;

public interface IProductService {
    Product create(Product product);
    Product get(String partitionKey, String rowKey);
    Product getByName(String name);
    Product update(Product product);
    void delete(Product product);
    List<Product> List();
}
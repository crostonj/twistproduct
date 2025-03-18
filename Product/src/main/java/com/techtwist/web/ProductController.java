package com.techtwist.web;

import com.azure.data.tables.models.TableEntity;
import com.techtwist.models.Product;
import com.techtwist.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            if (product == null || product.getPartitionKey() == null || product.getRowKey() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            return ResponseEntity.ok(productService.create(product));
        } catch (Exception e) {
            // Handle the exception, e.g., log it or return an error response
            e.printStackTrace(); // Or use a logging framework
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product", e);
        }
    }

    @GetMapping(value = "/{partitionKey}/{rowKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> get(
            @PathVariable String partitionKey,
            @PathVariable String rowKey) {
        try {
            Product product = productService.get(partitionKey, rowKey);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            e.printStackTrace(); // Or use a logging framework
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read entity", e);
        }
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        try {
            if (product == null || product.getPartitionKey() == null || product.getRowKey() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            return ResponseEntity.ok(productService.update(product));
        } catch (Exception e) {
            // Handle the exception, e.g., log it or return an error response
            e.printStackTrace(); // Or use a logging framework
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update product", e);
        }
    }

    @DeleteMapping("/{partitionKey}/{rowKey}")
    public void delete(@RequestBody Product product) {
        try {
            // Validate the product before deletion
            if (product == null || product.getPartitionKey() == null || product.getRowKey() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            productService.delete(product);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or return an error response
            e.printStackTrace(); // Or use a logging framework
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete product", e);
        }

    }

    private Product convertTableEntityToProduct(TableEntity tableEntity) {
        Product product = new Product();
        product.setPartitionKey(tableEntity.getPartitionKey());
        product.setRowKey(tableEntity.getRowKey());
        // Map other properties from TableEntity to Product
        if(tableEntity.getProperties().containsKey("name")){
            product.setName(tableEntity.getProperties().get("name").toString());
        }
        if(tableEntity.getProperties().containsKey("price")){
            product.setPrice(Double.parseDouble(tableEntity.getProperties().get("price").toString()));
        }

        return product;
    }


}

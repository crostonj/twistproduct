package com.techtwist.web;

import com.techtwist.models.Product;
import com.techtwist.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //get product ny name
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getByName(
            @PathVariable String name) {
        try {
            Product product = productService.getByName(name);
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

    @DeleteMapping
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

}

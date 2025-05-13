package com.techtwist.controllers;

import com.techtwist.models.Product;
import com.techtwist.services.interfaces.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final IProductService productService;

    @Autowired // Constructor injection
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product",
            description = "Create a new product")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The product to create", required = true)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            logger.debug("Received product: {}", product); // Log the incoming product
            if (product == null || product.getPartitionKey() == null || product.getRowKey() == null) {
                logger.error("Invalid product data: {}", product);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            Product createdProduct = productService.create(product);
            logger.info("Product created successfully: {}", createdProduct);
            return ResponseEntity.ok(createdProduct);
        } catch (ResponseStatusException e) {
            logger.error("Error creating product: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product", e);
        }
    }

    @Operation(summary = "Get a product by partitionKey and rowKey",
            description = "Get a product by partitionKey and rowKey")
    @Parameter(name = "partitionKey", description = "The partition key of the product")
    @Parameter(name = "rowKey", description = "The row key of the product")
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
            logger.error("Error getting product by key", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read entity", e);
        }
    }

    @Operation(summary = "Get a product by name",
            description = "Get a product by name")
    @Parameter(name = "name", description = "The name of the product")
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE) // Changed path to avoid conflict with key-based get
    public ResponseEntity<Product> getByName(
            @PathVariable String name) {
        try {
            Product product = productService.getByName(name);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            logger.error("Error getting product by name", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read entity", e);
        }
    }

    @Operation(summary = "Update a product",
            description = "Update a product")
    @Parameter(name = "product", description = "The product to update")
    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        try {
            if (product == null || product.getPartitionKey() == null || product.getRowKey() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            Product updatedProduct = productService.update(product);
            if (updatedProduct == null) {
                return ResponseEntity.notFound().build(); // Or handle as appropriate if update implies existence
            }
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            logger.error("Error updating product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update product", e);
        }
    }

    @Operation(summary = "Delete a product",
            description = "Delete a product")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The product to delete", required = true)
    @DeleteMapping
    public void delete(@RequestBody Product product) {
        try {
            if (product == null || product.getPartitionKey() == null || product.getRowKey() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            productService.delete(product);
        } catch (Exception e) {
            logger.error("Error deleting product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete product", e);
        }
    }

    @Operation(summary = "List all products",
            description = "Retrieve a list of all products")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> listAll() {
        try {
            List<Product> products = productService.List();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving product list", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve product list", e);
        }
    }
}
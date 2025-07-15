package com.techtwist.controllers;

import com.techtwist.dto.*;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product",
            description = "Create a new product using ProductCreateDTO")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductCreateDTO createDTO) {
        try {
            logger.debug("Creating product: {}", createDTO);
            if (createDTO == null) {
                logger.error("Invalid product data: null");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            ProductResponseDTO createdProduct = productService.create(createDTO);
            logger.info("Product created successfully with ID: {}", createdProduct.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (ResponseStatusException e) {
            logger.error("Error creating product: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating product", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product", e);
        }
    }

    @Operation(summary = "Get a product by ID",
            description = "Retrieve a product by its unique identifier")
    @Parameter(name = "id", description = "The unique identifier of the product")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable String id) {
        try {
            Optional<ProductResponseDTO> product = productService.findById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting product by ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve product", e);
        }
    }

    @Operation(summary = "Get a product by name",
            description = "Retrieve a product by its name")
    @Parameter(name = "name", description = "The name of the product")
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> getByName(@PathVariable String name) {
        try {
            ProductResponseDTO product = productService.findByName(name);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting product by name: {}", name, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve product", e);
        }
    }

    @Operation(summary = "Update a product",
            description = "Update an existing product using ProductUpdateDTO")
    @Parameter(name = "id", description = "The unique identifier of the product to update")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable String id, @RequestBody ProductUpdateDTO updateDTO) {
        try {
            if (updateDTO == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data");
            }
            ProductResponseDTO updatedProduct = productService.update(id, updateDTO);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResponseStatusException e) {
            logger.error("Error updating product: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error updating product with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update product", e);
        }
    }

    @Operation(summary = "Delete a product",
            description = "Delete a product by its unique identifier")
    @Parameter(name = "id", description = "The unique identifier of the product to delete")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting product with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete product", e);
        }
    }

    @Operation(summary = "List all products",
            description = "Retrieve a list of all active products")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> listAll() {
        try {
            List<ProductResponseDTO> products = productService.findAll();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving product list", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve product list", e);
        }
    }

    @Operation(summary = "Get product summaries",
            description = "Retrieve a list of all active products in summary format (optimized for performance)")
    @GetMapping(value = "/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductSummaryDTO>> getSummaries() {
        try {
            List<ProductSummaryDTO> summaries = productService.findAllSummary();
            if (summaries.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(summaries);
        } catch (Exception e) {
            logger.error("Error retrieving product summaries", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve product summaries", e);
        }
    }

    @Operation(summary = "Get products by category",
            description = "Retrieve products filtered by category")
    @Parameter(name = "category", description = "The category to filter by")
    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(@PathVariable String category) {
        try {
            List<ProductResponseDTO> products = productService.findByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving products by category: {}", category, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve products", e);
        }
    }

    @Operation(summary = "Get products by brand",
            description = "Retrieve products filtered by brand")
    @Parameter(name = "brand", description = "The brand to filter by")
    @GetMapping(value = "/brand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> getByBrand(@PathVariable String brand) {
        try {
            List<ProductResponseDTO> products = productService.findByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving products by brand: {}", brand, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve products", e);
        }
    }

    @Operation(summary = "Get products by product area",
            description = "Retrieve products filtered by product area")
    @Parameter(name = "productArea", description = "The product area to filter by")
    @GetMapping(value = "/area/{productArea}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> getByProductArea(@PathVariable String productArea) {
        try {
            List<ProductResponseDTO> products = productService.findByProductArea(productArea);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving products by product area: {}", productArea, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve products", e);
        }
    }

    @Operation(summary = "Get featured products",
            description = "Retrieve all featured products")
    @GetMapping(value = "/featured", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> getFeaturedProducts() {
        try {
            List<ProductResponseDTO> products = productService.findFeaturedProducts();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error retrieving featured products", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve featured products", e);
        }
    }
}
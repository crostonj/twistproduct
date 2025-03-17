package com.techtwist.web;

import com.azure.data.tables.models.TableEntity;
import com.techtwist.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public void createProduct(@RequestParam String partitionKey, @RequestParam String rowKey, @RequestBody Map<String, Object> properties) {
        productService.createEntity(partitionKey, rowKey, properties);
    }

    @GetMapping("/{partitionKey}/{rowKey}")
    public TableEntity getProduct(@PathVariable String partitionKey, @PathVariable String rowKey) {
        return productService.readEntity(partitionKey, rowKey);
    }

    @PutMapping("/{partitionKey}/{rowKey}")
    public void updateProduct(@PathVariable String partitionKey, @PathVariable String rowKey, @RequestBody Map<String, Object> properties) {
        productService.updateEntity(partitionKey, rowKey, properties);
    }

    @DeleteMapping("/{partitionKey}/{rowKey}")
    public void deleteProduct(@PathVariable String partitionKey, @PathVariable String rowKey) {
        productService.deleteEntity(partitionKey, rowKey);
    }
}

package com.techtwist.web;

import com.azure.data.tables.models.TableEntity;
import com.techtwist.services.ProductServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductServce productServce;

    @PostMapping
    public void createProduct(@RequestParam String partitionKey, @RequestParam String rowKey, @RequestBody Map<String, Object> properties) {
        productServce.createEntity(partitionKey, rowKey, properties);
    }

    @GetMapping("/{partitionKey}/{rowKey}")
    public TableEntity getProduct(@PathVariable String partitionKey, @PathVariable String rowKey) {
        return productServce.readEntity(partitionKey, rowKey);
    }

    @PutMapping("/{partitionKey}/{rowKey}")
    public void updateProduct(@PathVariable String partitionKey, @PathVariable String rowKey, @RequestBody Map<String, Object> properties) {
        productServce.updateEntity(partitionKey, rowKey, properties);
    }

    @DeleteMapping("/{partitionKey}/{rowKey}")
    public void deleteProduct(@PathVariable String partitionKey, @PathVariable String rowKey) {
        productServce.deleteEntity(partitionKey, rowKey);
    }
}

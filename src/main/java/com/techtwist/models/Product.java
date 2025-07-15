package com.techtwist.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Product entity representing a technology product in the TechTwist catalog
 */
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 100, message = "Brand must be between 2 and 100 characters")
    private String brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotEmpty(message = "At least one feature is required")
    private List<String> features;

    private Map<String, String> specifications;

    @NotBlank(message = "Category is required")
    private String category;

    @JsonProperty("categoryName")
    @Field("categoryName")
    private String categoryName;

    @NotBlank(message = "Product area is required")
    @JsonProperty("productArea")
    @Field("productArea")
    private String productArea;

    @JsonProperty("productAreaName")
    @Field("productAreaName")
    private String productAreaName;

    // Additional fields for inventory and business logic
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private String sku; // Stock Keeping Unit

    private String status; // ACTIVE, INACTIVE, DISCONTINUED

    private Boolean featured;

    private Double weight; // in kg

    private String dimensions; // e.g., "30x20x15 cm"

    private String warranty; // e.g., "2 years"

    private String manufacturer;

    private List<String> tags; // for search and categorization

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.featured = false;
        this.status = "ACTIVE";
    }

    public Product(String name, String brand, BigDecimal price, String description, String imageUrl) {
        this();
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
        this.updatedAt = LocalDateTime.now();
    }

    public Map<String, String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Map<String, String> specifications) {
        this.specifications = specifications;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public String getProductAreaName() {
        return productAreaName;
    }

    public void setProductAreaName(String productAreaName) {
        this.productAreaName = productAreaName;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
        this.updatedAt = LocalDateTime.now();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility methods
    public boolean isInStock() {
        return stockQuantity != null && stockQuantity > 0;
    }

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", productArea='" + productArea + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", status='" + status + '\'' +
                '}';
    }

    // equals and hashCode based on id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

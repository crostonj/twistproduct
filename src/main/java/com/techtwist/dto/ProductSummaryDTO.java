package com.techtwist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * DTO for product summaries in lists and search results
 */
public class ProductSummaryDTO {

    private String id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String imageUrl;
    private String category;
    private String categoryName;
    private Integer stockQuantity;
    private Boolean featured;
    private String status;

    // Computed fields
    @JsonProperty("inStock")
    public boolean isInStock() {
        return stockQuantity != null && stockQuantity > 0;
    }

    public ProductSummaryDTO() {}

    public ProductSummaryDTO(String id, String name, String brand, BigDecimal price, 
                           String imageUrl, String category, String categoryName, 
                           Integer stockQuantity, Boolean featured, String status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.categoryName = categoryName;
        this.stockQuantity = stockQuantity;
        this.featured = featured;
        this.status = status;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ProductSummaryDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", status='" + status + '\'' +
                '}';
    }
}

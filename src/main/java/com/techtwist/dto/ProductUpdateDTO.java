package com.techtwist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * DTO for updating existing products
 * All fields are optional for partial updates
 */
public class ProductUpdateDTO {

    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private List<String> features;
    private Map<String, String> specifications;
    private String category;

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("productArea")
    private String productArea;

    @JsonProperty("productAreaName")
    private String productAreaName;

    private Integer stockQuantity;
    private String sku;
    private String status;
    private Boolean featured;
    private Double weight;
    private String dimensions;
    private String warranty;
    private String manufacturer;
    private List<String> tags;

    // Constructors
    public ProductUpdateDTO() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    }

    public Map<String, String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Map<String, String> specifications) {
        this.specifications = specifications;
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
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
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
    }

    @Override
    public String toString() {
        return "ProductUpdateDTO{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", features=" + features +
                ", specifications=" + specifications +
                ", category='" + category + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", productArea='" + productArea + '\'' +
                ", productAreaName='" + productAreaName + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", sku='" + sku + '\'' +
                ", status='" + status + '\'' +
                ", featured=" + featured +
                ", weight=" + weight +
                ", dimensions='" + dimensions + '\'' +
                ", warranty='" + warranty + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", tags=" + tags +
                '}';
    }
}
package com.techtwist.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product{

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("imageUrl")
    private String imageUrl;
    
    @JsonProperty("category")   
    private String category;

    @JsonProperty("brand")
    private String brand;
    
    @JsonProperty("partitionKey")
    private String partitionKey;
    
    @JsonProperty("rowKey")
    private String rowKey;
}

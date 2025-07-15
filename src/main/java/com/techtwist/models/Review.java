package com.techtwist.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Review entity representing a customer review for a product
 */
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    @NotBlank(message = "Product ID is required")
    @JsonProperty("productId")
    @Field("productId")
    private String productId;

    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters")
    @JsonProperty("userName")
    @Field("userName")
    private String userName;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Comment is required")
    @Size(min = 10, max = 2000, message = "Comment must be between 10 and 2000 characters")
    private String comment;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Verified status is required")
    private Boolean verified;

    @Min(value = 0, message = "Helpful count cannot be negative")
    private Integer helpful;

    @Size(max = 10, message = "Avatar must be at most 10 characters")
    private String avatar;

    // Additional fields for enhanced functionality
    private String userId; // Reference to actual user if available

    private String email; // For verification purposes

    private Boolean recommended; // Would recommend to others

    private String pros; // What they liked

    private String cons; // What they didn't like

    private String status; // PENDING, APPROVED, REJECTED, HIDDEN

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime moderatedAt;

    private String moderatedBy;

    // Constructors
    public Review() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.helpful = 0;
        this.verified = false;
        this.status = "PENDING";
    }

    public Review(String productId, String userName, Integer rating, String title, String comment) {
        this();
        this.productId = productId;
        this.userName = userName;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.date = LocalDate.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
        this.updatedAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getHelpful() {
        return helpful;
    }

    public void setHelpful(Integer helpful) {
        this.helpful = helpful;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getModeratedAt() {
        return moderatedAt;
    }

    public void setModeratedAt(LocalDateTime moderatedAt) {
        this.moderatedAt = moderatedAt;
    }

    public String getModeratedBy() {
        return moderatedBy;
    }

    public void setModeratedBy(String moderatedBy) {
        this.moderatedBy = moderatedBy;
    }

    // Utility methods
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    public boolean isPending() {
        return "PENDING".equals(status);
    }

    public void incrementHelpful() {
        this.helpful = (this.helpful == null) ? 1 : this.helpful + 1;
        this.updatedAt = LocalDateTime.now();
    }

    public void approve(String moderator) {
        this.status = "APPROVED";
        this.moderatedBy = moderator;
        this.moderatedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(String moderator) {
        this.status = "REJECTED";
        this.moderatedBy = moderator;
        this.moderatedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", userName='" + userName + '\'' +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", verified=" + verified +
                ", helpful=" + helpful +
                ", status='" + status + '\'' +
                '}';
    }

    // equals and hashCode based on id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id != null && id.equals(review.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

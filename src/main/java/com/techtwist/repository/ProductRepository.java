package com.techtwist.repository;

import com.techtwist.models.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity
 * Provides database operations for Product management
 * Only active when mongodb profile is enabled
 */
@Repository
@Profile("mongodb")
public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * Find products by category
     * @param category Category to filter by
     * @return List of products in the specified category
     */
    List<Product> findByCategoryAndStatus(String category, String status);

    /**
     * Find products by product area
     * @param productArea Product area to filter by
     * @return List of products in the specified product area
     */
    List<Product> findByProductAreaAndStatus(String productArea, String status);

    /**
     * Find products by category and product area
     * @param category Category to filter by
     * @param productArea Product area to filter by
     * @return List of products matching both criteria
     */
    List<Product> findByCategoryAndProductAreaAndStatus(String category, String productArea, String status);

    /**
     * Find products by brand
     * @param brand Brand to filter by
     * @return List of products from the specified brand
     */
    List<Product> findByBrandAndStatus(String brand, String status);

    /**
     * Find products within a price range
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of products within the price range
     */
    List<Product> findByPriceBetweenAndStatus(BigDecimal minPrice, BigDecimal maxPrice, String status);

    /**
     * Find featured products
     * @return List of featured products
     */
    List<Product> findByFeaturedTrueAndStatus(String status);

    /**
     * Find products in stock
     * @return List of products with stock quantity > 0
     */
    List<Product> findByStockQuantityGreaterThanAndStatus(Integer quantity, String status);

    /**
     * Search products by name or description (case-insensitive)
     * @param searchTerm Term to search for
     * @param pageable Pagination information
     * @return Page of products matching the search term
     */
    @Query("{ $and: [ " +
           "  { $or: [ " +
           "    { 'name': { $regex: ?0, $options: 'i' } }, " +
           "    { 'description': { $regex: ?0, $options: 'i' } }, " +
           "    { 'brand': { $regex: ?0, $options: 'i' } }, " +
           "    { 'tags': { $regex: ?0, $options: 'i' } } " +
           "  ] }, " +
           "  { 'status': ?1 } " +
           "] }")
    Page<Product> searchProducts(String searchTerm, String status, Pageable pageable);

    /**
     * Find products by SKU
     * @param sku SKU to search for
     * @return Optional Product with the specified SKU
     */
    Optional<Product> findBySku(String sku);

    /**
     * Find products by manufacturer
     * @param manufacturer Manufacturer to filter by
     * @return List of products from the specified manufacturer
     */
    List<Product> findByManufacturerAndStatus(String manufacturer, String status);

    /**
     * Find products by tags (case-insensitive)
     * @param tag Tag to search for
     * @return List of products containing the specified tag
     */
    @Query("{ 'tags': { $regex: ?0, $options: 'i' }, 'status': ?1 }")
    List<Product> findByTagsContainingIgnoreCase(String tag, String status);

    /**
     * Count products by category
     * @param category Category to count
     * @return Number of products in the category
     */
    long countByCategoryAndStatus(String category, String status);

    /**
     * Count products by product area
     * @param productArea Product area to count
     * @return Number of products in the product area
     */
    long countByProductAreaAndStatus(String productArea, String status);

    /**
     * Find all active products (status = "ACTIVE")
     * @return List of all active products
     */
    List<Product> findByStatus(String status);

    /**
     * Find products with pagination
     * @param pageable Pagination information
     * @return Page of products
     */
    Page<Product> findByStatus(String status, Pageable pageable);

    /**
     * Complex search with multiple filters
     * @param category Category filter (optional)
     * @param productArea Product area filter (optional)
     * @param minPrice Minimum price filter (optional)
     * @param maxPrice Maximum price filter (optional)
     * @param searchTerm Search term for name/description (optional)
     * @param status Product status
     * @param pageable Pagination information
     * @return Page of products matching the filters
     */
    @Query("{ $and: [ " +
           "  { $or: [ { 'category': { $exists: false } }, { 'category': ?0 } ] }, " +
           "  { $or: [ { 'productArea': { $exists: false } }, { 'productArea': ?1 } ] }, " +
           "  { $or: [ { 'price': { $exists: false } }, { 'price': { $gte: ?2 } } ] }, " +
           "  { $or: [ { 'price': { $exists: false } }, { 'price': { $lte: ?3 } } ] }, " +
           "  { $or: [ " +
           "    { 'name': { $regex: ?4, $options: 'i' } }, " +
           "    { 'description': { $regex: ?4, $options: 'i' } } " +
           "  ] }, " +
           "  { 'status': ?5 } " +
           "] }")
    Page<Product> findWithFilters(String category, String productArea, BigDecimal minPrice, 
                                 BigDecimal maxPrice, String searchTerm, String status, Pageable pageable);

    /**
     * Find products by name and status
     * @param name Product name to search for
     * @param status Product status
     * @return List of products with the specified name and status
     */
    List<Product> findByNameAndStatus(String name, String status);

    /**
     * Find products by featured status
     * @param featured Featured status
     * @param status Product status
     * @return List of products with the specified featured and status
     */
    List<Product> findByFeaturedAndStatus(boolean featured, String status);
}

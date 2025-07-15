package com.techtwist.services.interfaces;

import com.techtwist.dto.ProductDTO;
import com.techtwist.dto.ProductCreateDTO;
import com.techtwist.dto.ProductUpdateDTO;
import com.techtwist.dto.ProductResponseDTO;
import com.techtwist.dto.ProductSummaryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for product operations using DTOs
 */
public interface IProductService {

    // Create operations
    ProductResponseDTO create(ProductCreateDTO productCreateDTO);
    
    // Read operations
    Optional<ProductResponseDTO> findById(String id);
    ProductResponseDTO findByName(String name);
    List<ProductResponseDTO> findAll();
    List<ProductSummaryDTO> findAllSummary(); // New method for performance
    List<ProductResponseDTO> findByCategory(String category);
    List<ProductResponseDTO> findByBrand(String brand);
    List<ProductResponseDTO> findByProductArea(String productArea);
    List<ProductResponseDTO> findFeaturedProducts();
    
    // Update operations
    ProductResponseDTO update(String id, ProductUpdateDTO productUpdateDTO);
    
    // Delete operations
    void delete(String id);
    
    // Backward compatibility methods (keeping ProductDTO for existing code)
    ProductDTO createLegacy(ProductDTO productDTO);
    Optional<ProductDTO> findByIdLegacy(String id);
    ProductDTO updateLegacy(String id, ProductDTO productDTO);
}

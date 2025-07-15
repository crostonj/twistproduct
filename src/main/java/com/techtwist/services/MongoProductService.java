package com.techtwist.services;

import com.techtwist.dto.*;
import com.techtwist.mapper.ProductMapper;
import com.techtwist.models.Product;
import com.techtwist.repository.ProductRepository;
import com.techtwist.services.interfaces.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MongoDB-based implementation of IProductService
 * Active only when 'mongodb' profile is enabled
 */
@Service
@Profile("mongodb")
@Transactional
public class MongoProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public MongoProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDTO create(ProductCreateDTO createDTO) {
        Product product = productMapper.toEntity(createDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public Optional<ProductResponseDTO> findById(String id) {
        return productRepository.findById(id)
                .map(productMapper::toResponseDTO);
    }

    @Override
    public ProductResponseDTO findByName(String name) {
        // For now, find by searching all products
        return productRepository.findAll().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .map(productMapper::toResponseDTO)
                .orElse(null);
    }

    @Override
    public ProductResponseDTO update(String id, ProductUpdateDTO updateDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            productMapper.updateEntity(product, updateDTO);
            Product savedProduct = productRepository.save(product);
            return productMapper.toResponseDTO(savedProduct);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductSummaryDTO> findAllSummary() {
        return productRepository.findAll().stream()
                .map(productMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().equals(category))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByBrand(String brand) {
        return productRepository.findAll().stream()
                .filter(product -> product.getBrand().equals(brand))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByProductArea(String productArea) {
        return productRepository.findAll().stream()
                .filter(product -> product.getProductArea().equals(productArea))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findFeaturedProducts() {
        return productRepository.findAll().stream()
                .filter(product -> Boolean.TRUE.equals(product.getFeatured()))
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Legacy compatibility methods - simplified
    @Override
    public ProductDTO createLegacy(ProductDTO productDTO) {
        // Convert DTO to CreateDTO then to ResponseDTO then back to DTO
        return new ProductDTO(); // Simplified for now
    }

    @Override
    public Optional<ProductDTO> findByIdLegacy(String id) {
        return Optional.empty(); // Simplified for now
    }

    @Override
    public ProductDTO updateLegacy(String id, ProductDTO productDTO) {
        return new ProductDTO(); // Simplified for now
    }
}

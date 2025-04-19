package com.lekimthanh.thegioiso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lekimthanh.thegioiso.domain.Category;
import com.lekimthanh.thegioiso.domain.Product;
import com.lekimthanh.thegioiso.repository.CategoryRepository;
import com.lekimthanh.thegioiso.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product, Long categoryId) {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
        }
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Danh mục không tồn tại");
        }

        product.setCategory(category.get());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        return product.get();
    }

    public Product updateProduct(Long id, Product product, Long categoryId) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Danh mục không tồn tại");
        }

        Product updatedProduct = existingProduct.get();
        if (!updatedProduct.getName().equals(product.getName()) &&
                productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
        }

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setImage(product.getImage());
        updatedProduct.setDetailDesc(product.getDetailDesc());
        updatedProduct.setShortDesc(product.getShortDesc());
        updatedProduct.setQuantity(product.getQuantity());
        updatedProduct.setSold(product.getSold());
        updatedProduct.setFactory(product.getFactory());
        updatedProduct.setTarget(product.getTarget());
        updatedProduct.setCategory(category.get());

        return productRepository.save(updatedProduct);
    }

    // Delete
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        productRepository.deleteById(id);
    }

}

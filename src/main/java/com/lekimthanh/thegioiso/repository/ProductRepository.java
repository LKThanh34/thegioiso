package com.lekimthanh.thegioiso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lekimthanh.thegioiso.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}

package com.example.a3.service;

import com.example.a3.model.Product;
import com.example.a3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.getProductsByName(name);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProductsById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

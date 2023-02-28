package com.example.a3.service;

import com.example.a3.model.Product;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    public Product getProductByName(String name);

    public Product getProductById(Long id);

    public List<Product> getAllProducts();
}

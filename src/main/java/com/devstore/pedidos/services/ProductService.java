package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.Product;
import com.devstore.pedidos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        productRepository.deleteById(id);
    }
}

package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.Product;
import com.devstore.pedidos.exceptions.ResourceNotFoundException;
import com.devstore.pedidos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retorna todos os produtos cadastrados no sistema.
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Busca um produto pelo ID, lançando exceção caso não seja encontrado.
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));
    }

    /**
     * Salva um novo produto no banco de dados.
     */
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Deleta um produto garantindo que ele exista antes da exclusão.
     */
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado para exclusão.");
        }
        productRepository.deleteById(id);
    }
}

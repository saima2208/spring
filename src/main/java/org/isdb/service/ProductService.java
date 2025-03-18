package org.isdb.service;

import java.util.Optional;

import org.isdb.model.Product;
import org.isdb.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	private ProductRepository repository;

	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	public Product savedAll(Product product) {
		int save = repository.savedAll(product);
		return getProductById(save);
	}

	public Product getProductById(long id) {
		Optional<Product> byId = repository.findById(id);
		return byId.get();
	}

}

package org.isdb.controller;

import org.isdb.model.Product;
import org.isdb.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ("/product"))
public class ProductController {
	private ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@PostMapping
	public Product saveProduct(@RequestBody Product product) {
		Product saved = service.savedAll(product);
		return saved;
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") long id) {
		Product productById = service.getProductById(id);
		return productById;

	}

}

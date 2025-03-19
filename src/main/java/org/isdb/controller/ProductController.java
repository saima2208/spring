package org.isdb.controller;

import java.util.List;

import org.isdb.model.Product;
import org.isdb.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		Product savedProduct = service.saveProduct(product);
		return savedProduct;
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") int id) {
		Product productById = service.getProductById(id);
		return productById;
	}

	@GetMapping
	public List<Product> getAllProduct() {
		List<Product> allProduct = service.getAllProduct();
		return allProduct;
	}

	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
		Product updated = service.updateProduct(id, product);
		return updated;

	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") int id) {
		service.deleteById(id);
	}

	@GetMapping("/byName")
	public List<Product> getProductByName(@RequestParam String name) {
		List<Product> byName = service.getProductByName(name);
		return byName;

	}
}

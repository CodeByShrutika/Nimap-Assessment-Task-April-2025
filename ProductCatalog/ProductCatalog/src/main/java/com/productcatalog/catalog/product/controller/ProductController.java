package com.productcatalog.catalog.product.controller;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productcatalog.catalog.product.model.Product;
import com.productcatalog.catalog.product.service.ProductService;
import com.productcatalog.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "products")
public class ProductController {
//the default post is set to 8081

	@Autowired
	private ProductService productService;

	@GetMapping(produces = "application/json")
	public Page<Product> getListOfAllProducts( @PageableDefault(size = 3, page = 0) Pageable page) {
		Page<Product> list = productService.getListOfAllProducts(page);

		return productService.getListOfAllProducts(page);
	}

	@GetMapping(path = "/{productId}", produces = "application/json")
	public Product getProductById(@PathVariable Long productId) {
		try {
			return productService.getProductById(productId);
		} catch (ResourceNotFoundException e) {
			return null;
		}
	}

	@DeleteMapping(path = "/{productId}", produces = "application/json")
	public Status deleteProducts(@PathVariable Long productId) {
		return productService.deleteProduct(productId);
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public Status addProducts(@RequestBody Product product) {
		return productService.addProduct(product);
	}

	@PutMapping(path = "/{productId}", consumes = "application/json", produces = "application/json")
	public Status updateProducts(@PathVariable Long productId, @RequestBody Product product) {
		return productService.updateProduct(productId, product);
	}

	@GetMapping(path = "/categories/{categoryId}", consumes = "application/json", produces = "application/json")
	public List<Product> getProductsByCategoryId(@PathVariable Long categoryId) {
		return productService.getProductListByCategoryId(categoryId);
	}

	// getter setter
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}

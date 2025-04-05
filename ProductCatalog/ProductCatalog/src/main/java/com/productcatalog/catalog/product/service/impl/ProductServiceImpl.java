package com.productcatalog.catalog.product.service.impl;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.productcatalog.catalog.category.dao.CategoryRepository;
import com.productcatalog.catalog.category.model.Category;
import com.productcatalog.catalog.product.dao.ProductRepository;
import com.productcatalog.catalog.product.model.Product;
import com.productcatalog.catalog.product.service.ProductService;
import com.productcatalog.exception.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Page<Product> getListOfAllProducts(Pageable page) {
		Page<Product> productList = productRepository.findAll(page);
		return productList;
	}

	@Override
	public Product getProductById(Long productId) throws ResourceNotFoundException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			return product;
		} else {
			throw new ResourceNotFoundException("No record found for the particular product id");
		}
	}

	private String getCategoryById(Long categoryId) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isPresent()) {
			return optionalCategory.get().getCategoryName();
		} else {
			return null;
		}
	}

	@Override
	public Status deleteProduct(Long productId) {
		if (null != productId) {
			productRepository.deleteById(productId);
			return Response.Status.NO_CONTENT;
		} else {
			return Response.Status.BAD_REQUEST;
		}
	}

	@Override
	public Status addProduct(Product product) {
		if (null != product) {
			productRepository.save(product);
			return Response.Status.CREATED;
		} else {
			return Response.Status.BAD_REQUEST;
		}
	}

	@Override
	public Status updateProduct(Long productId, Product updatedProduct) {

		Optional<Product> productOptional = productRepository.findById(productId);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setId(productId);
			product.setProductName(updatedProduct.getProductName());
			product.setProductCost(updatedProduct.getProductCost());
			product.setCategory(updatedProduct.getCategory());
			product.setDescription(updatedProduct.getDescription());

			productRepository.save(product);
			return Response.Status.OK;
		}

		return Response.Status.BAD_REQUEST;
	}

	@Override
	public List<Product> getProductListByCategoryId(Long categoryId) {
		List<Product> productList = productRepository.findByCategoryCategoryId(categoryId);
		return productList;
	}

	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

}

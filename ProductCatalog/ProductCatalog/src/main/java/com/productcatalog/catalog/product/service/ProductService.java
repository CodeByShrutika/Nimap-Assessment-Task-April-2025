package com.productcatalog.catalog.product.service;

import java.util.List;

import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.productcatalog.catalog.product.model.Product;
import com.productcatalog.exception.ResourceNotFoundException;

@Service
@Named("productServiceBean")
public interface ProductService {

	Page<Product> getListOfAllProducts(Pageable page);

	Product getProductById(Long productId) throws ResourceNotFoundException;

	Status deleteProduct(Long productId);

	Status addProduct(Product product);

	Status updateProduct(Long productId, Product product);

	List<Product> getProductListByCategoryId(Long categoryId);
}

package com.productcatalog.catalog.category.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.productcatalog.catalog.category.model.Category;
import com.productcatalog.exception.CustomException;

@Service
public interface CategoryService {

	Page<Category> getAllCategories(Pageable page);

	Status addCategory(Category category);

	Status deleteCategory(Long categoryId);

	Category getCategoryById(Long categoryId) throws CustomException;

	Status updateCategory(Long categoryId, Category category);
}

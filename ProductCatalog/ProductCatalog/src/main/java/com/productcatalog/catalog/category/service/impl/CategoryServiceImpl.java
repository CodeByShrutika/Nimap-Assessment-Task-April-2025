package com.productcatalog.catalog.category.service.impl;

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
import com.productcatalog.catalog.category.service.CategoryService;
import com.productcatalog.exception.CustomException;
import com.productcatalog.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Page<Category> getAllCategories(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Status addCategory(Category category) {
		if (null != category) {
			categoryRepository.save(category);
			return Response.Status.CREATED;
		} else {
			return Response.Status.BAD_REQUEST;
		}
	}

	@Override
	public Status deleteCategory(Long categoryId) {
		if (null != categoryId) {
			try {
				Category cate = categoryRepository.findById(categoryId).orElse(null);
				categoryRepository.delete(cate);

			//	categoryRepository.deleteById(categoryId);
				return Response.Status.NO_CONTENT;
			} catch (IllegalArgumentException e) {
				return Response.Status.NOT_FOUND;
			}
		} else {
			return Response.Status.BAD_REQUEST;
		}
	}

	@Override
	public Category getCategoryById(Long categoryId) throws CustomException {
		Optional<Category> category = null;
		if (null != categoryId) {
			category = categoryRepository.findById(categoryId);
			if (category.isPresent())
				return category.get();
		} else {
			throw new ResourceNotFoundException("Requested record not found!");
		}
		return null;
	}

	@Override
	public Status updateCategory(Long categoryId, Category updatedCategory) {

		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		if (categoryOptional.isPresent()) {
			Category category1 = categoryOptional.get();
			category1.setCategoryName(updatedCategory.getCategoryName());
			category1.setDescription(updatedCategory.getDescription());
			categoryRepository.save(category1);
			return Response.Status.OK;

		}
		return Response.Status.BAD_REQUEST;
	}

	// getter setter
	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

}

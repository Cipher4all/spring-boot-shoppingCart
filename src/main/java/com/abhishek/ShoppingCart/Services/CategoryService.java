package com.abhishek.ShoppingCart.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.Category;
import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	public Category createCategory(Category category) {
		Category category2 = new Category(category.getCategoryName(), category.getImageURL(), category.getDescription());
		 List<Product> products = new ArrayList<>();
		 for(Product product: category.getProducts()) {
			 Product product1 = new Product(product.getName(), product.getPrice(), product.getDescription(), product.getImageData());
			 product1.setCategory(category2);
			 products.add(product1);
		 }
		 category2.setProducts(products);
		return categoryRepository.save(category2);
	}
	
	public Category readCategory(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}
	
	public Optional<Category> findById(Integer Id){
		return categoryRepository.findById(Id);
	}
	
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}
	
	public void updateCategory(Integer categoryId, Category newCategory) {
		Category category = categoryRepository.findById(categoryId).get();
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getCategoryName());
		category.setImageURL(newCategory.getImageURL());
		category.setProducts(newCategory.getProducts());
		
		categoryRepository.save(category);	
	}
}

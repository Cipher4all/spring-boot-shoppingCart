package com.abhishek.ShoppingCart.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.ShoppingCart.Model.Category;
import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Services.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public ResponseEntity<List<Category>> getCategories(){
		List<Category> body = categoryService.getAllCategories();
		return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createCategory(@RequestBody Category category){
		 if(categoryService.readCategory(category.getCategoryName()) != null){
			 return new ResponseEntity<>("category already exists", HttpStatus.CONFLICT);
		 }
		 categoryService.createCategory(category);
		 return new ResponseEntity<>("category created.", HttpStatus.CREATED);
	}

	@PostMapping("/update/{categoryId}")
	public ResponseEntity<?> updateCategory(@PathVariable Integer categoryId, @Valid @RequestBody Category category){
		Optional<Category> optional = categoryService.findById(categoryId);
		if(optional.isPresent()) {
			categoryService.updateCategory(categoryId, category);
			return new ResponseEntity<>("Category has been updated", HttpStatus.OK);
		}
		return new ResponseEntity<>("Category does not exist", HttpStatus.NOT_FOUND);
	}
}

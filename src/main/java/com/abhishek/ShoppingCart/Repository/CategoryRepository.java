package com.abhishek.ShoppingCart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findByCategoryName(String categoryName);
}

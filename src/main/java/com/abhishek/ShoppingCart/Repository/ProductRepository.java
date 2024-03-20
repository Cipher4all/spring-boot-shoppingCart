package com.abhishek.ShoppingCart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}

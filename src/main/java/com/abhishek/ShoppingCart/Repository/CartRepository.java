package com.abhishek.ShoppingCart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.Cart;
import com.abhishek.ShoppingCart.Model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
	List<Cart> deleteByUser(User user);
}

package com.abhishek.ShoppingCart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.Order;
import com.abhishek.ShoppingCart.Model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}

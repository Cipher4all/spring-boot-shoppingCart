package com.abhishek.ShoppingCart.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.OrderItem;
import com.abhishek.ShoppingCart.Repository.OrderItemRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public void addOrderProducts(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}
}

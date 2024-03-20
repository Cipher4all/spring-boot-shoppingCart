package com.abhishek.ShoppingCart.dto.order;

import javax.validation.constraints.NotNull;

import com.abhishek.ShoppingCart.Model.Order;

public class OrderDto {

	private Integer id;
	private @NotNull Integer userId;
	
	public OrderDto(Order order) {
		this.setId(order.getId());
	}
	
	public OrderDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
}

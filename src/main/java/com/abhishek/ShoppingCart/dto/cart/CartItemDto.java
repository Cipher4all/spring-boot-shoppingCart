package com.abhishek.ShoppingCart.dto.cart;

import javax.validation.constraints.NotNull;

import com.abhishek.ShoppingCart.Model.Cart;
import com.abhishek.ShoppingCart.Model.Product;

public class CartItemDto {

	private Integer id;
    private @NotNull Integer quantity;
    private @NotNull Product product;
    
    public CartItemDto() {
		// TODO Auto-generated constructor stub
	}
    
    public CartItemDto(Cart cart) {
    	this.setId(cart.getId());
    	this.setProduct(cart.getProduct());
    	this.setQuantity(cart.getQuantity());
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
    
    
}

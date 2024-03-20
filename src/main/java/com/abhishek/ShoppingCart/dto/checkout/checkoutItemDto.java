package com.abhishek.ShoppingCart.dto.checkout;

public class checkoutItemDto {

	private String productName;
	private int quantity;
	private double price;
	private Long productId;
	private Long userId;
	
	public checkoutItemDto() {}
	
	public checkoutItemDto(String productName, int quantity, double price, Long productId, Long userId) {
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.productId = productId;
		this.userId = userId;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}

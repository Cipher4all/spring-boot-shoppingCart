package com.abhishek.ShoppingCart.dto.Product;

import javax.validation.constraints.NotNull;

import com.abhishek.ShoppingCart.Model.Product;

public class ProductDto {

	private int id;
	private String name;
    private byte[] image;
    private double price;
    private String description;
    private int categoryId;
    
    
    public ProductDto(Product product) {
    	this.setId(product.getId());
    	this.setName(product.getName());
    	this.setDescription(product.getDescription());
    	this.setPrice(product.getPrice());
    	this.setImage(product.getImageData());
    	this.setCategoryId(product.getCategory().getId());
    }
	
    public ProductDto(String name, byte[] imageURL, double price, String description, Integer categoryId) {
        this.name = name;
        this.image = imageURL;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }
	public ProductDto() {
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

    
    
}

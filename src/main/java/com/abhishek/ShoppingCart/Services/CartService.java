package com.abhishek.ShoppingCart.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.Cart;
import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Repository.CartRepository;
import com.abhishek.ShoppingCart.dto.cart.AddtoCartDto;
import com.abhishek.ShoppingCart.dto.cart.CartDto;
import com.abhishek.ShoppingCart.dto.cart.CartItemDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {

	private final CartRepository cartRepository;

	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	public void addtoCart(AddtoCartDto addtoCartDto, Product product, User user) {
		Cart cart = new Cart(product, addtoCartDto.getQuantity(), user);
		cartRepository.save(cart);
	}
	
	public void deleteCartItem(Integer id, Integer userId) throws Exception {
		if(!cartRepository.existsById(id)) {
			throw new Exception("Cart id is invalid : " + id);
		}
		cartRepository.deleteById(id);
	}
	
	public CartDto listCartItems(User user) {
		List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
		List<CartItemDto> cartItems = new ArrayList<>();
		for(Cart cart:cartList) {
			CartItemDto cartItemDto = getDtofromcart(cart);
			cartItems.add(cartItemDto);
		}
		
		double totalCost = 0;
		for(CartItemDto cartItemDto: cartItems) {
			totalCost += (cartItemDto.getProduct().getPrice())*cartItemDto.getQuantity();
		}
		
		return new CartDto(cartItems, totalCost);
	}
	public void updateCartItem(AddtoCartDto addtoCartDto, Product product) {
		@SuppressWarnings("deprecation")
		Cart cart = cartRepository.getOne(addtoCartDto.getId());
		cart.setQuantity(addtoCartDto.getQuantity());
		cart.setCreatedDate(new Date());
		cartRepository.save(cart);
	}
	public static CartItemDto getDtofromcart(Cart cart) {
		return new CartItemDto(cart);
	}
	
	public void deleteCartItems(Long userId) {
		cartRepository.deleteAll();
	}
	
	public void deleteUserCartItems(User user) {
		cartRepository.deleteByUser(user);
	}
}

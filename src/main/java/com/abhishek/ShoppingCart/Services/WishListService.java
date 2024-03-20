package com.abhishek.ShoppingCart.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.WishList;
import com.abhishek.ShoppingCart.Repository.WishListRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WishListService {

	private final WishListRepository wishListRepository;

	public WishListService(WishListRepository wishListRepository) {
		this.wishListRepository = wishListRepository;
	}
	
	public void createWishList(WishList wishList) {
		wishListRepository.save(wishList);
	}
	
	public List<WishList> readWishList(Integer userId){
		return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
	}
	
}

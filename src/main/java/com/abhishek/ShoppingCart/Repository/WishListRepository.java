package com.abhishek.ShoppingCart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer>{

	List<WishList> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}

package com.abhishek.ShoppingCart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByEmail(String email);
	
	List<User> findAll();

    User findByEmail(String email);
}

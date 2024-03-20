package com.abhishek.ShoppingCart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.ShoppingCart.Model.AuthenticationToken;
import com.abhishek.ShoppingCart.Model.User;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer>{

	AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}

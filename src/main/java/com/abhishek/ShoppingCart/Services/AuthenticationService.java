package com.abhishek.ShoppingCart.Services;

import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.AuthenticationToken;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Repository.TokenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {

	private final TokenRepository tokenRepository;

	public AuthenticationService(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	public void saveConfirmationToken(AuthenticationToken authenticationToken) {
		tokenRepository.save(authenticationToken);
	}
	
	public AuthenticationToken getToken(User user) {
		return tokenRepository.findTokenByUser(user);
	}
	
	public User getUser(String token) {
		AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
		if(authenticationToken != null) {
			if(authenticationToken.getUser() != null) {
				return authenticationToken.getUser();
			}
		}
		return null;
	}
	
	public void authenticate(String token) throws Exception {
		if (token == null) {
            throw new Exception("Authentication token not present");
        }
        if (getUser(token) == null) {
            throw new Exception("Authentication token not valid");
        }
	}
}

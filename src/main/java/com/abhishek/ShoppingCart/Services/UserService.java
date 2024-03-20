package com.abhishek.ShoppingCart.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.AuthenticationToken;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Repository.UserRepository;
import com.abhishek.ShoppingCart.dto.user.ResponseDto;
import com.abhishek.ShoppingCart.dto.user.SignInDto;
import com.abhishek.ShoppingCart.dto.user.SignInResponseDto;
import com.abhishek.ShoppingCart.dto.user.SignupDto;
import com.abhishek.ShoppingCart.dto.user.UserCreateDto;
import com.abhishek.ShoppingCart.enums.ResponseStatus;
import com.abhishek.ShoppingCart.enums.Role;

import jakarta.xml.bind.DatatypeConverter;


@Service
public class UserService {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public ResponseDto signUp(SignupDto signupDto) throws Exception{
		if(userRepository.findByEmail(signupDto.getEmail()) !=  null) {
			throw new Exception("User already exists");
		}
		
		String encryptedPassword = signupDto.getPassword();
		
		try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }
		
		User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Role.USER, encryptedPassword);
		
		User createUser;
		
		try {
            // save the User
			createUser = userRepository.save(user);
            // generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(createUser);
            // save token in database
            authenticationService.saveConfirmationToken(authenticationToken);
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), "User created");
        } catch (Exception e) {
            // handle signup error
            throw new Exception(e.getMessage());
        }
	}

	 String hashPassword(String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String myhash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myhash;
	}
	
	public SignInResponseDto signIn(SignInDto signInDto) throws Exception{
		User user = userRepository.findByEmail(signInDto.getEmail());
		if(user == null) {
			throw  new Exception("user not present");
		}
		
		try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                // passowrd doesnot match
                throw new Exception("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
		
		AuthenticationToken token = authenticationService.getToken(user);
		if(token == null) {
            // token not present
            throw new Exception("token not present");
        }

        return new SignInResponseDto ("success", token.getToken());
	}
	
	boolean canCrudUser(Role role) {
        if (role == Role.ADMIN || role == Role.MANAGER) {
            return true;
        }
        return false;
    }
	
	boolean canCrudUser(User userUpdating, Integer userIdBeingUpdated) {
        Role role = userUpdating.getRole();
        // admin and manager can crud any user
        if (role == Role.ADMIN || role == Role.MANAGER) {
            return true;
        }
        // user can update his own record, but not his role
        if (role == Role.USER && userUpdating.getId() == userIdBeingUpdated) {
            return true;
        }
        return false;
    }
	
	public ResponseDto createUser(String token, UserCreateDto userCreateDto) throws Exception {
        User creatingUser = authenticationService.getUser(token);
        if (!canCrudUser(creatingUser.getRole())) {
            // user can't create new user
            throw  new Exception("User not permitted");
        }
        String encryptedPassword = userCreateDto.getPassword();
        try {
            encryptedPassword = hashPassword(userCreateDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User user = new User(userCreateDto.getFirstName(), userCreateDto.getLastName(), userCreateDto.getEmail(), userCreateDto.getRole(), encryptedPassword );
        User createdUser;
        try {
            createdUser = userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new ResponseDto(ResponseStatus.success.toString(), "User created");
        } catch (Exception e) {
            // handle user creation fail error
            throw new Exception(e.getMessage());
        }

    }
}

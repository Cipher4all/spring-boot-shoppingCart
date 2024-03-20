package com.abhishek.ShoppingCart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Repository.UserRepository;
import com.abhishek.ShoppingCart.Services.AuthenticationService;
import com.abhishek.ShoppingCart.Services.UserService;
import com.abhishek.ShoppingCart.dto.user.ResponseDto;
import com.abhishek.ShoppingCart.dto.user.SignInDto;
import com.abhishek.ShoppingCart.dto.user.SignInResponseDto;
import com.abhishek.ShoppingCart.dto.user.SignupDto;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

	public UserController(UserRepository userRepository, AuthenticationService authenticationService,
			UserService userService) {
		this.userRepository = userRepository;
		this.authenticationService = authenticationService;
		this.userService = userService;
	}
	
	@GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }
    
	@PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignupDto signupDto) throws Exception {
        return userService.signUp(signupDto);
    }
	
	@PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws Exception {
        return userService.signIn(signInDto);
    }
}

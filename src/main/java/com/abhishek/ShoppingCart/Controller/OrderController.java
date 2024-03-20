package com.abhishek.ShoppingCart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Services.AuthenticationService;
import com.abhishek.ShoppingCart.Services.OrderService;
import com.abhishek.ShoppingCart.dto.checkout.StripeResponse;
import com.abhishek.ShoppingCart.dto.checkout.checkoutItemDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private OrderService orderService;

	public OrderController(AuthenticationService authenticationService, OrderService orderService) {
		this.authenticationService = authenticationService;
		this.orderService = orderService;
	}
	
	@PostMapping("/create-checkout-session")
	public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<checkoutItemDto> checkoutItemDtoList) throws StripeException{
		Session session = orderService.createSession(checkoutItemDtoList);
		StripeResponse stripeResponse = new StripeResponse(session.getId());
		return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
	}
	
	@PostMapping("/add")
    public ResponseEntity<?> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws Exception {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>( "Order has been placed", HttpStatus.CREATED);
    }
}

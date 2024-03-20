package com.abhishek.ShoppingCart.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Model.WishList;
import com.abhishek.ShoppingCart.Services.AuthenticationService;
import com.abhishek.ShoppingCart.Services.ProductService;
import com.abhishek.ShoppingCart.Services.WishListService;
import com.abhishek.ShoppingCart.dto.Product.ProductDto;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

	@Autowired
    private WishListService wishListService;

    @Autowired
    private AuthenticationService authenticationService;
    
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token){
    	Integer user_id = authenticationService.getUser(token).getId();
    	List<WishList> body = wishListService.readWishList(user_id);
    	List<ProductDto> products = new ArrayList<ProductDto>();
    	for(WishList wishList: body) {
    		products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
    	}
    	
    	return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> addWishList(@RequestBody Product product, @RequestParam("token") String token) throws Exception {
            authenticationService.authenticate(token);
            User user = authenticationService.getUser(token);
            WishList wishList = new WishList(user, product);
            wishListService.createWishList(wishList);
            return new ResponseEntity<>("Add to wishlist", HttpStatus.CREATED);

    }
}

package com.abhishek.ShoppingCart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Services.AuthenticationService;
import com.abhishek.ShoppingCart.Services.CartService;
import com.abhishek.ShoppingCart.Services.ProductService;
import com.abhishek.ShoppingCart.dto.cart.AddtoCartDto;
import com.abhishek.ShoppingCart.dto.cart.CartDto;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@ModelAttribute AddtoCartDto addToCartDto,
                                                 @RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());
        System.out.println("product to add"+  product.getName());
        cartService.addtoCart(addToCartDto, product, user);
        return new ResponseEntity<>("Added to cart", HttpStatus.CREATED);

    }
    @GetMapping("/")
    public String getCartItems(Model model, @RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        model.addAttribute("cartlist",cartDto);
        return "cart";
    }
    
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Integer itemID,@RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);
        Integer userId = authenticationService.getUser(token).getId();
        cartService.deleteCartItem(itemID, userId);
        return new ResponseEntity<>("Item has been removed", HttpStatus.OK);
    }
}

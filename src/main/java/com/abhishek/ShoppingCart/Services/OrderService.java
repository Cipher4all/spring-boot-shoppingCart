package com.abhishek.ShoppingCart.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abhishek.ShoppingCart.Model.Order;
import com.abhishek.ShoppingCart.Model.OrderItem;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Repository.OrderItemRepository;
import com.abhishek.ShoppingCart.Repository.OrderRepository;
import com.abhishek.ShoppingCart.dto.cart.CartDto;
import com.abhishek.ShoppingCart.dto.cart.CartItemDto;
import com.abhishek.ShoppingCart.dto.checkout.checkoutItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.Mode;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

	private final CartService cartService;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	
	public OrderService(CartService cartService, OrderRepository orderRepository,
			OrderItemRepository orderItemRepository) {
		this.cartService = cartService;
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
	}
	
	@Value("${BASE_URL}")
	private String baseURL;
	
	@Value("${STRIPE_SECRET_KEY}")
	private String apiKey;
	
	public void placeOrder(User user, String sessionId) {
		CartDto cartDto = cartService.listCartItems(user);
		
		List<CartItemDto> cartItemDtos = cartDto.getCartItems();
		Order newOrder = new Order();
		newOrder.setCreatedDate(new Date());
		newOrder.setSessionId(sessionId);
		newOrder.setUser(user);
		newOrder.setTotalPrice(cartDto.getTotalCost());
		orderRepository.save(newOrder);
		
		for(CartItemDto cartItemDto: cartItemDtos) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCreatedDate(new Date());
			orderItem.setProduct(cartItemDto.getProduct());
			orderItem.setQuantity(cartItemDto.getQuantity());
			orderItem.setPrice(cartItemDto.getProduct().getPrice());
			orderItem.setOrder(newOrder);
			orderItemRepository.save(orderItem);
		}
		cartService.deleteUserCartItems(user);
	}
	public List<Order> orderList(User user){
		return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
	}
	
	public Order getOrder(Long orderId) throws Exception{
		Optional<Order> order = orderRepository.findById(orderId);
		if(!order.isPresent()) {
			throw new Exception("Order not found");
		}
		return order.get();

	}

	public Session createSession(List<checkoutItemDto> checkoutItemDtoList) throws StripeException {
		String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";
        
        Stripe.apiKey = apiKey;
        
        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();
        for(checkoutItemDto checkoutItemDto: checkoutItemDtoList) {
        	sessionItemsList.add(createSessionLineItem(checkoutItemDto));
        }
        
        SessionCreateParams params = SessionCreateParams.builder().
        		addPaymentMethodType(PaymentMethodType.CARD)
        		.setMode(Mode.PAYMENT)
        		.setCancelUrl(failedURL)
        		.addAllLineItem(sessionItemsList)
        		.setSuccessUrl(successURL)
        		.build();
        return Session.create(params);
	}
	private LineItem createSessionLineItem(checkoutItemDto checkoutItemDto) {
		return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                // set quantity for each product
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
	}
	private PriceData createPriceData(checkoutItemDto checkoutItemDto) {
		return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)(checkoutItemDto.getPrice()*100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()).build();
	}
	
}

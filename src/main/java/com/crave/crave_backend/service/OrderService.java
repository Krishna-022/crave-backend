package com.crave.crave_backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.crave.crave_backend.config.security.SecurityUtils;
import com.crave.crave_backend.constant.SuccessMessageConstants;
import com.crave.crave_backend.dto.out.MessageOutDto;
import com.crave.crave_backend.entity.Cart;
import com.crave.crave_backend.entity.CartItem;
import com.crave.crave_backend.entity.Order;
import com.crave.crave_backend.entity.OrderItem;
import com.crave.crave_backend.enums.OrderState;
import com.crave.crave_backend.repository.CartItemRepository;
import com.crave.crave_backend.repository.CartRepository;
import com.crave.crave_backend.repository.OrderItemRepository;
import com.crave.crave_backend.repository.OrderRepository;
import com.crave.crave_backend.validation.CartValidator;

@Component
public class OrderService {

	private final OrderRepository orderRepository;

	private final OrderItemRepository orderItemRepository;

	private final CartRepository cartRepository;

	private final CartItemRepository cartItemRepository;
	
	private final CartValidator cartValidator;

	private final Logger log = LoggerFactory.getLogger(OrderService.class);

	@Transactional
	public MessageOutDto placeOrder(Long cartId) {
		Optional<Cart> cartOptional = cartRepository.findByIdForUpdate(cartId);
		Long userId = SecurityUtils.getCurrentUserId();
		Cart cart = cartValidator.validateCartForOrder(cartOptional, cartId, userId);
		List<CartItem> cartItemsList = cartItemRepository.findByCartIdForUpdate(cartId);
		
		BigDecimal totalOrderPrice = BigDecimal.ZERO;
		for (int i = 0; i < cartItemsList.size(); i++) {
			BigDecimal unitPrice = cartItemsList.get(i).getUnitPrice();
			Integer quantity = cartItemsList.get(i).getQuantity();
			BigDecimal menuItemTotalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity.longValue()));
			totalOrderPrice = totalOrderPrice.add(menuItemTotalPrice);
		}
		Order order = new Order(userId, cart.getRestaurantId(), totalOrderPrice, OrderState.PREPARING);
		Long orderId = orderRepository.save(order).getId();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();

		for (int i = 0; i < cartItemsList.size(); i++) {
			CartItem cartItem = cartItemsList.get(i);
			orderItemList.add(new OrderItem(orderId, cartItem.getMenuItemId(), cartItem.getQuantity(), cartItem.getUnitPrice()));
		}
		orderItemRepository.saveAll(orderItemList);
		cartItemRepository.deleteByCartId(cartId);
		cartRepository.deleteById(cartId);

		log.info("event=Order placed successfully, orderId={}", orderId);
		return new MessageOutDto(SuccessMessageConstants.ORDER_PLACED);
	}

	public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			CartRepository cartRepository, CartItemRepository cartItemRepository, CartValidator cartValidator) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.cartValidator = cartValidator;
	}
}

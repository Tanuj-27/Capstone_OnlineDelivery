package com.ey.service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ey.dto.request.CartItemAddRequest;
import com.ey.dto.request.CartItemUpdateRequest;
import com.ey.dto.response.CartItemResponse;
import com.ey.dto.response.CartResponse;
import com.ey.exception.ApiException;
import com.ey.mapper.CartMapper;
import com.ey.model.Cart;
import com.ey.model.CartItem;
import com.ey.model.MenuItem;
import com.ey.repository.CartItemRepository;
import com.ey.repository.CartRepository;
import com.ey.repository.MenuItemRepository;

@Service
public class CartService {
   private static final Logger logger = LoggerFactory.getLogger(CartService.class);
   
   @Autowired
   private CartRepository cartRepository;
   
   @Autowired
   private CartItemRepository cartItemRepository;
  
   @Autowired
   private MenuItemRepository menuItemRepository;
   
   public ResponseEntity<?> getCart(Long customerId) {
       logger.info("Get cart for customerId {}", customerId);
       
       Cart cart = cartRepository.findByCustomerIdAndActiveTrue(customerId)
               .orElseThrow(() -> new ApiException("Active cart not found"));
       
       List<CartItem> items = cartItemRepository.findByCartId(cart.getCartId());
       List<CartItemResponse> itemResponses = items.stream().map(item -> {
           MenuItem menuItem = menuItemRepository.findByMenuItemIdAndIsDeletedFalse(item.getMenuItemId())
                   .orElseThrow(() -> new ApiException("Menu item not found"));
           return CartMapper.toItemResponse(item, menuItem.getName());
       }).toList();
       
       CartResponse response = CartMapper.toCartResponse(cart, cart.getTotalAmount(), itemResponses);
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<?> addItem(CartItemAddRequest request) {
       logger.info("Add item to cart. customerId {}, menuItemId {}",
               request.getCustomerId(), request.getMenuItemId());       
       Cart cart = cartRepository.findByCustomerIdAndActiveTrue(request.getCustomerId())
               .orElseGet(() -> {
                   Cart newCart = new Cart();
                   newCart.setCustomerId(request.getCustomerId());
                   newCart.setActive(true);
                   newCart.setTotalAmount(0);
                   newCart.setCreatedAt(LocalDateTime.now());
                   newCart.setUpdatedAt(LocalDateTime.now());
                   return cartRepository.save(newCart);
               });
       
       MenuItem menuItem = menuItemRepository.findByMenuItemIdAndIsDeletedFalse(request.getMenuItemId())
               .orElseThrow(() -> new ApiException("Menu item not found"));
       
       CartItem item = new CartItem();
       item.setCartId(cart.getCartId());
       item.setMenuItemId(menuItem.getMenuItemId());
       item.setQuantity(request.getQuantity());
       item.setUnitPrice(menuItem.getPrice());
       item.setCreatedAt(LocalDateTime.now());
       item.setUpdatedAt(LocalDateTime.now());
       cartItemRepository.save(item);
       
       int total = cart.getTotalAmount() + (item.getQuantity() * item.getUnitPrice());
       cart.setTotalAmount(total);
       cart.setUpdatedAt(LocalDateTime.now());
       cartRepository.save(cart);
       
       Map<String, Object> response = new HashMap<>();
       response.put("cartId", cart.getCartId());
       response.put("message", "Item added");
       response.put("totalAmount", cart.getTotalAmount());
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<?> updateItem(CartItemUpdateRequest request) {
       logger.info("Update cart item {} for customer {}",
               request.getCartItemId(), request.getCustomerId());
       
       Cart cart = cartRepository.findByCustomerIdAndActiveTrue(request.getCustomerId())
               .orElseThrow(() -> new ApiException("Active cart not found"));
       CartItem item = cartItemRepository.findByCartItemIdAndCartId(request.getCartItemId(), cart.getCartId())
               .orElseThrow(() -> new ApiException("Cart item not found"));
       
       int oldTotal = item.getQuantity() * item.getUnitPrice();
       item.setQuantity(request.getQuantity());
       item.setUpdatedAt(LocalDateTime.now());
       cartItemRepository.save(item);
       
       int newTotal = request.getQuantity() * item.getUnitPrice();
       cart.setTotalAmount(cart.getTotalAmount() - oldTotal + newTotal);
       cart.setUpdatedAt(LocalDateTime.now());
       cartRepository.save(cart);
       
       Map<String, Object> response = new HashMap<>();
       response.put("cartId", cart.getCartId());
       response.put("message", "Item updated");
       response.put("totalAmount", cart.getTotalAmount());
       
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<?> removeItem(Long customerId, Long cartItemId) {
       logger.info("Remove cart item {} for customer {}", cartItemId, customerId);
       Cart cart = cartRepository.findByCustomerIdAndActiveTrue(customerId)
               .orElseThrow(() -> new ApiException("Active cart not found"));
       CartItem item = cartItemRepository.findByCartItemIdAndCartId(cartItemId, cart.getCartId())
               .orElseThrow(() -> new ApiException("Cart item not found"));
       
       int itemTotal = item.getQuantity() * item.getUnitPrice();
       cartItemRepository.delete(item);
       cart.setTotalAmount(cart.getTotalAmount() - itemTotal);
       cart.setUpdatedAt(LocalDateTime.now());
       cartRepository.save(cart);
       
       Map<String, Object> response = new HashMap<>();
       response.put("cartId", cart.getCartId());
       response.put("message", "Item removed");
       response.put("totalAmount", cart.getTotalAmount());
       
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<?> clearCart(Long customerId) {
       logger.info("Clear cart for customer {}", customerId);
       Cart cart = cartRepository.findByCustomerIdAndActiveTrue(customerId)
               .orElseThrow(() -> new ApiException("Active cart not found"));
       
       List<CartItem> items = cartItemRepository.findByCartId(cart.getCartId());
       cartItemRepository.deleteAll(items);
       cart.setTotalAmount(0);
       cart.setUpdatedAt(LocalDateTime.now());
       cartRepository.save(cart);
       
       Map<String, Object> response = new HashMap<>();
       response.put("message", "Cart cleared");
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
}
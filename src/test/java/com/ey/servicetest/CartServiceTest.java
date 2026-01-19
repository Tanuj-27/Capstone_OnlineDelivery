package com.ey.servicetest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import com.ey.dto.request.CartItemCreateRequest;
import com.ey.exception.ApiException;
import com.ey.model.Cart;
import com.ey.model.MenuItem;
import com.ey.repository.CartItemRepository;
import com.ey.repository.CartRepository;
import com.ey.repository.MenuItemRepository;
import com.ey.service.CartService;

@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean //fake repo that's we use mockbean
    private CartRepository cartRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @Test
    void addItemToCart_success() {
       CartItemCreateRequest request = new CartItemCreateRequest();
       request.setCustomerId(1L);
       request.setMenuItemId(10L);
       request.setQuantity(2);
       Cart cart = new Cart();
       cart.setCartId(1L);
       cart.setCustomerId(1L);
       cart.setActive(true);
       cart.setDeleted(false);
       MenuItem menuItem = new MenuItem();
       menuItem.setMenuItemId(10L);
       menuItem.setPrice(100);
       menuItem.setRestaurantId(1L);
       when(cartRepository.findByCustomerIdAndActiveTrueAndIsDeletedFalse(1L))
               .thenReturn(Optional.of(cart));
       when(menuItemRepository.findByMenuItemIdAndIsDeletedFalse(10L))
               .thenReturn(Optional.of(menuItem));
       when(cartItemRepository.findByCartIdAndIsDeletedFalse(1L))
               .thenReturn(List.of());
       ResponseEntity<?> response = cartService.addItemToCart(request);
       assertNotNull(response);
    }
    
    @Test
    void addItemToCart_failure_menuItemNotFound() {
        CartItemCreateRequest request = new CartItemCreateRequest();
        request.setCustomerId(1L);
        request.setMenuItemId(99L);
        request.setQuantity(1);
        when(cartRepository.findByCustomerIdAndActiveTrue(1L))
                .thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> {
            cartService.addItemToCart(request);
        });

    }

}
 
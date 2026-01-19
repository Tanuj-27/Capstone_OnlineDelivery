package com.ey.servicetest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.ey.exception.ApiException;
import com.ey.model.Restaurant;
import com.ey.repository.RestaurantRepository;
import com.ey.service.RestaurantService;
@SpringBootTest
class RestaurantServiceTest {
   @Autowired
   private RestaurantService restaurantService;
   @MockBean
   private RestaurantRepository restaurantRepository;
   
   @Test
   void getRestaurantById_success() {
       Restaurant restaurant = new Restaurant();
       restaurant.setRestaurantId(1L);
       restaurant.setName("Spice Garden");
       restaurant.setDeleted(false);
       when(restaurantRepository.findByRestaurantIdAndIsDeletedFalse(1L))
               .thenReturn(Optional.of(restaurant));
       Object response = restaurantService.getRestaurantById(1L);
       assertNotNull(response);
   }
   
   @Test
   void getRestaurantById_failure_notFound() {
       when(restaurantRepository.findByRestaurantIdAndIsDeletedFalse(1L))
               .thenReturn(Optional.empty());
       assertThrows(ApiException.class, () -> {
           restaurantService.getRestaurantById(1L);
       });
   }
}
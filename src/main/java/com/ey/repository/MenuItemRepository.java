package com.ey.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ey.model.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
   List<MenuItem> findByRestaurantIdAndIsDeletedFalse(Long restaurantId);
   
   Optional<MenuItem> findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(Long menuItemId, Long restaurantId);
   
   List<MenuItem> findByRestaurantIdAndNameContainingIgnoreCaseAndIsDeletedFalse(
           Long restaurantId, String name);
   
   List<MenuItem> findByRestaurantIdAndPriceBetweenAndIsDeletedFalse(
           Long restaurantId, Integer min, Integer max);
   
   Optional<MenuItem> findByMenuItemIdAndIsDeletedFalse(Long menuItemId);
}
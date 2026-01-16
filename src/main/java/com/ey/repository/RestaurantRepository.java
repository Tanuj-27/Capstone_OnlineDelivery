package com.ey.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ey.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
   List<Restaurant> findByIsDeletedFalse();
   List<Restaurant> findByCityAndIsDeletedFalse(String city);
   List<Restaurant> findByOwnerIdAndIsDeletedFalse(Long ownerId);
   Optional<Restaurant> findByRestaurantIdAndIsDeletedFalse(Long restaurantId);
}
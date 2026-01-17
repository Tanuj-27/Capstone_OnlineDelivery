package com.ey.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ey.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
   List<Address> findByCustomerIdAndIsDeletedFalse(Long customerId);
   Optional<Address> findByAddressIdAndCustomerIdAndIsDeletedFalse(
           Long addressId, Long customerId);
   Optional<Address> findByCustomerIdAndIsDefaultTrueAndIsDeletedFalse(Long customerId);
}
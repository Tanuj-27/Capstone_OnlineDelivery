package com.ey.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ey.dto.request.AddressCreateRequest;
import com.ey.dto.request.AddressUpdateRequest;
import com.ey.exception.ApiException;
import com.ey.model.Address;
import com.ey.repository.AddressRepository;
@Service
public class AddressService {
   private final AddressRepository addressRepository;
   public AddressService(AddressRepository addressRepository) {
       this.addressRepository = addressRepository;
   }
   public ResponseEntity<?> createAddress(AddressCreateRequest request) {
       Address address = new Address();
       address.setCustomerId(request.getCustomerId());
       address.setAddress(request.getAddress());
       address.setCity(request.getCity());
       address.setState(request.getState());
       address.setPincode(request.getPincode());
       address.setPhone(request.getPhone());
       address.setDefault(request.getIsDefault());
       address.setDeleted(false);
       address.setCreatedAt(LocalDateTime.now());
       address.setUpdatedAt(LocalDateTime.now());
       addressRepository.save(address);
       return ResponseEntity.ok("Address created");
   }
   public ResponseEntity<?> getAddressesByCustomerId(Long customerId) {
       List<Address> addresses = addressRepository.findByCustomerIdAndIsDeletedFalse(customerId);
       return ResponseEntity.ok(addresses);
   }
   public ResponseEntity<?> getAddressById(Long addressId) {
       Address address = addressRepository.findByAddressIdAndIsDeletedFalse(addressId)
               .orElseThrow(() -> new ApiException("Address not found"));
       return ResponseEntity.ok(address);
   }
   public ResponseEntity<?> updateAddress(AddressUpdateRequest request) {
       Address address = addressRepository.findByAddressIdAndIsDeletedFalse(request.getAddressId())
               .orElseThrow(() -> new ApiException("Address not found"));
       if (request.getAddress() != null) address.setAddress(request.getAddress());
       if (request.getCity() != null) address.setCity(request.getCity());
       if (request.getState() != null) address.setState(request.getState());
       if (request.getPincode() != null) address.setPincode(request.getPincode());
       if (request.getPhone() != null) address.setPhone(request.getPhone());
       if (request.getIsDefault() != null) address.setDefault(request.getIsDefault());
       address.setUpdatedAt(LocalDateTime.now());
       addressRepository.save(address);
       return ResponseEntity.ok("Address updated");
   }
   public ResponseEntity<?> deleteAddress(Long addressId) {
       Address address = addressRepository.findByAddressIdAndIsDeletedFalse(addressId)
               .orElseThrow(() -> new ApiException("Address not found"));
       address.setDeleted(true);
       address.setUpdatedAt(LocalDateTime.now());
       addressRepository.save(address);
       return ResponseEntity.ok("Address deleted");
   }
   public ResponseEntity<?> setDefaultAddress(Long customerId, Long addressId) {
       List<Address> addresses = addressRepository.findByCustomerIdAndIsDeletedFalse(customerId);
       if (addresses.isEmpty()) {
           throw new ApiException("No addresses found for customer");
       }
       for (Address a : addresses) {
           a.setDefault(a.getAddressId().equals(addressId));
           a.setUpdatedAt(LocalDateTime.now());
           addressRepository.save(a);
       }
       return ResponseEntity.ok("Default address set");
   }
}
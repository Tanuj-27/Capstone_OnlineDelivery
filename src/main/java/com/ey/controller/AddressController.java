package com.ey.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.AddressCreateRequest;
import com.ey.dto.request.AddressUpdateRequest;
import com.ey.service.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AddressController {
   @Autowired
   private AddressService addressService;
  
   @GetMapping("/customers/{customerId}/addresses")
   public ResponseEntity<?> getAddresses(@PathVariable Long customerId) {
       return addressService.getAddresses(customerId);
   }
   
   @PostMapping("/addresses")
   public ResponseEntity<?> createAddress(@Valid @RequestBody AddressCreateRequest request) {
       return addressService.createAddress(request);
   }
   
   @PutMapping("/addresses")
   public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressUpdateRequest request) {
       return addressService.updateAddress(request);
   }
   
   @DeleteMapping("/customers/{customerId}/addresses/{addressId}")
   public ResponseEntity<?> deleteAddress(
           @PathVariable Long customerId,
           @PathVariable Long addressId) {
       return addressService.deleteAddress(customerId, addressId);
   }
   
   @PutMapping("/customers/{customerId}/addresses/{addressId}/default")
   public ResponseEntity<?> setDefaultAddress(
           @PathVariable Long customerId,
           @PathVariable Long addressId) {
       return addressService.setDefaultAddress(customerId, addressId);
   }
   
}
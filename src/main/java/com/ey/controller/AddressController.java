package com.ey.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.AddressCreateRequest;
import com.ey.dto.request.AddressUpdateRequest;
import com.ey.service.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
   private final AddressService addressService;
   public AddressController(AddressService addressService) {
       this.addressService = addressService;
   }
   @PostMapping
   public ResponseEntity<?> createAddress(@Valid @RequestBody AddressCreateRequest request) {
       return addressService.createAddress(request);
   }
   @GetMapping("/customer/{customerId}")
   public ResponseEntity<?> getAddressesByCustomer(@PathVariable Long customerId) {
       return addressService.getAddressesByCustomerId(customerId);
   }
   @GetMapping("/{addressId}")
   public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
       return addressService.getAddressById(addressId);
   }
   @PutMapping
   public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressUpdateRequest request) {
       return addressService.updateAddress(request);
   }
   @DeleteMapping("/{addressId}")
   public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
       return addressService.deleteAddress(addressId);
   }
   @PutMapping("/customer/{customerId}/default/{addressId}")
   public ResponseEntity<?> setDefault(
           @PathVariable Long customerId,
           @PathVariable Long addressId) {
       return addressService.setDefaultAddress(customerId, addressId);
   }
}
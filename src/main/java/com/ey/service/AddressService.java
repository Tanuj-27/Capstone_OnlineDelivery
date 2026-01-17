package com.ey.service;

import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.ey.dto.request.AddressCreateRequest;

import com.ey.dto.request.AddressUpdateRequest;

import com.ey.dto.response.AddressResponse;

import com.ey.exception.ApiException;

import com.ey.mapper.AddressMapper;

import com.ey.model.Address;

import com.ey.repository.AddressRepository;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<?> getAddresses(Long customerId) {
        logger.info("Fetching addresses for customerId {}", customerId);

        List<AddressResponse> responses = addressRepository
                .findByCustomerIdAndIsDeletedFalse(customerId)
                .stream()
                .map(AddressMapper::toResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);

    }

    public ResponseEntity<?> createAddress(AddressCreateRequest request) {
        logger.info("Creating address for customerId {}", request.getCustomerId());

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository
                    .findByCustomerIdAndIsDefaultTrueAndIsDeletedFalse(request.getCustomerId())
                    .ifPresent(existing -> {
                        existing.setDefault(false);
                        existing.setUpdatedAt(LocalDateTime.now());
                        addressRepository.save(existing);
                    });
        }

        Address address = AddressMapper.toEntity(request);
        address.setDeleted(false);
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(address);

        return new ResponseEntity<>("Address created", HttpStatus.CREATED);

    }

    public ResponseEntity<?> updateAddress(AddressUpdateRequest request) {
        logger.info("Updating address {} for customerId {}", 
                request.getAddressId(), request.getCustomerId());

        Address address = addressRepository
                .findByAddressIdAndCustomerIdAndIsDeletedFalse(
                        request.getAddressId(), request.getCustomerId())
                .orElseThrow(() -> new ApiException("Address not found"));

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository
                    .findByCustomerIdAndIsDefaultTrueAndIsDeletedFalse(request.getCustomerId())
                    .ifPresent(existing -> {
                        existing.setDefault(false);
                        existing.setUpdatedAt(LocalDateTime.now());
                        addressRepository.save(existing);
                    });
        }

        AddressMapper.updateEntity(address, request);
        address.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(address);

        return new ResponseEntity<>("Address updated", HttpStatus.OK);

    }

    public ResponseEntity<?> deleteAddress(Long customerId, Long addressId) {
        logger.info("Soft deleting address {} for customerId {}", addressId, customerId);

        Address address = addressRepository
                .findByAddressIdAndCustomerIdAndIsDeletedFalse(addressId, customerId)
                .orElseThrow(() -> new ApiException("Address not found"));

        address.setDeleted(true);
        address.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(address);

        return new ResponseEntity<>("Address deleted", HttpStatus.OK);

    }

    public ResponseEntity<?> setDefaultAddress(Long customerId, Long addressId) {
        logger.info("Setting default address {} for customerId {}", addressId, customerId);

        Address address = addressRepository
                .findByAddressIdAndCustomerIdAndIsDeletedFalse(addressId, customerId)
                .orElseThrow(() -> new ApiException("Address not found"));
        
        addressRepository
                .findByCustomerIdAndIsDefaultTrueAndIsDeletedFalse(customerId)
                .ifPresent(existing -> {
                    existing.setDefault(false);
                    existing.setUpdatedAt(LocalDateTime.now());
                    addressRepository.save(existing);
                });

        address.setDefault(true);
        address.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(address);
        
        return new ResponseEntity<>("Default address set", HttpStatus.OK);

    }

}
 
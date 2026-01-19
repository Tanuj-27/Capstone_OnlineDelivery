package com.ey.mapper;
import com.ey.dto.request.AddressCreateRequest;
import com.ey.dto.request.AddressUpdateRequest;
import com.ey.dto.response.AddressResponse;
import com.ey.model.Address;
public class AddressMapper {
   
	public static Address toEntity(AddressCreateRequest request) {
       Address address = new Address();
       address.setCustomerId(request.getCustomerId());
       address.setAddress(request.getAddress());
       address.setCity(request.getCity());
       address.setState(request.getState());
       address.setPincode(request.getPincode());
       address.setPhone(request.getPhone());
       address.setDefault(request.getIsDefault());
       return address;
   }
	
   public static void updateEntity(Address address, AddressUpdateRequest request) {
       address.setAddress(request.getAddress());
       address.setCity(request.getCity());
       address.setState(request.getState());
       address.setPincode(request.getPincode());
       address.setPhone(request.getPhone());
       address.setDefault(request.getIsDefault());
   }
   
   public static AddressResponse toResponse(Address address) {
       AddressResponse response = new AddressResponse();
       response.setAddressId(address.getAddressId());
       response.setCustomerId(address.getCustomerId());
       response.setAddress(address.getAddress());
       response.setCity(address.getCity());
       response.setState(address.getState());
       response.setPincode(address.getPincode());
       response.setPhone(address.getPhone());
       response.setIsDefault(address.isDefault());
       return response;
   }
}
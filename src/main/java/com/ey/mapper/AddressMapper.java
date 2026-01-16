package com.ey.mapper;
import com.ey.dto.request.AddressCreateRequest;
import com.ey.dto.response.AddressResponse;
import com.ey.model.Address;

public class AddressMapper {
   public static Address toEntity(AddressCreateRequest request, Long customerId) {
	   
       Address address = new Address();
       address.setAddress(request.getAddress());
       address.setCity(request.getCity());
       address.setState(request.getState());
       address.setPincode(request.getPincode());
       address.setPhone(request.getPhone());
       address.setDefault(request.isDefault());
       address.setCustomerId(customerId);
       
       return address;
   }
   public static AddressResponse toResponse(Address address) {
	   
       AddressResponse response = new AddressResponse();
       response.setAddressId(address.getAddressId());
       response.setAddress(address.getAddress());
       response.setCity(address.getCity());
       response.setState(address.getState());
       response.setPincode(address.getPincode());
       response.setPhone(address.getPhone());
       response.setDefault(address.isDefault());
       
       return response;
   }
}
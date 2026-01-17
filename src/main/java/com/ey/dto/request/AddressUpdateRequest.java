package com.ey.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
public class AddressUpdateRequest {
   
	@NotNull(message = "AddressId is required")
   private Long addressId;
  
	@NotNull(message = "CustomerId is required")
   private Long customerId;
  
	@NotBlank(message = "Address is required")
   private String address;
   
	@NotBlank(message = "City is required")
   private String city;
   
	@NotBlank(message = "State is required")
   private String state;
   
	@NotBlank(message = "Pincode is required")
   @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
   private String pincode;
   
	@NotBlank(message = "Phone is required")
   @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone must be 10 digits")
   private String phone;
   private Boolean isDefault;
  
   public Long getAddressId() {
       return addressId;
   }
   public void setAddressId(Long addressId) {
       this.addressId = addressId;
   }
   public Long getCustomerId() {
       return customerId;
   }
   public void setCustomerId(Long customerId) {
       this.customerId = customerId;
   }
   public String getAddress() {
       return address;
   }
   public void setAddress(String address) {
       this.address = address;
   }
   public String getCity() {
       return city;
   }
   public void setCity(String city) {
       this.city = city;
   }
   public String getState() {
       return state;
   }
   public void setState(String state) {
       this.state = state;
   }
   public String getPincode() {
       return pincode;
   }
   public void setPincode(String pincode) {
       this.pincode = pincode;
   }
   public String getPhone() {
       return phone;
   }
   public void setPhone(String phone) {
       this.phone = phone;
   }
   public Boolean getIsDefault() {
       return isDefault;
   }
   public void setIsDefault(Boolean isDefault) {
       this.isDefault = isDefault;
   }
}
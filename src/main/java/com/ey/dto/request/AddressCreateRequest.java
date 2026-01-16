package com.ey.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class AddressCreateRequest {
	
   @NotEmpty(message = "Address cannot be empty")
   private String address;
   
   @NotEmpty(message = "City cannot be empty")
   private String city;
   
   @NotEmpty(message = "State cannot be empty")
   private String state;
   
   @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must have 6 digits")
   private String pincode;
   
   @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone must have 10 digits")
   private String phone;
   private boolean isDefault;
   
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
   public boolean isDefault() {
       return isDefault;
   }
   public void setDefault(boolean isDefault) {
       this.isDefault = isDefault;
   }
}
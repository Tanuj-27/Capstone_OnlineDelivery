package com.ey.dto.response;
public class AddressResponse {
   private Long addressId;
   private String address;
   private String city;
   private String state;
   private String pincode;
   private String phone;
   private boolean isDefault;
   public Long getAddressId() {
       return addressId;
   }
   public void setAddressId(Long addressId) {
       this.addressId = addressId;
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
   public boolean isDefault() {
       return isDefault;
   }
   public void setDefault(boolean isDefault) {
       this.isDefault = isDefault;
   }
}
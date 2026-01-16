package com.ey.exception;
public class ApiException extends RuntimeException {
   public ApiException(String message) {
       super(message);
   }
}
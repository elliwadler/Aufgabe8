/*
 * project management system
 * Spring-boot, Thymeleaf, MySQL
 * Author: Elisabeth Wadler
 * Last Change: 03.06.2022
 */

package com.example.aufgabe8_wadler.exception;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message,Throwable cause) {
        super(message, cause);
    }

    public ApiRequestException(String message) {
        super(message);
    }
}

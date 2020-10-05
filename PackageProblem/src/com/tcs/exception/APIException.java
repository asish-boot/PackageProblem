package com.tcs.exception;


/**
 * @author Asish
 * Purpose Custom Exception Class
 *
 */
public class APIException extends Exception {
 
    public APIException(String message) {
        super(message);
    }
}
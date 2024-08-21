package com.online.market.orderservice.exception;

public class OrderPlaceException extends Exception{

    public OrderPlaceException() {
        super("An error occurred while placing the order.");
    }

    public OrderPlaceException(String message) {
        super(message);
    }

    public OrderPlaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderPlaceException(Throwable cause) {
        super(cause);
    }

}

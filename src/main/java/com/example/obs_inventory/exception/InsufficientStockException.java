package com.example.obs_inventory.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String message) {
        super(message);
    }
}

package com.example.obs_inventory.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDTO implements Serializable {
    private Long id;
    private String orderNo;
    private Long itemId;
    private int qty;
    private int price;
}

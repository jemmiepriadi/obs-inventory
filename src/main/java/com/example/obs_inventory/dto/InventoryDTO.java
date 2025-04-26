package com.example.obs_inventory.dto;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryDTO {
    private Long id;
    private Long itemId;
    private int qty;
    private String type;
}

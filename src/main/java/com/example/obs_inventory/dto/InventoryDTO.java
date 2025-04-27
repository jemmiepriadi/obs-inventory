package com.example.obs_inventory.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryDTO {
    private Long id;
    private Long itemId;
    @Min(value = 0, message = "qty cannot be negative")
    private int qty;
    private String type;
}

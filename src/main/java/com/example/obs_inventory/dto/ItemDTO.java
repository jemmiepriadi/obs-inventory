package com.example.obs_inventory.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ItemDTO {
    private Long id;

    @NotNull(message = "Item name cannot be null")
    private String name;

    @Min(value = 0, message = "Price must be non-negative")
    private int price;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;
}
package com.example.obs_inventory.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String name;
    private int price;
    private int stock;
}
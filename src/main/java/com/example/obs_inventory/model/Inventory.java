package com.example.obs_inventory.model;


import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    private int qty;

    @Column(length = 1)
    private String type; // "T" for Top-Up, "W" for Withdrawal

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

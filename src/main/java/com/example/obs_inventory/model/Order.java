package com.example.obs_inventory.model;

import jakarta.persistence.*;

@Entity(name = "orders")
public class Order {
    @Id @GeneratedValue
    private Long id;

    private String orderNo;

    @ManyToOne(optional = false)
    private Item item;

    private int qty;
    private int price;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}

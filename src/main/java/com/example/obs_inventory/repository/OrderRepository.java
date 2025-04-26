package com.example.obs_inventory.repository;

import com.example.obs_inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByItemId(Long itemId);
}

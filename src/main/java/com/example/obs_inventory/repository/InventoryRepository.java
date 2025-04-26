package com.example.obs_inventory.repository;

import com.example.obs_inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByItemId(Long itemId);
}

package com.example.obs_inventory.service.services;

import com.example.obs_inventory.dto.InventoryDTO;
import com.example.obs_inventory.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    Page<InventoryDTO> getAllInventory(Pageable pageable);

    InventoryDTO getInventoryById(Long id);

    InventoryDTO saveInventory(InventoryDTO dto);

    void deleteInventory(Long id);

    List<InventoryDTO> findByItemId(Long itemId);
}

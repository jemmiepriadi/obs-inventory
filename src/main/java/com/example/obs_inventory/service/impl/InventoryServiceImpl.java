package com.example.obs_inventory.service.impl;

import com.example.obs_inventory.dto.InventoryDTO;
import com.example.obs_inventory.dto.ItemDTO;
import com.example.obs_inventory.exception.InsufficientStockException;
import com.example.obs_inventory.model.Inventory;
import com.example.obs_inventory.model.Item;
import com.example.obs_inventory.repository.InventoryRepository;
import com.example.obs_inventory.service.services.InventoryService;
import com.example.obs_inventory.service.services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepo;
    @Autowired
    @Lazy
    private ItemService itemService;

    @Override
    public Page<InventoryDTO> getAllInventory(Pageable pageable) {
        return inventoryRepo.findAll(pageable)
                .map(this::toDto);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        Inventory entity = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return toDto(entity);
    }

    @Override
    @Transactional
    public InventoryDTO saveInventory(InventoryDTO dto) {
        if (!"T".equals(dto.getType()) && !"W".equals(dto.getType())) {
            throw new IllegalArgumentException("Inventory type must be 'T' (Top-Up) or 'W' (Withdrawal)");
        }

        // Use ItemService to get the item
        ItemDTO itemDTO = itemService.getItemById(dto.getItemId());

        if (itemDTO == null) {
            log.error("error item not found");
            return null;
        }

        if ("T".equals(dto.getType())) {
            itemDTO.setStock(itemDTO.getStock() + dto.getQty());
        } else if ("W".equals(dto.getType())) {
            if (itemDTO.getStock() < dto.getQty()) {
                throw new InsufficientStockException("Insufficient stock for item: " + dto.getItemId());
            }
            itemDTO.setStock(itemDTO.getStock() - dto.getQty());
        }

        // Save the updated item
        itemService.saveItem(itemDTO);
        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        // Create and save Inventory
        Inventory entity = new Inventory();
        BeanUtils.copyProperties(dto, entity);
        entity.setItem(item);

        Inventory saved = inventoryRepo.save(entity);

        InventoryDTO result = new InventoryDTO();
        BeanUtils.copyProperties(saved, result);
        result.setItemId(saved.getItem().getId());
        return result;
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepo.deleteById(id);
    }

    @Override
    public List<InventoryDTO> findByItemId(Long itemId) {
        return inventoryRepo.findByItemId(itemId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private InventoryDTO toDto(Inventory entity) {
        InventoryDTO dto = new InventoryDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setItemId(entity.getItem().getId());
        return dto;
    }
}

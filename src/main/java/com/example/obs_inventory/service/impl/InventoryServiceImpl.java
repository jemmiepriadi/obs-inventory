package com.example.obs_inventory.service.impl;

import com.example.obs_inventory.dto.InventoryDTO;
import com.example.obs_inventory.model.Inventory;
import com.example.obs_inventory.model.Item;
import com.example.obs_inventory.repository.InventoryRepository;
import com.example.obs_inventory.service.services.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepo;

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
    public InventoryDTO saveInventory(InventoryDTO dto) {
        Inventory entity = new Inventory();
        BeanUtils.copyProperties(dto, entity);
        Item item = new Item();
        item.setId(dto.getItemId());
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

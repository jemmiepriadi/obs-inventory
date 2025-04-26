package com.example.obs_inventory.service.impl;

import com.example.obs_inventory.dto.InventoryDTO;
import com.example.obs_inventory.dto.ItemDTO;
import com.example.obs_inventory.dto.OrderDTO;
import com.example.obs_inventory.model.Item;
import com.example.obs_inventory.repository.ItemRepository;
import com.example.obs_inventory.service.services.InventoryService;
import com.example.obs_inventory.service.services.ItemService;
import com.example.obs_inventory.service.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;

    @Override
    public Page<ItemDTO> getAllItems(Pageable pageable) {
        return itemRepo.findAll(pageable)
                .map(this::toDto);
    }

    @Override
    public ItemDTO getItemById(Long id) {
        Item entity = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return toDto(entity);
    }

    @Override
    public ItemDTO saveItem(ItemDTO dto) {
        Item entity = new Item();
        BeanUtils.copyProperties(dto, entity);

        Item saved = itemRepo.save(entity);
        ItemDTO result = new ItemDTO();
        BeanUtils.copyProperties(saved, result);
        result.setStock(calculateStock(saved.getId()));
        return result;
    }

    @Override
    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }

    @Override
    public int calculateStock(Long itemId) {
        int topUp = inventoryService.findByItemId(itemId).stream()
                .filter(i -> i.getType().equals("T"))
                .mapToInt(InventoryDTO::getQty).sum();
        int withdraw = inventoryService.findByItemId(itemId).stream()
                .filter(i -> i.getType().equals("W"))
                .mapToInt(InventoryDTO::getQty).sum();
        int ordered = orderService.findByItemId(itemId).stream()
                .mapToInt(OrderDTO::getQty).sum();
        return topUp - withdraw - ordered;
    }

    private ItemDTO toDto(Item entity) {
        ItemDTO dto = new ItemDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setStock(calculateStock(entity.getId()));
        return dto;
    }
}

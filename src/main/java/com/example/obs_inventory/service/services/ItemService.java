package com.example.obs_inventory.service.services;

import com.example.obs_inventory.dto.ItemDTO;
import com.example.obs_inventory.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    Page<ItemDTO> getAllItems(Pageable pageable);

    ItemDTO getItemById(Long id);

    ItemDTO saveItem(ItemDTO dto);

    void deleteItem(Long id);

    int calculateStock(Long itemId);
}

package com.example.obs_inventory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.obs_inventory.dto.ItemDTO;
import com.example.obs_inventory.model.Item;
import com.example.obs_inventory.repository.ItemRepository;
import com.example.obs_inventory.service.services.InventoryService;
import com.example.obs_inventory.service.services.ItemService;
import com.example.obs_inventory.service.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ItemService itemService;

    private ItemDTO mockItemDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockItemDTO = new ItemDTO();
        mockItemDTO.setId(1L);
        mockItemDTO.setStock(100); // Initial stock
        mockItemDTO.setPrice(50);
        mockItemDTO.setName("Test Item");
    }

    @Test
    public void testSaveItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(new Item());

        ItemDTO result = itemService.saveItem(mockItemDTO);

        assertNotNull(result);
        assertEquals("Test Item", result.getName());
    }

    @Test
    public void testGetItemById() {
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(new Item()));

        ItemDTO result = itemService.getItemById(1L);

        assertNotNull(result);
    }

    @Test
    public void testGetAllItems() {
        Pageable pageable = PageRequest.of(0, 10);
        when(itemRepository.findAll(pageable)).thenReturn(new org.springframework.data.domain.PageImpl<>(Collections.singletonList(new Item())));

        Page<ItemDTO> itemPage = itemService.getAllItems(pageable);

        assertNotNull(itemPage);
        assertEquals(1, itemPage.getTotalElements());
    }
}

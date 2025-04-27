package com.example.obs_inventory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.obs_inventory.dto.InventoryDTO;
import com.example.obs_inventory.dto.ItemDTO;
import com.example.obs_inventory.dto.OrderDTO;
import com.example.obs_inventory.exception.InsufficientStockException;
import com.example.obs_inventory.model.Inventory;
import com.example.obs_inventory.model.Item;
import com.example.obs_inventory.repository.InventoryRepository;
import com.example.obs_inventory.repository.OrderRepository;
import com.example.obs_inventory.service.impl.InventoryServiceImpl;
import com.example.obs_inventory.service.services.InventoryService;
import com.example.obs_inventory.service.services.ItemService;
import com.example.obs_inventory.service.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private InventoryServiceImpl inventoryService;  // Concrete class

    private ItemDTO mockItemDTO;
    private InventoryDTO mockInventoryDTO;

    @BeforeEach
    public void setUp() {
        mockItemDTO = new ItemDTO();
        mockItemDTO.setId(1L);
        mockItemDTO.setStock(100); // Initial stock

        mockInventoryDTO = new InventoryDTO();
        mockInventoryDTO.setItemId(1L);
        mockInventoryDTO.setQty(10); // Quantity to be updated
        mockInventoryDTO.setType("T"); // Type "T" for Top-Up
    }

    @Test
    public void testSaveInventory_TopUp() {
        // Mock the item service to return the mock item
        when(itemService.getItemById(1L)).thenReturn(mockItemDTO);
        when(itemService.saveItem(any(ItemDTO.class))).thenReturn(mockItemDTO);

        // Call the service
        InventoryDTO result = inventoryService.saveInventory(mockInventoryDTO);

        // Verify stock update (100 + 10)
        assertNotNull(result);
        assertEquals(110, result.getQty()); // Verify quantity is updated correctly
        verify(itemService).saveItem(any(ItemDTO.class)); // Ensure item service save was called
    }

    @Test
    public void testSaveInventory_InsufficientStockWithdrawal() {
        // Set the inventory operation to Withdrawal ("W")
        mockInventoryDTO.setType("W");

        // Mock the item service to return the mock item with low stock
        mockItemDTO.setStock(5); // Only 5 stock available

        when(itemService.getItemById(1L)).thenReturn(mockItemDTO);

        // Call the service, expecting an exception
        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> {
            inventoryService.saveInventory(mockInventoryDTO);
        });

        assertTrue(exception.getMessage().contains("Insufficient stock for item"));
    }


    @Test
    public void testGetAllInventory() {
        Item mockItem = new Item();
        mockItem.setId(1L); // Mock the item ID
        Inventory mockInventory = new Inventory();
        mockInventory.setItem(mockItem); // Set a valid item for the inventory

        Pageable pageable = PageRequest.of(0, 10);
        when(inventoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(mockInventory)));

        Page<InventoryDTO> inventoryPage = inventoryService.getAllInventory(pageable);

        assertNotNull(inventoryPage);
        assertEquals(1, inventoryPage.getTotalElements());
    }
}

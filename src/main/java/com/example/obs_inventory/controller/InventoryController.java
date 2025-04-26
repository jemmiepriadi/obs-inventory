package com.example.obs_inventory.controller;

import com.example.obs_inventory.dto.InventoryDTO;
import com.example.obs_inventory.service.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory API", description = "Manage inventory transactions: Top-Up and Withdrawal")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @Operation(
            summary = "Get paginated inventory records",
            description = "Retrieve inventory records with pagination"
    )
    public Page<InventoryDTO> getAllInventory(@ParameterObject Pageable pageable) {
        return inventoryService.getAllInventory(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get inventory by ID",
            description = "Retrieve a single inventory record by its ID"
    )
    public InventoryDTO getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create inventory transaction",
            description = "Create a new inventory transaction: Top-Up or Withdrawal",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Inventory data for the transaction",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = InventoryDTO.class),
                            examples = @ExampleObject(
                                    name = "Top-Up Example",
                                    value = "{\n"
                                            + "  \"itemId\": 1,\n"
                                            + "  \"qty\": 50,\n"
                                            + "  \"type\": \"T\"\n"
                                            + "}"
                            )
                    )
            )
    )
    public InventoryDTO createInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.saveInventory(inventoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete inventory record",
            description = "Delete an inventory record by its ID"
    )
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }
}

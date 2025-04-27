package com.example.obs_inventory.controller;

import com.example.obs_inventory.dto.ItemDTO;
import com.example.obs_inventory.service.services.ItemService;
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
@RequestMapping("/api/items")
@Tag(name = "Item API", description = "Manage Items: create, retrieve, update, and delete items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @Operation(
            summary = "Get paginated list of items",
            description = "Retrieve all items with pagination and current stock information"
    )
    public Page<ItemDTO> getAllItems(@ParameterObject Pageable pageable) {
        return itemService.getAllItems(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get item by ID",
            description = "Retrieve a single item by its unique ID"
    )
    public ItemDTO getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create new item",
            description = "Create a new item and save it to the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Item data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ItemDTO.class),
                            examples = @ExampleObject(
                                    name = "New Item Example",
                                    value = "{\n"
                                            + "  \"name\": \"iPhone 15\",\n"
                                            + "  \"price\": 1500\n"
                                            + "  \"stock\": 10\n"
                                            + "}"
                            )
                    )
            )
    )
    public ItemDTO createItem(@RequestBody ItemDTO itemDTO) {
        return itemService.saveItem(itemDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update existing item",
            description = "Update an existing item by providing its ID and updated data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated item data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ItemDTO.class),
                            examples = @ExampleObject(
                                    name = "Update Item Example",
                                    value = "{\n"
                                            + "  \"name\": \"iPhone 15 Pro\",\n"
                                            + "  \"price\": 1700\n"
                                            + "  \"stock\": 10\n"
                                            + "}"
                            )
                    )
            )
    )
    public ItemDTO updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        itemDTO.setId(id);
        return itemService.update(itemDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete item",
            description = "Delete an item by its ID"
    )
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}

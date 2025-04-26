package com.example.obs_inventory.controller;

import com.example.obs_inventory.dto.OrderDTO;
import com.example.obs_inventory.service.services.OrderService;
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
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Manage customer orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(
            summary = "Get paginated list of orders",
            description = "Retrieve all customer orders with pagination"
    )
    public Page<OrderDTO> getAllOrders(@ParameterObject Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by ID",
            description = "Retrieve a single order by its ID"
    )
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create new order",
            description = "Create a new customer order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = OrderDTO.class),
                            examples = @ExampleObject(
                                    name = "New Order Example",
                                    value = "{\n"
                                            + "  \"itemId\": 1,\n"
                                            + "  \"qty\": 2,\n"
                                            + "  \"price\": 1000\n"
                                            + "}"
                            )
                    )
            )
    )
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.saveOrder(orderDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete order",
            description = "Delete a customer order by its ID"
    )
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}

package com.example.obs_inventory.service.services;

import com.example.obs_inventory.dto.OrderDTO;
import com.example.obs_inventory.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Page<OrderDTO> getAllOrders(Pageable pageable);

    OrderDTO getOrderById(Long id);

    OrderDTO saveOrder(OrderDTO dto);

    void deleteOrder(Long id);

    List<OrderDTO> findByItemId(Long itemId);
}

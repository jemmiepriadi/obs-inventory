package com.example.obs_inventory.service.impl;

import com.example.obs_inventory.dto.OrderDTO;
import com.example.obs_inventory.model.Item;
import com.example.obs_inventory.model.Order;
import com.example.obs_inventory.repository.OrderRepository;
import com.example.obs_inventory.service.services.ItemService;
import com.example.obs_inventory.service.services.OrderService;
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
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ItemService itemService;

    @Override
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepo.findAll(pageable)
                .map(this::toDto);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order entity = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toDto(entity);
    }

    @Override
    public OrderDTO saveOrder(OrderDTO dto) {
        int currentStock = itemService.calculateStock(dto.getItemId());
        if (dto.getQty() > currentStock) {
            throw new RuntimeException("Insufficient stock for item: " + dto.getItemId());
        }

        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity);
        Item item = new Item();
        item.setId(dto.getItemId());
        entity.setItem(item);

        Order saved = orderRepo.save(entity);
        OrderDTO result = new OrderDTO();
        BeanUtils.copyProperties(saved, result);
        result.setItemId(saved.getItem().getId());
        return result;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public List<OrderDTO> findByItemId(Long itemId) {
        return orderRepo.findByItemId(itemId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private OrderDTO toDto(Order entity) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setItemId(entity.getItem().getId());
        return dto;
    }
}

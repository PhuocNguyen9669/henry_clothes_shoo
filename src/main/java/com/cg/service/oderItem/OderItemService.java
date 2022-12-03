package com.cg.service.oderItem;

import com.cg.model.OrderItem;
import com.cg.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OderItemService implements IOderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return null;
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem getById(Long id) {
        return null;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return null;
    }

    @Override
    public void remove(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public void softDelete(OrderItem orderItem) {

    }
}

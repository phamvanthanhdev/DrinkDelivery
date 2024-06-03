package com.phuclong.milktea.milktea.repository;

import com.phuclong.milktea.milktea.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

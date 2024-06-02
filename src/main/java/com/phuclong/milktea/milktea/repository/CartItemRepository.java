package com.phuclong.milktea.milktea.repository;

import com.phuclong.milktea.milktea.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

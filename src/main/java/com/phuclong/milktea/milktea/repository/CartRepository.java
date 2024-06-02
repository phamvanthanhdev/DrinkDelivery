package com.phuclong.milktea.milktea.repository;

import com.phuclong.milktea.milktea.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Cart findByCustomerId(Long userId);
}

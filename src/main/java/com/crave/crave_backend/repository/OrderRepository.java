package com.crave.crave_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crave.crave_backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

package com.example.lab3.repositories;

import com.example.lab3.models.orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ordersRepository extends JpaRepository<orders, Long> {
}

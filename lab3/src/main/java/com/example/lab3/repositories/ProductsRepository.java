package com.example.lab3.repositories;

import com.example.lab3.models.products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<products, Long> {
}

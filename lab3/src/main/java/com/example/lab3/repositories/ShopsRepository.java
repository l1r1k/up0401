package com.example.lab3.repositories;

import com.example.lab3.models.shops;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopsRepository extends JpaRepository<shops, Long> {
}

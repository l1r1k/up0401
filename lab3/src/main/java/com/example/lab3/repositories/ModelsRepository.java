package com.example.lab3.repositories;

import com.example.lab3.models.models;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelsRepository extends JpaRepository<models, Long> {
}

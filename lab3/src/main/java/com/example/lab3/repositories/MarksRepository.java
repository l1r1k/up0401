package com.example.lab3.repositories;

import com.example.lab3.models.marks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarksRepository extends JpaRepository<marks, Long> {
}

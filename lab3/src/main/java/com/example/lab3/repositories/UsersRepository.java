package com.example.lab3.repositories;

import com.example.lab3.models.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<users, Long> {

}

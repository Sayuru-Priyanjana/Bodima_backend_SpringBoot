package com.sayuru.Bodima_backend.repository;

import com.sayuru.Bodima_backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
}

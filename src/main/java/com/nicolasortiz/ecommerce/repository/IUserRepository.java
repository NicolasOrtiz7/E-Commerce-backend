package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
}

package com.devtalkers.restapitest.repository;

import com.devtalkers.restapitest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

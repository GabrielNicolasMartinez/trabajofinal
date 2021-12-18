package com.informatorio.emprendimientos.repository;

import com.informatorio.emprendimientos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDateOfCreationAfter(LocalDateTime fromDate);
    List<User> findByCity(String city);

    User findByEmail(String email);
}
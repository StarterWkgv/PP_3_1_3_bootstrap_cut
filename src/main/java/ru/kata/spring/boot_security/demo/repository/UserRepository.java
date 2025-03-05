package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles")
    List<User> findAll();

//    @Query("SELECT u FROM User u JOIN FETCH u.roles  WHERE u.id = :id")
//    Optional<User> getById(@Param("id") long id);
}

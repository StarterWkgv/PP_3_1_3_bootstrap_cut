package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(int id);
    Optional<Role> findByRole(String role);
}

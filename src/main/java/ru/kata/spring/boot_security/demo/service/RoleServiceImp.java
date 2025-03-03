package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {

    final private RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}

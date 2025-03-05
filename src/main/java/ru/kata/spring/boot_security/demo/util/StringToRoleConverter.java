package ru.kata.spring.boot_security.demo.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

public class StringToRoleConverter implements Converter<String, Role> {

    private final RoleRepository roleRepository;

    public StringToRoleConverter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role convert(String source) {
        return roleRepository.findByRole(source).orElse(new Role(source));
    }
}
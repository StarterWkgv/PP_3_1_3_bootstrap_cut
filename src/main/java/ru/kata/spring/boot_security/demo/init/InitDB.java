package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;

@Component
public class InitDB {
    private final UserServiceImp userService;
    private final RoleRepository roleRepository;

    public InitDB(UserServiceImp userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");
        if (roleRepository.findByRole(roleAdmin.getRole()).isEmpty()) {
            roleRepository.save(roleAdmin);
        }
        if (roleRepository.findByRole(roleUser.getRole()).isEmpty()) {
            roleRepository.save(roleUser);
        }
        if (userService.getUserByEmail("admin").isEmpty()) {
            User admin = new User("Admin", "Admin", (byte) 30, "admin",
                    new HashSet<>(List.of(roleAdmin,roleUser)), "admin");
            userService.save(admin);
        }
        if (userService.getUserByEmail("user").isEmpty()) {
            User user = new User("User", "User", (byte) 35, "user",
                    new HashSet<>(List.of(roleUser)), "user");
            userService.save(user);
        }

    }
}
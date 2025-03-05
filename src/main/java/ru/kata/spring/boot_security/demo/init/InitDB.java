package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;

@Component
public class InitDB {
    private final UserService userService;
    private final RoleService roleService;

    public InitDB(UserServiceImp userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");
        if (roleService.findByRole(roleAdmin.getRole()).isEmpty()) {
            roleService.save(roleAdmin);
        }
        if (roleService.findByRole(roleUser.getRole()).isEmpty()) {
            roleService.save(roleUser);
        }
        if (userService.getUserByEmail("admin").isEmpty()) {
            User admin = new User("Admin", "Admin", (byte) 30, "admin", new HashSet<>(List.of(roleAdmin, roleUser)), "admin");
            userService.save(admin);
        }
        if (userService.getUserByEmail("user").isEmpty()) {
            User user = new User("User", "User", (byte) 35, "user", new HashSet<>(List.of(roleUser)), "user");
            userService.save(user);
        }
    }
}
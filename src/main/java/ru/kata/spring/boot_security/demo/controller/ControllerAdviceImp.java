package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdviceImp {
    private final RoleService roleService;
    private final UserService userService;

    public ControllerAdviceImp(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @ModelAttribute("rolList")
    public List<String> roleList() {
        return roleService.findAll().stream().map(Role::getRole).collect(Collectors.toList());
    }

    @ModelAttribute("details")
    public UserDetails details(@AuthenticationPrincipal UserDetails curUser) {
        return userService.getUserByEmail(curUser.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(@AuthenticationPrincipal User curUser) {
        return curUser.getRoles().stream().anyMatch(r -> r.getRole().equals("ADMIN"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        return "login";
    }
}

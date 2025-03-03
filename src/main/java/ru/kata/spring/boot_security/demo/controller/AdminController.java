package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsersPage(Model model) {
        List<User> list = userService.findAll();
        model.addAttribute("users", list);
        return "page";
    }

    @PostMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String editUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.update(user, id);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}

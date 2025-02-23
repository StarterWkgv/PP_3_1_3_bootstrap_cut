package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.RoleType;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping({"/user", "/admin"})
    public String showUser(@AuthenticationPrincipal User user, Model model) {
        boolean isAdmin = user.getRoles().stream().anyMatch(r -> r.getRole() == RoleType.ADMIN);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("rolList", RoleType.values());
        return "/page";
    }

}

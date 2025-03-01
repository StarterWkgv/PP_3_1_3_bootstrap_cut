package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;

@ControllerAdvice
public class ControllerAdviceImp {

    @ModelAttribute("rolList")
    public String[] roleList() {
        return new String[] { "ADMIN", "USER" };
    }

    @ModelAttribute("details")
    public UserDetails details(@AuthenticationPrincipal User ud) {
        return ud;
    }
}

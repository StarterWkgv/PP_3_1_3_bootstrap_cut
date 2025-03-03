package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Comparable<Role> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public int compareTo(Role o) {
        return getRole().compareTo(o.getRole());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role1 = (Role) o;
        return Objects.equals(getRole(), role1.getRole());
    }

    @Override
    public int hashCode() {
        return getRole().hashCode();
    }

}

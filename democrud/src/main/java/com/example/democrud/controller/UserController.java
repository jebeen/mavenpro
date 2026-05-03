package com.example.democrud.controller;

import com.example.democrud.entity.User;
import com.example.democrud.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User u) {
        User user = repo.findById(id).orElseThrow();
        user.setName(u.getName());
        user.setEmail(u.getEmail());
        return repo.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
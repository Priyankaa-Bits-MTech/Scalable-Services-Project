package com.ecommerce.users.controller;

import com.ecommerce.users.entity.User;
import com.ecommerce.users.exception.UserNotFoundException;
import com.ecommerce.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    Optional<User> getUserById(@PathVariable Long id) {

        return Optional.ofNullable(userService.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @GetMapping("/{email}")
    User getUserByEmail(@PathVariable String email) {
        return userService.getUserDetails(email);
    }

    @PutMapping("/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return userService.findUserById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setPassword(newUser.getPassword());
                    user.setAddress(newUser.getAddress());
                    user.setMobile(newUser.getMobile());
                    user.setPinCode(newUser.getPinCode());

                    return userService.saveUser(newUser);
                })
                .orElseGet(() -> {
                    return userService.saveUser(newUser);
                });
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);
        return "Successfully deleted";
    }

}

package org.ecomm.auth.controller;

import org.ecomm.auth.constant.Roles;
import org.ecomm.auth.domain.CreateUser;
import org.ecomm.auth.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This controller is public
@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity createAdmin(@RequestBody CreateUser createUser) {
        userService.createUser(createUser.toBuilder().password(passwordEncoder.encode(createUser.getPassword())).build(), Roles.ROLE_ADMIN.name());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}

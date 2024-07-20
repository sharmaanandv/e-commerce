package org.ecomm.auth.controller;

import com.ecomm.comms.dto.UserInfo;
import org.ecomm.auth.config.JwtUtil;
import org.ecomm.auth.constant.Roles;
import org.ecomm.auth.domain.CreateUser;
import org.ecomm.auth.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// This controller is public
@RestController
@RequestMapping("/v1/api/user")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info/{token}")
    public UserInfo getUserInfo(@PathVariable String token) {
        return userService.getUserInfo(token);
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody CreateUser user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping
    public ResponseEntity register(@RequestBody CreateUser createUser) {
        userService.createUser(createUser.toBuilder().password(passwordEncoder.encode(createUser.getPassword())).build(), Roles.ROLE_USER.name());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}

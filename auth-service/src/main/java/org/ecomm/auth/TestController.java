package org.ecomm.auth;

import org.ecomm.auth.config.JwtUtil;
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

@RestController
@RequestMapping("/v1/api/test")
public class TestController {

    @GetMapping("/")
    public String createAuthenticationToken() throws Exception {
        return "pass";
    }
}

package org.ecomm.auth.services;

import com.ecomm.comms.dto.UserInfo;
import com.ecomm.comms.exception.EcommException;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.auth.config.JwtUtil;
import org.ecomm.auth.config.UserInfoDetails;
import org.ecomm.auth.domain.CreateUser;
import org.ecomm.auth.entity.UserEntity;
import org.ecomm.auth.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public void createUser(CreateUser createUser, String roles) {
        Optional<UserEntity> user = userRepository.findByUsername(createUser.getUsername());
        if (user.isEmpty()) {
            userRepository.save(UserEntity.builder().username(createUser.getUsername()).password(createUser.getPassword()).roles(roles).build());
            log.info("User created successfully.");
        } else {
            throw new EcommException(HttpStatus.BAD_REQUEST, "User Already Present");
        }
    }

    public UserInfo getUserInfo(String token) {
        String username = jwtUtil.extractUsername(token);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new UserInfo(user.get().getId(), username, user.get().getRoles());
        }
        throw new EcommException(HttpStatus.NOT_FOUND, "User not found");
    }
}
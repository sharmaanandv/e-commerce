package com.ecomm.comms.config;

import com.ecomm.comms.dto.Principal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SecurityContext {

    public static final String X_USER_ROLE = "X-User-Roles";
    public static final String X_USER_ID = "X-User-Id";
    public static final String X_USER_NAME = "X-User-Name";
    public static final String X_USER_TOKEN = "X-User-Token";

    public static Principal getUserInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        List<String> roles = Arrays.stream(request.getHeader(X_USER_ROLE).split(",")).collect(Collectors.toList());
        return new Principal(Long.parseLong(request.getHeader(X_USER_ID)), request.getHeader(X_USER_NAME), roles, request.getHeader(X_USER_TOKEN));
    }

}

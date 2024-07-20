package org.ecomm.product.config;

import com.ecomm.comms.config.SecurityContext;
import com.ecomm.comms.exception.EcommException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RoleRequiredAspect {

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(RoleRequired)")
    public void checkRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RoleRequired roleRequired = method.getAnnotation(RoleRequired.class);

        String requiredRole = roleRequired.value();
        String roleAvailable = request.getHeader(SecurityContext.X_USER_ROLE);
        if (requiredRole.equals("ROLE_ADMIN") && (roleAvailable.isBlank() || roleAvailable.equals("ROLE_USER"))) {
            throw new EcommException(HttpStatus.FORBIDDEN, "Authorization failed.");
        }

    }
}

package com.grinder.common.interceptor;

import com.grinder.common.security.common.model.AdminUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails user = (AdminUserDetails) authentication.getPrincipal();

        if (user == null || user.getSystemAdmin() == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("접근이 불가능한 페이지입니다.");
            return false;
        }

        return true;
    }
}

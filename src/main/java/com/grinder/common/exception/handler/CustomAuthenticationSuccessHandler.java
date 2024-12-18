package com.grinder.common.exception.handler;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
/**
 * LoginHistoryManger에서 loginMessage 설정
 * 해당 핸들러에서 세션에 저장
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String loginMessage = (String) request.getAttribute("loginMessage");
        if (loginMessage != null) {
            request.getSession().setAttribute("loginMessage", loginMessage);
        }

        String targetUrl = determineTargetUrl(request, response, authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     *로그인 성공 후 경로 지정
     * TODO: 테스트 필요
     */
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        String targetUrl = request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null ?
                request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST").toString() :
                getDefaultTargetUrl();

        request.getSession().removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
        return targetUrl;
    }
}
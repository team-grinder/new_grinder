package com.grinder.common.exception.handler;

import com.grinder.common.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
/**
 * 에러 발생 시 errorMessage세션에 저장
 * 일반,잠금 에러 분리
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = exception.getMessage();
        String redirectUrl = "/login?error=true";

        if (exception.getCause() instanceof LoginException) {
            LoginException loginException = (LoginException) exception.getCause();
            errorMessage = loginException.getMessage();

            if (loginException.getMessage().contains("계정이 잠겼습니다")) {
                redirectUrl = "/login?locked=true";
            }
        }

        request.getSession().setAttribute("errorMessage", errorMessage);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
package com.grinder.common.security.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grinder.common.exception.LoginException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.common.model.ErrorResult;
import com.grinder.common.model.FailureResult;
import com.grinder.common.model.ReslutEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
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
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");


        if (exception instanceof LockedException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            FailureResult<ErrorResult> result = FailureResult.from(AuthResultEnum.ACCOUNT_LOCKED);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
        else if (exception.getCause() instanceof LoginException) {
            LoginException loginException = (LoginException) exception.getCause();

            if (loginException.getMessage().contains("계정이 잠겼습니다")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                FailureResult<ErrorResult> result = FailureResult.from(AuthResultEnum.ACCOUNT_LOCKED);
                response.getWriter().write(objectMapper.writeValueAsString(result));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                FailureResult<ErrorResult> result = FailureResult.from(AuthResultEnum.LOGIN_FAILED);
                response.getWriter().write(objectMapper.writeValueAsString(result));
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            FailureResult<ErrorResult> result = FailureResult.from(AuthResultEnum.LOGIN_FAILED);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }
}
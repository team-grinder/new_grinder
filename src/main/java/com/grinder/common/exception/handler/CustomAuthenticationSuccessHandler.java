package com.grinder.common.exception.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.grinder.common.model.ReslutEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.common.security.oauth.model.OAuth2MemberDetails;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.login.LoginResult;
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
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String loginMessage = (String) request.getAttribute("loginMessage");
        Member member;

        if (authentication.getPrincipal() instanceof MemberUserDetails) {
            member = ((MemberUserDetails) authentication.getPrincipal()).getMemberEntity().toMember();
        } else if (authentication.getPrincipal() instanceof OAuth2MemberDetails) {
            member = ((OAuth2MemberDetails) authentication.getPrincipal()).getMember();
        } else {
            throw new IllegalStateException("지원하지 않는 인증타입 입니다.");
        }

        LoginResult loginResult = loginMessage != null ?
                new LoginResult(member, loginMessage) :
                new LoginResult(member);

        SuccessResult<LoginResult> result = SuccessResult.of(ReslutEnum.SUCCESS, loginResult);
            response.getWriter().write(objectMapper.writeValueAsString(result));
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
package com.grinder.common.security.common.handler;

import com.grinder.common.security.common.service.MemberDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final MemberDetailsService memberDetailsService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomDaoAuthenticationProvider(MemberDetailsService memberDetailsService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.memberDetailsService = memberDetailsService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        super.setUserDetailsService(userDetailsService);
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();

        try {
            // 사용자 정보를 조회하며 추가 검증 로직 수행
            UserDetails userDetails = memberDetailsService.loadUserByUsername(email);

            try {
                // 부모 클래스의 인증 로직 호출
                String password = authentication.getCredentials().toString();

                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
                }

                // 인증 성공 시 처리
                memberDetailsService.handleLoginSuccess(email);

                return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            } catch (BadCredentialsException e) {
                // 인증 실패 시 처리
                memberDetailsService.handleLoginFailure(email);
                throw e;
            }

        } catch (AuthenticationException e) {
            // 추가적인 로그인 상태 검증 실패 처리
            throw new LockedException(e.getMessage());
        }
    }
}
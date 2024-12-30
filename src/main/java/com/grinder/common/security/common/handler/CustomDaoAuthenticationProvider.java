package com.grinder.common.security.common.handler;

import com.grinder.common.exception.LoginException;
import com.grinder.common.model.AuthResultEnum;
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
            UserDetails userDetails = memberDetailsService.loadUserByUsername(email);
            String password = authentication.getCredentials().toString();
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                try {
                    memberDetailsService.handleLoginFailure(email);
                } catch (LoginException e) {
                    throw new LockedException(e.getMessage());
                }
                throw new BadCredentialsException(AuthResultEnum.PASSWORD_MISMATCH.getMessage());
            }

            memberDetailsService.handleLoginSuccess(email);
            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
        } catch (AuthenticationException e) {
            throw e;
        }
    }
}
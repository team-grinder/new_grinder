package com.grinder.common.config;

import com.grinder.common.security.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CacheManager cacheManager;
    private final MemberDetailsService memberDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProcess")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .permitAll()
                )
                .httpBasic(httpBasic -> httpBasic
                        .disable()
                )
                .csrf()
                .disable();

        return http.build();
    }

    /**
     * 유저 인증 시 캐싱 처리
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(memberDetailsService);

        // SpringCacheBasedUserCache 설정
        authProvider.setUserCache(new SpringCacheBasedUserCache(cacheManager.getCache("userCache")));
        return authProvider;
    }
}

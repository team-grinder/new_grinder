package com.grinder.common.config;

import com.grinder.common.security.common.filter.CustomUsernamePasswordAuthenticationFilter;
import com.grinder.common.security.common.handler.CustomAuthenticationFailureHandler;
import com.grinder.common.security.common.handler.CustomAuthenticationSuccessHandler;
import com.grinder.common.security.oauth.service.OAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${permitAll.List}")
    private String[] permitAllUrls;

    private final OAuth2MemberService customOAuth2MemberService; // OAuth2 전용 서비스
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/fonts/**", "/images/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // AuthenticationManager 설정
        AuthenticationManager authenticationManager = authenticationManager(http);

        // Custom 필터 설정
        CustomUsernamePasswordAuthenticationFilter customAuthenticationFilter =
                new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        http.authenticationManager(authenticationManager) // 명시적으로 AuthenticationManager 설정
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 필터 위치 설정
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(permitAllUrls).permitAll()
                        .antMatchers("/admin/**").hasAuthority("admin")
                        .antMatchers("/cafe-manager").hasAnyAuthority("cafe_manager","admin")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2MemberService))
                        .loginProcessingUrl("/login/oauth2/code/**")
                        .successHandler(authenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login?expired=true")
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
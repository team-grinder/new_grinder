package com.grinder.common.security.common.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grinder.common.security.common.model.MemberType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        // /login 또는 /login/admin 요청을 매칭
        this.setRequiresAuthenticationRequestMatcher(
                new OrRequestMatcher(
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/login/admin")
                )
        );
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String requestURL = request.getRequestURI();
            MemberType memberType = "/login/admin".equals(requestURL) ?
                    MemberType.ADMIN : MemberType.COMMON;

            Map<String, String> requestJSON = parseRequestJSON(request);
            String username = requestJSON.get(EMAIL);
            username = username != null ? username.trim() : "";
            String password = requestJSON.get(PASSWORD);
            password = password != null ? password : "";

            CustomAuthenticationToken authRequest =
                    new CustomAuthenticationToken(username, password, memberType);

            this.setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            return objectMapper.readValue(reader, new TypeReference<>() {});
        } catch (Exception e) {
            throw new AuthenticationServiceException("Failed to parse request JSON", e);
        }
    }

}
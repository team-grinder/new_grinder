package com.grinder;

import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.model.LoginType;
import com.grinder.domain.member.model.TierType;
import com.grinder.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String email = withAccount.value();
        String password = passwordEncoder.encode("1234");

        MemberEntity member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            member = memberRepository.save(new MemberEntity(
                    email,
                    password,
                    "test",
                    "010-1234-5678",
                    TierType.MASTER,
                    LoginType.COMMON,
                    false)
            );
        }

        List<SimpleGrantedAuthority> roleUser = new ArrayList<>();
        roleUser.add(new SimpleGrantedAuthority(TierType.MASTER.getValue()));
        MemberUserDetails principal = new MemberUserDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
                principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
package com.grinder.domain.member.service;

import com.grinder.common.utils.PasswordValidator;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.MemberRegister;
import com.grinder.domain.member.implement.MemberManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberManager memberManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public Member register(MemberRegister request) {
        memberManager.validateDuplicateEmail(request.getEmail());
        PasswordValidator.validatePassword(request.getPassword(), request.getConfirmPassword());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return memberManager.save(request, encodedPassword);
    }
}

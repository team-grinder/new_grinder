package com.grinder.domain.member.service;

import com.grinder.common.model.Pages;
import com.grinder.common.utils.PasswordValidator;
import com.grinder.domain.member.implement.MemberManager;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.MemberCreate;
import com.grinder.domain.member.model.MemberSearchPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberManager memberManager;

    @Transactional
    public Member register(
            String email,
            String password,
            String nickname,
            String confirmPassword
    ) {
        memberManager.validateDuplicateEmail(email);
        memberManager.validateDuplicateNickname(nickname);

        PasswordValidator.validatePassword(password, confirmPassword);

        return memberManager.save(email, password, nickname);
    }

    public boolean existsByEmail(String email) {
        return memberManager.existsByEmail(email);
    }

    public Pages<Member> getUsers(MemberSearchPage searchPage) {
        return memberManager.getUsers(searchPage);
    }


    @Transactional
    public Member createUser(MemberCreate request) {
        return memberManager.save(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        );
    }

    @Transactional
    public Member updateUser(Long userId, Member user) {
        return memberManager.update(userId, user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        memberManager.delete(userId);
    }

    public Member getUser(Long userId) {
        return memberManager.readById(userId);
    }
}

package com.grinder.common.security.common.model;

import com.grinder.common.security.AuthenticatedUser;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.model.TierType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@Getter
@AllArgsConstructor
public class MemberUserDetails implements UserDetails, AuthenticatedUser {

    private final MemberEntity memberEntity;

    @Override
    public TierType getTier() {
        return memberEntity.getTier();
    }

    @Override
    public Long getId() {
        return memberEntity.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(() -> memberEntity.getTier().getValue());
        return authorities;
    }

    public String getNickname() {
        return memberEntity.getNickname();
    }

    public String getImageUrl() {
        return memberEntity.getImageUrl();
    }

    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !memberEntity.isDeleted();
    }
}

package com.grinder.common.security.oauth.service;

import com.grinder.common.security.oauth.model.OAuth2MemberDetails;
import com.grinder.common.security.oauth.model.response.GoogleResponse;
import com.grinder.common.security.oauth.model.response.KakaoResponse;
import com.grinder.common.security.oauth.model.response.NaverResponse;
import com.grinder.common.security.oauth.model.response.OAuth2Response;
import com.grinder.domain.member.model.TierType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OAuth2MemberService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(request);

        // 소셜 로그인 provider 판단
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("naver")){

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        } else {
            return null;
        }

        TierType type = TierType.SILVER; // 디폴트 티어

        return new OAuth2MemberDetails(oAuth2Response,type);
    }
}

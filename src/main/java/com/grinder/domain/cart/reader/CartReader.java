package com.grinder.domain.cart.reader;

import com.grinder.domain.cafe.entity.Cafe;
import com.grinder.domain.cafe.repository.CafeRepository;
import com.grinder.domain.cart.model.CartInformation;
import com.grinder.domain.cart.model.CartVO;
import com.grinder.domain.cart.reopository.CartDetailRepository;
import com.grinder.domain.cart.reopository.CartRepository;
import com.grinder.domain.member.entity.Member;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartReader {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;

    public boolean hasUnorderedCart(CartVO cartVO) {
        Member member = memberRepository.findByEmail(cartVO.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 이메일로 가입된 회원이 없습니다.")
        );
        Cafe cafe = cafeRepository.findById(cartVO.getCafeId()).orElseThrow(
                () -> new IllegalArgumentException("해당 카페가 존재하지 않습니다.")
        );
        return cartRepository.existsByMemberIdAndCafeIdAndOrderedIsFalse(member.getId(), cafe.getId());
    }
}

package com.grinder.web.main.member;

import com.grinder.domain.member.model.MemberRegister;
import com.grinder.domain.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public String register(@Valid MemberRegister request,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "main/register";
        }

        try {
            memberService.register(request);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error", e.getMessage());
            return "main/register";
        }
    }
}

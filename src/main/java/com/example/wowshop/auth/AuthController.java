package com.example.wowshop.auth;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest("", "", "", "", "", "", false, false));
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute SignupRequest signupRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        try {
            memberService.signup(signupRequest);
        } catch (IllegalArgumentException exception) {
            bindingResult.reject("signup.failed", exception.getMessage());
            return "signup";
        }
        redirectAttributes.addFlashAttribute("message", "회원가입 완료! 로그인해 주세요.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

package com.kh.hellomentor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "login/login";
    }

    @RequestMapping("/mypage")
    public String myPage() {
        return "mypage/mypage";
    }


    @RequestMapping("/choose-sign-up")
    public String chooseSignUp() {
        return "login/choose-sign-up";
    }

    @RequestMapping("/sign-up")
    public String signUp() {
        return "login/sign-up";
    }

    @RequestMapping("/adminMain")
    public String adminMain() {
        return "admin/admin-main";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            redirectAttributes.addFlashAttribute("message", "로그아웃 되었습니다.");
        }
        return "redirect:/";
    }
}





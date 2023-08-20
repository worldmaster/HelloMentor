package com.kh.springboot;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "mypage";
    }

    @GetMapping("/home_notification")
    public String homeNotification() {
        return "home_notification";
    }

    @GetMapping("/home_following_list")
    public String homeFollowingList() {
        return "home_following_list";
    }

    @GetMapping("/profile_edit_info")
    public String profileEditInfo() {
        return "profile_edit_info";
    }

    @GetMapping("/profile_my_post")
    public String profileMyPost() {
        return "profile_my_post";
    }

    @GetMapping("/profile_my_reply")
    public String profileReply() {
        return "profile_my_reply";
    }

    @GetMapping("/devhelper_calendar")
    public String devhelperCalendar() {
        return "devhelper_calendar";
    }

    @GetMapping("/devhelper_codelab")
    public String devhelperCodelab() {
        return "devhelper_codelab";
    }

    @GetMapping("/mentoring_mentor_applications")
    public String mentoringMentorApplications() {
        return "mentoring_mentor_applications";
    }

    @GetMapping("/mentoring_mentor_registdetail")
    public String mentoringMentorRegistdetail() {
        return "mentoring_mentor_registdetail";
    }

    @GetMapping("/payment_payment_history")
    public String paymentHistory() {
        return "payment_payment_history";
    }

    @GetMapping("/payment_exchange_history")
    public String paymentExchangeHistory() {
        return "payment_exchange_history";
    }

}

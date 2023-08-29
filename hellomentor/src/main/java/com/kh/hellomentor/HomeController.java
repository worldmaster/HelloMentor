package com.kh.hellomentor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}


//    @RequestMapping("/home_notification")
//    public String homeNotification() {
//        return "mypage/home_notification";
//    }
//
//    @RequestMapping("/home_following_list")
//    public String homeFollowingList() {
//        return "mypage/home_following_list";
//    }
//
//    @RequestMapping("/profile_edit_info")
//    public String profileEditInfo() {
//        return "mypage/profile_edit_info";
//    }
//
////    @RequestMapping("/profile_my_post")
////    public String profileMyPost() {
////        return "mypage/profile_my_post";
////    }
//
//    @RequestMapping("/profile_my_reply")
//    public String profileMyReply() {
//        return "mypage/profile_my_reply";
//    }
//
//    @RequestMapping("/devhelper_calendar")
//    public String devhelperCalendar() {
//        return "mypage/devhelper_calendar";
//    }
//
//    @RequestMapping("/devhelper_codelab")
//    public String devhelperCodelab() {
//        return "mypage/devhelper_codelab";
//    }
//
//    @RequestMapping("/mentoring_mentor_applications")
//    public String mentoringMentorApplications() {
//        return "mypage/mentoring_mentor_applications";
//    }
//
//    @RequestMapping("/mentoring_mentor_registdetail")
//    public String mentoringMentorRegistDetail() {
//        return "mypage/mentoring_mentor_registdetail";
//    }
//
//    @RequestMapping("/payment_payment_history")
//    public String paymentPaymentHistory() {
//        return "mypage/payment_payment_history";
//    }
//
//    @RequestMapping("/payment_exchange_history")
//    public String paymentExchangeHistory() {
//        return "mypage/payment_exchange_history";
//    }



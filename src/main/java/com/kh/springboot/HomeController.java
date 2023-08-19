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
}

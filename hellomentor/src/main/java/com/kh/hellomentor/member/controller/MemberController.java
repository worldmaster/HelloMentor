package com.kh.hellomentor.member.controller;

import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Controller
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping("/profile_my_post")
    public String profileMyPost(Model model) {
        int userNo = 2;

        List<Board> myPosts = memberService.getPostsByUserNo(userNo);
        model.addAttribute("myPosts", myPosts);

        logger.info("myPosts: {}", myPosts);

        return "mypage/profile_my_post";
    }
}

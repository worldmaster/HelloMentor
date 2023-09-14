package com.kh.hellomentor.matching.controller;


import com.kh.hellomentor.common.template.Pagination;
import com.kh.hellomentor.common.vo.PageInfo;
import com.kh.hellomentor.matching.model.service.MatchingService;
import com.kh.hellomentor.matching.model.vo.Matching;
import com.kh.hellomentor.matching.model.vo.Mentoring;
import com.kh.hellomentor.member.model.vo.Member;
import com.kh.hellomentor.member.model.vo.Profile;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@SessionAttributes({"loginUser"})
public class MatchingController {


    @Autowired
    private MatchingService matchingService;


    //   보낸 제안내역
    @RequestMapping("/mentoring_mentor_applications")
    public String mentor_applications(HttpSession session, Model model) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();


        List<Member> mentorList = matchingService.getMentorList(userNo);
        List<Profile> mentorProfileList = matchingService.getMentorProfileList(userNo);
        List<Mentoring> mentoringList = matchingService.getMentoringList(userNo);
        List<Matching> matchingList = matchingService.getMatchingList(userNo);


        List<Map<String, Object>> combinedList = new ArrayList<>();

        for (Member mentor : mentorList) {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("userNo", mentor.getUserNo());
            combinedInfo.put("userId", mentor.getUserId());
            combinedInfo.put("introduction", mentor.getIntroduction());
            combinedInfo.put("memberType", mentor.getMemberType());

            Profile profile = null;
            for (Profile p : mentorProfileList) {
                if (p.getUserNo() == mentor.getUserNo()) {
                    profile = p;
                    break;
                }
            }

            if (profile != null) {
                combinedInfo.put("filePath", profile.getFilePath());
                combinedInfo.put("changeName", profile.getChangeName());

            } else {
                Profile defaultProfile = new Profile();
                defaultProfile.setFilePath("/img/");
                defaultProfile.setChangeName("default-profile.jpg");
                combinedInfo.put("filePath", defaultProfile.getFilePath());
                combinedInfo.put("changeName", defaultProfile.getChangeName());

            }


            for (Matching matching : matchingList) {
                if (matching.getMenteeNo() == mentor.getUserNo()) {
                    for (Mentoring mentoring : mentoringList) {
                        if (matching.getMatchingRegisNo() == mentoring.getRegisNo()) {
                            combinedInfo.put("title", mentoring.getTitle());
                            combinedInfo.put("regisNo", mentoring.getRegisNo());
                            break;
                        }
                    }
                    break;
                }
            }

            combinedList.add(combinedInfo);

        }

        combinedList.removeIf(combinedInfo -> {
            Matching matching = (Matching) combinedInfo.get("matching");
            return matching != null && "C".equals(matching.getStatus());
        });
        model.addAttribute("combinedList", combinedList);
        log.info("combinedList: " + combinedList);
        return "mypage/mentoring_mentor_applications";
    }


    // 받은 제안내역
    @RequestMapping("/mentoring_mentor_applications2")
    public String mentor_applications2(HttpSession session, Model model) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();

        List<Member> mentorList = matchingService.getMentorList2(userNo);
        List<Profile> mentorProfileList = matchingService.getMentorProfileList2(userNo);
        List<Mentoring> mentoringList = matchingService.getMentoringList2(userNo);

        List<Matching> matchingList = matchingService.getMatchingList2(userNo);

        List<Map<String, Object>> combinedList = new ArrayList<>();

        for (Member mentor : mentorList) {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("userNo", mentor.getUserNo());
            combinedInfo.put("userId", mentor.getUserId());
            combinedInfo.put("introduction", mentor.getIntroduction());
            combinedInfo.put("memberType", mentor.getMemberType());

            Profile profile = null;
            for (Profile p : mentorProfileList) {
                if (p.getUserNo() == mentor.getUserNo()) {
                    profile = p;
                    break;
                }
            }

            if (profile != null) {
                combinedInfo.put("filePath", profile.getFilePath());
                combinedInfo.put("changeName", profile.getChangeName());
            } else {
                Profile defaultProfile = new Profile();
                defaultProfile.setFilePath("/img/");
                defaultProfile.setChangeName("default-profile.jpg");
                combinedInfo.put("filePath", defaultProfile.getFilePath());
                combinedInfo.put("changeName", defaultProfile.getChangeName());
            }

            for (Matching matching : matchingList) {
                if (matching.getMentorNo() == mentor.getUserNo()) {
                    combinedInfo.put("status", matching.getStatus());
                    for (Mentoring mentoring : mentoringList) {
                        if (matching.getMatchingRegisNo() == mentoring.getRegisNo()) {
                            combinedInfo.put("title", mentoring.getTitle());
                            combinedInfo.put("regisNo", mentoring.getRegisNo());
                            break;
                        }
                    }
                    break;
                }
            }

            combinedList.add(combinedInfo);
        }


        combinedList.removeIf(combinedInfo -> {
            String status = (String) combinedInfo.get("status");
            return status != null && "C".equals(status);
        });


        model.addAttribute("combinedList", combinedList);
        log.info("combinedList: " + combinedList);
        return "mypage/mentoring_mentor_applications2";
    }


    @DeleteMapping("/mentoring_cancel")
    @ResponseBody
    public Map<String, Object> mentoring_cancel(@RequestParam("userNo") int userNo, @RequestParam("regisNo") int regisNo) {
        Map<String, Object> response = new HashMap<>();

        try {
            matchingService.mentoring_cancel(userNo, regisNo);
            response.put("success", true);
            response.put("message", "제안이 취소되었습니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "제안 취소에 실패했습니다.");
        }

        return response;
    }

    @PostMapping("/mentoring_accept")
    @ResponseBody
    public Map<String, Object> mentoring_accept(@RequestParam("userNo") int userNo, @RequestParam("regisNo") int regisNo, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int loginuserNo = loginUser.getUserNo();
        Map<String, Object> response = new HashMap<>();


        try {
            matchingService.mentoring_accept(userNo, regisNo, loginuserNo);
            response.put("success", true);
            response.put("message", "제안이 수락되었습니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "제안 수락에 실패했습니다.");
        }

        return response;
    }


    @RequestMapping("/mentoring_mentor_registdetail")
    public String mentor_registdetail(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        return "mypage/mentoring_mentor_registdetail";
    }


    @GetMapping("/mentoring")
    public String selectList(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            HttpSession session,
            Model model,
            //검색요청이 들어오는경우 paramMap내부에는 keyword, condition
            @RequestParam Map<String, Object> paramMap
    ) {
//
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            log.info("로그인정보가 없어요유");
        } else {
//
            String memberType = loginUser.getMemberType();
            paramMap.put("memberType", memberType);
            log.info("memberType {}", memberType);


            List<Mentoring> list = matchingService.selectList(currentPage, paramMap);

            //총 게시글 갯수
            int total = matchingService.selectListCount(paramMap);
            int pageLimit = 10;
            int boardLimit = 9;
            PageInfo pi = Pagination.getPageInfo(total, currentPage, pageLimit, boardLimit);

            model.addAttribute("list", list);
            model.addAttribute("param", paramMap);
            model.addAttribute("pi", pi);

            log.info("list {}", list);
            log.info("param {}", paramMap);
            log.info("pi {}", pi);


        }
        return "matching/matching-list";

    }


    //멘토/멘티 등록페이지로 이동
    @GetMapping("/mentoring/insert")
    public String enrollMentoring(
            Model model
    ) {
        return "matching/insertMemberInfo";
    }

    //멘토/멘티 등록
    @PostMapping("/mentoring/insert")
    public String insertMentoring(
            Mentoring mt,
            HttpSession session,
            Model model
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        mt.setUserNo(loginUser.getUserNo()); //사용자 번호 설정도 같이 넣어줘야됨.

        int result = 0;

        try {
            result = matchingService.insertMentoring(mt);
        } catch (Exception e) {
            log.error("error = {}", e.getMessage());
            //e.printStackTrace();
        }

        if (result > 0 && loginUser.getMemberType().equals("E")) {

            return "redirect:/mentoring";
        } else if (result > 0 && loginUser.getMemberType().equals("O")) {

            return "redirect:/mentoring";
        } else {
            return "common/main";
        }

    }


}






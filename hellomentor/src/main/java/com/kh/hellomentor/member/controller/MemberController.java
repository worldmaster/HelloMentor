package com.kh.hellomentor.member.controller;

import javax.servlet.http.HttpSession;

import com.kh.hellomentor.matching.model.service.MatchingService;
import com.kh.hellomentor.member.model.vo.Calendar;
import com.kh.hellomentor.member.model.vo.Payment;
import com.kh.hellomentor.member.model.vo.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kh.hellomentor.member.model.service.MemberService;
import com.kh.hellomentor.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Slf4j
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService mService;

    @Autowired
    private MatchingService mtService;

    @PostMapping("login.me")
    public String loginMember(
            @ModelAttribute Member m,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Member loginUser = mService.loginUser(m);
        String url = "";
        if (loginUser != null) {
            if (loginUser.getUserId().equals("admin")) {
                session.setAttribute("loginUser", loginUser);
                redirectAttributes.addFlashAttribute("message", "관리자로 로그인했습니다.");
                url = "redirect:/adminMain";
            } else {
                session.setAttribute("loginUser", loginUser);
                redirectAttributes.addFlashAttribute("message", loginUser.getUserName() + "님 반갑습니다");
                url = "redirect:/main";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "아이디 또는 비밀번호를 확인해주세요.");
            url = "redirect:/";
        }
        return url;
    }

    @PostMapping("/sign.up")
    public String insertMember(@Validated Member m, HttpSession session, Model model, BindingResult bindingResult) {
        int result = mService.insertMember(m);
        String url = "";

        System.out.println(m);
        if (result > 0) {
            //성공시
            model.addAttribute("message", "회원가입을 축하드립니다. 로그인 해주세요");
            url = "login/login";
        } else {
            //실패 - 에러페이지로
            model.addAttribute("message", "회원가입 실패");
            url = "login/login";
        }
        return url;
    }

    @RequestMapping("/home_follow")
    public String homeFollow() {
        return "mypage/home_follow";
    }

    @RequestMapping("/home_following_list")
    public String getFollowList(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        int userNo = loginUser.getUserNo();
        List<Member> followingList = mService.getFollowList(userNo);
        List<Profile> profileList = mService.getFollowingProfileList(userNo);


        List<Map<String, Object>> combinedList = new ArrayList<>();
        for (Member member : followingList) {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("member", member);

            Profile profile = null;
            for (Profile p : profileList) {
                if (p.getUserNo() == member.getUserNo()) {
                    profile = p;
                    break;
                }
            }

            if (profile != null) {
                combinedInfo.put("profile", profile);
            } else {
                Profile defaultProfile = new Profile();
                defaultProfile.setFilePath("/img/");
                defaultProfile.setChangeName("default-profile.jpg");
                combinedInfo.put("profile", defaultProfile);
            }

            combinedList.add(combinedInfo);
        }

        model.addAttribute("combinedList", combinedList);
        return "mypage/home_following_list";
    }


    @RequestMapping("/home_follower_list")
    public String getFollowerList(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();
        List<Member> followerList = mService.getFollowerList(userNo);
        List<Profile> profileList = mService.getFollowerProfileList(userNo);


        List<Map<String, Object>> combinedList = new ArrayList<>();
        for (Member member : followerList) {
            Map<String, Object> combinedInfo = new HashMap<>();
            combinedInfo.put("member", member);

            Profile profile = null;
            for (Profile p : profileList) {
                if (p.getUserNo() == member.getUserNo()) {
                    profile = p;
                    break;
                }
            }

            if (profile != null) {
                combinedInfo.put("profile", profile);
            } else {
                Profile defaultProfile = new Profile();
                defaultProfile.setFilePath("/img/");
                defaultProfile.setChangeName("default-profile.jpg");
                combinedInfo.put("profile", defaultProfile);
            }

            combinedList.add(combinedInfo);
        }

        model.addAttribute("combinedList", combinedList);
        return "mypage/home_follower_list";
    }


    @RequestMapping("/profile_edit_info")

    public String profileEdit(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if ("E".equals(loginUser.getMemberType())) {
            loginUser.setMemberType("멘티");
        } else if ("O".equals(loginUser.getMemberType())) {
            loginUser.setMemberType("멘토");
        }
        model.addAttribute("loginUser", loginUser);
        return "mypage/profile_edit_info";
    }

    public String uploadProfileImg(MultipartFile file, int userNo) throws IOException {
        String currentDirectory = System.getProperty("user.dir");

        String uploadDir = currentDirectory + "/hellomentor/src/main/resources/static/img/profile/";

        String fileName = "profile_" + userNo + ".jpg";

        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        File destFile = new File(uploadPath, fileName);
        file.transferTo(destFile);
        logger.info("Saved file path: {}", destFile.getAbsolutePath());
        return fileName;
    }


    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("originPwd") String originPwd,
                                                @RequestParam("newPwd") String newPwd,
                                                @RequestParam("intro") String intro,
                                                HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (!originPwd.equals(loginUser.getUserPwd())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        }

        try {
            String fileName = uploadProfileImg(file, loginUser.getUserNo());

            Profile profile = new Profile();
            profile.setUserNo(loginUser.getUserNo());
            profile.setOriginName(file.getOriginalFilename());
            profile.setChangeName(fileName);
            profile.setFilePath("img/profile/");
            profile.setFileSize(file.getSize());

            if (newPwd.isEmpty()) {
                loginUser.setUserPwd(originPwd);
            } else {
                loginUser.setUserPwd(newPwd);
            }

            if (mService.isProfileImgExists(loginUser.getUserNo())) {
                mService.updateProfileImg(profile);
            } else {
                mService.insertProfileImg(profile);
            }


            loginUser.setIntroduction(intro);
            mService.updateMember(loginUser);

            return ResponseEntity.ok("프로필이 업데이트되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 업데이트 중 오류가 발생했습니다. 다시 시도해주세요");
        }
    }

    @RequestMapping("/payment_payment_history")

    public String paymentHistory(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        String type = "p"; // P면 페이먼트
        List<Payment> payments = mService.getPaymentHistory(loginUser.getUserNo(), type);
        model.addAttribute("payments", payments);
        model.addAttribute("loginUser", loginUser);
        return "mypage/payment_payment_history";
    }

    @RequestMapping("/payment_exchange_history")

    public String exchangeHistory(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        String type = "e"; // E면 익스체인지
        List<Payment> payments = mService.getPaymentHistory(loginUser.getUserNo(), type);
        model.addAttribute("payments", payments);
        model.addAttribute("loginUser", loginUser);
        return "mypage/payment_exchange_history";
    }

    @RequestMapping("/devhelper_calendar")

    public String calendar(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        return "mypage/devhelper_calendar";
    }

    @PostMapping("/saveMemo")
    public ResponseEntity<String> saveMemo(@RequestBody Calendar memoRequest, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        try {
            memoRequest.setUserNo(loginUser.getUserNo());
            boolean isMemoExists = mService.isMemoExists(memoRequest);
            if (isMemoExists) {
                mService.updateMemo(memoRequest);
                return ResponseEntity.ok("메모가 업데이트되었습니다.");
            } else {
                mService.saveMemo(memoRequest);
                return ResponseEntity.ok("메모가 저장되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("메모 저장 중 오류 발생: " + e.getMessage());
        }

    }

    @PostMapping("/deleteMemo")
    public ResponseEntity<String> deleteMemo(@RequestBody Calendar memoRequest, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        memoRequest.setUserNo(loginUser.getUserNo());
        boolean isMemoExists = mService.isMemoExists(memoRequest);
        if (isMemoExists) {
            mService.deleteMemo(memoRequest);
            return ResponseEntity.ok("메모가 삭제되었습니다");
        } else {
            return ResponseEntity.ok("메모가 존재하지 않습니다.");
        }
    }

    @PostMapping("/loadMemo")
    public ResponseEntity<String> loadMemo(@RequestBody String date, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        Calendar memoRequest = new Calendar();
        log.info(date);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = (Date) dateFormat.parse(date);
            memoRequest.setTodoDeadline(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("날짜 형식 오류");
        }

        memoRequest.setUserNo(loginUser.getUserNo());

        String memoContent = mService.loadMemo(memoRequest);

        if (memoContent != null) {
            return ResponseEntity.ok(memoContent);
        } else {
            return ResponseEntity.ok("");
        }
    }







}












   

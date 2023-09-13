package com.kh.hellomentor.board.controller;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.hellomentor.matching.model.vo.StudyApplicant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hellomentor.board.model.service.BoardService;
import com.kh.hellomentor.board.model.vo.Answer;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Knowledge;
import com.kh.hellomentor.board.model.vo.Reply;
import com.kh.hellomentor.common.Utils;
import com.kh.hellomentor.member.controller.MemberController;
import com.kh.hellomentor.member.model.vo.Member;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes({"loginUser"})
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private BoardService boardService;

    @Autowired
    private ServletContext application;


    //    마이페이지 내가 쓴 글
    @RequestMapping("/profile_my_post")
    public String profileMyPost(Model model, HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginUser");

        int userNo = loginUser.getUserNo();

        List<Board> myPosts = boardService.getPostsByUserNo(userNo);


        // 치환된 값을 사용하도록 수정
        List<Board> postsWithReplacedBoardType = myPosts.stream()
                .map(board -> {
                    String originalBoardType = board.getBoardType();
                    String replacedBoardType = replaceBoardType(originalBoardType);
                    board.setBoardType(replacedBoardType);
                    return board;
                })
                .collect(Collectors.toList());

        Set<String> uniqueBoardTypes = postsWithReplacedBoardType.stream()
                .map(Board::getBoardType)
                .collect(Collectors.toSet());

        model.addAttribute("myPosts", postsWithReplacedBoardType);
        model.addAttribute("boardTypes", uniqueBoardTypes);
        return "mypage/profile_my_post";
    }

    // 마이페이지 내가 쓴 댓글
    @RequestMapping("/profile_my_reply")
    public String profileMyReply(Model model, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        int userNo = loginUser.getUserNo();

        List<Reply> myReplies = boardService.getReplyByUserNo(userNo);

        List<Reply> repliesWithReplacedBoardType = myReplies.stream()
                .map(reply -> {
                    String originalBoardType = reply.getBoardType();
                    String replacedBoardType = replaceBoardType(originalBoardType);
                    reply.setBoardType(replacedBoardType);
                    return reply;
                })
                .collect(Collectors.toList());

        Set<String> uniqueBoardTypes = repliesWithReplacedBoardType.stream()
                .map(Reply::getBoardType)
                .collect(Collectors.toSet());

        model.addAttribute("myreply", repliesWithReplacedBoardType);
        model.addAttribute("boardTypes", uniqueBoardTypes);

        return "mypage/profile_my_reply";
    }


    //    게시판 타입 이름으로 바꿔주는 메소드
    private String replaceBoardType(String originalBoardType) {
        switch (originalBoardType) {
            case "A":
                return "문의게시판";
            case "N":
                return "공지사항게시판";
            case "Q":
                return "FAQ게시판";
            case "F":
                return "자유게시판";
            case "K":
                return "지식인게시판";
            case "KA":
                return "지식인답글게시판";
            case "S":
                return "스터디게시판";
            case "R":
                return "신고게시판";
            default:
                return originalBoardType;
        }
    }


  //이찬우 구역 시작
    //1. 공지사항 게시글 조회
    @GetMapping("/noticelist")
    public String selectNoticeList(
            Model model,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        List<Board> list = boardService.selectNoticeList();
        model.addAttribute("list", list);
        log.info("list {}", list);

        return "board/notice/notice-board";
    }

    //1-1. 공지사항 상세 조회
    @GetMapping("/noticedetail")
    public String selectNoticeDetail(
            Model model,
            HttpSession session,
            @RequestParam(name = "nno") int postNo,
            HttpServletRequest req, 
            HttpServletResponse res,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
        Member loginUser = (Member) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        Board selectedPost = boardService.selectNoticeDetail(postNo);
        model.addAttribute("selectedPost", selectedPost);
        log.info("selectedPost {}", selectedPost);
        
     // 상세조회 성공시 쿠키를 이용해서 조회수가 중복으로 증가되지 않도록 방지 + 본인의 글은 애초에 조회수 증가되지 않게 설정
     		if (selectedPost != null) {

     			// int userNo = 0;
     			String userId = "";

     			if (loginUser != null) {
     				userId = loginUser.getUserId();
     			}

     			// 게시글의 작성자 아이디와, 현재 세션의 접속중인 아이디가 같지 않은 경우에만 조회수증가
     			if (!selectedPost.getUserNo().equals(userId)) {

     				// 쿠키
     				Cookie cookie = null;

     				Cookie[] cArr = req.getCookies(); // 사용자의 쿠키정보 얻어오기.
     				if (cArr != null && cArr.length > 0) {

     					for (Cookie c : cArr) {
     						if (c.getName().equals("readPostNo")) {
     							cookie = c;
     							break;
     						}
     					}
     				}

     				int result = 0;

     				if (cookie == null) { // 원래 readBoardNo라는 이름의 쿠키가 없는 케이스
     					// 쿠키 생성
     					cookie = new Cookie("readPostNo", postNo + "");// 게시글작성자와 현재 세션에 저장된 작성자 정보가 일치하지않고, 쿠기도 없다.
     					// 조회수 증가 서비스 호출
     					result = boardService.increaseCount(postNo);
     				} else { // 존재 했던 케이스
     					// 쿠키에 저장된 값중에 현재 조회된 게시글번호(boardNo)를 추가
     					// 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께

     					String[] arr = cookie.getValue().split("/");
     					// "reacBoardNo" : "1/2/5/10/135" ==> ["1","2","5","10","135"]

     					// 배열을 컬렉션으로 변환 => indexOf를 사용하기 위  해서
     					// List.indexOf(obj) : list안에서 매개변수로 들어온 obj와 일치(equals)하는 부분의 인덱스를 반환
     					// 일치하는 값이 없는경우 -1 반환
     					List<String> list2 = Arrays.asList(arr);
     					log.info("list2 {}", list2);

     					if (list2.indexOf(postNo + "") == -1) { // 기존 쿠키값에 현재 게시글 번호와 일치하는 값이 없는경우(처음들어온글)
     						// 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께
     						cookie.setValue(cookie.getValue() + "/" + postNo);
     						result = boardService.increaseCount(postNo);
     					}
     				}
     				log.info("result {}", result);
     				if (result > 0) { // 성공적으로 조회수 증가함 (브라우저 단위)
     					selectedPost.setViews(selectedPost.getViews() + 1);

     					cookie.setPath(req.getContextPath());
     					cookie.setMaxAge(60 * 60 * 1); // 1시간만 유지
     					res.addCookie(cookie);
     				}
     			}
     		} else {
     			redirectAttributes.addFlashAttribute("message", "게시글 조회 실패");
     		}

        
        
        return "board/notice/notice-detail";

    }
    //1-2. 공지사항 삭제
    @GetMapping("/deletenotice")
    public String deleteNotice(
            Model model,
            HttpSession session,
            @RequestParam(name = "nno") int postNo,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
       int result = boardService.deletePost(postNo);
       log.info("result {}", result);
        if (result > 0) {
             redirectAttributes.addFlashAttribute("message", postNo + "번 공지사항이 성공적으로 삭제되었습니다");
             return "redirect:/noticelist";
         } else {
             redirectAttributes.addFlashAttribute("message", "게시글 삭제에 실패했습니다.");
             return "redirect:/noticelist";
         }
    }
    //2. FAQ 글 조회
    @GetMapping("/faqlist")
    public String selectFaqList(
            Model model,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        
        List<Board> list = boardService.selectFaqList();
        model.addAttribute("list", list);
        log.info("list {}", list);

        return "board/faq/faq-board";
    }
    //2-1. FAQ 삭제
    @GetMapping("/deletefaq")
    public String deleteFaq(
            Model model,
            HttpSession session,
            @RequestParam(name = "fno") int postNo,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
       int result = boardService.deletePost(postNo);
       log.info("result {}", result);
       
        if (result > 0) {
             redirectAttributes.addFlashAttribute("message",  postNo + "번 공지사항이 성공적으로 삭제되었습니다");
             return "redirect:/faqlist";
         } else {
             redirectAttributes.addFlashAttribute("message", "게시글 삭제에 실패했습니다.");
             return "redirect:/faqlist";
         }
    }

    //3. 문의 내역 insert
    @GetMapping("/inquiryinsert")
    public String moveInquiryInsert(Model model) {
        return "board/inquiry/inquiry-insert";
    }

    @PostMapping("/inquiryinsert")
    public String inquiryInsert(
            Board board,
            Inquiry inquiry,
            @RequestParam(value = "upfile", required = false) List<MultipartFile> upfiles,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes

    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();
        
        // 이미지, 파일을 저장할 저장경로 얻어오기
        String webPath = "/resources/static/img/attachment/inquiry";
        String severFolderPath = application.getRealPath(webPath);

        board.setUserNo(userNo + "");

        // 디렉토리생성 , 해당디렉토리가 존재하지 않는다면 생성
        File dir = new File(severFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<Attachment> attachList = new ArrayList();

        int level = -1;
        for (MultipartFile upfile : upfiles) {
            // input[name=upFile]로 만들어두면 비어있는 file이 넘어올수 있음.
            level++;
            if (upfile.isEmpty())
                continue;

            //  파일명 재정의 해주는 함수.
            String changeName = Utils.saveFile(upfile, severFolderPath);
            Attachment at = Attachment.
                    builder().
                    changeName(changeName).
                    originName(upfile.getOriginalFilename()).
                    build();
            attachList.add(at);
        }

        int postNo = 0;
        int result = 0;

        try {
            postNo = boardService.insertInquiry(board, attachList, severFolderPath, webPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inquiry.setPostNo(postNo);
        result = boardService.insertInquiry2(inquiry);
        
        if (result > 0) {
           redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다");
            return "redirect:/inquirylist";
        } else {
           redirectAttributes.addFlashAttribute("message",  "게시글 작성에 실패하였습니다. 다시 작성해주세요");
            return "redirect:/inquiryinsert";
        }
    }

    //4. 문의 내역 조회
    @GetMapping("/inquirylist")
    public String selectInquiryList(
            Model model,
            HttpSession session
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();

        List<Board> list = boardService.selectInquiryList(userNo);
        model.addAttribute("list", list);
        log.info("list {}", list);
        
        List<Inquiry> list2 = boardService.selectInquiryList2(userNo);
        model.addAttribute("list2", list2);
        log.info("list2 {}", list2);

        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[]{list.get(i), list2.get(i)});
        }
        model.addAttribute("combinedList", combinedList);

        return "board/inquiry/inquiry-board";
    }

    //4-1. 문의 내역 상세 조회
    @GetMapping("/inquirydetail")
    public String selectInquiryDetail(
            Model model,
            @RequestParam(name = "ino") int postNo
    ) {
       log.info("postNo {}", postNo);
       
        Board selectedPost = boardService.selectInquiryDetail(postNo);
        model.addAttribute("selectedPost", selectedPost);
        log.info("selectedPost {}", selectedPost);
        
        Inquiry selectedPost2 = boardService.selectInquiryDetail2(postNo);
        model.addAttribute("selectedPost2", selectedPost2);
        log.info("selectedPost2 {}", selectedPost2);

        return "board/inquiry/inquiry-detail";
    }


    //5. 자유게시판 글 조회 (화제 게시글 포함)
    @GetMapping("/freelist")
    public String selectFreeList(
            Model model
    ) {
        //일반 게시글
        List<Board> list = boardService.selectFreeList();
        model.addAttribute("list", list);
        log.info("list {}", list);
        
        List<Free> list2 = boardService.selectFreeList2();
        model.addAttribute("list2", list2);
        log.info("list2 {}", list2);

        //핫 게시글
        List<Board> list3 = boardService.selectBestFreeList();
        model.addAttribute("list3", list3);
        log.info("list3 {}", list3);
        
        List<Free> list4 = boardService.selectBestFreeList2();
        model.addAttribute("list4", list4);
        log.info("list4 {}", list4);

        // 일반 게시글 묶어주기
        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[]{list.get(i), list2.get(i)});
        }
        model.addAttribute("combinedList", combinedList);

        //핫게시글 묶어주기
        List<Object[]> combinedList2 = new ArrayList<>();
        for (int i = 0; i < list3.size(); i++) {
            combinedList2.add(new Object[]{list3.get(i), list4.get(i)});
        }
        model.addAttribute("combinedList2", combinedList2);

        return "board/free/free-board";
    }

    //5-1. 자유게시판 상세 조회
    @GetMapping("/freedetail")
    public String selectFreeDetail(
            Model model,
            HttpSession session,
            @RequestParam(name = "fno") int postNo,
            HttpServletRequest req, 
            HttpServletResponse res,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
        Member loginUser = (Member) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        Board selectedPost = boardService.selectFreeDetail(postNo);
        model.addAttribute("selectedPost", selectedPost);
        log.info("selectedPost {}", selectedPost);

        Free selectedPost2 = boardService.selectFreeDetail2(postNo);
        model.addAttribute("selectedPost2", selectedPost2);
        log.info("selectedPost2 {}", selectedPost2);

        List<Reply> list = boardService.selectFreeReplyList(postNo);
        model.addAttribute("list", list);
        log.info("list {}", list);
        
    	// 상세조회 성공시 쿠키를 이용해서 조회수가 중복으로 증가되지 않도록 방지 + 본인의 글은 애초에 조회수 증가되지 않게 설정
		if (selectedPost != null) {

			// int userNo = 0;
			String userId = "";

			if (loginUser != null) {
				userId = loginUser.getUserId();
			}

			// 게시글의 작성자 아이디와, 현재 세션의 접속중인 아이디가 같지 않은 경우에만 조회수증가
			if (!selectedPost.getUserNo().equals(userId)) {

				// 쿠키
				Cookie cookie = null;

				Cookie[] cArr = req.getCookies(); // 사용자의 쿠키정보 얻어오기.
				if (cArr != null && cArr.length > 0) {

					for (Cookie c : cArr) {
						if (c.getName().equals("readPostNo")) {
							cookie = c;
							break;
						}
					}
				}

				int result = 0;

				if (cookie == null) { // 원래 readBoardNo라는 이름의 쿠키가 없는 케이스
					// 쿠키 생성
					cookie = new Cookie("readPostNo", postNo + "");// 게시글작성자와 현재 세션에 저장된 작성자 정보가 일치하지않고, 쿠기도 없다.
					// 조회수 증가 서비스 호출
					result = boardService.increaseCount(postNo);
				} else { // 존재 했던 케이스
					// 쿠키에 저장된 값중에 현재 조회된 게시글번호(boardNo)를 추가
					// 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께

					String[] arr = cookie.getValue().split("/");
					// "reacBoardNo" : "1/2/5/10/135" ==> ["1","2","5","10","135"]

					// 배열을 컬렉션으로 변환 => indexOf를 사용하기 위  해서
					// List.indexOf(obj) : list안에서 매개변수로 들어온 obj와 일치(equals)하는 부분의 인덱스를 반환
					// 일치하는 값이 없는경우 -1 반환
					List<String> list2 = Arrays.asList(arr);
					log.info("list2 {}", list2);

					if (list2.indexOf(postNo + "") == -1) { // 기존 쿠키값에 현재 게시글 번호와 일치하는 값이 없는경우(처음들어온글)
						// 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께
						cookie.setValue(cookie.getValue() + "/" + postNo);
						result = boardService.increaseCount(postNo);
					}
				}
				log.info("result {}", result);
				if (result > 0) { // 성공적으로 조회수 증가함 (브라우저 단위)
					selectedPost.setViews(selectedPost.getViews() + 1);

					cookie.setPath(req.getContextPath());
					cookie.setMaxAge(60 * 60 * 1); // 1시간만 유지
					res.addCookie(cookie);
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "게시글 조회 실패");
		}

        return "board/free/free-detail";

    }

    //5-2. 자유게시판 글 등록
    @GetMapping("/freeinsert")
    public String moveFreeInsert() {
        return "board/free/free-insert";
    }
    @PostMapping("/freeinsert")
    public String freeInsert(
            Board board,
            @RequestParam(value = "upfile", required = false) List<MultipartFile> upfiles,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes

    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();
        // 이미지, 파일을 저장할 저장경로 얻어오기
        String webPath = "/resources/static/img/attachment/free";
        String severFolderPath = application.getRealPath(webPath);

        board.setUserNo(userNo + "");

        // 디렉토리생성 , 해당디렉토리가 존재하지 않는다면 생성
        File dir = new File(severFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<Attachment> attachList = new ArrayList();

        int level = -1;
        for (MultipartFile upfile : upfiles) {
            // input[name=upFile]로 만들어두면 비어있는 file이 넘어올수 있음.
            level++;
            if (upfile.isEmpty())
                continue;

            //  파일명 재정의 해주는 함수.
            String changeName = Utils.saveFile(upfile, severFolderPath);
            Attachment at = Attachment.
                    builder().
                    changeName(changeName).
                    originName(upfile.getOriginalFilename()).
                    build();
            attachList.add(at);
        }

        int postNo = 0;
        int result = 0;

        try {
            postNo = boardService.insertFree(board, attachList, severFolderPath, webPath);
            log.info("postNo {}", postNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = boardService.insertFree2(postNo);
        log.info("result {}", result);
        
        if (result > 0) {
           redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다");
            return "redirect:/freelist";
        } else {
           redirectAttributes.addFlashAttribute("message", "게시글이 작성에 실패하였습니다. 다시 작성해주세요.");
            return "redirect:/freeinsert";
        }
    }
   
    //5-3. 자유게시판 댓글 등록
    @GetMapping("/insertFreeReply")
	@ResponseBody // 리턴되는 값이 뷰페이지가 아니라 값 자체임을 의미
	public int insertFreeReply(Reply reply, HttpSession session) {

    	Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser != null) {
			reply.setUserNo(loginUser.getUserNo() + "");
		}

		int result = boardService.insertFreeReply(reply);
		log.info("result {}", result);

		return result;
	}
    //5-4. 자유게시판 댓글 조회
	@GetMapping("/selectFreeReplyList")
	@ResponseBody
	public List<Reply> selectFreeReplyList(int postNo) {
		return boardService.selectFreeReplyList(postNo);
	}
	//5-5. 자유게시판 댓글 삭제
    @GetMapping("/deletereply")
    public String deleteReply(
            Model model,
            HttpSession session,
            @RequestParam(name = "rno") int replyId,
            @RequestParam(name = "fno") int postNo,
            RedirectAttributes redirectAttributes
    ) {
       log.info("replyId {}", replyId);
       
       int result = boardService.deleteReply(replyId);
       log.info("result {}", result);
        if (result > 0) {
             redirectAttributes.addFlashAttribute("message", "댓글이 성공적으로 삭제되었습니다");
             return "redirect:/freedetail?fno="+postNo;
         } else {
             redirectAttributes.addFlashAttribute("message", "댓글 삭제에 실패했습니다.");
             return "redirect:/freedetail?fno="+postNo;
         }
    }
  //5-6. 자유게시판 글 삭제
    @GetMapping("/deletefree")
    public String deleteFree(
            Model model,
            HttpSession session,
            @RequestParam(name = "fno") int postNo,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
       int result = boardService.deletePost(postNo);
       log.info("result {}", result);
        if (result > 0) {
             redirectAttributes.addFlashAttribute("message", postNo + "번 글이 성공적으로 삭제되었습니다");
             return "redirect:/freelist";
         } else {
             redirectAttributes.addFlashAttribute("message", "글 삭제에 실패했습니다.");
             return "redirect:/freelist";
         }
    }

   
    //5-7. 자유게시판 추천수
	/*
	 * @GetMapping("/increaseupvotes") public String increaseUpvotes( Model model,
	 * HttpSession session,
	 * 
	 * @RequestParam(name = "fno") int postNo,
	 * 
	 * @RequestParam(name = "uno") String userNo, HttpServletRequest req,
	 * HttpServletResponse res, RedirectAttributes redirectAttributes ) {
	 * log.info("postNo {}", postNo);
	 * 
	 * Member loginUser = (Member) session.getAttribute("loginUser");
	 * model.addAttribute("loginUser", loginUser);
	 * 
	 * 
	 * Free selectedPost2 = boardService.selectFreeDetail2(postNo);
	 * log.info("selectedPost2 {}", selectedPost2);
	 * 
	 * // 상세조회 성공시 쿠키를 이용해서 조회수가 중복으로 증가되지 않도록 방지 + 본인의 글은 애초에 조회수 증가되지 않게 설정 if
	 * (selectedPost2 != null) {
	 * 
	 * // int userNo = 0; String userId = "";
	 * 
	 * if (loginUser != null) { userId = loginUser.getUserId(); }
	 * 
	 * // 게시글의 작성자 아이디와, 현재 세션의 접속중인 아이디가 같지 않은 경우에만 조회수증가 if
	 * (!userNo.equals(userId)) {
	 * 
	 * // 쿠키 Cookie cookie = null;
	 * 
	 * Cookie[] cArr = req.getCookies(); // 사용자의 쿠키정보 얻어오기. if (cArr != null &&
	 * cArr.length > 0) {
	 * 
	 * for (Cookie c : cArr) { if (c.getName().equals("readPostNo")) { cookie = c;
	 * break; } } }
	 * 
	 * int result = 0;
	 * 
	 * if (cookie == null) { // 원래 readBoardNo라는 이름의 쿠키가 없는 케이스 // 쿠키 생성 cookie =
	 * new Cookie("readPostNo", postNo + "");// 게시글작성자와 현재 세션에 저장된 작성자 정보가 일치하지않고,
	 * 쿠기도 없다. // 조회수 증가 서비스 호출 result = boardService.increaseUpvotes(postNo); }
	 * else { // 존재 했던 케이스 // 쿠키에 저장된 값중에 현재 조회된 게시글번호(boardNo)를 추가 // 단, 기존 쿠키값에
	 * 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께
	 * 
	 * String[] arr = cookie.getValue().split("/"); // "reacBoardNo" :
	 * "1/2/5/10/135" ==> ["1","2","5","10","135"]
	 * 
	 * // 배열을 컬렉션으로 변환 => indexOf를 사용하기 위 해서 // List.indexOf(obj) : list안에서 매개변수로
	 * 들어온 obj와 일치(equals)하는 부분의 인덱스를 반환 // 일치하는 값이 없는경우 -1 반환 List<String> list2 =
	 * Arrays.asList(arr); log.info("list2 {}", list2);
	 * 
	 * if (list2.indexOf(postNo + "") == -1) { // 기존 쿠키값에 현재 게시글 번호와 일치하는 값이
	 * 없는경우(처음들어온글) // 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께
	 * cookie.setValue(cookie.getValue() + "/" + postNo); result =
	 * boardService.increaseUpvotes(postNo); } } log.info("result {}", result); if
	 * (result > 0) { // 성공적으로 조회수 증가함 (브라우저 단위)
	 * selectedPost2.setUpVotes(selectedPost2.getUpVotes() + 1);
	 * 
	 * cookie.setPath(req.getContextPath()); cookie.setMaxAge(60 * 60 * 1); // 1시간만
	 * 유지 res.addCookie(cookie); } } } else {
	 * redirectAttributes.addFlashAttribute("message", "추천수 증가 실패"); }
	 * 
	 * return "board/free/free-detail";
	 * 
	 * }
	 */
    //5-8. 자유게시판 글 수정
    @GetMapping("/freeupdate")
    public String moveFreeUpdate() {
        return "board/free/free-insert";
    }
    
    //6. 지식인 글 조회
    @GetMapping("/knowledgelist")
    public String selectKnowledgeList(
            Model model
    ) {

        List<Board> list = boardService.selectKnowledgeList();
        model.addAttribute("list", list);
        log.info("list {}", list);
        
        List<Knowledge> list2 = boardService.selectKnowledgeList2();
        model.addAttribute("list2", list2);
        log.info("list2 {}", list2);
        
        List<Answer> list3 = boardService.selectKnowledgeList3();
        model.addAttribute("list3", list3);
        log.info("list3 {}", list3);

        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[]{list.get(i), list2.get(i), list3.get(i)});
        }
        model.addAttribute("combinedList", combinedList);

        return "board/knowledge/knowledge-board";
    }

    //6-2. 지식인 상세 조회
    @GetMapping("/knowledgedetail")
    public String selectKnowledgeDetail(
            Model model,
            HttpSession session,
            @RequestParam(name = "kno") int postNo,
            HttpServletRequest req, 
            HttpServletResponse res,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
        Member loginUser = (Member) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        
        int total = boardService.selectKnowledgeAnswerCount(postNo);
        model.addAttribute("total", total);
        log.info("total {}", total);

        Board selectedPost = boardService.selectKnowledgeDetail(postNo);
        model.addAttribute("selectedPost", selectedPost);
        log.info("selectedPost {}", selectedPost);

        Knowledge selectedPost2 = boardService.selectKnowledgeDetail2(postNo);
        model.addAttribute("selectedPost2", selectedPost2);
        log.info("selectedPost2 {}", selectedPost2);

        List<Board> list = boardService.selectKnowledgeDetailAnswer(postNo);
        model.addAttribute("list", list);
        log.info("list {}", list);
        
        
        	// 상세조회 성공시 쿠키를 이용해서 조회수가 중복으로 증가되지 않도록 방지 + 본인의 글은 애초에 조회수 증가되지 않게 설정
     		if (selectedPost != null) {

     			// int userNo = 0;
     			String userId = "";

     			if (loginUser != null) {
     				userId = loginUser.getUserId();
     			}

     			// 게시글의 작성자 아이디와, 현재 세션의 접속중인 아이디가 같지 않은 경우에만 조회수증가
     			if (!selectedPost.getUserNo().equals(userId)) {

     				// 쿠키
     				Cookie cookie = null;

     				Cookie[] cArr = req.getCookies(); // 사용자의 쿠키정보 얻어오기.
     				if (cArr != null && cArr.length > 0) {

     					for (Cookie c : cArr) {
     						if (c.getName().equals("readPostNo")) {
     							cookie = c;
     							break;
     						}
     					}
     				}

     				int result = 0;

     				if (cookie == null) { // 원래 readBoardNo라는 이름의 쿠키가 없는 케이스
     					// 쿠키 생성
     					cookie = new Cookie("readPostNo", postNo + "");// 게시글작성자와 현재 세션에 저장된 작성자 정보가 일치하지않고, 쿠기도 없다.
     					// 조회수 증가 서비스 호출
     					result = boardService.increaseCount(postNo);
     				} else { // 존재 했던 케이스
     					// 쿠키에 저장된 값중에 현재 조회된 게시글번호(boardNo)를 추가
     					// 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께

     					String[] arr = cookie.getValue().split("/");
     					// "reacBoardNo" : "1/2/5/10/135" ==> ["1","2","5","10","135"]

     					// 배열을 컬렉션으로 변환 => indexOf를 사용하기 위  해서
     					// List.indexOf(obj) : list안에서 매개변수로 들어온 obj와 일치(equals)하는 부분의 인덱스를 반환
     					// 일치하는 값이 없는경우 -1 반환
     					List<String> list2 = Arrays.asList(arr);
     					log.info("list2 {}", list2);

     					if (list2.indexOf(postNo + "") == -1) { // 기존 쿠키값에 현재 게시글 번호와 일치하는 값이 없는경우(처음들어온글)
     						// 단, 기존 쿠키값에 중복되는 번호가 없는 경우에만 추가 => 조회수증가와함께
     						cookie.setValue(cookie.getValue() + "/" + postNo);
     						result = boardService.increaseCount(postNo);
     					}
     				}
     				log.info("result {}", result);
     				if (result > 0) { // 성공적으로 조회수 증가함 (브라우저 단위)
     					selectedPost.setViews(selectedPost.getViews() + 1);

     					cookie.setPath(req.getContextPath());
     					cookie.setMaxAge(60 * 60 * 1); // 1시간만 유지
     					res.addCookie(cookie);
     				}
     			}
     		} else {
     			redirectAttributes.addFlashAttribute("message", "게시글 조회 실패");
     		}
        return "board/knowledge/knowledge-detail";

    }

    //6-3.지식인 글 등록
    @GetMapping("/knowledgequestioninsert")
    public String moveKnowledgeQuestionInsert() {
        return "board/knowledge/knowledge-question";
    }

    @PostMapping("/knowledgequestioninsert")
    public String knowledgeQuestionInsert(
            Board board,
            Knowledge knowledge,
            @RequestParam(value = "upfile", required = false) List<MultipartFile> upfiles,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes

    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();
        // 이미지, 파일을 저장할 저장경로 얻어오기
        String webPath = "/resources/static/img/attachment/knowledge";
        String severFolderPath = application.getRealPath(webPath);

        board.setUserNo(userNo + "");

        // 디렉토리생성 , 해당디렉토리가 존재하지 않는다면 생성
        File dir = new File(severFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<Attachment> attachList = new ArrayList();

        int level = -1;
        for (MultipartFile upfile : upfiles) {
            // input[name=upFile]로 만들어두면 비어있는 file이 넘어올수 있음.
            level++;
            if (upfile.isEmpty())
                continue;

            //  파일명 재정의 해주는 함수.
            String changeName = Utils.saveFile(upfile, severFolderPath);
            Attachment at = Attachment.
                    builder().
                    changeName(changeName).
                    originName(upfile.getOriginalFilename()).
                    build();
            attachList.add(at);
        }

        int postNo = 0;
        int result = 0;

        try {
            postNo = boardService.insertKnowledgeQuestion(board, attachList, severFolderPath, webPath);
            log.info("postNo {}", postNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        knowledge.setPostNo(postNo);
        result = boardService.insertKnowledgeQuestion2(knowledge);
        log.info("result {}", result);
        
        if (result > 0) {
           redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다");
            return "redirect:/knowledgelist";
        } else {
           redirectAttributes.addFlashAttribute("message", "게시글이 작성에 실패하였습니다. 다시 작성해주세요.");
            return "redirect:/knowledgequestioninsert";
        }
    }

    //6-4.지식인 답변 등록
    @GetMapping("/knowledgeanswerinsert")
    public String moveKnowledgeAnswerInsert() {
        return "board/knowledge/knowledge-answer";
    }

    @PostMapping("/knowledgeanswerinsert")
    public String knowledgeAnswerInsert(
            Board board,
            Answer answer,
            HttpSession session,
            Model model,
            @RequestParam(name = "ano") int knowledgePostNo,
            RedirectAttributes redirectAttributes

    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        int userNo = loginUser.getUserNo();

        board.setUserNo(userNo + "");
        answer.setKnowledgePostNo(knowledgePostNo);

        int postNo = 0;
        int result = 0;

        try {
            postNo = boardService.insertKnowledgeAnswer(board);
            log.info("postNo {}", postNo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        answer.setPostNo(postNo);
        result = boardService.insertKnowledgeAnswer2(answer);
        log.info("result {}", result);
        
        if (result > 0) {
           redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다");
            return "redirect:/knowledgelist";
        } else {
           redirectAttributes.addFlashAttribute("message", "게시글 작성에 실패하였습니다. 다시 작성해주세요.");
            return "redirect:/knowledgeanswerinsert";
        }
    }
    //6-5. 지식인 글 삭제
    @GetMapping("/deleteknowledge")
    public String deleteknowledge(
            Model model,
            HttpSession session,
            @RequestParam(name = "kno") int postNo,
            RedirectAttributes redirectAttributes
    ) {
       log.info("postNo {}", postNo);
       
       int result = boardService.deletePost(postNo);
       log.info("result {}", result);
        if (result > 0) {
             redirectAttributes.addFlashAttribute("message", postNo + "번 글이 성공적으로 삭제되었습니다");
             return "redirect:/knowledgelist";
         } else {
             redirectAttributes.addFlashAttribute("message", "글 삭제에 실패했습니다.");
             return "redirect:/knowledgelist";
         }
    }
    //6-6. 지식인 글 수정
    @GetMapping("/knowledgeupdate")
    public String moveKnowledgeUpdate() {
        return "board/knowledge/knowledge-question";
    }
    
    //이찬우 구역 끝


    //------------------------------정승훈-----------------------------------------

    //정승훈 스터디화면으로 이동 그리고 조회
    @GetMapping("/study")
    public String selectStudy(
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            Model model,
            @RequestParam Map<String, Object> paramMap,
            Board board,
            HttpServletRequest request
    ) {


        List<Board> list = boardService.selectStudyList(currentPage, paramMap);


        // 각 게시물의 POST_NO 목록을 가져옵니다.
        List<Integer> postNoList = list.stream()
                .map(Board::getPostNo)
                .collect(Collectors.toList());

        // POST_NO 목록을 paramMap에 추가합니다.
        paramMap.put("POST_NO_LIST", postNoList);

        log.info("paramMap {}", paramMap);
        // POST_NO별 RECRUITMENT_COUNT 조회
        List<Map<String, Object>> recruitmentCountList = boardService.selectRecruitmentCount(paramMap);

        log.info("recruitmentCountList {}", recruitmentCountList);

        // 각 POST_NO와 RECRUITMENT_COUNT를 매핑하여 Map에 저장
        Map<Integer, Integer> postRecruitmentCountMap = new HashMap<>();
        for (Map<String, Object> entry : recruitmentCountList) {
            Integer postNo = (Integer) entry.get("POST_NO");
            Integer recruitmentCount = ((Long) entry.get("RECRUITMENT_COUNT")).intValue(); // COUNT(*) 결과를 Integer로 변환
            postRecruitmentCountMap.put(postNo, recruitmentCount);
        }


        log.info("postRecruitmentCountMap {}", postRecruitmentCountMap);
        // 컨트롤러 모델에 POST_NO별 RECRUITMENT_COUNT를 추가
        model.addAttribute("postRecruitmentCountMap", postRecruitmentCountMap);


        log.info("list {}", list);


        // 총 게시글 갯수


        model.addAttribute("param", paramMap); //보드타입
        model.addAttribute("list", list); //study에 대한 정보가 담김


        return "/board/study/studyList";
    }


    //스터디등록페이지로 이동
    @GetMapping("/study/insert")
    public String EnrollStudy(
            Model model
    ) {
        return "/board/study/insertStudy";
    }

    //스터디 등록
    @PostMapping("/study/insert")
    public String insertStudy(
            HttpSession session,
            Model model,
            Board b,
            RedirectAttributes redirectAttributes
    ) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        b.setUserNo(loginUser.getUserNo() + ""); //작성자의 번호도 넣어주기


        int result = 0;


        result = boardService.insertStudy(b);


        log.info("POST_TITLE{}", b.getPostTitle());
        log.info("POST_CONTENT{}", b.getPostContent());
        log.info("result{}", result);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("message", "스터디 등록이 완료되었습니다");
            return "redirect:/study";
        } else {
            redirectAttributes.addFlashAttribute("message", "스터디 등록을 실패했습니다.");
            return "common/main";
        }

    }


    //스터디 상세 페이지
    @GetMapping("/study/detail/{postNo}")
    public String detailStudy(
            @PathVariable("postNo") int postNo,
            HttpSession session,
            Model model,
            Board board,
            StudyApplicant studyApplicant,
            HttpServletRequest req,
            HttpServletResponse res
    ) {

        Member loginUser = (Member) session.getAttribute("loginUser");

        //게시글에있는 정보들 조회 (제목,작성자,작성날짜,조회수)
        Board dstudy = boardService.selectDetailStudy(postNo);

        //신청자 인원수 조회
        int studyDetailApplicant = boardService.studyDetailApplicant(postNo);


        //댓글리스트 조회
        List<Reply> replyList = boardService.selectReplyList(postNo);

        String url = "";

        log.info("postNo{}", postNo);
        log.info("dstudy {}", dstudy);
        log.info("loginUser {}", loginUser);
        log.info("studyDetailApplicant {}", studyDetailApplicant);
        log.info("replyList {}", replyList);


        model.addAttribute("loginUser", loginUser);
        model.addAttribute("dstudy", dstudy);
        model.addAttribute("studyDetailApplicant", studyDetailApplicant);
        model.addAttribute("replyList", replyList);

        url = "board/study/updateStudy";

        //게시글에있는 현재참여인원 조회

        return url;


    }


}

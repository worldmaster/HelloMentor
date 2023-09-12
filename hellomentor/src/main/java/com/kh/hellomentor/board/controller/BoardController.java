package com.kh.hellomentor.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hellomentor.board.model.service.BoardService;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Reply;
import com.kh.hellomentor.common.Utils;
import com.kh.hellomentor.member.controller.MemberController;
import com.kh.hellomentor.member.model.vo.Member;

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
<<<<<<< HEAD
         Model model,
         HttpSession session
=======
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            Model model
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
    ) {
    	 Member loginUser = (Member) session.getAttribute("loginUser");
    	 model.addAttribute("loginUser", loginUser);
    	 
        List<Board> list = boardService.selectNoticeList();
        model.addAttribute("list", list);
        
        return "board/notice/notice-board";
    }
<<<<<<< HEAD
    
    //1-1. 공지사항 상세 조회
    @GetMapping("/noticedetail")
    public String selectNoticeDetail(
			Model model,
			 HttpSession session,
			@RequestParam(value = "postNo") int postNo
    ) {
    	Member loginUser = (Member) session.getAttribute("loginUser");
   	 model.addAttribute("loginUser", loginUser);
   	 System.out.println(postNo);
   	 
    	 Board selectedPost = boardService.selectNoticeDetail(postNo);
         model.addAttribute("selectedPost", selectedPost);
         return "board/notice/notice-detail";
         
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
        
        return "board/faq/faq-board";
    }
    
    
    //3. 문의 내역 insert
    @GetMapping("/inquiryinsert")
    public String moveInquiryInsert(Model model) {
        return "board/inquiry/inquiry-insert"; 
    }
    @PostMapping("/inquiryinsert")
    public String inquiryInsert(
=======


    //2. 문의 내역 insert
    @PostMapping("/insert.iq")
    public String insertBoard(
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
            Board board,
            Inquiry inquiry,
            @RequestParam(value = "upfile", required = false) List<MultipartFile> upfiles,
<<<<<<< HEAD
			HttpSession session, 
			Model model

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
=======
            HttpSession session,
            Model model
    ) {

        // 이미지, 파일을 저장할 저장경로 얻어오기
        // /resources/images/board/{boardCode}/
        String webPath = "/resources/static/img/attachment";
        String severFolderPath = application.getRealPath(webPath);

        board.setUserNo("2");

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

            // 1. 파일명 재정의 해주는 함수.
            String changeName = Utils.saveFile(upfile, severFolderPath);
            Attachment at = Attachment.
                    builder().
                    changeName(changeName).
                    originName(upfile.getOriginalFilename()).
                    build();
            attachList.add(at);
        }

        int result = 0;

        try {
            result = boardService.insertInquiry(board, attachList, severFolderPath, webPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
        if (result > 0) {
            //session.setAttribute("alertMsg", "게시글 작성에 성공하셨습니다.");
            return "redirect:/inquirylist";
        } else {
<<<<<<< HEAD
           // model.addAttribute("errorMsg", "게시글 작성 실패");
            return "redirect:/inquiryinsert";
        }
    }
    
    //4. 문의 내역 조회
    @GetMapping("/inquirylist")
    public String selectInquiryList(
         Model model,
     	HttpSession session
=======
            // model.addAttribute("errorMsg", "게시글 작성 실패");
            return "board/inquiry/inquiry-board";
        }
    }

    //3. 문의 내역 조회
    @GetMapping("/inquirylist")
    public String selectInquiryList(
            Model model
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
    ) {
    	Member loginUser = (Member) session.getAttribute("loginUser");
         int userNo = loginUser.getUserNo();
         
        List<Board> list = boardService.selectInquiryList(userNo);
        model.addAttribute("list", list);
        List<Inquiry> list2 = boardService.selectInquiryList2(userNo);
        model.addAttribute("list2", list2);

        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[]{list.get(i), list2.get(i)});
        }
        model.addAttribute("combinedList", combinedList);

        return "board/inquiry/inquiry-board";
    }
<<<<<<< HEAD
    //4-1. 문의 내역 상세 조회
    @GetMapping("/inquirydetail")
    public String selectInquiryDetail(
			Model model,
			@RequestParam(value = "postNo", defaultValue = "2") int postNo
    ) {
    	 Board selectedPost = boardService.selectInquiryDetail(postNo);
         model.addAttribute("selectedPost", selectedPost);
         Inquiry selectedPost2 = boardService.selectInquiryDetail2(postNo);
         model.addAttribute("selectedPost2", selectedPost2);
         
=======

    //3-1. 문의 내역 상세 조회
    @GetMapping("/inquirydetail")
    public String selectInquiryDetail(
            Model model
    ) {
        List<Board> list = boardService.selectinquirydetail(2);
        model.addAttribute("list", list);
        List<Inquiry> list2 = boardService.selectinquirydetail2(2);
        model.addAttribute("list2", list2);

        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[]{list.get(i), list2.get(i)});
        }
        model.addAttribute("combinedList", combinedList);

        return "board/inquiry/inquiry-detail";
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf

    }
<<<<<<< HEAD
    
    
    
    //5. 자유게시판 글 조회 (화제 게시글 포함)
=======

    //4. 자유게시판 글 조회
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
    @GetMapping("/freelist")
    public String selectFreeList(
            Model model
    ) {
    	//일반 게시글
        List<Board> list = boardService.selectFreeList();
        model.addAttribute("list", list);
        List<Free> list2 = boardService.selectFreeList2();
        model.addAttribute("list2", list2);
<<<<<<< HEAD
        
        //핫 게시글
        List<Board> list3 = boardService.selectBestFreeList();
        model.addAttribute("list3", list3);
        List<Free> list4 = boardService.selectBestFreeList2();
        model.addAttribute("list4", list4);
        
        // 일반 게시글 묶어주기
        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[] { list.get(i), list2.get(i)});
=======

        List<Object[]> combinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            combinedList.add(new Object[]{list.get(i), list2.get(i)});
>>>>>>> 42b6222ba4db5b70f7496f560ed53eacff73f8bf
        }
        model.addAttribute("combinedList", combinedList);
        
        //핫게시글 묶어주기
        List<Object[]> combinedList2 = new ArrayList<>();
        for (int i = 0; i < list3.size(); i++) {
            combinedList2.add(new Object[] { list3.get(i), list4.get(i)});
        }
        model.addAttribute("combinedList2", combinedList2);
        
        return "board/free/free-board";
    }
    //5-2. 자유게시판 상세 조회
    @GetMapping("/freedetail")
    public String selectFreeDetail(
			Model model,
			 HttpSession session,
			@RequestParam(value = "postNo", defaultValue = "1") int postNo
    ) {
    	Member loginUser = (Member) session.getAttribute("loginUser");
   	 	model.addAttribute("loginUser", loginUser);
   	 
    	 Board selectedPost = boardService.selectFreeDetail(postNo);
         model.addAttribute("selectedPost", selectedPost);
         System.out.println(selectedPost);
         
    	 Free selectedPost2 = boardService.selectFreeDetail2(postNo);
         model.addAttribute("selectedPost2", selectedPost2);
         System.out.println(selectedPost2);
         
         List<Reply> list = boardService.selectFreeDetailReply(postNo);
         model.addAttribute("list", list);
         System.out.println(list);
         
         return "board/free/free-detail";
         
    }
    //5-3. 자유게시판 글 등록
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
			Free free

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = boardService.insertFree2(postNo);
        if (result > 0) {
            //session.setAttribute("alertMsg", "게시글 작성에 성공하셨습니다.");
            return "redirect:/freelist";
        } else {
           // model.addAttribute("errorMsg", "게시글 작성 실패");
            return "redirect:/freeinsert";
        }
    }
    
    
    
    //6. 지식인 글 조회
    @GetMapping("/knowledgelist")
    public String selectKnowledgeList(
         Model model
    ) {

    	 List<Board> list = boardService.selectKnowledgeList();
         model.addAttribute("list", list);
         List<Knowledge> list2 = boardService.selectKnowledgeList2();
         model.addAttribute("list2", list2);
         List<Answer> list3 = boardService.selectKnowledgeList3();
         model.addAttribute("list3", list3);
         
         List<Object[]> combinedList = new ArrayList<>();
         for (int i = 0; i < list.size(); i++) {
             combinedList.add(new Object[] { list.get(i), list2.get(i), list3.get(i) });
         }
         model.addAttribute("combinedList", combinedList);

         return "board/knowledge/knowledge-board";
    }
    //6-2. 지식인 상세 조회
    @GetMapping("/knowledgedetail")
    public String selectKnowledgeDetail(
			Model model,
			 HttpSession session,
			@RequestParam(value = "postNo", defaultValue = "1") int postNo
    ) {
    	Member loginUser = (Member) session.getAttribute("loginUser");
   	 	model.addAttribute("loginUser", loginUser);
   	 
    	 Board selectedPost = boardService.selectKnowledgeDetail(postNo);
         model.addAttribute("selectedPost", selectedPost);
    
         log.info("selectedPost {}",selectedPost);
         
    	 Knowledge selectedPost2 = boardService.selectKnowledgeDetail2(postNo);
         model.addAttribute("selectedPost2", selectedPost2);
         System.out.println(selectedPost2);
         
         List<Board> list = boardService.selectKnowledgeDetailAnswer(postNo);
         model.addAttribute("list", list);
         System.out.println(list);
         
         return "board/free/free-detail";
         
    }
    //6-3.지식인 글 등록
    @GetMapping("/knowledgequestioninsert")
    public String moveKnowledgeQuestionInsert() {
        return "board/knowledge/knowledge-question";
    }

	/*
	 * @PostMapping("/knowledgequestioninsert") public String knowledgeQuestionInsert() {
	 * 
	 * }
	 */
    //6-4.지식인 답변 등록
    @GetMapping("/knowledgeanswerinsert")
    public String moveKnowledgeAnswerInsert() {
        return "board/knowledge/knowledge-answer";
    }

	/*
	 * @PostMapping("/knowledgeanswerinsert") public String knowledgeAnswerInsert() {
	 * 
	 * }
	 */

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

        log.info("paramMap {}" ,paramMap );
        // POST_NO별 RECRUITMENT_COUNT 조회
        List<Map<String, Object>> recruitmentCountList = boardService.selectRecruitmentCount(paramMap);

        log.info("recruitmentCountList {}" ,recruitmentCountList);

        // 각 POST_NO와 RECRUITMENT_COUNT를 매핑하여 Map에 저장
        Map<Integer, Integer> postRecruitmentCountMap = new HashMap<>();
        for (Map<String, Object> entry : recruitmentCountList) {
            Integer postNo = (Integer) entry.get("POST_NO");
            Integer recruitmentCount = ((Long) entry.get("RECRUITMENT_COUNT")).intValue(); // COUNT(*) 결과를 Integer로 변환
            postRecruitmentCountMap.put(postNo, recruitmentCount);
        }


        log.info("postRecruitmentCountMap {}" ,postRecruitmentCountMap );
        // 컨트롤러 모델에 POST_NO별 RECRUITMENT_COUNT를 추가
        model.addAttribute("postRecruitmentCountMap", postRecruitmentCountMap);




        log.info("list {}",list);


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
        b.setUserNo(loginUser.getUserNo()+""); //작성자의 번호도 넣어주기



        int result = 0;


        result = boardService.insertStudy(b);



        log.info("POST_TITLE{}",b.getPostTitle());
        log.info("POST_CONTENT{}",b.getPostContent());
        log.info("result{}",result);

        if(result > 0) {
            redirectAttributes.addFlashAttribute("message", "스터디 등록이 완료되었습니다");
            return "redirect:/study";
        }else {
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

        log.info("postNo{}",postNo);
        log.info("dstudy {}",dstudy);
        log.info("loginUser {}",loginUser);
        log.info("studyDetailApplicant {}",studyDetailApplicant);
        log.info("replyList {}",replyList);


        model.addAttribute("loginUser",loginUser);
        model.addAttribute("dstudy",dstudy);
        model.addAttribute("studyDetailApplicant",studyDetailApplicant);
        model.addAttribute("replyList",replyList);

        url="board/study/updateStudy";

        //게시글에있는 현재참여인원 조회

        return url;


    }



}

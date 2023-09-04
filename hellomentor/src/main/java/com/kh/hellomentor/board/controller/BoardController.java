package com.kh.hellomentor.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hellomentor.board.model.service.BoardService;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Reply;
import com.kh.hellomentor.common.Utils;
import com.kh.hellomentor.common.template.Pagination;
import com.kh.hellomentor.common.vo.PageInfo;
import com.kh.hellomentor.member.controller.MemberController;
import com.kh.hellomentor.member.model.vo.Member;

@Controller
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private BoardService boardService;

    @Autowired
    private ServletContext application;


    //    마이페이지 내가 쓴 글
    @RequestMapping("/profile_my_post")
    public String profileMyPost(Model model) {
        int userNo = 2;

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
    public String profileMyReply(Model model) {
        int userNo = 2;

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
    //1. 공지사항 게시글 조회, 글 갯수 조회 (페이징바)
    @GetMapping("/noticelist")
    public String selectNoticeList(
         @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
         Model model
    ) {

        List<Board> list = boardService.selectNoticeList(currentPage);

        // 총 게시글 갯수
        int total = boardService.selectNoticeListCount();
        int pageLimit = 10;
        int boardLimit = 10;
        PageInfo pi = Pagination.getPageInfo(total, currentPage, pageLimit, boardLimit);
        model.addAttribute("list", list);
        model.addAttribute("pi", pi);

        return "board/notice/notice-board";
    }

    
    
    //2. 문의 내역 insert
    @PostMapping("/insert.iq")
    public String insertBoard(
            Board board,
            @RequestParam(value = "upfile", required = false) List<MultipartFile> upfiles,
			HttpSession session, 
			Model model
    ) {

    	// 이미지, 파일을 저장할 저장경로 얻어오기
		// /resources/images/board/{boardCode}/
		String webPath = "/resources/images/board/" + "i" + "/";
		String severFolderPath = application.getRealPath(webPath);

		board.setUserNo(2);
		
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

        if (result > 0) {
            //session.setAttribute("alertMsg", "게시글 작성에 성공하셨습니다.");
            return "redirect:/";
        } else {
           // model.addAttribute("errorMsg", "게시글 작성 실패");
            return "redirect:/";
        }
    }
    //이찬우 구역 끝

}

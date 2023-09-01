package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Reply;

public interface BoardService {
    List<Board> getPostsByUserNo(int userNo);

    List<Reply> getReplyByUserNo(int userNo);

    
    
    
    
    
    
    
    //이찬우 구역 시작
    //1. 공지사항 목록 select
	public List<Board> selectNoticeList(int currentPage, Map<String, Object> paramMap);
	
	public int selectListCount(Map<String, Object> paramMap);
	
	//2. 1:1문의 작성
	public int insertInquiry(Board board, List<Attachment> list, Inquiry inquiry, String serverPath, String webPath) throws Exception;


}

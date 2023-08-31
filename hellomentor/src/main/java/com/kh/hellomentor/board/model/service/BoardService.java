package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Reply;

public interface BoardService {
    List<Board> getPostsByUserNo(int userNo);

    List<Reply> getReplyByUserNo(int userNo);

    
	public List<Board> selectNoticeList(int currentPage, Map<String, Object> paramMap);
	
	public int selectListCount(Map<String, Object> paramMap);
}

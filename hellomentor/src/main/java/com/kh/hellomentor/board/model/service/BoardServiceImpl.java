package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.board.model.dao.BoardDao;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Reply;
import com.kh.hellomentor.common.Utils;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public List<Board> getPostsByUserNo(int userNo) {
        return boardDao.getPostsByUserNo(userNo);
    }

    @Override
    public List<Reply> getReplyByUserNo(int userNo) {
        return boardDao.getReplyByUserNo(userNo);
    }

    
    
    
    
    
    
    
    //이찬우 구역 시작
    //1. 공지사항 목록 select
    @Override
    public List<Board> selectNoticeList(int currentPage, Map<String, Object> paramMap){
		return boardDao.selectNoticeList(currentPage, paramMap);
	}
	
    @Override
	public int selectListCount(Map<String, Object> paramMap) {
		return boardDao.selectListCount(paramMap);
	}
    
    //2. 1:1문의 작성
    @Override
	public int insertInquiry(Board board, List<Attachment> list, Inquiry inquiry, String inquiry, String webPath) throws Exception {

    }
    //이찬우 구역 끝
}


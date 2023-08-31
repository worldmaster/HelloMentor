package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.board.model.dao.BoardDao;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Reply;

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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public List<Board> selectNoticeList(int currentPage, Map<String, Object> paramMap){
		return boardDao.selectNoticeList(currentPage, paramMap);
	}
	
    @Override
	public int selectListCount(Map<String, Object> paramMap) {
		return boardDao.selectListCount(paramMap);
	}
}


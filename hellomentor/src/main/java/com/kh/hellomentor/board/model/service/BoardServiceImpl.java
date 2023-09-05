package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.board.model.dao.BoardDao;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
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
    public List<Board> selectNoticeList(int currentPage) {
        return boardDao.selectNoticeList(currentPage);
    }

    @Override
    public int selectNoticeListCount() {
        return boardDao.selectNoticeListCount();
    }

    //2. 1:1문의 작성
    @Override
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	int postNo = boardDao.insertInquiry(board);
    	
    	int result = 0;
		if(postNo > 0 && !list.isEmpty()) {
			for(Attachment attach    :   list) {
				attach.setPostNo(postNo);
				attach.setFilePath(webPath);
			}
			result = boardDao.insertInquiryAttachment(list);
			
			if(result != list.size()) {// 이미지 삽입 실패시 강제 예외 발생
				throw new Exception("예외발생");
			}
		}
		return result;
    }
    
    //3. 문의내역 조회
    @Override
    public List<Board> selectInquiryList(){
    	 return boardDao.selectInquiryList();
    }
    @Override
    public List<Inquiry> selectInquiryList2(){
   	 return boardDao.selectInquiryList2();
   }
    //4.문의내역 상세조회
    @Override
    public List<Board> selectinquirydetail(int postNo){
   	 return boardDao.selectInquiryList();
   }
    @Override
   public List<Inquiry> selectinquirydetail2(int postNo){
  	 return boardDao.selectInquiryList2();
  }
    
    //5. 자유게시판 조회
    @Override
    public List<Board> selectFreeList(){
    	 return boardDao.selectFreeList();
    }
    @Override
    public List<Free> selectFreeList2(){
    	return boardDao.selectFreeList2();
   }
    //이찬우 구역 끝
}


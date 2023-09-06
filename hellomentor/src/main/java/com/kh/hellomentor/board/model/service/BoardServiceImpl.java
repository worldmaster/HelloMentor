package com.kh.hellomentor.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.board.model.dao.BoardDao;
import com.kh.hellomentor.board.model.vo.Answer;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Knowledge;
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


    //이찬우 구역 시작
    //1. 공지사항 목록 select
    @Override
    public List<Board> selectNoticeList() {
        return boardDao.selectNoticeList();
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
    //3-2.문의내역 상세조회
    @Override
    public List<Board> selectinquirydetail(int postNo){
   	 return boardDao.selectInquiryList();
   }
    @Override
   public List<Inquiry> selectinquirydetail2(int postNo){
  	 return boardDao.selectInquiryList2();
  }
    
    //4. 자유게시판 조회
    @Override
    public List<Board> selectFreeList(){
    	 return boardDao.selectFreeList();
    }
    @Override
    public List<Free> selectFreeList2(){
    	return boardDao.selectFreeList2();
   }
    //4-1. 자유게시판 조회 (화제글 3개)
    @Override
    public List<Board> selectBestFreeList(){
    	 return boardDao.selectBestFreeList();
    }
    @Override
    public List<Free> selectBestFreeList2(){
    	return boardDao.selectBestFreeList2();
   }
    //5. 지식인 조회 (메인)
    @Override
    public List<Board> selectKnowledgeList(){
    	 return boardDao.selectKnowledgeList();
    }
    @Override
    public List<Knowledge> selectKnowledgeList2(){
    	return boardDao.selectKnowledgeList2();
   }
    @Override
    public List<Answer> selectKnowledgeList3(){
    	return boardDao.selectKnowledgeList3();
   }
    //6. FAQ 조회
    @Override
    public List<Board> selectFaqList() {
        return boardDao.selectFaqList();
    }
    
    //이찬우 구역 끝
}


package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;


import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;
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
    //0. 조회수 증가
    @Override
    public int increaseCount(int postNo) {
    	 return boardDao.increaseCount(postNo);
    };
    //0-1. FAQ 삭제
    @Override
    public int deletePost(int postNo) {
    	 return boardDao.deletePost(postNo);
    }

    //1. 공지사항 목록 select
    @Override
    public int selectNoticeCount() {
    	return boardDao.selectNoticeCount();
    };
    @Override
    public int searchNoticeCount(String ntkind, String keyword) {
    	return boardDao.searchNoticeCount(ntkind,keyword);
    };
    @Override
    public List<Board> selectNoticeList(int page, int pageSize){
    	return boardDao.selectNoticeList(page,pageSize);
    };
    @Override
    public List<Board> searchNoticeList(String ntkind, String keyword, int page, int pageSize){
    	return boardDao.searchNoticeList(ntkind,keyword,page,pageSize);
    };
    //1-1.공지사항 상세조회
    @Override
    public Board selectNoticeDetail(int postNo){
   	 return boardDao.selectNoticeDetail(postNo);
   }
 
    //2. FAQ 조회
    @Override
    public int selectFaqCount() {
    	return boardDao.selectFaqCount();
    };
    @Override
    public int searchFaqCount(String faqkind, String keyword) {
    	return boardDao.searchFaqCount(faqkind,keyword);
    };
    @Override
    public List<Board> selectFaqList(int page, int pageSize){
    	return boardDao.selectFaqList(page,pageSize);
    };
    @Override
    public List<Board> searchFaqList(String faqkind, String keyword, int page, int pageSize){
    	return boardDao.searchFaqList(faqkind,keyword,page,pageSize);
    };
    
    //3. 1:1문의 작성
    @Override
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	
    	board.setPostTitle(Utils.XSSHandling(board.getPostTitle()));
		board.setPostContent(Utils.XSSHandling(board.getPostContent()));
		board.setPostContent(Utils.newLineHandling(board.getPostContent()));
    	
		int result = 0;
    	int postNo = boardDao.insertInquiry(board);
    	if(postNo > 0 && !list.isEmpty()) {
			for(Attachment attach    :   list) {
				attach.setPostNo(postNo);
				attach.setFilePath(webPath);
			}
			result = boardDao.insertInquiryAttachment(list);
    	}
		return postNo;
    }
    @Override
    public int insertInquiry2(Inquiry inquiry) {
    	
    	
    	int result = boardDao.insertInquiry2(inquiry);
    
		return result;
    }
    
    //4. 문의내역 조회
    @Override
    public int selectInquiryCount() {
    	return boardDao.selectInquiryCount();
    };
    
    @Override
    public List<Board> selectInquiryList(int userNo, int page, int pageSize){
    	 return boardDao.selectInquiryList(userNo, page,pageSize);
    }
    @Override
    public List<Inquiry> selectInquiryList2(int userNo, int page, int pageSize){
   	 return boardDao.selectInquiryList2(userNo, page,pageSize);
   }
    
    //4-1.문의내역 상세조회
    @Override
    public Board selectInquiryDetail(int postNo){
   	 return boardDao.selectInquiryDetail(postNo);
   }
    @Override
   public Inquiry selectInquiryDetail2(int postNo){
  	 return boardDao.selectInquiryDetail2(postNo);
  }
    
    //5. 자유게시판 조회
    @Override
    public int selectFreeCount() {
    	 return boardDao.selectFreeCount();
    };
    @Override
    public int searchFreeCount(String freekind, String keyword) {
    	 return boardDao.searchFreeCount(freekind,keyword);
    };
    @Override
    public List<Board> selectFreeList(int page, int pageSize){
    	 return boardDao.selectFreeList(page,pageSize);
    };
    @Override
    public List<Free> selectFreeList2(int page, int pageSize){
    	 return boardDao.selectFreeList2(page,pageSize);
    };
    @Override
    public List<Board> searchFreeList(String freekind, String keyword, int page, int pageSize){
    	 return boardDao.searchFreeList(freekind,keyword,page,pageSize);
    };
    @Override
    public List<Free> searchFreeList2(String freekind, String keyword, int page, int pageSize){
    	 return boardDao.searchFreeList2(freekind,keyword,page,pageSize);
    };
    //5-1. 자유게시판 조회 (화제글 3개)
    @Override
    public List<Board> selectBestFreeList(){
    	 return boardDao.selectBestFreeList();
    }
    @Override
    public List<Free> selectBestFreeList2(){
    	return boardDao.selectBestFreeList2();
   }
    //5-2.자유게시판 상세조회
    @Override
    public Board selectFreeDetail(int postNo){
   	 return boardDao.selectFreeDetail(postNo);
   }
    @Override
    public Free selectFreeDetail2(int postNo){
   	 return boardDao.selectFreeDetail2(postNo);
   }
 
    //5-3. 자유게시판 글 작성
    @Override
    public int insertFree(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	
    	board.setPostTitle(Utils.XSSHandling(board.getPostTitle()));
		board.setPostContent(Utils.XSSHandling(board.getPostContent()));
		board.setPostContent(Utils.newLineHandling(board.getPostContent()));
    	
    	int postNo = boardDao.insertFree(board);
    
		return postNo;
    }
    @Override
    public int insertFree2(int postNo) {
    	
    	
    	int result = boardDao.insertFree2(postNo);
    
		return result;
    }
    //5-4. 자유게시판 댓글 삽입
    @Override
    public int insertFreeReply(Reply reply) {
    	return boardDao.insertFreeReply(reply);
	}
	//5-5. 자유게시판 댓글 조회
    @Override
	public List<Reply> selectFreeReplyList(int postNo){
		return boardDao.selectFreeReplyList(postNo);
	}
    //5-6. 자유게시판 댓글 삭제
    @Override
    public int deleteReply(int replyId) {
    	 return boardDao.deleteReply(replyId);
    }
    //5-7. 자유게시판 추천수 증가
    @Override
    public int increaseUpvotes(int postNo) {
    	 return boardDao.increaseUpvotes(postNo);
    };
    
    //6. 지식인 조회 (메인)
    @Override
    public int selectKnowledgeCount() {
    	return boardDao.selectKnowledgeCount();
    };
    @Override
    public int searchKnowledgeCount(String knowledgekind, String keyword) {
    	return boardDao.searchKnowledgeCount(knowledgekind,keyword);
    };
    @Override
    public List<Board> selectKnowledgeList(int page, int pageSize){
    	return boardDao.selectKnowledgeList(page,pageSize);
    };
    @Override
    public List<Knowledge> selectKnowledgeList2(int page, int pageSize){
    	return boardDao.selectKnowledgeList2(page,pageSize);
    };
    @Override
    public List<Answer> selectKnowledgeList3(int page, int pageSize){
    	return boardDao.selectKnowledgeList3(page,pageSize);
    };
    @Override
    public List<Board> searchKnowledgeList(String knowledgekind, String keyword, int page, int pageSize){
    	return boardDao.searchKnowledgeList(knowledgekind,keyword,page,pageSize);
    };
    @Override
    public List<Knowledge> searchKnowledgeList2(String knowledgekind, String keyword, int page, int pageSize){
    	return boardDao.searchKnowledgeList2(knowledgekind,keyword,page,pageSize);
    };
    @Override
    public List<Answer> searchKnowledgeList3(String knowledgekind, String keyword, int page, int pageSize){
    	return boardDao.searchKnowledgeList3(knowledgekind,keyword,page,pageSize);
    };
    //6-1. 지식인 상세 조회
    @Override
    public Board selectKnowledgeDetail(int postNo){
      	 return boardDao.selectKnowledgeDetail(postNo);
    };
    @Override
    public Knowledge selectKnowledgeDetail2(int postNo){
      	 return boardDao.selectKnowledgeDetail2(postNo);
    };
    @Override
    public List<Board> selectKnowledgeDetailAnswer(int postNo){
      	 return boardDao.selectKnowledgeDetailAnswer(postNo);
    };
    //6-2. 지식인 답변 갯수 조회
    @Override
    public int selectKnowledgeAnswerCount(int postNo) {
		return boardDao.selectKnowledgeAnswerCount(postNo);
	}
    //6-3. 지식인 질문 등록
    @Override
    public int insertKnowledgeQuestion(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception {
    	
    	board.setPostTitle(Utils.XSSHandling(board.getPostTitle()));
		board.setPostContent(Utils.XSSHandling(board.getPostContent()));
		board.setPostContent(Utils.newLineHandling(board.getPostContent()));
    	
    	int postNo = boardDao.insertKnowledgeQuestion(board);
    
		return postNo;
    }
    @Override
    public int insertKnowledgeQuestion2(Knowledge knowledge) {
    	
    	
    	int result = boardDao.insertKnowledgeQuestion2(knowledge);
    
		return result;
    }
    //6-4. 지식인 답변 등록
    @Override
    public int insertKnowledgeAnswer(Board board) throws Exception {
    	
    	board.setPostTitle(Utils.XSSHandling(board.getPostTitle()));
		board.setPostContent(Utils.XSSHandling(board.getPostContent()));
		board.setPostContent(Utils.newLineHandling(board.getPostContent()));
    	
    	int postNo = boardDao.insertKnowledgeQuestion(board);
    
		return postNo;
    }
    @Override
    public int insertKnowledgeAnswer2(Answer answer) {
    	
    	
    	int result = boardDao.insertKnowledgeAnswer2(answer);
    
		return result;
    }

    
    //이찬우 구역 끝









    //----------------------------------정승훈----------------------------------
    @Override
    public List<Board> selectStudyList(int currentPage, Map<String, Object> paramMap) {
        return boardDao.selectStudyList(currentPage, paramMap);
    }


    @Override
    public List<StudyApplicant> selectPepleList(Map<String, Object> paramMap) {
        return boardDao.selectPepleList(paramMap);
    }


    //스터디 인원수
    @Override
    public List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap) {

        return boardDao.selectRecruitmentCount(paramMap);
    }


    //스터디 게시글 등록
    @Override
    public int insertStudy(Board b) {
        return boardDao.insertStudy(b);
    }

    @Override
    public Board selectDetailStudy(int postNo) {
        return boardDao.selectDetailStudy(postNo);
    }

    @Override
    public int studyDetailApplicant(int postNo) {
        return boardDao.studyDetailApplicant(postNo);
    }

    @Override
    public List<Reply> selectReplyList(int postNo) {
        return boardDao.selectReplyList(postNo);
    }

    @Override
    public Board selectBoard(int postNo) {
        return boardDao.selectBoard(postNo);
    }

    @Override
    public int insertReport(Map<String, Object> reportInfo) {
        return boardDao.insertReport(reportInfo);
    }


}


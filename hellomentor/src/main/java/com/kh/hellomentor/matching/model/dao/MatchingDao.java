package com.kh.hellomentor.matching.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.hellomentor.matching.model.vo.Matching;
import com.kh.hellomentor.member.model.vo.Member;
import com.kh.hellomentor.member.model.vo.Profile;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.kh.hellomentor.matching.model.vo.Mentoring;


@Repository
public class MatchingDao {

    @Autowired
    private SqlSessionTemplate sqlSession;



    public List<Mentoring> selectList(int currentPage, Map<String, Object> paramMap) {
        int offset = (currentPage -1) * 9;
        int limit  = 9;

        RowBounds rowBounds = new RowBounds(offset,limit);

        return sqlSession.selectList("matchingMapper.selectList",paramMap,rowBounds);
    }

    public int selectListCount(Map<String, Object> paramMap) {
        return sqlSession.selectOne("matchingMapper.selectListCount",paramMap);
    }

    public int selectList(Mentoring mt) {

        return sqlSession.insert("matchingMapper.insertMentoring",mt);

    }

    public List<Member> getMentorList(int userNo) {
        return sqlSession.selectList("matchingMapper.getMentorList",userNo);
    }


    public List<Profile> getMentorProfileList(int userNo) {
        return sqlSession.selectList("matchingMapper.getMentorProfileList",userNo);
    }

    public List<Mentoring> getMentoringList(int userNo) {
        return sqlSession.selectList("matchingMapper.getMentoringList",userNo);
    }

    public List<Matching> getMatchingList(int userNo) {
        return sqlSession.selectList("matchingMapper.getMatchingList",userNo);
    }

    public List<Member> getMentorList2(int userNo) {
        return sqlSession.selectList("matchingMapper.getMentorList2",userNo);
    }

    public List<Profile> getMentorProfileList2(int userNo) {
        return sqlSession.selectList("matchingMapper.getMentorProfileList2",userNo);
    }

    public List<Mentoring> getMentoringList2(int userNo) {
        return sqlSession.selectList("matchingMapper.getMentoringList2",userNo);
    }

    public List<Matching> getMatchingList2(int userNo) {
        return sqlSession.selectList("matchingMapper.getMatchingList2",userNo);
    }

    public void mentoring_cancel(int userNo, int regisNo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userNo", userNo);
        paramMap.put("regisNo", regisNo);
        sqlSession.delete("matchingMapper.mentoringCancel", paramMap);
    }
}

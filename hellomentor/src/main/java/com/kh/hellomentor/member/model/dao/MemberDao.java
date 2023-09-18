package com.kh.hellomentor.member.model.dao;

import com.kh.hellomentor.member.model.vo.Calendar;
import com.kh.hellomentor.member.model.vo.Payment;
import com.kh.hellomentor.member.model.vo.Profile;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.member.model.vo.Member;

import java.util.List;
import java.util.Objects;


@Repository
public class MemberDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public Member loginUser(Member m) {
        Member loginUser = sqlSession.selectOne("memberMapper.loginUser", m);
        Profile profile = sqlSession.selectOne("memberMapper.selectProfile", loginUser.getUserNo());
        if (profile != null) {
            loginUser.setProfile(profile.getFilePath() + profile.getChangeName());
        } else {
            loginUser.setProfile("/img/default-profile.jpg");
        }
        return loginUser;
    }
    
    public int idCheck(String userId) {
    	return sqlSession.selectOne("memberMapper.idCheck", userId);
    }

    public int insertMember(Member m) {
        return sqlSession.insert("memberMapper.insertMember", m);
    }

    public List<Member> getFollowList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowList", userNo);
    }


    public List<Profile> getFollowingProfileList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowingProfileList", userNo);
    }

    public List<Member> getFollwerList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowerList", userNo);
    }

    public List<Profile> getFollowerProfileList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowerProfileList", userNo);
    }

    public void updateMember(Member updatedMember) {
        sqlSession.update("memberMapper.updateProfile", updatedMember);
    }


    public void updateProfileImg(Profile profile) {
        sqlSession.update("memberMapper.updateProfileImg", profile);
    }

    public void insertProfileImg(Profile profile) {
        sqlSession.insert("memberMapper.insertProfileImg", profile);
    }

    public Boolean isProfileImgExists(int userNo) {
        Integer count = sqlSession.selectOne("memberMapper.isProfileImgExists", userNo);
        return count != null && count > 0;
    }

    public List<Payment> getPaymentHistory(int userNo, String type) {
        if (Objects.equals(type, "p")) {
            return sqlSession.selectList("memberMapper.getPaymentHistory", userNo);
        } else {
            return sqlSession.selectList("memberMapper.getExchangeHistory", userNo);
        }
    }


    public void saveMemo(Calendar memoRequest) {
        sqlSession.insert("memberMapper.saveMemo", memoRequest);
    }

    public void updateMemo(Calendar memoRequest) {
        sqlSession.update("memberMapper.updateMemo", memoRequest);
    }

    public Boolean isMemoExists(Calendar memoRequest) {
        List<Calendar> result = sqlSession.selectList("memberMapper.isMemoExists", memoRequest);
        return result != null && !result.isEmpty();
    }

    public void deleteMemo(Calendar memoRequest) {
        sqlSession.delete("memberMapper.deleteMemo", memoRequest);
    }

    public Calendar loadMemo(Calendar memoRequest) {
        return sqlSession.selectOne("memberMapper.loadMemo", memoRequest);
    }

    public boolean performExit(int userNo) {
        int result = sqlSession.update("memberMapper.performExit",userNo);
        if(result > 0) {
            return true;
        }
        return false;

    }
}
package com.kh.hellomentor.admin.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {

    @Autowired
    private SqlSessionTemplate sqlSession;


    public int selectCountList() {
        return sqlSession.selectOne("adminMapper.selectMList");
    }

    public int selectSCountList() {
        return sqlSession.selectOne("adminMapper.selectSList");
    }

    public int selectRCountList() {
        return sqlSession.selectOne("adminMapper.selectRList");
    }

    public int selectICountList() {
        return sqlSession.selectOne("adminMapper.selectIList");
    }


}
package com.kh.springboot.menu.model.dao;

import com.kh.springboot.menu.model.vo.Menu;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MenuDao {
    @Autowired
    private SqlSessionTemplate session;

    public List<Menu> selectMenuList() {
        return session.selectList("menu.selectMenuList");
    }

    public List<Menu> selectMenuListByTypeAndTaste(Map<String, Object> paramMap) {
        return session.selectList("menu.selectMenuListByTypeAndTaste", paramMap);
    }

    public int insertMenu(Menu menu){
        return session.insert("menu.insertMenu", menu);
    }

    public Menu selectOneMenu(String id) {
        return session.selectOne("menu.selectOneMenu",id);
    }

    public int updateMenu(Menu menu) {
        return session.update("menu.updateMenu", menu);
    }

    public int deleteMenu(String id) {
        return session.delete("menu.deleteMenu", id);
    }
}

package com.kh.springboot.menu.model.service;

import com.kh.springboot.menu.model.vo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    List<Menu> selectMenuList();

    List<Menu> selectMenuListByTypeAndTaste(Map<String, Object> paramMap);

    int insertMenu(Menu menu);

    Menu selectOneMenu(String id);

    int updateMenu(Menu menu);

    int deleteMenu(String id);
}

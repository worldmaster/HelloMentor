package com.kh.springboot.menu.controller;

import com.kh.springboot.menu.model.service.MenuService;
import com.kh.springboot.menu.model.vo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // @Controller + @ResponseBody 내부의 모든 hnadler는 @ResponseBody로 처리된다
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menus")
    public List<Menu> menus() {
        List<Menu> list = menuService.selectMenuList();
        log.info("list = {}, ", list);

        return list;
    }

    @GetMapping("/menus/{type}/{taste}")
    public List<Menu> menus(@PathVariable String type,
                            @PathVariable String taste) {
        log.info("type={}, taste={}", type, taste);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", type);
        paramMap.put("taste", taste);
        List<Menu> list = menuService.selectMenuListByTypeAndTaste(paramMap);
        log.info("list = {}, ", list);
        return list;
    }


    /*
    * @RequestBody 요청시 전달할 값중 body에 작성된 "json" 문자열을 java객체로 변환
    * (MenuType 4번에 JSON value어노테이션 필수*/
    @PostMapping("/menu")
    public Map<String, Object> insertMenu( @RequestBody Menu menu){
// Post방식의 경우 사용자가 요청시 전달한 데이터가 Body영역에 들어가있음


        log.info("menu = {}, ", menu);
        int result = menuService.insertMenu(menu);

        Map<String, Object> map = new HashMap<>();
        if(result>0) {
            map.put("msg", "메뉴 등록 성공");
        }else{
            map.put("msg", "메뉴 등록 실패");
        }

        return map;
    }

//    @GetMapping("/menu/{id}")
//    public Menu selectOneMenu(@PathVariable String id){
//        return menuService.selectOneMenu(id);
//    }


    // ResponseEntity를 사용해서 하는 방법
    // 존재하지 않는 메뉴번호를 요청한 경우, null값 반환이 아닌 404 응답을 반환할 예정
    @GetMapping("/menu/{id}")
    public ResponseEntity<Menu> selectOneMenu(@PathVariable String id){

        Menu menu = menuService.selectOneMenu(id);
        if(menu == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(menu);
        }
    }

    @PutMapping("/menu/{id}")
    public Map<String, Object> updateMenu(@RequestBody Menu menu){
        log.info("menu = {}", menu);
        int result = menuService.updateMenu(menu);          // ID는 이미 들어가있어서 menu만 넣어주면 됨

        Map<String, Object> map = new HashMap();
        map.put("msg", "메뉴 수정 성공");

        return map;
    }

    @DeleteMapping("/menu/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        int result = menuService.deleteMenu(id);

        if (result > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }







}

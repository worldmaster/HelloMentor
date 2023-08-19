package com.kh.springboot.menu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// 게터세터, 투스트링 등 포함
@Data
// 매개변수 없는 기본생성자
@NoArgsConstructor

// 모든 매개변수 있는 생성자
@AllArgsConstructor

@Builder
public class Menu {
    private int id;
    private String restaurant;
    private String name;
    private int price;
    private MenuType type; // JAVA상에선 MenuType(직접만든클래스) DB상에선 TYPE(VARCHAR2)
    private String taste;

}

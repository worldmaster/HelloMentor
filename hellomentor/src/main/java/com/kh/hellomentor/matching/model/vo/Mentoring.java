package com.kh.hellomentor.matching.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mentoring {
    private int regisNo;
    private int userNo;
    private String title;
    private String content1;
    private String content2;
    private String content3;
    private String codeLang;
}

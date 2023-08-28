package com.kh.hellomentor.chat.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    private int chatRoomNo;
    private String chatRoomType;
    private int relatedNo;
    private Date createDate;
}

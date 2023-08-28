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
public class ChatMessage {
    private int messageNo;
    private int chatRoomNo;
    private int userNo;
    private String messageText;
    private Date sendDateTime;
}

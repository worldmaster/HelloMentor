package com.kh.hellomentor.chat.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomPerson {
    private int chatRoomNo;
    private int userNo;
}

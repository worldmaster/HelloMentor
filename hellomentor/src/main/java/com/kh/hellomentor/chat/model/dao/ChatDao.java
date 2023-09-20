package com.kh.hellomentor.chat.model.dao;

import com.kh.hellomentor.chat.model.vo.ChatMessageDTO;
import com.kh.hellomentor.chat.model.vo.ChatRoomDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDao {

    @Autowired
    private SqlSessionTemplate session;

    public List<ChatRoomDTO> findAllRooms(int userNo) {

        List<ChatRoomDTO> result = session.selectList("chatMapper.findAllRooms", userNo);

        return result;
    }

    public ChatRoomDTO findRoomById(String roomId) {
        return session.selectOne("chatMapper.findRoomById", roomId);
    }

    public List<ChatMessageDTO> findMessageById(String roomId) {
        return session.selectList("chatMapper.findMessageById", roomId);
    }

    public void insertMessage(ChatMessageDTO message) {
        session.insert("chatMapper.insertMessage", message);
    }
}

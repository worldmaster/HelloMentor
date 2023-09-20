package com.kh.hellomentor.chat.model.repo;

import com.kh.hellomentor.chat.model.dao.ChatDao;
import com.kh.hellomentor.chat.model.vo.ChatMessageDTO;
import com.kh.hellomentor.chat.model.vo.ChatRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomDTO> chatRoomDTOMap;

    @PostConstruct
    private void init() {
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    @Autowired
    private ChatDao chatDao;

    public List<ChatRoomDTO> findAllRooms(int userNo) {
        List<ChatRoomDTO> result = chatDao.findAllRooms(userNo);
        return result;
    }

    public ChatRoomDTO findRoomById(String id) {return chatDao.findRoomById(id);}

    public List<ChatMessageDTO> findMessageById(String id) {
        return chatDao.findMessageById(id);
    }

    public ChatRoomDTO createChatRoomDTO(String name) {
        ChatRoomDTO room = ChatRoomDTO.create(name);
        chatRoomDTOMap.put(room.getRoomId(), room);

        return room;
    }

    public void insertMessage(ChatMessageDTO message) {
        chatDao.insertMessage(message);
    }
}

package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.board.model.vo.Board;

import java.util.List;

public interface MemberService {
    List<Board> getPostsByUserNo(int userNo);

}

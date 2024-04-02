package com.sist.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.manager.model.User;

@Repository("userDao")
public interface UserDao 
{
	// 사용자 리스트
	public List<User> userList(User user); 
	
	// 사용자 총 인원 조회
	public int userListCount(User user);
	
	// 사용자 조회
	public User userSelect(String userId);
	
	// 사용자 정보 수정
	public int userUpdate(User user);
}

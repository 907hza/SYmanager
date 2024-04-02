package com.sist.manager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.manager.dao.UserDao;
import com.sist.manager.model.User;

@Service("userService")
public class UserService 
{
	@Autowired
	private UserDao userDao;
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	// 사용자 리스트
	public List<User> userList(User user)
	{
		List<User> list = null;
		
		try
		{
			list = userDao.userList(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userList Exception", e);
		}
		
		return list;
	}
	
	// 사용자 총 인원 조회
	public int userListCount(User user)
	{
		int count = 0;
		
		try
		{
			count = userDao.userListCount(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userListCount Exception", e);
		}
		
		return count;
	}
	
	// 사용자 조회
	public User userSelect(String userId)
	{
		User user = null;
		
		try
		{
			user = userDao.userSelect(userId);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userSelect Exception", e);
		}
		return user;
	}
	
	// 사용자 정보 수정
	public int userUpdate(User user)
	{
		int count = 0;
		
		try
		{
			count = userDao.userUpdate(user);
		}
		catch(Exception e)
		{
			logger.error("[UserService] userUpdate Exception", e);
		}
		
		return count;
	}
}

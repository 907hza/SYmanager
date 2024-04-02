package com.sist.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.HttpUtil;
import com.sist.common.util.JsonUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Paging;
import com.sist.manager.model.Response;
import com.sist.manager.model.User;
import com.sist.manager.service.UserService;

@Controller("userController")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final int LIST_COUNT = 10;
	private static final int PAGE_COUNT = 10;
	
	@RequestMapping(value="/user/list")
	public String list(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		List<User> list = null;
		
		User user = new User();
		Paging paging = null;
		int totalCount = 0;
		
		int curPage = HttpUtil.get(request, "curPage", 1);
		String status = HttpUtil.get(request, "status","");
		String searchType = HttpUtil.get(request, "searchType",""); // 1 아이디 2 이름
		String searchValue = HttpUtil.get(request, "searchValue","");
		
		user.setStatus(status);
		
		if(!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue))
		{
			if(StringUtil.equals(searchType, "1"))
			{
				user.setUserId(searchValue);
			}
			else if(StringUtil.equals(searchType, "2"))
			{
				user.setUserName(searchValue);
			}
			else
			{
				searchType = "";
				searchValue = "";
			}
		}
		else
		{
			searchType = "";
			searchValue = "";
		}
		totalCount = userService.userListCount(user);
		
		if(totalCount > 0)
		{
			paging = new Paging("/user/list", totalCount, 
									LIST_COUNT, PAGE_COUNT, curPage, "curPage");
			user.setStartRow(paging.getStartRow());
			user.setEndRow(paging.getEndRow());
			
			list = userService.userList(user);
		}
		
		model.addAttribute("list",list);
		model.addAttribute("paging",paging);
		model.addAttribute("curPage", curPage);
		model.addAttribute("searchType", searchValue);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("status", status);
		
		return "/user/list";
	}
	
	// 회원정보 조회
	@RequestMapping(value="/user/update")
	public String update(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String userId = HttpUtil.get(request, "userId");
		
		if(!StringUtil.isEmpty(userId))
		{
			User user = userService.userSelect(userId);
			
			if(user != null)
			{
				model.addAttribute("user", user);
			}
		}
		
		return "/user/update";
	}
	
	// 회원정보 수정
	@RequestMapping(value="/user/updateProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> updateProc(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> res = new Response<Object>();
		
		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		String userName = HttpUtil.get(request, "userName");
		String userEmail = HttpUtil.get(request, "userEmail");
		String status = HttpUtil.get(request, "status");
		
		if(!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userPwd) && !StringUtil.isEmpty(userName)
				&& !StringUtil.isEmpty(userEmail) && !StringUtil.isEmpty(status))
		{
			User user = userService.userSelect(userId);
			
			if(user != null)
			{
				user.setUserPwd(userPwd);
				user.setUserName(userName);
				user.setUserEmail(userEmail);
				user.setStatus(status);
				
				if(userService.userUpdate(user) > 0)
				{
					res.setResponse(0, "success");
				}
				else
				{
					res.setResponse(-1, "server error");
				}
			}
			else
			{
				res.setResponse(404, "not found");
			}
		}
		else
		{
			res.setResponse(400, "bad request");
		}
			
		if(logger.isDebugEnabled())
		{
			logger.debug("[UserController] /updateProc response\n" +
							JsonUtil.toJsonPretty(res));
		}
		
		return res;
	}
}

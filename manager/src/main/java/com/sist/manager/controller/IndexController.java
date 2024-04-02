/**
 * <pre>
 * 프로젝트명 : Manager
 * 패키지명   : com.icia.manager.controller
 * 파일명     : IndexController.java
 * 작성일     : 2021. 7. 30.
 * 작성자     : mslim
 * </pre>
 */
package com.sist.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.CookieUtil;
import com.sist.common.util.HttpUtil;
import com.sist.common.util.JsonUtil;
import com.sist.common.util.StringUtil;
import com.sist.manager.model.Admin;
import com.sist.manager.model.Response;
import com.sist.manager.service.AdminService;


/**
 * <pre>
 * 패키지명   : com.icia.manager.controller
 * 파일명     : IndexController.java
 * 작성일     : 2021. 7. 30.
 * 작성자     : daekk
 * 설명       : 인덱스 컨트롤러
 * </pre>
 */
@Controller("indexController")
public class IndexController
{
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private AdminService adminService;
	
	// 쿠키명
	@Value("#{env['auth.cookie.name']}")
	private String AUTH_COOKIE_NAME;
	
	/**
	 * <pre>
	 * 메소드명   : index
	 * 작성일     : 2021. 7. 30.
	 * 작성자     : daekk
	 * 설명       : 인덱스
	 * </pre>
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return String
	 */
	@RequestMapping(value="/index")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		return "/index";
	}
	
	// 관리자 로그인 ajax
	@RequestMapping(value="/loginProc", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> loginProc(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> res = new Response<Object>();
		
		String admId = HttpUtil.get(request, "admId");
		String admPwd = HttpUtil.get(request, "admPwd");
		
		if(!StringUtil.isEmpty(admId) && !StringUtil.isEmpty(admPwd))
		{
			Admin admin = adminService.adminSelect(admId);
			
			if(admin != null)
			{
				if(StringUtil.equals(admin.getAdmPwd(), admPwd))
				{
					if(StringUtil.equals(admin.getStatus(),"Y"))
					{
						CookieUtil.addCookie(response, "/", -1, AUTH_COOKIE_NAME,
								CookieUtil.stringToHex(admId));
						
						res.setResponse(0, "success");
					}
					else
					{
						res.setResponse(403, "server error");
					}
				}
				else
				{
					res.setResponse(-1, "parameter Exception");
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
			logger.debug("[IndexController] /loginProc response \n" 
					+ JsonUtil.toJsonPretty(res));
		}
		
		return res;
	}
}
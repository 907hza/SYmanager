<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<style>
*, ::after, ::before {
  box-sizing: unset;
}
.btn_img{
  position: relative;
}
.image_text{
  position: absolute;
  left: 3.65rem;
  top:0;
  color: white;
  z-index: 1;
}
</style>
<script type="text/javascript">
$(document).ready(function() 
{
	$("#admId").keyup(function(e){
		if(e.which == 13)
		{
			fn_loginCheck();
		}
	});
	
	$("#admPwd").keyup(function(e){
		if(e.which == 13)
		{
			fn_loginCheck();
		}
	});
	
	$("#admId").focus();
	
});


function fn_loginCheck()
{
	if(icia.common.isEmpty($("#admId").val()))
	{ 
		alert("아이디를 입력하세요.");
		$("#admId").val("");
		$("#admId").focus();
		return;
	}
	
	if(icia.common.isEmpty($("#admPwd").val()))
	{
		alert("비밀번호를 입력하세요.");
		$("#admPwd").val("");
		$("#admPwd").focus();
		return;
	}
	
	icia.ajax.post({
		type:"POST",
		url:"/loginProc",
		data:{
			admId:$("#admId").val(),
			admPwd:$("#admPwd").val()
		},
		success:function(res)
		{
			if(res.code == -1)
			{
				alert("입력 값이 일치하지 않습니다.");
				$("#admId").focus();
			}
			else if(res.code == 0)
			{
				 //alert("로그인 성공");
				location.href = "/user/list";
			}
			else if(res.code == 400)
			{
				alert("입력 값이 올바르지 않습니다.");
				$("#admId").focus();
			}
			else if(res.code == 403)
			{
				alert("로그인 권한이 없습니다.");
				$("#admId").focus();
			}
			else if(res.code == 404)
			{
				alert("존재하지 않는 사용자입니다.");
				$("#admId").focus();
			}
			else
			{
				alert("로그인 중 알 수 없는 오류가 발생했습니다.");
				$("#admId").focus();
			}
		},
		error:function(error)
		{
			alert("로그인 중 오류가 발생했습니당.");
			icia.common.error(error);
		}
	});
}

</script>
</head>
<body id="adminLogin" style="background-color: #E6EBEE;">
<div id="login" style="position: relative; top: 30%;">
	<div class="login-contents" style="width: 580px;height: 18rem; margin: auto;text-align: center;border: .1px solid #D4D6D8; border-radius:20px; background-color: #F4F5F8; box-shadow: 1px 2px #AEB3B8;">
	    <div class="user-input">
	        <form name="loginForm" id="loginForm" method="post" onsubmit="return false" style="height:100%;">
	        <div class="login_block">
	            <h2 style="margin-top: 3%; margin-bottom:3%;">관리자</h2>
	        </div>
	        <div class="input-id">
	            <label for="admId" style="position:relative; left: .4rem; top:-.1rem; height:3rem"><img src="/resources/images/login/account_icon.png" style="height:3.4rem;"></label>
	            <input type="text" id="admId" name="admId" style="font-size:1rem; width: 70%; height:3rem; margin-bottom:3%;" maxlength="50" value="" title="아이디 입력" placeholder="아이디 입력" />
	        </div>
	        <div class="input-password">
	            <label for="admPwd" style="position:relative; left: .4rem; top:-.1rem; height:3rem"><img src="/resources/images/login/password_icon.png" style="height:3.4rem;"></label>
	            <input type="password" id="admPwd" name="admPwd" style="font-size:1rem; width: 70%; height:3rem; margin-bottom:3%;" maxlength="50" title="비밀번호 입력" placeholder="비밀번호 입력" />
	        </div>
	        </form>
	    </div>
	    <a href="javascript:void(0)" onclick="fn_loginCheck()" class="btn_img"><img src="/resources/images/login/system_manager_login_btn.png" style="width:30%;height: 15%;"><span class="image_text">LOGIN</span></a>
	</div>
</div>
</body>
</html>
package com.sist.manager.dao;
import org.springframework.stereotype.Repository;

import com.sist.manager.model.Admin;

@Repository("adminDao")
public interface AdminDao 
{
	// 관리자 조회
	public Admin adminSelect(String admId);
}
package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.UserVO;

public interface UserDAO {
	UserVO selectUser(UserVO vo);
	
	List<UserVO> selectUserList(UserVO vo);
	
	int insertUser(UserVO vo);
	
	int updateUser(UserVO vo);
	
	int deleteUser(UserVO vo);
}

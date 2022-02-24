package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.UserVO;

public interface UserService {
	UserVO selectUser(UserVO vo) throws Exception;
	
	List<UserVO> selectUserList(UserVO vo) throws Exception;
	
	int insertUser(UserVO vo) throws Exception;
	
	int updateUser(UserVO vo) throws Exception;
	
	int deleteUser(UserVO vo) throws Exception;
}

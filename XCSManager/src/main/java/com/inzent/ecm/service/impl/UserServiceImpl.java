package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.UserService;
import com.inzent.ecm.service.dao.UserDAO;
import com.inzent.ecm.util.EgovFileScrty;
import com.inzent.ecm.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userMapper;

	@Override
	public UserVO selectUser(UserVO vo) throws Exception {
		String enpassword = EgovFileScrty.encryptPassword(vo.getPassWD());
    	vo.setPassWD(enpassword);

    	UserVO userVO = userMapper.selectUser(vo);
    	if (userVO != null) {
    		userVO.setPassWD("");
        	
        	return userVO;
    	} else
    		return new UserVO();
    }
	
	@Override
	public List<UserVO> selectUserList(UserVO vo) throws Exception {
		List<UserVO> voList = userMapper.selectUserList(vo);
		
		for (UserVO userVO : voList)
			userVO.setPassWD("");
		
		return voList;
	}
	
	@Override
	public int insertUser(UserVO vo) throws Exception {
		if (vo.getUserId() == null || vo.getPassWD() == null || vo.getPassWD() == null)
			return 0;
		else {
			vo.setPassWD(EgovFileScrty.encryptPassword(vo.getPassWD()));
			return userMapper.insertUser(vo);
		}
	}
	
	@Override
	public int updateUser(UserVO vo) throws Exception {
		if (vo.getUserId() == null)
			return 0;
		else {
			if (vo.getPassWD() != null)
				vo.setPassWD(EgovFileScrty.encryptPassword(vo.getPassWD()));
			return userMapper.updateUser(vo);
		}
	}
	
	@Override
	public int deleteUser(UserVO vo) throws Exception {
		if (vo.getUserId() == null)
			return 0;
		else {
			return userMapper.deleteUser(vo);
		}
	}
}

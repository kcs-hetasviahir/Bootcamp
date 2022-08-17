package com.kcs.attendancesystem.service;

import java.util.List;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.UserVO;
import org.springframework.data.domain.Page;

public interface UserService {

	ResponseVO<List<UserVO>> findAll();

	ResponseVO<Page<UserVO>> findUsers(int pageNo, int pageSize);

	ResponseVO<UserVO> findById(Long id);

	ResponseVO<UserVO> save(UserVO vo);

	ResponseVO<UserVO> update(UserVO vo);

	ResponseVO<Void> delete(Long id);

}

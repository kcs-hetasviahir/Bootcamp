package com.kcs.attendancesystem.dto;

import com.kcs.attendancesystem.core.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {

	private Long id;
	
	private String name;
	
	private String userRole;
	
	private String status;
	private String username;
	private String password;
	
	public static UserVO convert(User user) {
		UserVO vo = new UserVO();
		vo.setId(user.getId());
		vo.setName(user.getName());
		vo.setUserRole(user.getUserRole().name());
		vo.setUsername(user.getUsername());
//		vo.setStatus(user.getStatus() ? "Active" : "Inactive");
		return vo;
	}
}

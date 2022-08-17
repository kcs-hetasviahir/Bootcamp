package com.kcs.attendancesystem.dto;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVO<T> {

	private String status;
	
	private Integer code;
	
	private String message;
	
	private T result;
	
	public static <T> ResponseVO<T> create(Integer code,String status, String message, T data) {
		ResponseVO<T> vo = new ResponseVO<T>();
		vo.code = code;
		vo.message = message;
		vo.result = data;
		vo.status = status;
		return vo;
	}
	
	public static <T> ResponseVO<T> create(Integer code, T data) {
		ResponseVO<T> vo = new ResponseVO<T>();
		vo.code = code;
		vo.message = StringUtils.EMPTY;
		vo.result = data;
		return vo;
	}
	
	public static ResponseVO<Void> create(Integer code, String message) {
		ResponseVO<Void> vo = new ResponseVO<Void>();
		vo.code = code;
		vo.message = message;
		return vo;
	}
}

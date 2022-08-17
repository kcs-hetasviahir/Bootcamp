package com.kcs.attendancesystem.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;


@Getter
public enum ResponseCode{
	
	 SUCCESSFUL(200,"Success"),
	 FAIL(500,"FAIL"),
	 BAD_REQUEST(400, "BAD REQUEST"),
	 DATABASE_ERROR(101,"DATABASE_ERROR"),
	 INVALID_ARGUMENT(102,"INVALID_ARGUMENT"); 
	 
	 static final Map<Integer, ResponseCode> MAP = new HashMap<>();
	 static{
		for (ResponseCode type : values()) {
	           MAP.put(type.id, type);
	       }
	}
		
    private final Integer id;
    private final String name;

    ResponseCode(Integer id, String name) {
        this.id = id;
        this.name = name;
    }	
	
	public static ResponseCode fromId(String string) {
     
        return MAP.get(string);
    }
}




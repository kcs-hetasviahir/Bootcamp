package com.kcs.attendancesystem.utility;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private JsonUtil(){
	}
	
	static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    
	private static ObjectMapper mapper = new ObjectMapper();
    
	static{
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
    }

    @SuppressWarnings("unchecked")
	public static <T> T toObject(final byte[] json, Class<?> aClass) throws JsonParseException, JsonMappingException, IOException  {
    	return (T) mapper.readValue(json, aClass);
      
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T toObject(Object fromValue, Class<?> aClass) {
    	 return (T) mapper.convertValue(fromValue, aClass);
      
    }
}

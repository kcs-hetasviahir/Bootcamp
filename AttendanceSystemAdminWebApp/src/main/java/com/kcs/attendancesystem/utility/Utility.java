package com.kcs.attendancesystem.utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class Utility {

	private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DATE_FORMAT_DOC = new SimpleDateFormat("yyyy-MM-dd");
	
	public static boolean fileUpload(MultipartFile uploadedFile, String fileName) {
		boolean flag = false;
		File file = new File(fileName);
		try {
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			uploadedFile.transferTo(file.getAbsoluteFile());
			flag = true;
		} catch (IllegalStateException | IOException e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}
	
	public static Long convertToLong(String strValue) {
		try {
			if (!StringUtils.isEmpty(strValue)) {
				return Long.parseLong(strValue);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static Date toDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
        	LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
	
	public static String toDateString(Date date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMAT_DOC.format(date);
	}

	
	public static void main(String[] args) {
		System.out.println(toDateString(Calendar.getInstance().getTime()));
	}
}

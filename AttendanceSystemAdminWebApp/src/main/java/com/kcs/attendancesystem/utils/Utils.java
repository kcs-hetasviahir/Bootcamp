package com.kcs.attendancesystem.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kcs.attendancesystem.dto.JwtUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private static final DateFormat DATE_MONTH_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	
	private static final DateFormat IOT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	
	public static JwtUser getLoggedUserFromToken() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (Objects.nonNull(object) && object instanceof JwtUser) {
			return ((JwtUser) object);
		}

		return null;
	}
	
	public static String formatDate(Date date) {
		if (Objects.nonNull(date))
			return DATE_FORMAT.format(date);

		return StringUtils.EMPTY;
	}

	public static String formatDateTime(Date date) {
		if (Objects.nonNull(date))
			return DATE_TIME_FORMAT.format(date);

		return StringUtils.EMPTY;
	}
	
	public static String formatDateMonth(Date date) {
		if (Objects.nonNull(date))
			return DATE_MONTH_FORMAT.format(date);

		return StringUtils.EMPTY;
	}
	
	public static Date parseDate(String date) {
		try {
			if (StringUtils.isNotBlank(date))
				return DATE_FORMAT.parse(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}
	
	public static Date parseIsoDate(String date) {
		try {
			if (StringUtils.isNotBlank(date))
				return IOT_DATE_FORMAT.parse(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}
	
	public static Long diffBetDates(String startDate, String endDate) {
		try {
			if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
				
				LocalDateTime dt1 = LocalDateTime.parse(startDate, dtf).truncatedTo(ChronoUnit.SECONDS);
				LocalDateTime dt2 = LocalDateTime.parse(endDate, dtf).truncatedTo(ChronoUnit.SECONDS);
				
				long diff = Duration.between(dt1, dt2).getSeconds();
				
				return diff;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return 0l;
	}
	
	public static String formatIoTDate(String date) {
		if (StringUtils.isNotBlank(date)) {
			LocalDateTime dt1 = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
			return dt1.atZone(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		
		return StringUtils.EMPTY;
	}
}

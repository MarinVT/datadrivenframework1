package com.datadriver.framework.utils;

import java.util.Date;

public class DateUtils {
	//Test git + github 2222
	public static String getTimeStamp() {
		Date date = new Date();
		return date.toString().replaceAll(":", "_").replaceAll(" ", "_");
	}
	
}

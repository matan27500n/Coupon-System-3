package com.johnbryce.app.util;

import java.util.Date;

public class Utilities {

	@SuppressWarnings("deprecation")
	public static Date convertUtilDateToSQL(java.util.Date date) {
		return new java.sql.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDate() + 1);
	}

}

package com.transformers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	
	public static String dateAsString(LocalDate dt) {
		return DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(dt);
	};
	

}

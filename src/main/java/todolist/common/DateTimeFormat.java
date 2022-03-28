package todolist.common;

import java.util.Date;

public class DateTimeFormat {

	public static String getTime(Date date) throws Exception {
		String dateString = date.toString();
		dateString = dateString.replace("z"," UTC");
		return dateString;
	}
}

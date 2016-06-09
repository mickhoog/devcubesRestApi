package Main;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateParser {
	//parse string date to sql date 
	public static java.sql.Date parseDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date(new java.util.Date().getTime());
		try {
			date1 =  new Date(format.parse(date.substring(0, 10)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return date1;
	}
}

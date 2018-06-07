package clb.beans.enums;

import java.util.ArrayList;
import java.util.List;

public enum Weeks {

	WEEK1(1,"Week 1"),
	WEEK2(2,"Week 2"),
	WEEK3(3,"Week 3"),
	WEEK4(4,"Week 4"),
	WEEK5(5,"Week 5");
	
	
	private int code;
	private String label;
	
	Weeks(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static Weeks getWeekByValue(int weekFromDate) {
		for(Weeks week: values()) {
			if(weekFromDate == week.getCode()) {
				return week;
			}
		}
		
		return null;
	}

	public static Weeks[] getWeeksLimited(int maxWeek) {
		List<Weeks> weeks = new ArrayList<Weeks>();
		
		for(Weeks week: values()) {
			if(week.getCode() <= maxWeek) {
				weeks.add(week);
			}
		}
		
		return weeks.toArray(new Weeks[weeks.size()]);
	}
}

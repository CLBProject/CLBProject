package clb.beans.enums;

public enum Months {

	JANUARY("January",1),
	FEBRUARY("February",2),
	MARCH("March",3),
	APRIL("April",4),
	MAY("May",5),
	JUNE("June",6),
	JULY("July",7),
	AUGUST("August",8),
	SEPTEMBER("September",9),
	OCTOBER("October",10),
	NOVEMBER("November",11),
	DECEMBER("December",12);
	
	private String label;
	private int value;
	
	Months(String label, int value){
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}

	public static Months getMonthByValue(int monthFromDate) {
		for(Months month: values()) {
			if(monthFromDate == month.getValue()) {
				return month;
			}
		}
		
		return null;
	}
	
}

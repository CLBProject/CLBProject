package clb.ui.enums;

public enum Months {

	JANUARY("January",0),
	FEBRUARY("February",1),
	MARCH("March",2),
	APRIL("April",3),
	MAY("May",4),
	JUNE("June",5),
	JULY("July",6),
	AUGUST("August",7),
	SEPTEMBER("September",8),
	OCTOBER("October",9),
	NOVEMBER("November",10),
	DECEMBER("December",11);
	
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

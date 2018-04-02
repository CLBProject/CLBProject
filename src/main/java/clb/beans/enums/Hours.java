package clb.beans.enums;

public enum Hours {

	ZERO(0,"00:00"),
	ONE(1,"01:00"),
	TWO(2,"02:00"),
	THREE(3,"03:00"),
	FOUR(4,"04:00"),
	FIVE(5,"05:00"),
	SIX(6,"06:00"),
	SEVEN(7,"07:00"),
	EIGHT(8,"08:00"),
	NINE(9,"09:00"),
	TEN(10,"10:00"),
	ELEVEN(11,"11:00"),
	TWELVE(12,"12:00"),
	THIRTEEN(13,"13:00"),
	FOURTEEN(14,"14:00"),
	FIFTEEN(15,"15:00"),
	SIXTEEN(16,"16:00"),
	SEVENTEEN(17,"17:00"),
	EIGHTEEN(18,"18:00"),
	NINETEEN(19,"19:00"),
	TWENTY(20,"20:00"),
	TWENTY_ONE(21,"21:00"),
	TWENTY_TWO(22,"22:00"),
	TWENTY_THREE(23,"23:00");
	
	private String label;
	private int value;
	
	Hours(int value, String label){
		this.value = value;
		this.label = label;
	}

	public static Hours getHourByValue(int value) {
		
		for(Hours hour: values()) {
			if(value == hour.getValue()) {
				return hour;
			}
		}
		
		return null;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}
	
	
}

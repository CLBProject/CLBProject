package clb.business.enums;

public enum AnalyzerCommand {
	GET_USERS_INFO("*getUsersInfo*"),
	END_USERS_INFO("*endUsersInfo*"),
	PERSIST_DATA_OBJECT("*persistDataObject*"),
	GET_LATEST_PERSISTED_DATE("*getLatestPersistedDate*"),
	EXIT_PERSIST_DATA_OBJECT("*exitPersistDataObject*"),
	ACKNOWLEDGE("*acknowledge*"),
	END("*end*");

	private String value;

	AnalyzerCommand(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public synchronized static AnalyzerCommand getValueOf(String commandLabel) {
		for(AnalyzerCommand command: values()) {
			if(command.getValue().equals(commandLabel)) {
				return command;
			}
		}
		return null;
	}
	
	
}

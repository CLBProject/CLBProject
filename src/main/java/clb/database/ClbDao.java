package clb.database;

import java.util.List;

import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryAverageEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;

public interface ClbDao{

	void insertAnalyzer(AnalyzerEntity analyzerEntity);
	
	void insertAnalyzerRegistry(AnalyzerRegistryEntity analyzerRegistryEntity);
	
	void insertAnalyzerRegistryAverage(AnalyzerRegistryAverageEntity analyzerRegistryAverageEntity);
	
	void insertDataLogger(DataLoggerEntity dataLoggerEntity);
	
	void insertBuilding(BuildingEntity buildingEntity);
	
	void insertUsersystem(UsersystemEntity userSystemEntity);
	
	void insertUsers(List<UsersystemEntity> users);
}

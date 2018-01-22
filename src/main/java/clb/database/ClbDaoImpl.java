package clb.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryAverageEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;
import clb.database.repository.AnalyzerMongoRepository;
import clb.database.repository.AnalyzerRegistryAverageMongoRepository;
import clb.database.repository.AnalyzerRegistryMongoRepository;
import clb.database.repository.BuildingsMongoRepository;
import clb.database.repository.DataLoggerMongoRepository;
import clb.database.repository.UsersystemMongoRepository;

@Service
public class ClbDaoImpl implements ClbDao{

	@Autowired
	private AnalyzerMongoRepository analyzerMongoRepository;
	
	@Autowired
	private AnalyzerRegistryAverageMongoRepository analyzerRegistryAverageMongoRepository;
	
	@Autowired
	private AnalyzerRegistryMongoRepository analyzerRegistryMongoRepository;
	
	@Autowired
	private BuildingsMongoRepository buildingsMongoRepository;
	
	@Autowired
	private DataLoggerMongoRepository dataLoggerMongoRepository;
	
	@Autowired
	private UsersystemMongoRepository userSystemMongoRepository;
	
	@Autowired
	private AnalyzerRegistryMongoRepository averageRegistryMongoRespository;
	
	public ClbDaoImpl() {
	    
	}
	
	@Override
	public void insertAnalyzer(AnalyzerEntity analyzerEntity) {
		analyzerMongoRepository.insert(analyzerEntity);
	}
	
	@Override
	public void insertAnalyzerRegistry(AnalyzerRegistryEntity analyzerRegistryEntity) {
		analyzerRegistryMongoRepository.insert(analyzerRegistryEntity);
	}
	
	@Override
	public void insertAnalyzerRegistryAverage(AnalyzerRegistryAverageEntity analyzerRegistryAverageEntity) {
		analyzerRegistryAverageMongoRepository.insert(analyzerRegistryAverageEntity);
	}
	
	@Override
	public void insertDataLogger(DataLoggerEntity dataLoggerEntity) {
		dataLoggerMongoRepository.insert(dataLoggerEntity);
	}
	
	@Override
	public void insertBuilding(BuildingEntity buildingEntity) {
		buildingsMongoRepository.insert(buildingEntity);
	}
	
	@Override
	public void insertUsersystem(UsersystemEntity userSystemEntity) {
		userSystemMongoRepository.insert(userSystemEntity);
	}
	
	@Override
	public void insertUsers(List<UsersystemEntity> userSystemEntity) {
		userSystemMongoRepository.insert(userSystemEntity);
	}
}

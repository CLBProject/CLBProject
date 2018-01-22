package clb.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryAverageObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
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
	public void insertAnalyzer(AnalyzerObject analyzerObject) {
		analyzerMongoRepository.insert(analyzerObject.toEntity());
	}
	
	@Override
	public void insertAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject) {
		analyzerRegistryMongoRepository.insert(analyzerRegistryObject.toEntity());
	}
	
	@Override
	public void insertAnalyzerRegistryAverage(AnalyzerRegistryAverageObject analyzerRegistryAverageObject) {
		analyzerRegistryAverageMongoRepository.insert(analyzerRegistryAverageObject.toEntity());
	}
	
	@Override
	public void insertDataLogger(DataLoggerObject dataLoggerObject) {
		dataLoggerMongoRepository.insert(dataLoggerObject.toEntity());
	}
	
	@Override
	public void insertBuilding(BuildingObject buildingObject) {
		buildingsMongoRepository.insert(buildingObject.toEntity());
	}
	
	@Override
	public void insertUsersystem(UsersystemObject userSystemObject) {
		userSystemMongoRepository.insert(userSystemObject.toEntity());
	}
	
	@Override
	public void insertUsers(List<UsersystemObject> userSystemObjectList) {
		userSystemObjectList.stream().forEach(userSObj -> userSystemMongoRepository.insert(userSObj.toEntity()));
	}

	public AnalyzerMongoRepository getAnalyzerMongoRepository() {
		return analyzerMongoRepository;
	}

	public void setAnalyzerMongoRepository(AnalyzerMongoRepository analyzerMongoRepository) {
		this.analyzerMongoRepository = analyzerMongoRepository;
	}

	public AnalyzerRegistryAverageMongoRepository getAnalyzerRegistryAverageMongoRepository() {
		return analyzerRegistryAverageMongoRepository;
	}

	public void setAnalyzerRegistryAverageMongoRepository(
			AnalyzerRegistryAverageMongoRepository analyzerRegistryAverageMongoRepository) {
		this.analyzerRegistryAverageMongoRepository = analyzerRegistryAverageMongoRepository;
	}

	public AnalyzerRegistryMongoRepository getAnalyzerRegistryMongoRepository() {
		return analyzerRegistryMongoRepository;
	}

	public void setAnalyzerRegistryMongoRepository(AnalyzerRegistryMongoRepository analyzerRegistryMongoRepository) {
		this.analyzerRegistryMongoRepository = analyzerRegistryMongoRepository;
	}

	public BuildingsMongoRepository getBuildingsMongoRepository() {
		return buildingsMongoRepository;
	}

	public void setBuildingsMongoRepository(BuildingsMongoRepository buildingsMongoRepository) {
		this.buildingsMongoRepository = buildingsMongoRepository;
	}

	public DataLoggerMongoRepository getDataLoggerMongoRepository() {
		return dataLoggerMongoRepository;
	}

	public void setDataLoggerMongoRepository(DataLoggerMongoRepository dataLoggerMongoRepository) {
		this.dataLoggerMongoRepository = dataLoggerMongoRepository;
	}

	public UsersystemMongoRepository getUserSystemMongoRepository() {
		return userSystemMongoRepository;
	}

	public void setUserSystemMongoRepository(UsersystemMongoRepository userSystemMongoRepository) {
		this.userSystemMongoRepository = userSystemMongoRepository;
	}

	public AnalyzerRegistryMongoRepository getAverageRegistryMongoRespository() {
		return averageRegistryMongoRespository;
	}

	public void setAverageRegistryMongoRespository(AnalyzerRegistryMongoRepository averageRegistryMongoRespository) {
		this.averageRegistryMongoRespository = averageRegistryMongoRespository;
	}
	
}

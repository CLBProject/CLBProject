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
import clb.database.entities.BuildingEntity;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.AnalyzerRegistryAverageEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;

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
	public void saveAnalyzer(AnalyzerObject analyzerObject) {
	    AnalyzerEntity analyzerEntity = analyzerObject.toEntity(); 
		analyzerMongoRepository.save(analyzerEntity);
		analyzerObject.setId(analyzerEntity.getId());
	}
	
	@Override
	public void saveAnalyzerRegistry(AnalyzerRegistryObject analyzerRegistryObject) {
	    AnalyzerRegistryEntity analyzerRegistryEntity = analyzerRegistryObject.toEntity();
		analyzerRegistryMongoRepository.save(analyzerRegistryEntity);
		analyzerRegistryObject.setId(analyzerRegistryEntity.getId());
	}
	
	@Override
	public void saveAnalyzerRegistryAverage(AnalyzerRegistryAverageObject analyzerRegistryAverageObject) {
	    AnalyzerRegistryAverageEntity analyzerRegistryAverageEntity = analyzerRegistryAverageObject.toEntity();
		analyzerRegistryAverageMongoRepository.save(analyzerRegistryAverageEntity);
		analyzerRegistryAverageObject.setId(analyzerRegistryAverageEntity.getId());
	}
	
	@Override
	public void saveDataLogger(DataLoggerObject dataLoggerObject) {
	    DataLoggerEntity dataLoggerEntity = dataLoggerObject.toEntity(); 
		dataLoggerMongoRepository.save(dataLoggerEntity);
		dataLoggerObject.setDataloggerid(dataLoggerEntity.getDataloggerid());
	}
	
	@Override
	public void saveBuilding(BuildingObject buildingObject) {
	    BuildingEntity buildingEntity = buildingObject.toEntity();
		buildingsMongoRepository.save(buildingEntity);
		buildingObject.setBuildingid(buildingEntity.getBuildingid());
	}
	
	@Override
	public void saveUsersystem(UsersystemObject userSystemObject) {
	    UsersystemEntity userSystemEntity = userSystemObject.toEntity();
		userSystemMongoRepository.save(userSystemEntity);
		userSystemEntity.setUserid(userSystemObject.getUserid());
	}

    @Override
    public UsersystemEntity userCanLogin( String userName, String password ) {
       return userSystemMongoRepository.findUserbyUsernameAndPassword( userName, password );
    }
    
    @Override
    public UsersystemEntity findUserByToken( String token) {
       return userSystemMongoRepository.findUserbyToken( token );
    }
	
	@Override
	public void saveUsers(List<UsersystemObject> userSystemObjectList) {
		userSystemObjectList.stream().forEach(userSObj -> userSystemMongoRepository.save(userSObj.toEntity()));
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

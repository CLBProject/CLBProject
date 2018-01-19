package clb.database;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;

import clb.database.entities.AnalyzerRegistryAverageEntity;
import clb.database.entities.ClbEntity;
import clb.database.repository.AnalyzerMongoRepository;
import clb.database.repository.AnalyzerRegistryAverageMongoRepository;
import clb.database.repository.AnalyzerRegistryMongoRepository;
import clb.database.repository.BuildingsMongoRepository;
import clb.database.repository.DataLoggerMongoRepository;
import clb.database.repository.UsersystemMongoRepository;

public class ClbDaoImpl implements ClbDao{

    @Autowired
    private UsersystemMongoRepository usersMongoRepository;

    @Autowired
    private BuildingsMongoRepository buildingsMongoRepository;

    @Autowired
    private DataLoggerMongoRepository dataLoggerMongoRepository;

    @Autowired
    private AnalyzerMongoRepository analyzerMongoRepository;

    @Autowired
    private AnalyzerRegistryMongoRepository analyzerRegistryMongoRepository;

    @Autowired
    private AnalyzerRegistryAverageMongoRepository analyzerRegistryAverageMongoRepository;
    
    @Autowired
    private MongoOperations mongoOperations;
    
    @Override
    public void insert(ClbEntity clbEntity) {
    	if(clbEntity instanceof AnalyzerRegistryAverageEntity) {
    		AnalyzerRegistryAverageEntity analyzerRegistryAverageEntity = (AnalyzerRegistryAverageEntity)clbEntity;
    		analyzerRegistryAverageMongoRepository.insert(analyzerRegistryAverageEntity);
    	}
    }
	
    @Override
	public void insert(List<ClbEntity> clbEntity) {
		// TODO Auto-generated method stub
		
	}
    
	private long getNextSequence(String seqName)
    {
        AnalyzerRegistryAverageEntity analyzerRegistryAverage = mongoOperations.findAndModify(
            query(where("_id").is(seqName)),
            new Update().inc("seq",1),
            options().returnNew(true).upsert(true),
            AnalyzerRegistryAverageEntity.class);
        return analyzerRegistryAverage.getRegid();
    }

	public UsersystemMongoRepository getUsersMongoRepository() {
		return usersMongoRepository;
	}

	public void setUsersMongoRepository(UsersystemMongoRepository usersMongoRepository) {
		this.usersMongoRepository = usersMongoRepository;
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

	public AnalyzerMongoRepository getAnalyzerMongoRepository() {
		return analyzerMongoRepository;
	}

	public void setAnalyzerMongoRepository(AnalyzerMongoRepository analyzerMongoRepository) {
		this.analyzerMongoRepository = analyzerMongoRepository;
	}

	public AnalyzerRegistryMongoRepository getAnalyzerRegistryMongoRepository() {
		return analyzerRegistryMongoRepository;
	}

	public void setAnalyzerRegistryMongoRepository(AnalyzerRegistryMongoRepository analyzerRegistryMongoRepository) {
		this.analyzerRegistryMongoRepository = analyzerRegistryMongoRepository;
	}

	public AnalyzerRegistryAverageMongoRepository getAnalyzerRegistryAverageMongoRepository() {
		return analyzerRegistryAverageMongoRepository;
	}

	public void setAnalyzerRegistryAverageMongoRepository(
			AnalyzerRegistryAverageMongoRepository analyzerRegistryAverageMongoRepository) {
		this.analyzerRegistryAverageMongoRepository = analyzerRegistryAverageMongoRepository;
	}

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
    
    
}

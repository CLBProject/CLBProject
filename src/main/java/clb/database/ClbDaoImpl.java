package clb.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.business.objects.BuildingMeterParameterObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.BuildingMeterEntity;
import clb.database.entities.BuildingMeterParameterEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;
import clb.database.repository.AnalyzerMongoRepository;
import clb.database.repository.BuildingsMetersMongoRepository;
import clb.database.repository.BuildingsMetersParametersMongoRepository;
import clb.database.repository.BuildingsMongoRepository;
import clb.database.repository.DataLoggerMongoRepository;
import clb.database.repository.UsersystemMongoRepository;

@Service
public class ClbDaoImpl implements ClbDao, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    @Autowired
    private AnalyzerMongoRepository analyzerMongoRepository;

    @Autowired
    private BuildingsMongoRepository buildingsMongoRepository;

    @Autowired
    private BuildingsMetersMongoRepository buildingsMetersMongoRepository;

    @Autowired
    private BuildingsMetersParametersMongoRepository buildingsMetersParametersMongoRepository;

    @Autowired
    private DataLoggerMongoRepository dataLoggerMongoRepository;

    @Autowired
    private UsersystemMongoRepository userSystemMongoRepository;

    private static final String ANALYZER_REGISTIES_COLL_NAME = "AnalyzerRegistries";
    
    @Autowired
    private MongoTemplate mongoTemplate;

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

        String collectionName = formatTimeToAnalyzerRegistryCollectionName(analyzerRegistryObject.getCurrenttime());

        DBCollection analyzerRegCol = null;
        
        if(mongoTemplate.collectionExists( collectionName )) {
            analyzerRegCol = mongoTemplate.getCollection( collectionName);
        }
        else analyzerRegCol = mongoTemplate.createCollection(collectionName );
        
        analyzerRegCol.insert( analyzerRegistryEntity.toDbObject() );
        analyzerRegistryObject.setId(analyzerRegistryEntity.getId());
    }
    
    private String formatTimeToAnalyzerRegistryCollectionName(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( time );
        
        String year = cal.get(Calendar.YEAR) + "";
        String month = cal.get(Calendar.MONTH) >= 10 ? "" + cal.get(Calendar.MONTH) : "0" + cal.get(Calendar.MONTH);
        String day = cal.get(Calendar.DAY_OF_MONTH) >= 10 ? "" + cal.get(Calendar.DAY_OF_MONTH) : "0" + cal.get(Calendar.DAY_OF_MONTH);
        
        return ANALYZER_REGISTIES_COLL_NAME + "_"+ year+month+day;
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
    public void saveBuildingMeter(BuildingMeterObject buildingMeterObject) {
        BuildingMeterEntity buildingMeterEntity = buildingMeterObject.toEntity();
        buildingsMetersMongoRepository.save(buildingMeterEntity);
        buildingMeterObject.setBuildingMeterId( buildingMeterEntity.getBuildingMeterId() );
    }

    @Override
    public void saveBuildingMeterParameter(BuildingMeterParameterObject buildingMeterParameterObject) {
        BuildingMeterParameterEntity buildingMeterParameterEntity = buildingMeterParameterObject.toEntity();
        buildingsMetersParametersMongoRepository.save(buildingMeterParameterEntity);
        buildingMeterParameterObject.setBuildingMeterParameterId( buildingMeterParameterEntity.getBuildingMeterParameterId());
    }

    @Override
    public void saveUsersystem(UsersystemObject userSystemObject) {
        UsersystemEntity userSystemEntity = userSystemObject.toEntity();
        userSystemMongoRepository.save(userSystemEntity);
        userSystemEntity.setUserid(userSystemObject.getUserid());
    }

    @Override
    public UsersystemObject findUserByToken( String token) {
        UsersystemEntity userEntity = userSystemMongoRepository.findUserbyToken( token );
        return userEntity != null ? new UsersystemObject(userEntity) : null;
    }

    @Override
    public UsersystemObject findUserByUserName( String userName ) {
        UsersystemEntity userEntity = userSystemMongoRepository.findUserbyUsername( userName ); 
        return userEntity != null ? new UsersystemObject(userEntity) : null;
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

    @Override
    public List<BuildingObject> findUserBuildings( String userName ) {
        return buildingsMongoRepository.findBuildingsByUsername( userName ).stream().map(BuildingObject::new).collect(Collectors.toList());
    }

    public BuildingsMetersMongoRepository getBuildingsMetersMongoRepository() {
        return buildingsMetersMongoRepository;
    }

    public void setBuildingsMetersMongoRepository( BuildingsMetersMongoRepository buildingsMetersMongoRepository ) {
        this.buildingsMetersMongoRepository = buildingsMetersMongoRepository;
    }

    public BuildingsMetersParametersMongoRepository getBuildingsMetersParametersMongoRepository() {
        return buildingsMetersParametersMongoRepository;
    }

    public void setBuildingsMetersParametersMongoRepository(
            BuildingsMetersParametersMongoRepository buildingsMetersParametersMongoRepository ) {
        this.buildingsMetersParametersMongoRepository = buildingsMetersParametersMongoRepository;
    }

    @Override
    public List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId, Date timeFrame ) {
        
        final String collectionName = formatTimeToAnalyzerRegistryCollectionName( timeFrame );
        
        DBCollection collection = this.mongoTemplate.getCollection( collectionName );
        
        DBObject dbObj = new BasicDBObject("analyzerId",analyzerId);
        
        //Missing Reset Dates
        //dbObj.put( "currenttime", BasicDBObjectBuilder.start("$gte", fromDate).add("$lte", toDate).get() );
        
        final List<AnalyzerRegistryObject> analyzerRegistries = new ArrayList<AnalyzerRegistryObject>();
        
        collection.find(dbObj).forEach( result -> {
            AnalyzerRegistryEntity analyzerReg = new AnalyzerRegistryEntity();
            analyzerReg.setId((String)result.get("id") );
            analyzerReg.setAnalyzerId((String)result.get("analyzerId")); 
            analyzerReg.setAl1( (Double)result.get("al1")  );
            
            analyzerRegistries.add( new AnalyzerRegistryObject(analyzerReg) );
        });

        return analyzerRegistries;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate( MongoTemplate mongoTemplate ) {
        this.mongoTemplate = mongoTemplate;
    }

}

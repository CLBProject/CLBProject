package clb.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.BuildingMeterEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;
import clb.database.repository.AnalyzerMongoRepository;
import clb.database.repository.BuildingsMetersMongoRepository;
import clb.database.repository.BuildingsMongoRepository;
import clb.database.repository.DataLoggerMongoRepository;
import clb.database.repository.UsersystemMongoRepository;
import clb.global.DateUtils;

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

		String collectionName = DateUtils.getInstance().concatTimeWithString(ANALYZER_REGISTIES_COLL_NAME,analyzerRegistryObject.getCurrenttime());

		DBCollection analyzerRegCol = null;

		if(mongoTemplate.collectionExists( collectionName )) {
			analyzerRegCol = mongoTemplate.getCollection( collectionName);
		}
		else analyzerRegCol = mongoTemplate.createCollection(collectionName );

		AnalyzerRegistryEntity analyzerRegistryEntity = analyzerRegistryObject.toEntity();

		analyzerRegCol.insert( analyzerRegistryEntity.toDbObject() );
		analyzerRegistryObject.setId(analyzerRegistryEntity.getId());
	}


	@Override
	public void saveAnalyzerRegistries( List<AnalyzerRegistryObject> analyzersRegistries ) {

		DBCollection analyzerRegCol = null;
		String currentCollectionName = "";

		for(AnalyzerRegistryObject analyzerRegistryObject : analyzersRegistries) {
			String collectionName =  DateUtils.getInstance().concatTimeWithString(ANALYZER_REGISTIES_COLL_NAME,analyzerRegistryObject.getCurrenttime());

			if(!currentCollectionName.equals( collectionName )) {

				if(mongoTemplate.collectionExists( collectionName )) {
					analyzerRegCol = mongoTemplate.getCollection( collectionName);
				}
				else {
					analyzerRegCol = mongoTemplate.createCollection(collectionName );
					currentCollectionName = collectionName;
				}
			}

			AnalyzerRegistryEntity analyzerRegistryEntity = analyzerRegistryObject.toEntity();

			analyzerRegCol.insert( analyzerRegistryEntity.toDbObject() );
			analyzerRegistryObject.setId(analyzerRegistryEntity.getId());
		}
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
	public void saveUsersystem(UsersystemObject userSystemObject) {
		UsersystemEntity userSystemEntity = userSystemObject.toEntity();
		userSystemMongoRepository.save(userSystemEntity);
		userSystemEntity.setUserid(userSystemObject.getUserid());
	}

	@Override
	public List<UsersystemObject> getAllUsers(){
		return userSystemMongoRepository.findAll().stream().map(UsersystemObject::new).collect(Collectors.toList());
	}

	@Override
	public List<BuildingObject> getAllBuildings(){
		return buildingsMongoRepository.findAll().stream().map(BuildingObject::new).collect(Collectors.toList());
	}

	@Override
	public List<DataLoggerObject> getAllDataLoggers() {
		return dataLoggerMongoRepository.findAll().stream().map(DataLoggerObject::new).collect(Collectors.toList());
	}

	@Override
	public List<AnalyzerObject> getAllAnalyzers() {
		return analyzerMongoRepository.findAll().stream().map(AnalyzerObject::new).collect(Collectors.toList());
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

	@Override
	public List<AnalyzerRegistryObject> getDayHourRegistriesFromAnalyzer( String analyzerId, Date from, Date to) {
		return  processRegistries(analyzerId, from, to);
	}


	@Override
	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay) {

		List<AnalyzerRegistryObject> weekRegistries = new ArrayList<AnalyzerRegistryObject>();

		if(DateUtils.getInstance().isTheSameDay(lastDay, firstDay)) {
			final Date nextDay = DateUtils.getInstance().getDay(firstDay,true);
			weekRegistries.addAll(processRegistries(analyzerId, firstDay,nextDay));
		}

		while(!DateUtils.getInstance().isTheSameDay(lastDay, firstDay)){
			final Date nextDay = DateUtils.getInstance().getDay(firstDay,true);

			weekRegistries.addAll(processRegistries(analyzerId, firstDay,nextDay));

			firstDay = nextDay;
		} 


		return weekRegistries;
	}

	@Override
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay) {

		List<AnalyzerRegistryObject> monthRegistries = new ArrayList<AnalyzerRegistryObject>();

		if(DateUtils.getInstance().isTheSameDay(lastDay, firstDay)) {
			final Date nextDay = DateUtils.getInstance().getDay(firstDay,true);
			monthRegistries.addAll(processRegistries(analyzerId, firstDay,nextDay));
		}

		while(!DateUtils.getInstance().isTheSameDay(lastDay, firstDay)){
			monthRegistries.addAll(processRegistries(analyzerId, firstDay, 
					DateUtils.getInstance().getDay(firstDay,true)));

			firstDay = DateUtils.getInstance().getDay(firstDay, true);
		} 

		return monthRegistries;
	}



	private List<AnalyzerRegistryObject> processRegistries(final String analyzerId, final Date previousTimeFrame, final Date timeFrameNow){

		final String collectionName =  DateUtils.getInstance().concatTimeWithString(ANALYZER_REGISTIES_COLL_NAME, previousTimeFrame );

		DBCollection collection = this.mongoTemplate.getCollection( collectionName );
		DBObject dbObj = new BasicDBObject("analyzerId",analyzerId);
		dbObj.put( "currenttime", BasicDBObjectBuilder.start("$gte",previousTimeFrame ).add("$lte", timeFrameNow).get() );

		final List<AnalyzerRegistryObject> analyzerRegistries = new ArrayList<AnalyzerRegistryObject>();

		collection.find(dbObj).forEach( result -> {
			AnalyzerRegistryEntity analyzerReg = new AnalyzerRegistryEntity();
			analyzerReg.setId((String)result.get("id") );
			analyzerReg.setAnalyzerId((String)result.get("analyzerId")); 
			analyzerReg.setVlnsys( (Double)result.get("vlnsys") );
			analyzerReg.setVllsys( (Double)result.get("vllsys") );
			analyzerReg.setKwsys( (Double)result.get("kwsys")  );
			analyzerReg.setKvasys( (Double)result.get("kvasys")  );
			analyzerReg.setKvarsys( (Double)result.get("kvarsys")  );
			analyzerReg.setPfsys( (Double)result.get("pfsys")  );
			analyzerReg.setHz( (Double)result.get("hz")  );
			analyzerReg.setAsys( (Double)result.get("asys")  );
			analyzerReg.setCurrenttime( (Date)result.get( "currenttime" ) );

			analyzerRegistries.add( new AnalyzerRegistryObject(analyzerReg) );
		});

		return analyzerRegistries;
	}

	@Override
	public Date getLowestAnalyzerRegistryDate() {
		Optional<Date> hasDate = this.mongoTemplate.getCollectionNames().stream()
				.filter(collname -> collname.startsWith(ANALYZER_REGISTIES_COLL_NAME))
				.map(collName -> {
					String dateRegistry = collName.split(ANALYZER_REGISTIES_COLL_NAME + "_")[1];

					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, Integer.parseInt(dateRegistry.substring(0,4)));
					cal.set(Calendar.MONTH, Integer.parseInt(dateRegistry.substring(4,6)));
					cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateRegistry.substring(6,8)));

					return cal.getTime();
				})
				.sorted()
				.findFirst();

		if(hasDate.isPresent())
			return hasDate.get();

		return null;
	}

	@Override
	public String[] getYearsAvailable() {
		Set<String> yearsValues = this.mongoTemplate.getCollectionNames().stream()
				.filter(collname -> collname.startsWith(ANALYZER_REGISTIES_COLL_NAME))	
				.map( colName -> colName.split("_")[1].substring(0, 4))
				.collect(Collectors.toSet());

		return yearsValues.toArray(new String[yearsValues.size()]);
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate( MongoTemplate mongoTemplate ) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Long getLatestDateForAnalyzer(String analyzerCodeName) {

		AnalyzerEntity analyzer = analyzerMongoRepository.findAnalyzerByCodename(analyzerCodeName);

		if(analyzer != null) {
			
			List<Date> listTimes = new ArrayList<Date>();

			mongoTemplate.getCollectionNames().stream()
					.filter(collname -> collname.startsWith(ANALYZER_REGISTIES_COLL_NAME))
					.sorted((f1, f2) -> f2.compareTo(f1))
					.forEach(collName -> 
						 mongoTemplate.getCollection(collName).find(new BasicDBObject("analyzerId",analyzer.getId()))
								 .forEach(result -> listTimes.add((Date)result.get( "currenttime" ))));
			
			return listTimes.stream().sorted((f1, f2) -> f2.compareTo(f1)).findFirst().orElse(null).getTime();
		}

		return null;
	}



}

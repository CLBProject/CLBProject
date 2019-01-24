package clb.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import clb.business.objects.AnalyzerMeterObject;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.business.objects.UsersystemObject;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerMeterEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.DivisionEntity;
import clb.database.entities.UsersystemEntity;
import clb.database.repository.AnalyzerMetersMongoRepository;
import clb.database.repository.AnalyzerMongoRepository;
import clb.database.repository.BuildingsMongoRepository;
import clb.database.repository.DivisionRepository;
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
	private AnalyzerMetersMongoRepository buildingsMetersMongoRepository;

	@Autowired
	private UsersystemMongoRepository userSystemMongoRepository;
	
	@Autowired
	private DivisionRepository divisionRepository;

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
	public void saveBuilding(BuildingObject buildingObject) {
		BuildingEntity buildingEntity = buildingObject.toEntity();
		buildingsMongoRepository.save(buildingEntity);
		buildingObject.setBuildingid(buildingEntity.getBuildingid());
	}

	@Override
	public void saveAnalyzerMeter(AnalyzerMeterObject buildingMeterObject) {
		AnalyzerMeterEntity buildingMeterEntity = buildingMeterObject.toEntity();
		buildingsMetersMongoRepository.save(buildingMeterEntity);
		buildingMeterObject.setMeterId( buildingMeterEntity.getBuildingMeterId() );
	}

	@Override
	public void saveUsersystem(UsersystemObject userSystemObject) {
		UsersystemEntity userSystemEntity = userSystemObject.toEntity();
		userSystemMongoRepository.save(userSystemEntity);
	}
	
	@Override
	public void saveDivision(DivisionObject divisionObject) {
		DivisionEntity divisionEntity = divisionObject.toEntity();
		divisionRepository.save(divisionEntity);
		divisionObject.setDivisionid(divisionEntity.getDivisionid());
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

	public AnalyzerMetersMongoRepository getBuildingsMetersMongoRepository() {
		return buildingsMetersMongoRepository;
	}

	public void setBuildingsMetersMongoRepository( AnalyzerMetersMongoRepository buildingsMetersMongoRepository ) {
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
	public String[] getDatesAvailable() {
		Set<String> yearsValues = this.mongoTemplate.getCollectionNames().stream()
				.filter(collname -> collname.startsWith(ANALYZER_REGISTIES_COLL_NAME))	
				.map(colName -> colName.split("_")[1])
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
			.forEach(collName -> 
			mongoTemplate.getCollection(collName).find(new BasicDBObject("analyzerId",analyzer.getId()))
			.forEach(result -> {
				Object currentTime = result.get( "currenttime" );

				if(currentTime != null) {
					listTimes.add((Date)currentTime);
				}}));

			Collections.sort(listTimes);

			return listTimes.size() > 0 ? listTimes.get(listTimes.size()-1).getTime() : null;
		}

		return null;
	}

	@Override
	public BuildingObject getBuildingByName(String buildingName) {
		BuildingEntity bEntity = buildingsMongoRepository.getBuildingByName(buildingName);

		if(bEntity != null) {
			return new BuildingObject(bEntity);
		}

		return null;
	}

	@Override
	public AnalyzerObject getAnalyzerByCodeName(String analyzerCodeName) {
		AnalyzerEntity aEntity = analyzerMongoRepository.findAnalyzerByCodename(analyzerCodeName);

		if(aEntity != null) {
			return new AnalyzerObject(aEntity);
		}

		return null;
	}

	

}

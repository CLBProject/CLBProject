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
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.ClbObject;
import clb.business.objects.DivisionObject;
import clb.business.objects.UsersystemObject;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.ClbEntity;
import clb.database.entities.DivisionEntity;
import clb.database.entities.UsersystemEntity;
import clb.database.repository.ClbMongoRepository;
import clb.global.DateUtils;

@Component
public class ClbDaoImpl implements ClbDao, Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private ClbMongoRepository clbRepository;

	private static final String ANALYZER_REGISTIES_COLL_NAME = "AnalyzerRegistries";

	@Autowired
	private MongoTemplate mongoTemplate;

	public ClbDaoImpl() {
	}
	
	private Object findById(String id, Class<?> classToSearch) {
		return mongoTemplate.findById(id, classToSearch);
	} 

	@Override
	public void deleteClbObject(ClbObject object) {
		mongoTemplate.remove(object.toEntity());
	}
	
	@Override
	public void saveClbObject(ClbObject clbObj) {
		ClbEntity clbEntity = clbObj.toEntity(); 
		mongoTemplate.save(clbEntity);
		clbObj.setId(clbEntity.getId());
	}

	@Override
	public void saveAnalyzerRegistries( Set<AnalyzerRegistryObject> analyzersRegistries, String analyzerId ) {

		DBCollection analyzerRegCol = null;
		String currentCollectionName = "";
		AnalyzerObject analyzer = findAnalyzerById(analyzerId);
		
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

			AnalyzerRegistryEntity analyzerRegistryEntity = (AnalyzerRegistryEntity) analyzerRegistryObject.toEntity();
			
			DBObject objId = analyzerRegistryEntity.toDbObject();
			analyzerRegCol.save( objId );
			String _id = objId.get("_id").toString();
			analyzerRegistryObject.setId(_id);

			analyzer.addAnalyzerRegistryId(_id);
		}

		saveClbObject(analyzer);
	}

	@Override
	public Set<UsersystemObject> getAllUsers(){
		return mongoTemplate.findAll(UsersystemEntity.class).stream().map(UsersystemObject::new).collect(Collectors.toSet());
	}
	
	@Override
	public AnalyzerObject findAnalyzerById(String analyzerId) {
		AnalyzerEntity analyzer = (AnalyzerEntity) findById(analyzerId, AnalyzerEntity.class);
		return analyzer != null ? new AnalyzerObject(analyzer) : null;
	}
	

	@Override
	public DivisionObject findDivisionById(String parentId) {
		DivisionEntity division = (DivisionEntity) findById(parentId, DivisionEntity.class);
		return division != null ? new DivisionObject(division) : null;
	}

	@Override
	public BuildingObject findBuildingById(String buildingId) {
		BuildingEntity building = (BuildingEntity) mongoTemplate.findById(buildingId, BuildingEntity.class);
		return building != null ? new BuildingObject(building) : null;
	}


	@Override
	public UsersystemObject findUserByToken( String token) {
		UsersystemEntity userEntity = clbRepository.findUserbyToken( token );
		return userEntity != null ? new UsersystemObject(userEntity) : null;
	}

	@Override
	public UsersystemObject findUserByUserName( String userName ) {
		UsersystemEntity userEntity = (UsersystemEntity) findById(userName, UsersystemEntity.class); 
		return userEntity != null ? new UsersystemObject(userEntity) : null;
	}

	@Override
	public void saveClbObjects(Set<ClbObject> clbObjects) {
		clbObjects.stream().forEach(userSObj -> saveClbObject(userSObj));
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
	public Long getLatestDateForAnalyzer(String analyzerId) {

		AnalyzerEntity analyzer = (AnalyzerEntity) findById(analyzerId, AnalyzerEntity.class);

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
	public void deleteDivisionCascade(DivisionObject currentDivision) {

		if(currentDivision.getChildrenDivisions() != null) {
			for(DivisionObject divisionChild: currentDivision.getChildrenDivisions()) {
				deleteDivisionCascade(divisionChild);
			}
		}

		mongoTemplate.remove(currentDivision.toEntity());
	}

	public ClbMongoRepository getClbRepository() {
		return clbRepository;
	}

	public void setClbRepository(ClbMongoRepository clbRepository) {
		this.clbRepository = clbRepository;
	}

	@Override
	public Set<AnalyzerObject> getAnalyzersFromBuilding(BuildingObject building) {
		// TODO Auto-generated method stub
		return null;
	}

}

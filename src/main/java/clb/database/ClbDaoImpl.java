package clb.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
    
    @Autowired
    ModelMapper modelMapper;

	public ClbDaoImpl() {
	}
	
	private Object findById(String id, Class<?> classToSearch) {
		return mongoTemplate.findById(id, classToSearch);
	} 

	@Override
	public void deleteClbObject(ClbObject object) {
		mongoTemplate.remove(modelMapper.map(object, ClbEntity.class));
	}
	
	@Override
	public void saveClbObject(ClbObject clbObj) {
		ClbEntity clbEntity = (ClbEntity) modelMapper.map(clbObj, clbObj.getEntity());
		mongoTemplate.save(clbEntity);
		clbObj.setId(clbEntity.getId());
	}

	@Override
	public void saveAnalyzerRegistries( Set<AnalyzerRegistryObject> analyzersRegistries, String analyzerId ) {

		DBCollection analyzerRegCol = null;
		String currentCollectionName = "";
		AnalyzerEntity analyzer =  mongoTemplate.findById(analyzerId, AnalyzerEntity.class);
		
		for(AnalyzerRegistryObject analyzerRegistryObject : analyzersRegistries) {
			String collectionName =  DateUtils.getInstance().concatTimeWithString(ANALYZER_REGISTIES_COLL_NAME,analyzerRegistryObject.getCurrenttime());

			if(!currentCollectionName.equals( collectionName )) {

				if(mongoTemplate.collectionExists( collectionName )) {
					analyzerRegCol = mongoTemplate.getCollection( collectionName);
				}
				else {
					analyzerRegCol = mongoTemplate.createCollection(collectionName );
					analyzerRegCol.createIndex("analyzerId");
					currentCollectionName = collectionName;
				}
			}

			AnalyzerRegistryEntity analyzerRegistryEntity = modelMapper.map(analyzerRegistryObject, AnalyzerRegistryEntity.class);
			
			DBObject objId = analyzerRegistryEntity.toDbObject();
			analyzerRegCol.save( objId );
			String _id = objId.get("_id").toString();
			analyzerRegistryObject.setId(_id);
			
			
			analyzer.putRegistryCollection(collectionName);
		}
		
		mongoTemplate.save(analyzer);
	}

	@Override
	public Set<UsersystemObject> getAllUsers(){
		return mongoTemplate.findAll(UsersystemEntity.class).stream()
				.map( userNew -> modelMapper.map(userNew, UsersystemObject.class))
				.collect(Collectors.toSet());
	}
	
	@Override
	public AnalyzerObject findAnalyzerById(String analyzerId) {
		AnalyzerEntity analyzer = (AnalyzerEntity) findById(analyzerId, AnalyzerEntity.class);
		return analyzer != null ? modelMapper.map(analyzer, AnalyzerObject.class) : null;
	}
	

	@Override
	public DivisionObject findDivisionById(String parentId) {
		DivisionEntity division = (DivisionEntity) findById(parentId, DivisionEntity.class);
		return division != null ? modelMapper.map(division, DivisionObject.class) : null;
	}

	@Override
	public BuildingObject findBuildingById(String buildingId) {
		BuildingEntity building = (BuildingEntity) mongoTemplate.findById(buildingId, BuildingEntity.class);
		return building != null ? modelMapper.map(building, BuildingObject.class) : null;
	}


	@Override
	public UsersystemObject findUserByToken( String token) {
		UsersystemEntity userEntity = clbRepository.findUserbyToken( token );
		return userEntity != null ? modelMapper.map(userEntity, UsersystemObject.class) : null;
	}

	@Override
	public UsersystemObject findUserByUserName( String userName ) {
		UsersystemEntity userEntity = (UsersystemEntity) findById(userName, UsersystemEntity.class); 
		return userEntity != null ? modelMapper.map(userEntity, UsersystemObject.class) : null;
	}

	@Override
	public void saveClbObjects(Set<ClbObject> clbObjects) {
		clbObjects.stream().forEach(userSObj -> saveClbObject(userSObj));
	}

	@Override
	public List<AnalyzerRegistryObject> getDayHourRegistriesFromAnalyzer( String analyzerId, Date from, Date to) {
		return  processRegistries(analyzerId, from, to, false);
	}


	@Override
	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay) {

		List<AnalyzerRegistryObject> weekRegistries = new ArrayList<AnalyzerRegistryObject>();

		if(DateUtils.getInstance().isTheSameDay(lastDay, firstDay)) {
			final Date nextDay = DateUtils.getInstance().getDay(firstDay,true);
			weekRegistries.addAll(processRegistries(analyzerId, firstDay,nextDay, true));
		}

		while(!DateUtils.getInstance().isTheSameDay(lastDay, firstDay)){
			final Date nextDay = DateUtils.getInstance().getDay(firstDay,true);

			weekRegistries.addAll(processRegistries(analyzerId, firstDay,nextDay, true));

			firstDay = nextDay;
		} 


		return weekRegistries;
	}

	@Override
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, Date firstDay, Date lastDay) {

		List<AnalyzerRegistryObject> monthRegistries = new ArrayList<AnalyzerRegistryObject>();

		if(DateUtils.getInstance().isTheSameDay(lastDay, firstDay)) {
			final Date nextDay = DateUtils.getInstance().getDay(firstDay,true);
			monthRegistries.addAll(processRegistries(analyzerId, firstDay,nextDay, true));
		}

		while(!DateUtils.getInstance().isTheSameDay(lastDay, firstDay)){
			monthRegistries.addAll(processRegistries(analyzerId, firstDay, 
					DateUtils.getInstance().getDay(firstDay,true),true));

			firstDay = DateUtils.getInstance().getDay(firstDay, true);
		} 

		return monthRegistries;
	}



	private List<AnalyzerRegistryObject> processRegistries(final String analyzerId, final Date previousTimeFrame, final Date timeFrameNow, boolean noDateInterval){

		final String collectionName =  DateUtils.getInstance().concatTimeWithString(ANALYZER_REGISTIES_COLL_NAME, previousTimeFrame );

		DBCollection collection = this.mongoTemplate.getCollection( collectionName );
		DBObject dbObj = new BasicDBObject("analyzerId",analyzerId);
		
		if(!noDateInterval)
			dbObj.put( "currenttime", BasicDBObjectBuilder.start("$gte",previousTimeFrame ).add("$lte", timeFrameNow).get() );

		final List<AnalyzerRegistryObject> analyzerRegistries = new ArrayList<AnalyzerRegistryObject>();

		long startTime = System.currentTimeMillis();
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

			final AnalyzerRegistryObject analyzerRegObj = modelMapper.map(analyzerReg, AnalyzerRegistryObject.class);
			analyzerRegistries.add( analyzerRegObj );
		});

		long endTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println("EndTime For " + timeFrameNow.toString() + ": " + endTime);
		return analyzerRegistries;
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate( MongoTemplate mongoTemplate ) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Date> getDatesFromAnalyzer(String analyzerId) {

		AnalyzerEntity analyzer = (AnalyzerEntity) findById(analyzerId, AnalyzerEntity.class);
		
		Set<String> analyzerRegistriesCol = analyzer.getRegistriesCollections();
		
		return analyzerRegistriesCol != null ? analyzerRegistriesCol.stream()
				.map(regCollection -> {
					String dateReg = regCollection.split("_")[1];
					Calendar cal = Calendar.getInstance();
					cal.set(Integer.parseInt(dateReg.substring(0, 4)),
							Integer.parseInt(dateReg.substring(4, 6)), 
							Integer.parseInt(dateReg.substring(6, 8)));
					return cal.getTime();
				})
				.sorted()
				.collect(Collectors.toList()) : new ArrayList<Date>();
	}

	@Override
	public void deleteDivisionCascade(DivisionObject currentDivision) {

		if(currentDivision.getChildrenDivisions() != null) {
			for(DivisionObject divisionChild: currentDivision.getChildrenDivisions()) {
				deleteDivisionCascade(divisionChild);
			}
		}

		mongoTemplate.remove(modelMapper.map(currentDivision, DivisionEntity.class));
	}

	public ClbMongoRepository getClbRepository() {
		return clbRepository;
	}

	public void setClbRepository(ClbMongoRepository clbRepository) {
		this.clbRepository = clbRepository;
	}

}

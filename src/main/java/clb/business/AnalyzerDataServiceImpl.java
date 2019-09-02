package clb.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.integration.FtpGatewayPut;
import clb.business.integration.FtpGatewayRm;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.business.objects.UsersystemObject;
import clb.database.ClbDao;
import clb.global.DateUtils;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> analyzersFtp;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ClbDao clbDao;
	
	@Autowired
	FtpGatewayPut ftpGatewayPut;
	
	@Autowired
	FtpGatewayRm ftpGatewayRm;

	@PostConstruct
	public void init(){
		taskExecutor.execute(new AnalyzerDataServiceImplExecutor(this.clbDao));
	}
	
	@Override
	public List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId , Date timeFrame) {

		//If this Hour set to current time
		if(DateUtils.getInstance().isThisHour(timeFrame)) {
			timeFrame = new Date();
			Date previousHourDateLimit = DateUtils.getInstance().getHourReseted(timeFrame);

			return  clbDao.getDayHourRegistriesFromAnalyzer( analyzerId, previousHourDateLimit, timeFrame);
		}
		else {
			Date previousHourDateLimit = DateUtils.getInstance().getHourReseted(timeFrame);
			Date nextHourDateLimit = DateUtils.getInstance().getHour(previousHourDateLimit,true);

			return  clbDao.getDayHourRegistriesFromAnalyzer( analyzerId, previousHourDateLimit, nextHourDateLimit);
		}

	}

	@Override
	public List<AnalyzerRegistryObject> getDayRegistriesFromAnalyzer( String analyzerId , Date timeFrame) {
		if(DateUtils.getInstance().isToday(timeFrame)) {
			Date nowDate = new Date();
			Date currentDateReseted = DateUtils.getInstance().getDayReseted(nowDate);
			return clbDao.getDayHourRegistriesFromAnalyzer(analyzerId, currentDateReseted, nowDate);

		} 
		else {
			Date currentDateReseted = DateUtils.getInstance().getDayReseted(timeFrame);
			Date nextDayReseted = DateUtils.getInstance().getDay(currentDateReseted,true);
			return clbDao.getDayHourRegistriesFromAnalyzer(analyzerId, currentDateReseted,nextDayReseted);
		}
	}

	@Override
	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, int week, int month, int year) {

		Date lastDay = DateUtils.getInstance().isThisWeek(week,month,year) ? 
				new Date() : DateUtils.getInstance().getWeekLastDay(week,month,year);

				Date firstDay = DateUtils.getInstance().getWeekFirstDayReseted(week,month,year);

				return clbDao.getWeekRegistriesFromAnalyzer( analyzerId,firstDay,lastDay);
	}

	@Override
	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzerWithWeekShift(String analyzerId, int week, 
			int month, int year, int weekShift) {

		Date lastDay = DateUtils.getInstance().getWeekLastDayShift(week, month, year, weekShift);
		Date firstDay = DateUtils.getInstance().getWeekFirstDayShift(week, month, year, weekShift);

		return clbDao.getWeekRegistriesFromAnalyzer( analyzerId,firstDay,lastDay);
	}

	@Override
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, int month, int year) {

		Date lastDay = DateUtils.getInstance().isThisMonth(month,year) ? 
				new Date() : DateUtils.getInstance().getDay(DateUtils.getInstance().getMonthLastDay(month,year),true);

				Date firstDay = DateUtils.getInstance().getMonthFirstDay(month, year);

				return clbDao.getMonthRegistriesFromAnalyzer( analyzerId, firstDay , lastDay );
	}

	@Override
	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzerWithShift(String analyzerId, int month, int year,
			int monthShift) {


		Date tempDate = DateUtils.getInstance().getMonthFirstDay(month, year);

		Date firstDay = DateUtils.getInstance().shiftMonth(tempDate, monthShift);
		Date lastDay = DateUtils.getInstance().getDay(DateUtils.getInstance().getMonthLastDay(firstDay),true);

		return clbDao.getMonthRegistriesFromAnalyzer( analyzerId,firstDay,lastDay);
	}

	@Override
	public Date getLowestAnalyzerRegistryDate() {
		return clbDao.getLowestAnalyzerRegistryDate();
	}

	@Override
	public Map<String,List<String>> getYearsAndMonthsAvailable() {
		
		Map<String,List<String>> yearsAndMonths = new HashMap<String,List<String>>();
		
		for(String date: clbDao.getDatesAvailable()) {
			String year = date.substring(0,4);
			String month = date.substring(4,6);
			
			List<String> months = yearsAndMonths.get(year);
			
			if(months == null) {
				months = new ArrayList<String>();
			}
			
			months.add(month);
			yearsAndMonths.put(year, months);
		}
		
		return yearsAndMonths;
	}
	
	@Transactional
	@Override
	public void saveBuildingForUser(UsersystemObject user, BuildingObject building) {
		
		Set<DivisionObject> divisions = building.getDivisions();
		
		if(divisions != null) {
			divisions.stream().forEach( division -> clbDao.saveClbObject(division));
		}
		
		clbDao.saveClbObject(building);
		
		user.addBuilding(building);
		
		
		ftpGatewayPut.upload(user.getId() + "/"+building.getId(), "", "");
		clbDao.saveClbObject(user);
	}

	@Override
	@Transactional
	public void saveDivisionForParent(String parentId, DivisionObject divisionObj) {
		clbDao.saveClbObject(divisionObj);

		DivisionObject parentDivision = clbDao.findDivisionById(parentId);
		parentDivision.addSubDivision(divisionObj);
		clbDao.saveClbObject(parentDivision);
	}
	

	@Override
	@Transactional
	public void saveDivisionForBuilding(String buildingId, DivisionObject divisionObj) {
		
		clbDao.saveClbObject(divisionObj);
		
		BuildingObject building = clbDao.findBuildingById(buildingId);
		building.addDivision(divisionObj);
		clbDao.saveClbObject(building);
	}

	
	@Override
	@Transactional
	public void deleteBuildingForUser(UsersystemObject user, BuildingObject building) {
		
		Set<DivisionObject> divisions = building.getDivisions();
		
		if(divisions != null)
			divisions.stream().forEach(division -> clbDao.deleteDivisionCascade(division));
		
		clbDao.deleteClbObject(building);
		
		user.removeBuilding(building);
		
		clbDao.saveClbObject(user);
	}


	@Override
	@Transactional
	public void deleteChildDivisionFromParent(String parentDivision, String childDivision) {
		DivisionObject divisionParentObj = clbDao.findDivisionById(parentDivision);
		DivisionObject divisionChildObj = clbDao.findDivisionById(childDivision);
		
		divisionParentObj.deleteSubDivision(divisionChildObj);
		
		clbDao.saveClbObject(divisionParentObj);
		clbDao.deleteDivisionCascade(divisionChildObj);
	}


	@Override
	@Transactional
	public void deleteChildDivisionFromBuilding(String buildingId, String divisionId) {
		DivisionObject divisionChildObj = clbDao.findDivisionById(divisionId);
		BuildingObject buildingObj = clbDao.findBuildingById(buildingId);
		
		buildingObj.deleteDivision(divisionChildObj);
		
		clbDao.saveClbObject(buildingObj);
		clbDao.deleteDivisionCascade(divisionChildObj);
	}


	@Override
	@Transactional
	public void saveAnalyzersForDivision(String userId, String buildingId, String parentId, AnalyzerObject analyzerToSave) {
		
		clbDao.saveClbObject(analyzerToSave);
		
		DivisionObject division = clbDao.findDivisionById(parentId);
		division.addAnalyzer(analyzerToSave);
		
		clbDao.saveClbObject(division);
		
		ftpGatewayPut.upload(userId + "/"+buildingId + "/" + analyzerToSave.getCodeName(), "", "");
	}

	@Override
	@Transactional
	public void removeAnalyzersForDivision(String userId, String buildingId, String divisionId, Set<String> analyzersToRemove) {
		DivisionObject division = clbDao.findDivisionById(divisionId);

		if(division != null && division.getAnalyzers() != null) {
			division.setAnalyzers(division.getAnalyzers().stream()
										.filter( analyzer -> !analyzersToRemove.contains(analyzer.getId()))
										.collect(Collectors.toSet()));
		}
		
		clbDao.saveClbObject(division);
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public ClbDao getClbDao() {
		return clbDao;
	}

	public void setClbDao(ClbDao clbDao) {
		this.clbDao = clbDao;
	}

	public List<String> getAnalyzersFtp() {
		return analyzersFtp;
	}

	public void setAnalyzersFtp(List<String> analyzersFtp) {
		this.analyzersFtp = analyzersFtp;
	}
}

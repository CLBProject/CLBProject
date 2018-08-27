package clb.business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.UsersystemObject;
import clb.database.ClbDao;
import clb.global.DateUtils;
import clb.global.springutils.InjectLogger;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ClbDao clbDao;

	@Autowired 
	private ApplicationEventPublisher eventPublisher;

	@InjectLogger
	private Logger log;

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
	public UsersystemObject getUserData( String username ) {
		UsersystemObject user = clbDao.findUserByUserName( username );
		user.setBuildings( clbDao.findUserBuildings(username));

		return user;
	}


	public void destroy(){
	}

	@Override
	public Date getLowestAnalyzerRegistryDate() {
		return clbDao.getLowestAnalyzerRegistryDate();
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

	public ApplicationEventPublisher getEventPublisher() {
		return eventPublisher;
	}

	public void setEventPublisher( ApplicationEventPublisher eventPublisher ) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public String[] getYearsAvailable() {
		return clbDao.getYearsAvailable();
	}

	@Override
	@Transactional
	public void fillUserWithAllBuildings(UsersystemObject user) {
		clbDao.getAllBuildings().stream().forEach(building -> user.addBuilding(building));;
		clbDao.saveUsersystem(user);

		System.out.println("User added!");
	}

}

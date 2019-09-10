package clb.ui.beans.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.services.AnalyzerDataService;
import clb.global.DateUtils;

public class AnalysisBeanCache {

	private AnalyzerDataService analyzerDataService;

	private Map<String,Map<String,List<AnalyzerRegistryObject>>> hoursCache;
	private Map<String,Map<String,List<AnalyzerRegistryObject>>> dayCache;
	private Map<String,Map<String,List<AnalyzerRegistryObject>>> weekCache;
	private Map<String,Map<String,List<AnalyzerRegistryObject>>> monthCache;

	public AnalysisBeanCache(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;

		hoursCache = new HashMap<String,Map<String,List<AnalyzerRegistryObject>>>();
		dayCache = new HashMap<String,Map<String,List<AnalyzerRegistryObject>>>();
		weekCache = new HashMap<String,Map<String,List<AnalyzerRegistryObject>>>();
		monthCache = new HashMap<String,Map<String,List<AnalyzerRegistryObject>>>();
	}

	public List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId, Date timeFrame ){

		Map<String,List<AnalyzerRegistryObject>> hours = hoursCache.get(analyzerId);
		final String hourKey = DateUtils.getInstance().convertDateToSimpleStringFormat(timeFrame);

		if(hours == null) {

			List<AnalyzerRegistryObject> hoursRegistries = analyzerDataService.getHourRegistriesFromAnalyzer(analyzerId, timeFrame);
			Map<String,List<AnalyzerRegistryObject>> hourRegs = new HashMap<String,List<AnalyzerRegistryObject>>();
			hourRegs.put(hourKey, hoursRegistries);
			hoursCache.put(analyzerId, hourRegs);

			return hoursRegistries;
		}
		else {
			List<AnalyzerRegistryObject> hoursRegistriesFromMap = hours.get(hourKey);

			if(hoursRegistriesFromMap == null) {
				hoursRegistriesFromMap = analyzerDataService.getHourRegistriesFromAnalyzer(analyzerId, timeFrame);
				hours.put(hourKey, hoursRegistriesFromMap);
			}

			return hoursRegistriesFromMap;
		}
	}

	public List<AnalyzerRegistryObject> getDayRegistriesFromAnalyzer( String analyzerId, Date timeFrame ){

		Map<String,List<AnalyzerRegistryObject>> days = dayCache.get(analyzerId);
		final String dayKey = DateUtils.getInstance().convertDateToSimpleStringFormat(timeFrame);

		if(days == null) {

			List<AnalyzerRegistryObject> dayRegistries = analyzerDataService.getDayRegistriesFromAnalyzer(analyzerId, timeFrame);
			Map<String,List<AnalyzerRegistryObject>> dayRegs = new HashMap<String,List<AnalyzerRegistryObject>>();
			dayRegs.put(dayKey, dayRegistries);
			dayCache.put(analyzerId, dayRegs);

			return dayRegistries;
		}
		else {
			List<AnalyzerRegistryObject> dayRegistriesFromMap = days.get(dayKey);

			if(dayRegistriesFromMap == null) {
				dayRegistriesFromMap = analyzerDataService.getDayRegistriesFromAnalyzer(analyzerId, timeFrame);
				days.put(dayKey, dayRegistriesFromMap);
			}

			return dayRegistriesFromMap;
		}
	}

	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, int week, int month, 
			int year){
		return getWeekRegistriesFromAnalyzerWithShift(analyzerId, week, month, year, 0);
	}

	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzerWithShift(String analyzerId, int week, int month, 
			int year, int weekShift) {

		Map<String,List<AnalyzerRegistryObject>> weeks = weekCache.get(analyzerId);
		final String weekKey = DateUtils.getInstance().convertDateToSimpleWeekFormat(week,month,year,weekShift);

		if(weeks == null) {
			List<AnalyzerRegistryObject> weekRegistries;
			if(weekShift == 0) {
				weekRegistries = analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerId, week, month, year);
			}
			else {
				weekRegistries = analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift(analyzerId, week, month, year,weekShift);
			}
			Map<String,List<AnalyzerRegistryObject>> weekRegs = new HashMap<String,List<AnalyzerRegistryObject>>();
			weekRegs.put(weekKey, weekRegistries);
			weekCache.put(analyzerId, weekRegs);
			return weekRegistries;
		}
		else {
			List<AnalyzerRegistryObject> weekRegistriesFromMap = weeks.get(weekKey);

			if(weekRegistriesFromMap == null) {
				if(weekShift == 0) {
					weekRegistriesFromMap = analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerId, week, month, year);
				}
				else {
					weekRegistriesFromMap = analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift(analyzerId, week, month, year,weekShift);
				}
				weeks.put(weekKey, weekRegistriesFromMap);
			}
			return weekRegistriesFromMap;
		}

	}

	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, int month, int year) {
		return getMonthRegistriesFromAnalyzerWithShift(analyzerId, month, year, 0);
	}

	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzerWithShift(String analyzerId, int month, int year, int monthShift) {

		Map<String,List<AnalyzerRegistryObject>> months = monthCache.get(analyzerId);
		final String monthKey = DateUtils.getInstance().convertDateToSimpleMonthFormat(month,year,monthShift);

		if(months == null) {
			List<AnalyzerRegistryObject> monthRegistries;
			if(monthShift == 0) {
				monthRegistries =  analyzerDataService.getMonthRegistriesFromAnalyzer(analyzerId, month, year);
			}
			else {
				monthRegistries = analyzerDataService.getMonthRegistriesFromAnalyzerWithShift(analyzerId, month, year, monthShift);
			}
			
			Map<String,List<AnalyzerRegistryObject>> monthRegs = new HashMap<String,List<AnalyzerRegistryObject>>();
			monthRegs.put(monthKey, monthRegistries);
			monthCache.put(analyzerId, monthRegs);
			return monthRegistries;
		}
		else {
			List<AnalyzerRegistryObject> monthRegistriesFromMap = months.get(monthKey);

			if(monthRegistriesFromMap == null) {
				if(monthShift == 0) {
					monthRegistriesFromMap =  analyzerDataService.getMonthRegistriesFromAnalyzer(analyzerId, month, year);
				}
				else {
					monthRegistriesFromMap = analyzerDataService.getMonthRegistriesFromAnalyzerWithShift(analyzerId, month, year, monthShift);
				}
				months.put(monthKey, monthRegistriesFromMap);
			}
			return monthRegistriesFromMap;
		}
	}

}

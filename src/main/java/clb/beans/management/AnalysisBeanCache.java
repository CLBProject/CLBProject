package clb.beans.management;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerRegistryObject;
import clb.global.DateUtils;

public class AnalysisBeanCache {

	private AnalyzerDataService analyzerDataService;

	private Map<String,List<AnalyzerRegistryObject>> hoursCache;
	private Map<String,List<AnalyzerRegistryObject>> dayCache;
	private Map<String,List<AnalyzerRegistryObject>> weekCache;
	private Map<String,List<AnalyzerRegistryObject>> monthCache;

	public AnalysisBeanCache(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;

		hoursCache = new HashMap<String,List<AnalyzerRegistryObject>>();
		dayCache = new HashMap<String,List<AnalyzerRegistryObject>>();
		weekCache = new HashMap<String,List<AnalyzerRegistryObject>>();
		monthCache = new HashMap<String,List<AnalyzerRegistryObject>>();
	}

	public List<AnalyzerRegistryObject> getHourRegistriesFromAnalyzer( String analyzerId, Date timeFrame ){
		final String hourKey = DateUtils.getInstance().convertDateToSimpleStringFormat(timeFrame);
		List<AnalyzerRegistryObject> values = hoursCache.get(hourKey);

		if(values == null) {
			List<AnalyzerRegistryObject> hoursRegistries = analyzerDataService.getHourRegistriesFromAnalyzer(analyzerId, timeFrame);
			hoursCache.put(hourKey, hoursRegistries);
			return hoursRegistries;
		}
		return values;
	}

	public List<AnalyzerRegistryObject> getDayRegistriesFromAnalyzer( String analyzerId, Date timeFrame ){
		final String dayKey = DateUtils.getInstance().convertDateToSimpleStringFormat(timeFrame);
		List<AnalyzerRegistryObject> values = dayCache.get(dayKey);

		if(values == null) {
			List<AnalyzerRegistryObject> dayRegistries = analyzerDataService.getDayRegistriesFromAnalyzer(analyzerId, timeFrame);
			dayCache.put(dayKey, dayRegistries);
			return dayRegistries;
		}
		return values;
	}

	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzer(String analyzerId, int week, int month, 
			int year){
		return getWeekRegistriesFromAnalyzerWithShift(analyzerId, week, month, year, 0);
	}

	public List<AnalyzerRegistryObject> getWeekRegistriesFromAnalyzerWithShift(String analyzerId, int week, int month, 
			int year, int weekShift) {
		final String weekKey = DateUtils.getInstance().convertDateToSimpleWeekFormat(week,month,year,weekShift);
		List<AnalyzerRegistryObject> values = weekCache.get(weekKey);

		if(values == null) {
			List<AnalyzerRegistryObject> weekRegistries;
			if(weekShift == 0) {
				weekRegistries = analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerId, week, month, year);
			}
			else {
				weekRegistries = analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift(analyzerId, week, month, year,weekShift);
			}
			weekCache.put(weekKey, weekRegistries);
			return weekRegistries;
		}
		return values;
	}

	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzer(String analyzerId, int month, int year) {
		return getMonthRegistriesFromAnalyzerWithShift(analyzerId, month, year, 0);
	}

	public List<AnalyzerRegistryObject> getMonthRegistriesFromAnalyzerWithShift(String analyzerId, int month, int year, int monthShift) {
		final String monthKey = DateUtils.getInstance().convertDateToSimpleMonthFormat(month,year,monthShift);
		List<AnalyzerRegistryObject> values = monthCache.get(monthKey);

		if(values == null) {
			List<AnalyzerRegistryObject> monthRegistries;
			if(monthShift == 0) {
				monthRegistries =  analyzerDataService.getMonthRegistriesFromAnalyzer(analyzerId, month, year);
			}
			else {
				monthRegistries = analyzerDataService.getMonthRegistriesFromAnalyzerWithShift(analyzerId, month, year, monthShift);
			}
			monthCache.put(monthKey, monthRegistries);
			return monthRegistries;
		}
		return values;
	}

}

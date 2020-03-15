package clb.ui.beans.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import clb.business.services.AnalyzerDataService;
import clb.global.DateUtils;
import clb.ui.beans.objects.AnalyzerRegistryGui;

public class AnalysisBeanCache {

	private AnalyzerDataService analyzerDataService;

	private Map<String,Map<String,List<AnalyzerRegistryGui>>> hoursCache;
	private Map<String,Map<String,List<AnalyzerRegistryGui>>> dayCache;
	private Map<String,Map<String,List<AnalyzerRegistryGui>>> weekCache;
	private Map<String,Map<String,List<AnalyzerRegistryGui>>> monthCache;

	public AnalysisBeanCache(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;

		hoursCache = new HashMap<String,Map<String,List<AnalyzerRegistryGui>>>();
		dayCache = new HashMap<String,Map<String,List<AnalyzerRegistryGui>>>();
		weekCache = new HashMap<String,Map<String,List<AnalyzerRegistryGui>>>();
		monthCache = new HashMap<String,Map<String,List<AnalyzerRegistryGui>>>();
	}

	public List<AnalyzerRegistryGui> getHourRegistriesFromAnalyzer( String analyzerId, Date timeFrame ){

		Map<String,List<AnalyzerRegistryGui>> hours = hoursCache.get(analyzerId);
		final String hourKey = DateUtils.getInstance().convertDateToSimpleStringFormat(timeFrame);

		if(hours == null) {

			List<AnalyzerRegistryGui> hoursRegistries = analyzerDataService.getHourRegistriesFromAnalyzer(analyzerId, timeFrame)
					.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
			
			Map<String,List<AnalyzerRegistryGui>> hourRegs = new HashMap<String,List<AnalyzerRegistryGui>>();
			hourRegs.put(hourKey, hoursRegistries);
			hoursCache.put(analyzerId, hourRegs);

			return hoursRegistries;
		}
		else {
			List<AnalyzerRegistryGui> hoursRegistriesFromMap = hours.get(hourKey);

			if(hoursRegistriesFromMap == null) {
				hoursRegistriesFromMap = analyzerDataService.getHourRegistriesFromAnalyzer(analyzerId, timeFrame)
						.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
				
				hours.put(hourKey, hoursRegistriesFromMap);
			}

			return hoursRegistriesFromMap;
		}
	}

	public List<AnalyzerRegistryGui> getDayRegistriesFromAnalyzer( String analyzerId, Date date ){

		Map<String,List<AnalyzerRegistryGui>> days = dayCache.get(analyzerId);

		if(date == null)
			return new ArrayList<AnalyzerRegistryGui>();
		
		final String dayKey = DateUtils.getInstance().convertDateToSimpleStringFormat(date);

		if(days == null) {

			List<AnalyzerRegistryGui> dayRegistries = analyzerDataService.getDayRegistriesFromAnalyzer(analyzerId, date)
					.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
			
			Map<String,List<AnalyzerRegistryGui>> dayRegs = new HashMap<String,List<AnalyzerRegistryGui>>();
			dayRegs.put(dayKey, dayRegistries);
			dayCache.put(analyzerId, dayRegs);

			return dayRegistries;
		}
		else {
			List<AnalyzerRegistryGui> dayRegistriesFromMap = days.get(dayKey);

			if(dayRegistriesFromMap == null) {
				dayRegistriesFromMap = analyzerDataService.getDayRegistriesFromAnalyzer(analyzerId, date)
						.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
				days.put(dayKey, dayRegistriesFromMap);
			}

			return dayRegistriesFromMap;
		}
	}

	public List<AnalyzerRegistryGui> getWeekRegistriesFromAnalyzer(String analyzerId, int week, int month, 
			int year){
		return getWeekRegistriesFromAnalyzerWithShift(analyzerId, week, month, year, 0);
	}

	public List<AnalyzerRegistryGui> getWeekRegistriesFromAnalyzerWithShift(String analyzerId, int week, int month, 
			int year, int weekShift) {

		Map<String,List<AnalyzerRegistryGui>> weeks = weekCache.get(analyzerId);
		final String weekKey = DateUtils.getInstance().convertDateToSimpleWeekFormat(week,month,year,weekShift);

		if(weeks == null) {
			List<AnalyzerRegistryGui> weekRegistries;
			if(weekShift == 0) {
				weekRegistries = analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerId, week, month, year)
						.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
			}
			else {
				weekRegistries = analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift(analyzerId, week, month, year,weekShift)
						.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
			}
			Map<String,List<AnalyzerRegistryGui>> weekRegs = new HashMap<String,List<AnalyzerRegistryGui>>();
			weekRegs.put(weekKey, weekRegistries);
			weekCache.put(analyzerId, weekRegs);
			return weekRegistries;
		}
		else {
			List<AnalyzerRegistryGui> weekRegistriesFromMap = weeks.get(weekKey);

			if(weekRegistriesFromMap == null) {
				if(weekShift == 0) {
					weekRegistriesFromMap = analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerId, week, month, year)
							.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
				}
				else {
					weekRegistriesFromMap = analyzerDataService.getWeekRegistriesFromAnalyzerWithWeekShift(analyzerId, week, month, year,weekShift)
							.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
				}
				weeks.put(weekKey, weekRegistriesFromMap);
			}
			return weekRegistriesFromMap;
		}

	}

	public List<AnalyzerRegistryGui> getMonthRegistriesFromAnalyzer(String analyzerId, Date date) {
		return getMonthRegistriesFromAnalyzerWithShift(analyzerId, date, 0);
	}

	public List<AnalyzerRegistryGui> getMonthRegistriesFromAnalyzerWithShift(String analyzerId, Date date, int monthShift) {

		Map<String,List<AnalyzerRegistryGui>> months = monthCache.get(analyzerId);
		final String monthKey = DateUtils.getInstance().convertDateToSimpleMonthFormat(date,monthShift);

		if(months == null) {
			List<AnalyzerRegistryGui> monthRegistries;
			if(monthShift == 0) {
				monthRegistries =  analyzerDataService.getMonthRegistriesFromAnalyzer(analyzerId, date)
													.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
			}
			else {
				monthRegistries = analyzerDataService.getMonthRegistriesFromAnalyzerWithShift(analyzerId, date, monthShift)
										.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
			}
			
			Map<String,List<AnalyzerRegistryGui>> monthRegs = new HashMap<String,List<AnalyzerRegistryGui>>();
			monthRegs.put(monthKey, monthRegistries);
			monthCache.put(analyzerId, monthRegs);
			return monthRegistries;
		}
		else {
			List<AnalyzerRegistryGui> monthRegistriesFromMap = months.get(monthKey);

			if(monthRegistriesFromMap == null) {
				if(monthShift == 0) {
					monthRegistriesFromMap =  analyzerDataService.getMonthRegistriesFromAnalyzer(analyzerId, date)
													.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
				}
				else {
					monthRegistriesFromMap = analyzerDataService.getMonthRegistriesFromAnalyzerWithShift(analyzerId, date, monthShift)
																.stream().map(AnalyzerRegistryGui::new).collect(Collectors.toList());
				}
				months.put(monthKey, monthRegistriesFromMap);
			}
			return monthRegistriesFromMap;
		}
	}

}

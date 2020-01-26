package clb.ui.beans.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clb.global.DateUtils;
import clb.ui.beans.objects.AnalyzerRegistryGui;
import clb.ui.enums.ScaleGraphic;

public class AnalyzerRegistryReductionAlgorithm {

	private final Logger logger = LoggerFactory.getLogger(AnalyzerRegistryReductionAlgorithm.class);
	private static AnalyzerRegistryReductionAlgorithm instance;

	public static synchronized AnalyzerRegistryReductionAlgorithm getInstance() {
		if (instance == null) {
			instance = new AnalyzerRegistryReductionAlgorithm();
		}
		return instance;
	}

	public List<AnalyzerRegistryGui> reduceRegistries(List<AnalyzerRegistryGui> registries, ScaleGraphic scale){
		List<AnalyzerRegistryGui> registryReduced = new ArrayList<AnalyzerRegistryGui>();


		Map<String,List<AnalyzerRegistryGui>> registriesReduced = new HashMap<String,List<AnalyzerRegistryGui>>();

		//Organize Registries and insert them on RegistriesReduced 
		for(AnalyzerRegistryGui registry: registries) {
			String dateTransformedToHoursAverage = null;

			switch(scale) {
			case HOUR:
			case DAY:
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToHoursOrDaysAverage(registry.getCurrentTime());
				break;
			case WEEK:
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToWeekAverage(registry.getCurrentTime());
				break;
			case MONTH:
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToMonthAverage(registry.getCurrentTime());
				break;
			default: 
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToHoursOrDaysAverage(registry.getCurrentTime());
				break;
			}

			List<AnalyzerRegistryGui> registriesForMinuteAverage = registriesReduced.get(dateTransformedToHoursAverage);

			if(registriesForMinuteAverage == null) {
				registriesForMinuteAverage = new ArrayList<AnalyzerRegistryGui>();
			}

			registriesForMinuteAverage.add(registry);
			registriesReduced.put(dateTransformedToHoursAverage, registriesForMinuteAverage);
		}

		//Reduce Registries
		for(Entry<String,List<AnalyzerRegistryGui>> registriesReducedEntry : registriesReduced.entrySet()) {
			try {
				registryReduced.add(averageAnalyzerRegistryObject(registriesReducedEntry.getValue(),
						registriesReducedEntry.getKey()));
			} catch (ParseException e) {
				logger.error("Error while Reducing Registries for Algorithm", e);
			}
		}

		return registryReduced;
	}


	private AnalyzerRegistryGui averageAnalyzerRegistryObject(List<AnalyzerRegistryGui> registries, String dateString) throws ParseException {

		Double asys = 0.0;
		Double hz = 0.0;
		Double kwsys = 0.0;
		Double pfsys = 0.0;
		Double kvarsys = 0.0;
		Double kvasys = 0.0;
		Double vlnsys = 0.0;
		Double vllsys = 0.0;

		for(AnalyzerRegistryGui registry : registries) {
			asys += registry.getAsys();
			hz += registry.getHz();
			kwsys += registry.getKwsys();
			pfsys += registry.getPfsys();
			kvarsys += registry.getKvarsys();
			kvasys += registry.getKvasys();
			vlnsys += registry.getVlnsys();
			vllsys += registry.getVllsys();
		}

		Double asysAverage = asys/registries.size();
		Double hzAverage = hz/registries.size();
		Double kwsysAverage = kwsys/registries.size();
		Double pfsysAverage = pfsys/registries.size();
		Double kvarsysAverage = kvarsys/registries.size();
		Double kvasysAverage = kvasys/registries.size();
		Double vlnsysAverage = vlnsys/registries.size();
		Double vllsysAverage = vllsys/registries.size();

		AnalyzerRegistryGui registryGui = new AnalyzerRegistryGui(asysAverage,hzAverage,kwsysAverage,pfsysAverage,
				kvarsysAverage,kvasysAverage,vlnsysAverage,vllsysAverage, 
				DateUtils.getInstance().convertDateStringToSimpleDateFormat(dateString), registries.get(0).getAnalyzerId());

		return registryGui;
	}
}

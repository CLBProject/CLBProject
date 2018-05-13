package clb.beans.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import clb.beans.enums.ScaleGraphic;
import clb.business.objects.AnalyzerRegistryObject;
import clb.global.DateUtils;

public class AnalyzerRegistryReductionAlgorithm {

	private static AnalyzerRegistryReductionAlgorithm instance;

	public static synchronized AnalyzerRegistryReductionAlgorithm getInstance() {
		if (instance == null) {
			instance = new AnalyzerRegistryReductionAlgorithm();
		}
		return instance;
	}

	public List<AnalyzerRegistryGui> reduceRegistries(List<AnalyzerRegistryObject> registries, ScaleGraphic scale){
		List<AnalyzerRegistryGui> registryReduced = new ArrayList<AnalyzerRegistryGui>();


		Map<String,List<AnalyzerRegistryObject>> registriesReduced = new HashMap<String,List<AnalyzerRegistryObject>>();

		//Organize Registries and insert them on RegistriesReduced 
		for(AnalyzerRegistryObject registry: registries) {
			String dateTransformedToHoursAverage = null;
			
			switch(scale) {
			case HOUR:
			case DAY:
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToHoursOrDaysAverage(registry.getCurrenttime());
				break;
			case WEEK:
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToWeekAverage(registry.getCurrenttime());
				break;
			case MONTH:
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToMonthAverage(registry.getCurrenttime());
				break;
			default: 
				dateTransformedToHoursAverage = DateUtils.getInstance().transformDateToHoursOrDaysAverage(registry.getCurrenttime());
				break;
			}

			List<AnalyzerRegistryObject> registriesForMinuteAverage = registriesReduced.get(dateTransformedToHoursAverage);

			if(registriesForMinuteAverage == null) {
				registriesForMinuteAverage = new ArrayList<AnalyzerRegistryObject>();
			}

			registriesForMinuteAverage.add(registry);
			registriesReduced.put(dateTransformedToHoursAverage, registriesForMinuteAverage);
		}

		//Reduce Registries
		for(Entry<String,List<AnalyzerRegistryObject>> registriesReducedEntry : registriesReduced.entrySet()) {
			registryReduced.add(averageAnalyzerRegistryObject(registriesReducedEntry.getValue(),
					registriesReducedEntry.getKey()));
		}

		return registryReduced;
	}
	

	private AnalyzerRegistryGui averageAnalyzerRegistryObject(List<AnalyzerRegistryObject> registries, String dateString) {

		Double asys = 0.0;
		Double hz = 0.0;
		Double kwsys = 0.0;
		Double pfsys = 0.0;
		Double kvarsys = 0.0;
		Double kvasys = 0.0;
		Double vlnsys = 0.0;
		Double vllsys = 0.0;

		for(AnalyzerRegistryObject registry : registries) {
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
				kvarsysAverage,kvasysAverage,vlnsysAverage,vllsysAverage, dateString);
		
		return registryGui;
	}
}

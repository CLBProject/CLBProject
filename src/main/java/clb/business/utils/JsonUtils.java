package clb.business.utils;

import java.util.Date;

import org.primefaces.json.JSONObject;

import clb.business.objects.AnalyzerRegistryObject;

public class JsonUtils
{
	private static JsonUtils instance;

	public static synchronized JsonUtils getInstance() {
		if (instance == null) {
			instance = new JsonUtils();
		}
		return instance;
	}

	public AnalyzerRegistryObject toAnalyzerRegistryObject(JSONObject jsonObj) {
		AnalyzerRegistryObject registry = new AnalyzerRegistryObject();
		
		registry.setAl1(getDoubleForJson("al1", jsonObj));
		registry.setAl2(getDoubleForJson("al2", jsonObj));
		registry.setAl3(getDoubleForJson("al3", jsonObj));
		// this.an = jsonObj.getDouble("an");
		// this.asys = jsonObj.getDouble("asys");
		registry.setComport(getIntForJson("comPort", jsonObj));
		registry.setCurrenttime(getDateForJson("date", jsonObj));
		// this.epochformat = new Date(jsonObj.getLong("epochFormat"));
		// this.hourmeterkwh = jsonObj.getDouble("hourmeterkwh");
		// this.hourmeterkwhnegative = jsonObj.getDouble("hourmeterkwhnegative");
		registry.setHz(getDoubleForJson("hZ", jsonObj));
		registry.setItemlabel(jsonObj.getString("itemLabel"));
		// this.kvahl = jsonObj.getDouble("kvahl");
		// this.kvahl1 = jsonObj.getDouble("kvahl1");
		// this.kvahl2 = jsonObj.getDouble("kvahl2");
		// this.kvahl3 = jsonObj.getDouble("kvahl3");
		registry.setKval1(getDoubleForJson("kval1", jsonObj));
		registry.setKval2(getDoubleForJson("kval2", jsonObj));
		registry.setKval3(getDoubleForJson("kval3", jsonObj));
		// this.kvarh = jsonObj.getDouble("kvarh");
		registry.setKvarl1(getDoubleForJson("kvarl1", jsonObj));
		registry.setKvarl2(getDoubleForJson("kvarl2", jsonObj));
		registry.setKvarl3(getDoubleForJson("kvarl3", jsonObj));
		registry.setKvarsys(getDoubleForJson("kvarsys", jsonObj));
		registry.setKvasys(getDoubleForJson("kvasys", jsonObj));
		registry.setKwsys(getDoubleForJson("kwsys", jsonObj));
		registry.setKwh(getDoubleForJson("kvasys", jsonObj));
		registry.setKwl1(getDoubleForJson("kwl1", jsonObj));
		registry.setKwl2(getDoubleForJson("kwl2", jsonObj));
		registry.setKwl2(getDoubleForJson("kwl3", jsonObj));
		registry.setKwl3(getDoubleForJson("kwl3", jsonObj));
		// this.kwhl1 = jsonObj.getDouble("kwhl1");
		// this.kwhl2 = jsonObj.getDouble("kwhl2");
		// this.kwhl3 = jsonObj.getDouble("kwhl3");
		registry.setModbusid(getIntForJson("modBusId", jsonObj));
		registry.setPfl1(getDoubleForJson("pfl1", jsonObj));
		registry.setPfl2(getDoubleForJson("pfl2", jsonObj));
		registry.setPfl3(getDoubleForJson("pfl3", jsonObj));
		registry.setPfsys(getDoubleForJson("pfsys", jsonObj));
		registry.setProducttype(jsonObj.getString("productType"));
		registry.setRecorttype(jsonObj.getString("recortType"));
		//  this.rfc3339format = new Date(jsonObj.getLong("rfc3339format"));
		//  this.temperature = jsonObj.getDouble("temperature");
		//	      this.thdal1 = jsonObj.getDouble("thdal1");
		//	      this.thdal2 = jsonObj.getDouble("thdal2");
		//	      this.thdal3 = jsonObj.getDouble("thdal3");
		//	      this.thdvl1n = jsonObj.getDouble("thdvl1n");
		//	      this.thdvl2n = jsonObj.getDouble("thdvl2n");
		//	      this.thdvl3n = jsonObj.getDouble("thdvl3n");
		//	      this.vadmd = jsonObj.getDouble("vadmd");
		//	      this.vardmd = jsonObj.getDouble("vardmd");
		registry.setVl1l2(getDoubleForJson("vl1l2", jsonObj));
		registry.setVl1n(getDoubleForJson("vl1n", jsonObj));
		registry.setVl2l3(getDoubleForJson("vl2l3", jsonObj));
		registry.setVl2n(getDoubleForJson("vl2n", jsonObj));
		registry.setVl3l1(getDoubleForJson("vl3l1", jsonObj));
		registry.setVl3n(getDoubleForJson("vl3n", jsonObj));
		registry.setVllsys(getDoubleForJson("vllsys", jsonObj));
		registry.setVlnsys(getDoubleForJson("vlnsys", jsonObj));

		return registry;

	}

	private Date getDateForJson(String key, JSONObject jsonObj) {
		return jsonObj.get(key) != null && !jsonObj.get(key).equals("") ? new Date(jsonObj.getLong(key)*1000) : null;
	}

	private double getDoubleForJson(String key, JSONObject jsonObj) {
		return jsonObj.get(key) != null && !jsonObj.get(key).equals("") ? jsonObj.getDouble(key) : 0.0;
	}

	private int getIntForJson(String key, JSONObject jsonObj) {
		return jsonObj.get(key) != null && !jsonObj.get(key).equals("") ? jsonObj.getInt(key) : -1;
	}
}

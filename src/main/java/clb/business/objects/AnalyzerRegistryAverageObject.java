package clb.business.objects;

import java.io.Serializable;
import java.sql.Timestamp;

import clb.database.entities.AnalyzerRegistryAverageEntity;

public class AnalyzerRegistryAverageObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private double al1Average;

	private double al2Average;

	private double al3Average;

	private double anAverage;

	private double asysAverage;

	private int comport;

	private Timestamp currenttime;

	private Timestamp epochformat;

	private double hourmeterkwhAverage;

	private double hourmeterkwhnegativeAverage;

	private double hzAverage;

	private String itemlabel;

	private double kvahlAverage;

	private double kvahl1Average;

	private double kvahl2Average;

	private double kvahl3Average;

	private double kval1Average;

	private double kval2Average;

	private double kval3Average;

	private double kvarhAverage;

	private double kvarl1Average;

	private double kvarl2Average;

	private double kvarl3Average;

	private double kvarsysAverage;

	private double kvasysAverage;

	private double kwhAverage;

	private double kwhl1Average;

	private double kwhl2Average;

	private double kwhl3Average;

	private double kwl1Average;

	private double kwl2Average;

	private double kwl3Average;

	private double kwsysAverage;

	private long modbusid;

	private double pfl1Average;

	private double pfl2Average;

	private double pfl3Average;

	private double pfsysAverage;

	private String producttype;

	private String recorttype;

	private Timestamp rfc3339format;

	private double temperatureAverage;

	private double thdal1Average;

	private double thdal2Average;

	private double thdal3Average;

	private double thdvl1nAverage;

	private double thdvl2nAverage;

	private double thdvl3nAverage;

	private double vadmdAverage;

	private double vardmdAverage;

	private double vl1l2Average;

	private double vl1nAverage;

	private double vl2l3Average;

	private double vl2nAverage;

	private double vl3l1Average;

	private double vl3nAverage;

	private double vllsysAverage;

	private double vlnsysAverage;

	public AnalyzerRegistryAverageObject() {
	}


    public AnalyzerRegistryAverageObject(AnalyzerRegistryAverageEntity analyzerRegEntity) {
        super();
        this.id = analyzerRegEntity.getId();
        this.al1Average = analyzerRegEntity.getAl1Average();
        this.al2Average = analyzerRegEntity.getAl2Average();
        this.al3Average = analyzerRegEntity.getAl3Average();
        this.anAverage = analyzerRegEntity.getAnAverage();
        this.asysAverage = analyzerRegEntity.getAsysAverage();
        this.comport = analyzerRegEntity.getComport();
        this.currenttime = analyzerRegEntity.getCurrenttime();
        this.epochformat = analyzerRegEntity.getEpochformat();
        this.hourmeterkwhAverage = analyzerRegEntity.getHourmeterkwhAverage();
        this.hourmeterkwhnegativeAverage = analyzerRegEntity.getHourmeterkwhnegativeAverage();
        this.hzAverage = analyzerRegEntity.getHzAverage();
        this.itemlabel = analyzerRegEntity.getItemlabel();
        this.kvahlAverage = analyzerRegEntity.getKvahlAverage();
        this.kvahl1Average = analyzerRegEntity.getKvahl1Average();
        this.kvahl2Average = analyzerRegEntity.getKvahl2Average();
        this.kvahl3Average = analyzerRegEntity.getKvahl3Average();
        this.kval1Average = analyzerRegEntity.getKval1Average();
        this.kval2Average = analyzerRegEntity.getKval2Average();
        this.kval3Average = analyzerRegEntity.getKval3Average();
        this.kvarhAverage = analyzerRegEntity.getKvarhAverage();
        this.kvarl1Average = analyzerRegEntity.getKvarl1Average();
        this.kvarl2Average = analyzerRegEntity.getKvarl2Average();
        this.kvarl3Average = analyzerRegEntity.getKvarl3Average();
        this.kvarsysAverage = analyzerRegEntity.getKvarsysAverage();
        this.kvasysAverage = analyzerRegEntity.getKvasysAverage();
        this.kwhAverage = analyzerRegEntity.getKwhAverage();
        this.kwhl1Average = analyzerRegEntity.getKwhl1Average();
        this.kwhl2Average = analyzerRegEntity.getKwhl2Average();
        this.kwhl3Average = analyzerRegEntity.getKwhl3Average();
        this.kwl1Average = analyzerRegEntity.getKwl1Average();
        this.kwl2Average = analyzerRegEntity.getKwl2Average();
        this.kwl3Average = analyzerRegEntity.getKwl3Average();
        this.modbusid = analyzerRegEntity.getModbusidAverage();
        this.pfl1Average = analyzerRegEntity.getPfl1Average();
        this.pfl2Average = analyzerRegEntity.getPfl2Average();
        this.pfl3Average = analyzerRegEntity.getPfl3Average();
        this.pfsysAverage = analyzerRegEntity.getPfsysAverage();
        this.producttype = analyzerRegEntity.getProducttype();
        this.recorttype = analyzerRegEntity.getRecorttype();
        this.rfc3339format = analyzerRegEntity.getRfc3339format();
        this.temperatureAverage = analyzerRegEntity.getTemperatureAverage();
        this.thdal1Average = analyzerRegEntity.getThdal1Average();
        this.thdal2Average = analyzerRegEntity.getThdal2Average();
        this.thdal3Average = analyzerRegEntity.getThdal3Average();
        this.thdvl1nAverage = analyzerRegEntity.getThdvl1nAverage();
        this.thdvl2nAverage = analyzerRegEntity.getThdvl2nAverage();
        this.thdvl3nAverage = analyzerRegEntity.getThdvl3nAverage();
        this.vadmdAverage = analyzerRegEntity.getVadmdAverage();
        this.vardmdAverage = analyzerRegEntity.getVardmdAverage();
        this.vl1l2Average = analyzerRegEntity.getVl1l2Average();
        this.vl1nAverage = analyzerRegEntity.getVl1nAverage();
        this.vl2l3Average = analyzerRegEntity.getVl2l3Average();
        this.vl2nAverage = analyzerRegEntity.getVl2nAverage();
        this.vl3l1Average = analyzerRegEntity.getVl3l1Average();
        this.vl3nAverage = analyzerRegEntity.getVl3nAverage();
        this.vllsysAverage = analyzerRegEntity.getVllsysAverage();
        this.vlnsysAverage = analyzerRegEntity.getVlnsysAverage();
    }


    public AnalyzerRegistryAverageEntity toEntity() {
    	AnalyzerRegistryAverageEntity anaRegEntity = new AnalyzerRegistryAverageEntity();
        anaRegEntity.setId(id);
        anaRegEntity.setAl1Average(al1Average);
        anaRegEntity.setAl2Average(al2Average);
        anaRegEntity.setAl3Average(al3Average);
        anaRegEntity.setAnAverage(anAverage);
        anaRegEntity.setAsysAverage(asysAverage);
        anaRegEntity.setComport(comport);
        anaRegEntity.setCurrenttime(currenttime);
        anaRegEntity.setEpochformat(epochformat);
        anaRegEntity.setHourmeterkwhAverage(hourmeterkwhAverage);
        anaRegEntity.setHourmeterkwhnegativeAverage(hourmeterkwhnegativeAverage);
        anaRegEntity.setHzAverage(hzAverage);
        anaRegEntity.setItemlabel(itemlabel);
        anaRegEntity.setKvahlAverage(kvahlAverage);
        anaRegEntity.setKvahl2Average(kvahl2Average);
        anaRegEntity.setKvahl3Average(kvahl3Average);
        anaRegEntity.setKval1Average(kval1Average);
        anaRegEntity.setKval2Average(kval2Average);
        anaRegEntity.setKval3Average(kval3Average);
        anaRegEntity.setKvarhAverage(kvarhAverage);
        anaRegEntity.setKvarl1Average(kvarl1Average);
        anaRegEntity.setKvarl2Average(kvarl2Average);
        anaRegEntity.setKvarl3Average(kvarl3Average);
        anaRegEntity.setKvarsysAverage(kvarsysAverage);
        anaRegEntity.setKvasysAverage(kvasysAverage);
        anaRegEntity.setKwhAverage(kwhAverage);
        anaRegEntity.setKwhl1Average(kwhl1Average);
        anaRegEntity.setKwhl2Average(kwhl2Average);
        anaRegEntity.setKwhl3Average(kwhl3Average);
        anaRegEntity.setKwl1Average(kwl1Average);
        anaRegEntity.setKwl2Average(kwl2Average);
        anaRegEntity.setKwl3Average(kwl3Average);
        anaRegEntity.setKwsysAverage(kwsysAverage);
        anaRegEntity.setModbusidAverage(modbusid);
        anaRegEntity.setPfl1Average(pfl1Average);
        anaRegEntity.setPfl2Average(pfl2Average);
        anaRegEntity.setPfl3Average(pfl3Average);
        anaRegEntity.setPfsysAverage(pfsysAverage);
        anaRegEntity.setProducttype(producttype);
        anaRegEntity.setRecorttype(recorttype);
        anaRegEntity.setRfc3339format(rfc3339format);
        anaRegEntity.setTemperatureAverage(temperatureAverage);
        anaRegEntity.setThdal1Average(thdal1Average);
        anaRegEntity.setThdal2Average(thdal2Average);
        anaRegEntity.setThdal3Average(thdal3Average);
        anaRegEntity.setThdvl1nAverage(thdvl1nAverage);
        anaRegEntity.setThdvl2nAverage(thdvl2nAverage);
        anaRegEntity.setThdvl3nAverage(thdvl3nAverage);
        anaRegEntity.setVadmdAverage(vadmdAverage);
        anaRegEntity.setVardmdAverage(vardmdAverage);
        anaRegEntity.setVl1l2Average(vl1l2Average);
        anaRegEntity.setVl1nAverage(vl1nAverage);
        anaRegEntity.setVl2l3Average(vl2l3Average);
        anaRegEntity.setVl2nAverage(vl2nAverage);
        anaRegEntity.setVl3l1Average(vl3l1Average);
        anaRegEntity.setVl3nAverage(thdvl3nAverage);
        anaRegEntity.setVllsysAverage(vllsysAverage);
        anaRegEntity.setVlnsysAverage(vlnsysAverage);
        
        return anaRegEntity;
    }
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public double getAl1Average() {
		return al1Average;
	}


	public void setAl1Average(double al1Average) {
		this.al1Average = al1Average;
	}


	public double getAl2Average() {
		return al2Average;
	}


	public void setAl2Average(double al2Average) {
		this.al2Average = al2Average;
	}


	public double getAl3Average() {
		return al3Average;
	}


	public void setAl3Average(double al3Average) {
		this.al3Average = al3Average;
	}


	public double getAnAverage() {
		return anAverage;
	}


	public void setAnAverage(double anAverage) {
		this.anAverage = anAverage;
	}


	public double getAsysAverage() {
		return asysAverage;
	}


	public void setAsysAverage(double asysAverage) {
		this.asysAverage = asysAverage;
	}


	public int getComport() {
		return comport;
	}


	public void setComport(int comport) {
		this.comport = comport;
	}


	public Timestamp getCurrenttime() {
		return currenttime;
	}


	public void setCurrenttime(Timestamp currenttime) {
		this.currenttime = currenttime;
	}


	public Timestamp getEpochformat() {
		return epochformat;
	}


	public void setEpochformat(Timestamp epochformat) {
		this.epochformat = epochformat;
	}


	public double getHourmeterkwhAverage() {
		return hourmeterkwhAverage;
	}


	public void setHourmeterkwhAverage(double hourmeterkwhAverage) {
		this.hourmeterkwhAverage = hourmeterkwhAverage;
	}


	public double getHourmeterkwhnegativeAverage() {
		return hourmeterkwhnegativeAverage;
	}


	public void setHourmeterkwhnegativeAverage(double hourmeterkwhnegativeAverage) {
		this.hourmeterkwhnegativeAverage = hourmeterkwhnegativeAverage;
	}


	public double getHzAverage() {
		return hzAverage;
	}


	public void setHzAverage(double hzAverage) {
		this.hzAverage = hzAverage;
	}


	public String getItemlabel() {
		return itemlabel;
	}


	public void setItemlabel(String itemlabel) {
		this.itemlabel = itemlabel;
	}


	public double getKvahlAverage() {
		return kvahlAverage;
	}


	public void setKvahlAverage(double kvahlAverage) {
		this.kvahlAverage = kvahlAverage;
	}


	public double getKvahl1Average() {
		return kvahl1Average;
	}


	public void setKvahl1Average(double kvahl1Average) {
		this.kvahl1Average = kvahl1Average;
	}


	public double getKvahl2Average() {
		return kvahl2Average;
	}


	public void setKvahl2Average(double kvahl2Average) {
		this.kvahl2Average = kvahl2Average;
	}


	public double getKvahl3Average() {
		return kvahl3Average;
	}


	public void setKvahl3Average(double kvahl3Average) {
		this.kvahl3Average = kvahl3Average;
	}


	public double getKval1Average() {
		return kval1Average;
	}


	public void setKval1Average(double kval1Average) {
		this.kval1Average = kval1Average;
	}


	public double getKval2Average() {
		return kval2Average;
	}


	public void setKval2Average(double kval2Average) {
		this.kval2Average = kval2Average;
	}


	public double getKval3Average() {
		return kval3Average;
	}


	public void setKval3Average(double kval3Average) {
		this.kval3Average = kval3Average;
	}


	public double getKvarhAverage() {
		return kvarhAverage;
	}


	public void setKvarhAverage(double kvarhAverage) {
		this.kvarhAverage = kvarhAverage;
	}


	public double getKvarl1Average() {
		return kvarl1Average;
	}


	public void setKvarl1Average(double kvarl1Average) {
		this.kvarl1Average = kvarl1Average;
	}


	public double getKvarl2Average() {
		return kvarl2Average;
	}


	public void setKvarl2Average(double kvarl2Average) {
		this.kvarl2Average = kvarl2Average;
	}


	public double getKvarl3Average() {
		return kvarl3Average;
	}


	public void setKvarl3Average(double kvarl3Average) {
		this.kvarl3Average = kvarl3Average;
	}


	public double getKvarsysAverage() {
		return kvarsysAverage;
	}


	public void setKvarsysAverage(double kvarsysAverage) {
		this.kvarsysAverage = kvarsysAverage;
	}


	public double getKvasysAverage() {
		return kvasysAverage;
	}


	public void setKvasysAverage(double kvasysAverage) {
		this.kvasysAverage = kvasysAverage;
	}


	public double getKwhAverage() {
		return kwhAverage;
	}


	public void setKwhAverage(double kwhAverage) {
		this.kwhAverage = kwhAverage;
	}


	public double getKwhl1Average() {
		return kwhl1Average;
	}


	public void setKwhl1Average(double kwhl1Average) {
		this.kwhl1Average = kwhl1Average;
	}


	public double getKwhl2Average() {
		return kwhl2Average;
	}


	public void setKwhl2Average(double kwhl2Average) {
		this.kwhl2Average = kwhl2Average;
	}


	public double getKwhl3Average() {
		return kwhl3Average;
	}


	public void setKwhl3Average(double kwhl3Average) {
		this.kwhl3Average = kwhl3Average;
	}


	public double getKwl1Average() {
		return kwl1Average;
	}


	public void setKwl1Average(double kwl1Average) {
		this.kwl1Average = kwl1Average;
	}


	public double getKwl2Average() {
		return kwl2Average;
	}


	public void setKwl2Average(double kwl2Average) {
		this.kwl2Average = kwl2Average;
	}


	public double getKwl3Average() {
		return kwl3Average;
	}


	public void setKwl3Average(double kwl3Average) {
		this.kwl3Average = kwl3Average;
	}


	public double getKwsysAverage() {
		return kwsysAverage;
	}


	public void setKwsysAverage(double kwsysAverage) {
		this.kwsysAverage = kwsysAverage;
	}


	public long getModbusid() {
		return modbusid;
	}


	public void setModbusid(long modbusid) {
		this.modbusid = modbusid;
	}


	public double getPfl1Average() {
		return pfl1Average;
	}


	public void setPfl1Average(double pfl1Average) {
		this.pfl1Average = pfl1Average;
	}


	public double getPfl2Average() {
		return pfl2Average;
	}


	public void setPfl2Average(double pfl2Average) {
		this.pfl2Average = pfl2Average;
	}


	public double getPfl3Average() {
		return pfl3Average;
	}


	public void setPfl3Average(double pfl3Average) {
		this.pfl3Average = pfl3Average;
	}


	public double getPfsysAverage() {
		return pfsysAverage;
	}


	public void setPfsysAverage(double pfsysAverage) {
		this.pfsysAverage = pfsysAverage;
	}


	public String getProducttype() {
		return producttype;
	}


	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}


	public String getRecorttype() {
		return recorttype;
	}


	public void setRecorttype(String recorttype) {
		this.recorttype = recorttype;
	}


	public Timestamp getRfc3339format() {
		return rfc3339format;
	}


	public void setRfc3339format(Timestamp rfc3339format) {
		this.rfc3339format = rfc3339format;
	}


	public double getTemperatureAverage() {
		return temperatureAverage;
	}


	public void setTemperatureAverage(double temperatureAverage) {
		this.temperatureAverage = temperatureAverage;
	}


	public double getThdal1Average() {
		return thdal1Average;
	}


	public void setThdal1Average(double thdal1Average) {
		this.thdal1Average = thdal1Average;
	}


	public double getThdal2Average() {
		return thdal2Average;
	}


	public void setThdal2Average(double thdal2Average) {
		this.thdal2Average = thdal2Average;
	}


	public double getThdal3Average() {
		return thdal3Average;
	}


	public void setThdal3Average(double thdal3Average) {
		this.thdal3Average = thdal3Average;
	}


	public double getThdvl1nAverage() {
		return thdvl1nAverage;
	}


	public void setThdvl1nAverage(double thdvl1nAverage) {
		this.thdvl1nAverage = thdvl1nAverage;
	}


	public double getThdvl2nAverage() {
		return thdvl2nAverage;
	}


	public void setThdvl2nAverage(double thdvl2nAverage) {
		this.thdvl2nAverage = thdvl2nAverage;
	}


	public double getThdvl3nAverage() {
		return thdvl3nAverage;
	}


	public void setThdvl3nAverage(double thdvl3nAverage) {
		this.thdvl3nAverage = thdvl3nAverage;
	}


	public double getVadmdAverage() {
		return vadmdAverage;
	}


	public void setVadmdAverage(double vadmdAverage) {
		this.vadmdAverage = vadmdAverage;
	}


	public double getVardmdAverage() {
		return vardmdAverage;
	}


	public void setVardmdAverage(double vardmdAverage) {
		this.vardmdAverage = vardmdAverage;
	}


	public double getVl1l2Average() {
		return vl1l2Average;
	}


	public void setVl1l2Average(double vl1l2Average) {
		this.vl1l2Average = vl1l2Average;
	}


	public double getVl1nAverage() {
		return vl1nAverage;
	}


	public void setVl1nAverage(double vl1nAverage) {
		this.vl1nAverage = vl1nAverage;
	}


	public double getVl2l3Average() {
		return vl2l3Average;
	}


	public void setVl2l3Average(double vl2l3Average) {
		this.vl2l3Average = vl2l3Average;
	}


	public double getVl2nAverage() {
		return vl2nAverage;
	}


	public void setVl2nAverage(double vl2nAverage) {
		this.vl2nAverage = vl2nAverage;
	}


	public double getVl3l1Average() {
		return vl3l1Average;
	}


	public void setVl3l1Average(double vl3l1Average) {
		this.vl3l1Average = vl3l1Average;
	}


	public double getVl3nAverage() {
		return vl3nAverage;
	}


	public void setVl3nAverage(double vl3nAverage) {
		this.vl3nAverage = vl3nAverage;
	}


	public double getVllsysAverage() {
		return vllsysAverage;
	}


	public void setVllsysAverage(double vllsysAverage) {
		this.vllsysAverage = vllsysAverage;
	}


	public double getVlnsysAverage() {
		return vlnsysAverage;
	}


	public void setVlnsysAverage(double vlnsysAverage) {
		this.vlnsysAverage = vlnsysAverage;
	}

	
	
}
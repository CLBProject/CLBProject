  package clb.database.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * The persistent class for the ANALYZER_REGISTRY database table.
 * 
 */

@Document(collection="AnalyzersRegistriesAverage")
public class AnalyzerRegistryAverageEntity implements ClbEntity, Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    private String id;

	private String analyzerId;

	private double al1Average;

	private double al2Average;

	private double al3Average;

	private double anAverage;

	private double asysAverage;

	private int comport;

	private Date currenttime;

	private Date epochformat;

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

	private double kvarhcAverage;

	private double kvarhlAverage;

	private double kvarhl1Average;

	private double kvarhl1negativeAverage;

	private double kvarhl2Average;

	private double kvarhl2negativeAverage;

	private double kvarhl3Average;

	private double kvarhl3negativeAverage;

	private double kvarhnegativeAverage;

	private double kvarl1Average;

	private double kvarl2Average;

	private double kvarl3Average;

	private double kvarsysAverage;

	private double kvasysAverage;

	private double kwhAverage;

	private double kwhl1Average;

	private double kwhl1negativeAverage;

	private double kwhl2Average;

	private double kwhl2negativeAverage;

	private double kwhl3Average;

	private double kwhl3negativeAverage;

	private double kwhnegativeAverage;

	private double kwl1Average;

	private double kwl2Average;

	private double kwl3Average;

	private double kwsysAverage;

	private long modbusidAverage;

	private double pfl1Average;

	private double pfl2Average;

	private double pfl3Average;

	private double pfsysAverage;

	private double phasesequenceAverage;

	private String producttype;

	private String recorttype;

	private Date rfc3339format;

	private double temperatureAverage;

	private double thdal1Average;

	private double thdal2Average;

	private double thdal3Average;

	private double thdvl1nAverage;

	private double thdvl2nAverage;

	private double thdvl3nAverage;

	private double totalizer1Average;

	private double totalizer2Average;

	private double totalizer3Average;

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

	private double wdmdAverage;

	private double wdmdmaxAverage;

	public AnalyzerRegistryAverageEntity() {
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

    public void setAl1Average( double al1Average ) {
        this.al1Average = al1Average;
    }

    public double getAl2Average() {
        return al2Average;
    }

    public void setAl2Average( double al2Average ) {
        this.al2Average = al2Average;
    }

    public double getAl3Average() {
        return al3Average;
    }

    public void setAl3Average( double al3Average ) {
        this.al3Average = al3Average;
    }

    public double getAnAverage() {
        return anAverage;
    }

    public void setAnAverage( double anAverage ) {
        this.anAverage = anAverage;
    }

    public double getAsysAverage() {
        return asysAverage;
    }

    public void setAsysAverage( double asysAverage ) {
        this.asysAverage = asysAverage;
    }

    public int getComport() {
        return comport;
    }

    public void setComport( int comport ) {
        this.comport= comport;
    }

    public Date getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime( Date currenttime ) {
        this.currenttime = currenttime;
    }

    public Date getEpochformat() {
        return epochformat;
    }

    public void setEpochformat( Date epochformat ) {
        this.epochformat = epochformat;
    }

    public double getHourmeterkwhAverage() {
        return hourmeterkwhAverage;
    }

    public void setHourmeterkwhAverage( double hourmeterkwhAverage ) {
        this.hourmeterkwhAverage = hourmeterkwhAverage;
    }

    public double getHourmeterkwhnegativeAverage() {
        return hourmeterkwhnegativeAverage;
    }

    public void setHourmeterkwhnegativeAverage( double hourmeterkwhnegativeAverage ) {
        this.hourmeterkwhnegativeAverage = hourmeterkwhnegativeAverage;
    }

    public double getHzAverage() {
        return hzAverage;
    }

    public void setHzAverage( double hzAverage ) {
        this.hzAverage = hzAverage;
    }

    public String getItemlabel() {
        return itemlabel;
    }

    public void setItemlabel( String itemlabel ) {
        this.itemlabel = itemlabel;
    }

    public double getKvahlAverage() {
        return kvahlAverage;
    }

    public void setKvahlAverage( double kvahlAverage ) {
        this.kvahlAverage = kvahlAverage;
    }

    public double getKvahl1Average() {
        return kvahl1Average;
    }

    public void setKvahl1Average( double kvahl1Average ) {
        this.kvahl1Average = kvahl1Average;
    }

    public double getKvahl2Average() {
        return kvahl2Average;
    }

    public void setKvahl2Average( double kvahl2Average ) {
        this.kvahl2Average = kvahl2Average;
    }

    public double getKvahl3Average() {
        return kvahl3Average;
    }

    public void setKvahl3Average( double kvahl3Average ) {
        this.kvahl3Average = kvahl3Average;
    }

    public double getKval1Average() {
        return kval1Average;
    }

    public void setKval1Average( double kval1Average ) {
        this.kval1Average = kval1Average;
    }

    public double getKval2Average() {
        return kval2Average;
    }

    public void setKval2Average( double kval2Average ) {
        this.kval2Average = kval2Average;
    }

    public double getKval3Average() {
        return kval3Average;
    }

    public void setKval3Average( double kval3Average ) {
        this.kval3Average = kval3Average;
    }

    public double getKvarhAverage() {
        return kvarhAverage;
    }

    public void setKvarhAverage( double kvarhAverage ) {
        this.kvarhAverage = kvarhAverage;
    }

    public double getKvarhcAverage() {
        return kvarhcAverage;
    }

    public void setKvarhcAverage( double kvarhcAverage ) {
        this.kvarhcAverage = kvarhcAverage;
    }

    public double getKvarhlAverage() {
        return kvarhlAverage;
    }

    public void setKvarhlAverage( double kvarhlAverage ) {
        this.kvarhlAverage = kvarhlAverage;
    }

    public double getKvarhl1Average() {
        return kvarhl1Average;
    }

    public void setKvarhl1Average( double kvarhl1Average ) {
        this.kvarhl1Average = kvarhl1Average;
    }

    public double getKvarhl1negativeAverage() {
        return kvarhl1negativeAverage;
    }

    public void setKvarhl1negativeAverage( double kvarhl1negativeAverage ) {
        this.kvarhl1negativeAverage = kvarhl1negativeAverage;
    }

    public double getKvarhl2Average() {
        return kvarhl2Average;
    }

    public void setKvarhl2Average( double kvarhl2Average ) {
        this.kvarhl2Average = kvarhl2Average;
    }

    public double getKvarhl2negativeAverage() {
        return kvarhl2negativeAverage;
    }

    public void setKvarhl2negativeAverage( double kvarhl2negativeAverage ) {
        this.kvarhl2negativeAverage = kvarhl2negativeAverage;
    }

    public double getKvarhl3Average() {
        return kvarhl3Average;
    }

    public void setKvarhl3Average( double kvarhl3Average ) {
        this.kvarhl3Average = kvarhl3Average;
    }

    public double getKvarhl3negativeAverage() {
        return kvarhl3negativeAverage;
    }

    public void setKvarhl3negativeAverage( double kvarhl3negativeAverage ) {
        this.kvarhl3negativeAverage = kvarhl3negativeAverage;
    }

    public double getKvarhnegativeAverage() {
        return kvarhnegativeAverage;
    }

    public void setKvarhnegativeAverage( double kvarhnegativeAverage ) {
        this.kvarhnegativeAverage = kvarhnegativeAverage;
    }

    public double getKvarl1Average() {
        return kvarl1Average;
    }

    public void setKvarl1Average( double kvarl1Average ) {
        this.kvarl1Average = kvarl1Average;
    }

    public double getKvarl2Average() {
        return kvarl2Average;
    }

    public void setKvarl2Average( double kvarl2Average ) {
        this.kvarl2Average = kvarl2Average;
    }

    public double getKvarl3Average() {
        return kvarl3Average;
    }

    public void setKvarl3Average( double kvarl3Average ) {
        this.kvarl3Average = kvarl3Average;
    }

    public double getKvarsysAverage() {
        return kvarsysAverage;
    }

    public void setKvarsysAverage( double kvarsysAverage ) {
        this.kvarsysAverage = kvarsysAverage;
    }

    public double getKvasysAverage() {
        return kvasysAverage;
    }

    public void setKvasysAverage( double kvasysAverage ) {
        this.kvasysAverage = kvasysAverage;
    }

    public double getKwhAverage() {
        return kwhAverage;
    }

    public void setKwhAverage( double kwhAverage ) {
        this.kwhAverage = kwhAverage;
    }

    public double getKwhl1Average() {
        return kwhl1Average;
    }

    public void setKwhl1Average( double kwhl1Average ) {
        this.kwhl1Average = kwhl1Average;
    }

    public double getKwhl1negativeAverage() {
        return kwhl1negativeAverage;
    }

    public void setKwhl1negativeAverage( double kwhl1negativeAverage ) {
        this.kwhl1negativeAverage = kwhl1negativeAverage;
    }

    public double getKwhl2Average() {
        return kwhl2Average;
    }

    public void setKwhl2Average( double kwhl2Average ) {
        this.kwhl2Average = kwhl2Average;
    }

    public double getKwhl2negativeAverage() {
        return kwhl2negativeAverage;
    }

    public void setKwhl2negativeAverage( double kwhl2negativeAverage ) {
        this.kwhl2negativeAverage = kwhl2negativeAverage;
    }

    public double getKwhl3Average() {
        return kwhl3Average;
    }

    public void setKwhl3Average( double kwhl3Average ) {
        this.kwhl3Average = kwhl3Average;
    }

    public double getKwhl3negativeAverage() {
        return kwhl3negativeAverage;
    }

    public void setKwhl3negativeAverage( double kwhl3negativeAverage ) {
        this.kwhl3negativeAverage = kwhl3negativeAverage;
    }

    public double getKwhnegativeAverage() {
        return kwhnegativeAverage;
    }

    public void setKwhnegativeAverage( double kwhnegativeAverage ) {
        this.kwhnegativeAverage = kwhnegativeAverage;
    }

    public double getKwl1Average() {
        return kwl1Average;
    }

    public void setKwl1Average( double kwl1Average ) {
        this.kwl1Average = kwl1Average;
    }

    public double getKwl2Average() {
        return kwl2Average;
    }

    public void setKwl2Average( double kwl2Average ) {
        this.kwl2Average = kwl2Average;
    }

    public double getKwl3Average() {
        return kwl3Average;
    }

    public void setKwl3Average( double kwl3Average ) {
        this.kwl3Average = kwl3Average;
    }

    public double getKwsysAverage() {
        return kwsysAverage;
    }

    public void setKwsysAverage( double kwsysAverage ) {
        this.kwsysAverage = kwsysAverage;
    }

    public long getModbusidAverage() {
        return modbusidAverage;
    }

    public void setModbusidAverage( long modbusidAverage ) {
        this.modbusidAverage = modbusidAverage;
    }

    public double getPfl1Average() {
        return pfl1Average;
    }

    public void setPfl1Average( double pfl1Average ) {
        this.pfl1Average = pfl1Average;
    }

    public double getPfl2Average() {
        return pfl2Average;
    }

    public void setPfl2Average( double pfl2Average ) {
        this.pfl2Average = pfl2Average;
    }

    public double getPfl3Average() {
        return pfl3Average;
    }

    public void setPfl3Average( double pfl3Average ) {
        this.pfl3Average = pfl3Average;
    }

    public double getPfsysAverage() {
        return pfsysAverage;
    }

    public void setPfsysAverage( double pfsysAverage ) {
        this.pfsysAverage = pfsysAverage;
    }

    public double getPhasesequenceAverage() {
        return phasesequenceAverage;
    }

    public void setPhasesequenceAverage( double phasesequenceAverage ) {
        this.phasesequenceAverage = phasesequenceAverage;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype( String producttype ) {
        this.producttype = producttype;
    }

    public String getRecorttype() {
        return recorttype;
    }

    public void setRecorttype( String recorttype ) {
        this.recorttype = recorttype;
    }

    public Date getRfc3339format() {
        return rfc3339format;
    }

    public void setRfc3339format( Date rfc3339format ) {
        this.rfc3339format = rfc3339format;
    }

    public double getTemperatureAverage() {
        return temperatureAverage;
    }

    public void setTemperatureAverage( double temperatureAverage ) {
        this.temperatureAverage = temperatureAverage;
    }

    public double getThdal1Average() {
        return thdal1Average;
    }

    public void setThdal1Average( double thdal1Average ) {
        this.thdal1Average = thdal1Average;
    }

    public double getThdal2Average() {
        return thdal2Average;
    }

    public void setThdal2Average( double thdal2Average ) {
        this.thdal2Average = thdal2Average;
    }

    public double getThdal3Average() {
        return thdal3Average;
    }

    public void setThdal3Average( double thdal3Average ) {
        this.thdal3Average = thdal3Average;
    }

    public double getThdvl1nAverage() {
        return thdvl1nAverage;
    }

    public void setThdvl1nAverage( double thdvl1nAverage ) {
        this.thdvl1nAverage = thdvl1nAverage;
    }

    public double getThdvl2nAverage() {
        return thdvl2nAverage;
    }

    public void setThdvl2nAverage( double thdvl2nAverage ) {
        this.thdvl2nAverage = thdvl2nAverage;
    }

    public double getThdvl3nAverage() {
        return thdvl3nAverage;
    }

    public void setThdvl3nAverage( double thdvl3nAverage ) {
        this.thdvl3nAverage = thdvl3nAverage;
    }

    public double getTotalizer1Average() {
        return totalizer1Average;
    }

    public void setTotalizer1Average( double totalizer1Average ) {
        this.totalizer1Average = totalizer1Average;
    }

    public double getTotalizer2Average() {
        return totalizer2Average;
    }

    public void setTotalizer2Average( double totalizer2Average ) {
        this.totalizer2Average = totalizer2Average;
    }

    public double getTotalizer3Average() {
        return totalizer3Average;
    }

    public void setTotalizer3Average( double totalizer3Average ) {
        this.totalizer3Average = totalizer3Average;
    }

    public double getVadmdAverage() {
        return vadmdAverage;
    }

    public void setVadmdAverage( double vadmdAverage ) {
        this.vadmdAverage = vadmdAverage;
    }

    public double getVardmdAverage() {
        return vardmdAverage;
    }

    public void setVardmdAverage( double vardmdAverage ) {
        this.vardmdAverage = vardmdAverage;
    }

    public double getVl1l2Average() {
        return vl1l2Average;
    }

    public void setVl1l2Average( double vl1l2Average ) {
        this.vl1l2Average = vl1l2Average;
    }

    public double getVl1nAverage() {
        return vl1nAverage;
    }

    public void setVl1nAverage( double vl1nAverage ) {
        this.vl1nAverage = vl1nAverage;
    }

    public double getVl2l3Average() {
        return vl2l3Average;
    }

    public void setVl2l3Average( double vl2l3Average ) {
        this.vl2l3Average = vl2l3Average;
    }

    public double getVl2nAverage() {
        return vl2nAverage;
    }

    public void setVl2nAverage( double vl2nAverage ) {
        this.vl2nAverage = vl2nAverage;
    }

    public double getVl3l1Average() {
        return vl3l1Average;
    }

    public void setVl3l1Average( double vl3l1Average ) {
        this.vl3l1Average = vl3l1Average;
    }

    public double getVl3nAverage() {
        return vl3nAverage;
    }

    public void setVl3nAverage( double vl3nAverage ) {
        this.vl3nAverage = vl3nAverage;
    }

    public double getVllsysAverage() {
        return vllsysAverage;
    }

    public void setVllsysAverage( double vllsysAverage ) {
        this.vllsysAverage = vllsysAverage;
    }

    public double getVlnsysAverage() {
        return vlnsysAverage;
    }

    public void setVlnsysAverage( double vlnsysAverage ) {
        this.vlnsysAverage = vlnsysAverage;
    }

    public double getWdmdAverage() {
        return wdmdAverage;
    }

    public void setWdmdAverage( double wdmdAverage ) {
        this.wdmdAverage = wdmdAverage;
    }

    public double getWdmdmaxAverage() {
        return wdmdmaxAverage;
    }

    public void setWdmdmaxAverage( double wdmdmaxAverage ) {
        this.wdmdmaxAverage = wdmdmaxAverage;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getAnalyzerId() {
        return analyzerId;
    }

    public void setAnalyzerId( String analyzerId ) {
        this.analyzerId = analyzerId;
    }
	
}
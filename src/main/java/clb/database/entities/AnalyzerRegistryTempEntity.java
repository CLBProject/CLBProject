package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ANALYZER_REGISTRY_TEMP database table.
 * 
 */
@Entity
@Table(name="ANALYZER_REGISTRY_TEMP")
@NamedQuery(name="AnalyzerRegistryTemp.findAll", query="SELECT a FROM AnalyzerRegistryTempEntity a")
public class AnalyzerRegistryTempEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long regid;

	private double al1;

	private double al2;

	private double al3;

	private double an;

	private double asys;

	private int comport;

	@Temporal(TemporalType.DATE)
	private Date currentdate;

	private String currenttime;

	private Timestamp epochformat;

	private double hourmeterkwh;

	private double hourmeterkwhnegative;

	private double hz;

	private String itemlabel;

	private double kvahl;

	private double kvahl1;

	private double kvahl2;

	private double kvahl3;

	private double kval1;

	private double kval2;

	private double kval3;

	private double kvarh;

	private double kvarl1;

	private double kvarl2;

	private double kvarl3;

	private double kvarsys;

	private double kvasys;

	private double kwh;

	private double kwhl1;

	private double kwhl2;

	private double kwhl3;

	private double kwl1;

	private double kwl2;

	private double kwl3;

	private double kwsys;

	private long modbusid;

	private double pfl1;

	private double pfl2;

	private double pfl3;

	private double pfsys;

	private String producttype;

	private String recorttype;

	private Timestamp rfc3339format;

	private double temperature;

	private double thdal1;

	private double thdal2;

	private double thdal3;

	private double thdvl1n;

	private double thdvl2n;

	private double thdvl3n;

	private double vadmd;

	private double vardmd;

	private double vl1l2;

	private double vl1n;

	private double vl2l3;

	private double vl2n;

	private double vl3l1;

	private double vl3n;

	private double vllsys;

	private double vlnsys;

	//bi-directional many-to-one association to AnalyzerRegistryExtraInfoTemp
	@OneToMany(mappedBy="analyzerRegistryTemp")
	private List<AnalyzerRegistryExtraInfoTempEntity> analyzerRegistryExtraInfoTemps;

	//bi-directional many-to-one association to Analyzer
	@ManyToOne
	@JoinColumn(name="ANALYZERID")
	private AnalyzerEntity analyzer;

	public AnalyzerRegistryTempEntity() {
	}

	public long getRegid() {
		return this.regid;
	}

	public void setRegid(long regid) {
		this.regid = regid;
	}

	public double getAl1() {
		return this.al1;
	}

	public void setAl1(double al1) {
		this.al1 = al1;
	}

	public double getAl2() {
		return this.al2;
	}

	public void setAl2(double al2) {
		this.al2 = al2;
	}

	public double getAl3() {
		return this.al3;
	}

	public void setAl3(double al3) {
		this.al3 = al3;
	}

	public double getAn() {
		return this.an;
	}

	public void setAn(double an) {
		this.an = an;
	}

	public double getAsys() {
		return this.asys;
	}

	public void setAsys(double asys) {
		this.asys = asys;
	}

	public int getComport() {
		return this.comport;
	}

	public void setComport(int comport) {
		this.comport = comport;
	}

	public Date getCurrentdate() {
		return this.currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}

	public String getCurrenttime() {
		return this.currenttime;
	}

	public void setCurrenttime(String currenttime) {
		this.currenttime = currenttime;
	}

	public Timestamp getEpochformat() {
		return this.epochformat;
	}

	public void setEpochformat(Timestamp epochformat) {
		this.epochformat = epochformat;
	}

	public double getHourmeterkwh() {
		return this.hourmeterkwh;
	}

	public void setHourmeterkwh(double hourmeterkwh) {
		this.hourmeterkwh = hourmeterkwh;
	}

	public double getHourmeterkwhnegative() {
		return this.hourmeterkwhnegative;
	}

	public void setHourmeterkwhnegative(double hourmeterkwhnegative) {
		this.hourmeterkwhnegative = hourmeterkwhnegative;
	}

	public double getHz() {
		return this.hz;
	}

	public void setHz(double hz) {
		this.hz = hz;
	}

	public String getItemlabel() {
		return this.itemlabel;
	}

	public void setItemlabel(String itemlabel) {
		this.itemlabel = itemlabel;
	}

	public double getKvahl() {
		return this.kvahl;
	}

	public void setKvahl(double kvahl) {
		this.kvahl = kvahl;
	}

	public double getKvahl1() {
		return this.kvahl1;
	}

	public void setKvahl1(double kvahl1) {
		this.kvahl1 = kvahl1;
	}

	public double getKvahl2() {
		return this.kvahl2;
	}

	public void setKvahl2(double kvahl2) {
		this.kvahl2 = kvahl2;
	}

	public double getKvahl3() {
		return this.kvahl3;
	}

	public void setKvahl3(double kvahl3) {
		this.kvahl3 = kvahl3;
	}

	public double getKval1() {
		return this.kval1;
	}

	public void setKval1(double kval1) {
		this.kval1 = kval1;
	}

	public double getKval2() {
		return this.kval2;
	}

	public void setKval2(double kval2) {
		this.kval2 = kval2;
	}

	public double getKval3() {
		return this.kval3;
	}

	public void setKval3(double kval3) {
		this.kval3 = kval3;
	}

	public double getKvarh() {
		return this.kvarh;
	}

	public void setKvarh(double kvarh) {
		this.kvarh = kvarh;
	}

	public double getKvarl1() {
		return this.kvarl1;
	}

	public void setKvarl1(double kvarl1) {
		this.kvarl1 = kvarl1;
	}

	public double getKvarl2() {
		return this.kvarl2;
	}

	public void setKvarl2(double kvarl2) {
		this.kvarl2 = kvarl2;
	}

	public double getKvarl3() {
		return this.kvarl3;
	}

	public void setKvarl3(double kvarl3) {
		this.kvarl3 = kvarl3;
	}

	public double getKvarsys() {
		return this.kvarsys;
	}

	public void setKvarsys(double kvarsys) {
		this.kvarsys = kvarsys;
	}

	public double getKvasys() {
		return this.kvasys;
	}

	public void setKvasys(double kvasys) {
		this.kvasys = kvasys;
	}

	public double getKwh() {
		return this.kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public double getKwhl1() {
		return this.kwhl1;
	}

	public void setKwhl1(double kwhl1) {
		this.kwhl1 = kwhl1;
	}

	public double getKwhl2() {
		return this.kwhl2;
	}

	public void setKwhl2(double kwhl2) {
		this.kwhl2 = kwhl2;
	}

	public double getKwhl3() {
		return this.kwhl3;
	}

	public void setKwhl3(double kwhl3) {
		this.kwhl3 = kwhl3;
	}

	public double getKwl1() {
		return this.kwl1;
	}

	public void setKwl1(double kwl1) {
		this.kwl1 = kwl1;
	}

	public double getKwl2() {
		return this.kwl2;
	}

	public void setKwl2(double kwl2) {
		this.kwl2 = kwl2;
	}

	public double getKwl3() {
		return this.kwl3;
	}

	public void setKwl3(double kwl3) {
		this.kwl3 = kwl3;
	}

	public double getKwsys() {
		return this.kwsys;
	}

	public void setKwsys(double kwsys) {
		this.kwsys = kwsys;
	}

	public long getModbusid() {
		return this.modbusid;
	}

	public void setModbusid(long modbusid) {
		this.modbusid = modbusid;
	}

	public double getPfl1() {
		return this.pfl1;
	}

	public void setPfl1(double pfl1) {
		this.pfl1 = pfl1;
	}

	public double getPfl2() {
		return this.pfl2;
	}

	public void setPfl2(double pfl2) {
		this.pfl2 = pfl2;
	}

	public double getPfl3() {
		return this.pfl3;
	}

	public void setPfl3(double pfl3) {
		this.pfl3 = pfl3;
	}

	public double getPfsys() {
		return this.pfsys;
	}

	public void setPfsys(double pfsys) {
		this.pfsys = pfsys;
	}

	public String getProducttype() {
		return this.producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getRecorttype() {
		return this.recorttype;
	}

	public void setRecorttype(String recorttype) {
		this.recorttype = recorttype;
	}

	public Timestamp getRfc3339format() {
		return this.rfc3339format;
	}

	public void setRfc3339format(Timestamp rfc3339format) {
		this.rfc3339format = rfc3339format;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getThdal1() {
		return this.thdal1;
	}

	public void setThdal1(double thdal1) {
		this.thdal1 = thdal1;
	}

	public double getThdal2() {
		return this.thdal2;
	}

	public void setThdal2(double thdal2) {
		this.thdal2 = thdal2;
	}

	public double getThdal3() {
		return this.thdal3;
	}

	public void setThdal3(double thdal3) {
		this.thdal3 = thdal3;
	}

	public double getThdvl1n() {
		return this.thdvl1n;
	}

	public void setThdvl1n(double thdvl1n) {
		this.thdvl1n = thdvl1n;
	}

	public double getThdvl2n() {
		return this.thdvl2n;
	}

	public void setThdvl2n(double thdvl2n) {
		this.thdvl2n = thdvl2n;
	}

	public double getThdvl3n() {
		return this.thdvl3n;
	}

	public void setThdvl3n(double thdvl3n) {
		this.thdvl3n = thdvl3n;
	}

	public double getVadmd() {
		return this.vadmd;
	}

	public void setVadmd(double vadmd) {
		this.vadmd = vadmd;
	}

	public double getVardmd() {
		return this.vardmd;
	}

	public void setVardmd(double vardmd) {
		this.vardmd = vardmd;
	}

	public double getVl1l2() {
		return this.vl1l2;
	}

	public void setVl1l2(double vl1l2) {
		this.vl1l2 = vl1l2;
	}

	public double getVl1n() {
		return this.vl1n;
	}

	public void setVl1n(double vl1n) {
		this.vl1n = vl1n;
	}

	public double getVl2l3() {
		return this.vl2l3;
	}

	public void setVl2l3(double vl2l3) {
		this.vl2l3 = vl2l3;
	}

	public double getVl2n() {
		return this.vl2n;
	}

	public void setVl2n(double vl2n) {
		this.vl2n = vl2n;
	}

	public double getVl3l1() {
		return this.vl3l1;
	}

	public void setVl3l1(double vl3l1) {
		this.vl3l1 = vl3l1;
	}

	public double getVl3n() {
		return this.vl3n;
	}

	public void setVl3n(double vl3n) {
		this.vl3n = vl3n;
	}

	public double getVllsys() {
		return this.vllsys;
	}

	public void setVllsys(double vllsys) {
		this.vllsys = vllsys;
	}

	public double getVlnsys() {
		return this.vlnsys;
	}

	public void setVlnsys(double vlnsys) {
		this.vlnsys = vlnsys;
	}

	public List<AnalyzerRegistryExtraInfoTempEntity> getAnalyzerRegistryExtraInfoTemps() {
		return this.analyzerRegistryExtraInfoTemps;
	}

	public void setAnalyzerRegistryExtraInfoTemps(List<AnalyzerRegistryExtraInfoTempEntity> analyzerRegistryExtraInfoTemps) {
		this.analyzerRegistryExtraInfoTemps = analyzerRegistryExtraInfoTemps;
	}

	public AnalyzerRegistryExtraInfoTempEntity addAnalyzerRegistryExtraInfoTemp(AnalyzerRegistryExtraInfoTempEntity analyzerRegistryExtraInfoTemp) {
		getAnalyzerRegistryExtraInfoTemps().add(analyzerRegistryExtraInfoTemp);
		analyzerRegistryExtraInfoTemp.setAnalyzerRegistryTemp(this);

		return analyzerRegistryExtraInfoTemp;
	}

	public AnalyzerRegistryExtraInfoTempEntity removeAnalyzerRegistryExtraInfoTemp(AnalyzerRegistryExtraInfoTempEntity analyzerRegistryExtraInfoTemp) {
		getAnalyzerRegistryExtraInfoTemps().remove(analyzerRegistryExtraInfoTemp);
		analyzerRegistryExtraInfoTemp.setAnalyzerRegistryTemp(null);

		return analyzerRegistryExtraInfoTemp;
	}

	public AnalyzerEntity getAnalyzer() {
		return this.analyzer;
	}

	public void setAnalyzer(AnalyzerEntity analyzer) {
		this.analyzer = analyzer;
	}

}
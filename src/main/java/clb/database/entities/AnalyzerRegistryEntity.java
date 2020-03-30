package clb.database.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


/**
 * The persistent class for the ANALYZER_REGISTRY database table.
 * 
 */

@Document
public class AnalyzerRegistryEntity implements ClbEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    private String analyzerId;

    //Current
    private double asys;

    //Current Phase 1
    private double al1;

    //Current Phase 2
    private double al2;

    //Current Phase 3
    private double al3;

    private double an;

    //Com Port
    private int comport;
    
    //Current Time
    private Date currenttime;

    //Epoch Format
    private Date epochformat;

    private double hourmeterkwh;

    private double hourmeterkwhnegative;

    //Frequency
    private double hz;

    //Item Label
    private String itemlabel;

    private double kvahl;

    private double kvahl1;

    private double kvahl2;

    private double kvahl3;


    //Volt-Ampere
    private double kvasys;

    //Volt-Ampere Phase 1
    private double kval1;

    //Volt-Ampere Phase 2
    private double kval2;

    //Volt-Ampere Phase 3
    private double kval3;

    //Reactive Energy Consumption
    private double kvarh;

    //Reactive Energy Consumption Phase 1
    private double kvarhl1;

    //Reactive Energy Consumption Phase 2
    private double kvarhl2;

    //Reactive Energy Consumption Phase 3
    private double kvarhl3;

    private double kvarhc;

    private double kvarhl;

    private double kvarhl1negative;

    private double kvarhl2negative;

    private double kvarhl3negative;

    private double kvarhnegative;

    //Reactive Power
    private double kvarsys;

    //Reactive Power Phase 1
    private double kvarl1;

    //Reactive Power Phase 2
    private double kvarl2;

    //Reactive Power Phase 3
    private double kvarl3;

    private double kwh;

    private double kwhl1;

    private double kwhl1negative;

    private double kwhl2;

    private double kwhl2negative;

    private double kwhl3;

    private double kwhl3negative;

    private double kwhnegative;

    //Power Phase 1
    private double kwl1;

    //Power Phase 2
    private double kwl2;

    //Power Phase 3
    private double kwl3;

    //Power
    private double kwsys;

    //Modbus Id
    private long modbusid;

    //Power Factor L1
    private double pfl1;

    //Power Factor L2
    private double pfl2;

    //Power Factor L3
    private double pfl3;

    //Power Factor
    private double pfsys;

    private double phasesequence;

    //Product Type
    private String producttype;

    //Recort Type
    private String recorttype;

    //RFC3339 Format
    private Date rfc3339format;

    //Temperature
    private double temperature;

    //THDA Phase 1
    private double thdal1;

    //THDA Phase 2
    private double thdal2;

    //THDA Phase 3
    private double thdal3;

    //THDV Phase 1
    private double thdvl1n;

    //THDV Phase 2
    private double thdvl2n;

    //THDV Phase 3
    private double thdvl3n;

    private double totalizer1;

    private double totalizer2;

    private double totalizer3;

    private double vadmd;

    private double vardmd;

    //Tension Betwween 1 & 2
    private double vl1l2;
    
    //Tension Phase 1
    private double vl1n;

    //Tension Between 2 & 3
    private double vl2l3;

    //Tension Phase 2
    private double vl2n;

    //Tension Between 1 & 3
    private double vl3l1;

    //Tension Phase 3
    private double vl3n;

    //Global Tension Between Phases
    private double vllsys;

    //Global System Tension
    private double vlnsys;

    private double wdmd;

    private double wdmdmax;
    

    public AnalyzerRegistryEntity() {
    }

    public DBObject toDbObject() {
        DBObject dbObj = new BasicDBObject("vlnsys", vlnsys )
                .append( "vllsys", vllsys )
                .append( "kwsys", kwsys )
                .append( "kvarsys", kvarsys )
                .append( "kvasys", kvasys )
                .append( "pfsys", pfsys )
                .append( "hz", hz )
                .append( "asys", asys )
                .append( "currenttime", currenttime )
        		.append( "analyzerId", analyzerId );
        return dbObj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCurrenttime() {
        return this.currenttime;
    }

    public void setCurrenttime(Date currenttime) {
        this.currenttime = currenttime;
    }

    public Date getEpochformat() {
        return this.epochformat;
    }

    public void setEpochformat(Date epochformat) {
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

    public double getKvarhc() {
        return this.kvarhc;
    }

    public void setKvarhc(double kvarhc) {
        this.kvarhc = kvarhc;
    }

    public double getKvarhl() {
        return this.kvarhl;
    }

    public void setKvarhl(double kvarhl) {
        this.kvarhl = kvarhl;
    }

    public double getKvarhl1() {
        return this.kvarhl1;
    }

    public void setKvarhl1(double kvarhl1) {
        this.kvarhl1 = kvarhl1;
    }

    public double getKvarhl1negative() {
        return this.kvarhl1negative;
    }

    public void setKvarhl1negative(double kvarhl1negative) {
        this.kvarhl1negative = kvarhl1negative;
    }

    public double getKvarhl2() {
        return this.kvarhl2;
    }

    public void setKvarhl2(double kvarhl2) {
        this.kvarhl2 = kvarhl2;
    }

    public double getKvarhl2negative() {
        return this.kvarhl2negative;
    }

    public void setKvarhl2negative(double kvarhl2negative) {
        this.kvarhl2negative = kvarhl2negative;
    }

    public double getKvarhl3() {
        return this.kvarhl3;
    }

    public void setKvarhl3(double kvarhl3) {
        this.kvarhl3 = kvarhl3;
    }

    public double getKvarhl3negative() {
        return this.kvarhl3negative;
    }

    public void setKvarhl3negative(double kvarhl3negative) {
        this.kvarhl3negative = kvarhl3negative;
    }

    public double getKvarhnegative() {
        return this.kvarhnegative;
    }

    public void setKvarhnegative(double kvarhnegative) {
        this.kvarhnegative = kvarhnegative;
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

    public double getKwhl1negative() {
        return this.kwhl1negative;
    }

    public void setKwhl1negative(double kwhl1negative) {
        this.kwhl1negative = kwhl1negative;
    }

    public double getKwhl2() {
        return this.kwhl2;
    }

    public void setKwhl2(double kwhl2) {
        this.kwhl2 = kwhl2;
    }

    public double getKwhl2negative() {
        return this.kwhl2negative;
    }

    public void setKwhl2negative(double kwhl2negative) {
        this.kwhl2negative = kwhl2negative;
    }

    public double getKwhl3() {
        return this.kwhl3;
    }

    public void setKwhl3(double kwhl3) {
        this.kwhl3 = kwhl3;
    }

    public double getKwhl3negative() {
        return this.kwhl3negative;
    }

    public void setKwhl3negative(double kwhl3negative) {
        this.kwhl3negative = kwhl3negative;
    }

    public double getKwhnegative() {
        return this.kwhnegative;
    }

    public void setKwhnegative(double kwhnegative) {
        this.kwhnegative = kwhnegative;
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

    public double getPhasesequence() {
        return this.phasesequence;
    }

    public void setPhasesequence(double phasesequence) {
        this.phasesequence = phasesequence;
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

    public Date getRfc3339format() {
        return this.rfc3339format;
    }

    public void setRfc3339format(Date rfc3339format) {
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

    public double getTotalizer1() {
        return this.totalizer1;
    }

    public void setTotalizer1(double totalizer1) {
        this.totalizer1 = totalizer1;
    }

    public double getTotalizer2() {
        return this.totalizer2;
    }

    public void setTotalizer2(double totalizer2) {
        this.totalizer2 = totalizer2;
    }

    public double getTotalizer3() {
        return this.totalizer3;
    }

    public void setTotalizer3(double totalizer3) {
        this.totalizer3 = totalizer3;
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

    public double getWdmd() {
        return this.wdmd;
    }

    public void setWdmd(double wdmd) {
        this.wdmd = wdmd;
    }

    public double getWdmdmax() {
        return this.wdmdmax;
    }

    public void setWdmdmax(double wdmdmax) {
        this.wdmdmax = wdmdmax;
    }

	public String getAnalyzerId() {
		return analyzerId;
	}

	public void setAnalyzerId(String analyzerId) {
		this.analyzerId = analyzerId;
	}
    
    

}
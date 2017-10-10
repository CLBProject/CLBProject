package clb.database.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DATA_LOGGER_EXTRA_INFO database table.
 * 
 */
@Entity
@Table(name="DATA_LOGGER_EXTRA_INFO")
@NamedQuery(name="DataLoggerExtraInfo.findAll", query="SELECT d FROM DataLoggerExtraInfo d")
public class DataLoggerExtraInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long itemid;

	private double kvarhc;

	private double kvarhl;

	private double kvarhl1;

	private double kvarhl1negative;

	private double kvarhl2;

	private double kvarhl2negative;

	private double kvarhl3;

	private double kvarhl3negative;

	private double kvarhnegative;

	private double kwhl1negative;

	private double kwhl2negative;

	private double kwhl3negative;

	private double kwhnegative;

	private double phasesequence;

	private double totalizer1;

	private double totalizer2;

	private double totalizer3;

	private double wdmd;

	private double wdmdmax;

	//bi-directional many-to-one association to DataLogger
	@ManyToOne
	@JoinColumn(name="DATALOGGERID")
	private DataLogger dataLogger;

	public DataLoggerExtraInfo() {
	}

	public long getItemid() {
		return this.itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
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

	public double getKwhl1negative() {
		return this.kwhl1negative;
	}

	public void setKwhl1negative(double kwhl1negative) {
		this.kwhl1negative = kwhl1negative;
	}

	public double getKwhl2negative() {
		return this.kwhl2negative;
	}

	public void setKwhl2negative(double kwhl2negative) {
		this.kwhl2negative = kwhl2negative;
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

	public double getPhasesequence() {
		return this.phasesequence;
	}

	public void setPhasesequence(double phasesequence) {
		this.phasesequence = phasesequence;
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

	public DataLogger getDataLogger() {
		return this.dataLogger;
	}

	public void setDataLogger(DataLogger dataLogger) {
		this.dataLogger = dataLogger;
	}

}
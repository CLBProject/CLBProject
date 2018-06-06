package clb.beans.pojos;

import java.util.Date;

public class AnalyzerRegistryGui {

	private Double asys;
	private Double hz;
	private Double kwsys;
	private Double pfsys;
	private Double kvarsys;
	private Double kvasys;
	private Double vlnsys;
	private Double vllsys;
	private Date currentTime;

	public AnalyzerRegistryGui(Double asys, Double hz, Double kwsys, Double pfsys, Double kvarsys, Double kvasys,
			Double vlnsys, Double vllsys, Date currentTime) {
		super();
		this.asys = asys;
		this.hz = hz;
		this.kwsys = kwsys;
		this.pfsys = pfsys;
		this.kvarsys = kvarsys;
		this.kvasys = kvasys;
		this.vlnsys = vlnsys;
		this.vllsys = vllsys;
		this.currentTime = currentTime;
	}
	
	
	public Double getAsys() {
		return asys;
	}
	public void setAsys(Double asys) {
		this.asys = asys;
	}
	public Double getHz() {
		return hz;
	}
	public void setHz(Double hz) {
		this.hz = hz;
	}
	public Double getKwsys() {
		return kwsys;
	}
	public void setKwsys(Double kwsys) {
		this.kwsys = kwsys;
	}
	public Double getPfsys() {
		return pfsys;
	}
	public void setPfsys(Double pfsys) {
		this.pfsys = pfsys;
	}
	public Double getKvarsys() {
		return kvarsys;
	}
	public void setKvarsys(Double kvarsys) {
		this.kvarsys = kvarsys;
	}
	public Double getKvasys() {
		return kvasys;
	}
	public void setKvasys(Double kvasys) {
		this.kvasys = kvasys;
	}
	public Double getVlnsys() {
		return vlnsys;
	}
	public void setVlnsys(Double vlnsys) {
		this.vlnsys = vlnsys;
	}
	public Double getVllsys() {
		return vllsys;
	}
	public void setVllsys(Double vllsys) {
		this.vllsys = vllsys;
	}


	public Date getCurrentTime() {
		return currentTime;
	}


	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	
}

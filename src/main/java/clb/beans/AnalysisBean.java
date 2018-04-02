package clb.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import clb.beans.enums.AnalysisTypes;
import clb.beans.enums.Hours;
import clb.beans.enums.Months;
import clb.beans.enums.ScaleGraphic;
import clb.beans.pojos.AnalysisGraphicPojo;
import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.global.DateUtils;

@ViewScoped
@ManagedBean
public class AnalysisBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{clbHomeLoginBean}")
	private ClbHomeLoginBean clbHomeLoginBean;

	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;

	private Date analysisDate;
	private AnalysisGraphicPojo analysisDayPojo;

	private List<BuildingObject> buildingsToSelect;
	private BuildingObject tempBuildingSelected;
	private BuildingObject buildingSelected;

	private List<AnalyzerObject> analyzersSelected;
	private AnalyzerObject tempAnalyzerSelected;
	private AnalyzerObject analyzerSelected;

	private AnalysisTypes analysisType;
	private AnalysisTypes[] analysisTypes;

	private ScaleGraphic scaleGraphic;
	private ScaleGraphic[] scalesGraphic;
	
	private Hours hour;
	private Hours[] hoursValues;
	
	private Months month;
	private Months[] monthsValues;

	@PostConstruct
	public void init() {
		analysisDate = new Date();
		analysisTypes = AnalysisTypes.values();
		scalesGraphic = ScaleGraphic.values();
		scaleGraphic = ScaleGraphic.HOUR;
		hoursValues = Hours.values();
		hour = Hours.getHourByValue(DateUtils.getInstance().getHourFromDate(analysisDate));
		
		monthsValues = Months.values();
		month = Months.getMonthByValue(DateUtils.getInstance().getMonthFromDate(analysisDate));

		//Set Initial Selected Building, DataLogger and Analyzer
		if(clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings() != null && 
				clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings().size() > 0 ) {

			buildingsToSelect = clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings();

			boolean firstTime = false;

			for(BuildingObject bObj: clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings()) {
				if(bObj.getBuildingMeters() != null && bObj.getBuildingMeters().size() > 0 && bObj.getDataLoggers() != null) {
					for(DataLoggerObject dlObj: bObj.getDataLoggers()) {
						if(dlObj.getAnalyzers() != null) {
							for(AnalyzerObject aObj : dlObj.getAnalyzers()) {
								if(!firstTime) {
									buildingSelected = bObj;
									tempBuildingSelected = buildingSelected;
									analyzersSelected = dlObj.getAnalyzers(); 
									analyzerSelected = aObj;
									tempAnalyzerSelected = analyzerSelected;

									analysisDayPojo = new AnalysisGraphicPojo( buildingSelected.getBuildingMeters());

									analysisDayPojo.fillGraphicForData( 
											analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate), scaleGraphic, analysisDate );

									firstTime = true;
								}
							}
						}
					}
				}
			}
		}
	}

	public void selectBuilding() {
		buildingSelected = tempBuildingSelected;
	}

	public void selectAnalyzer() {
		analyzerSelected = tempAnalyzerSelected;
	}

	public void updateScaleValues() {

		List<AnalyzerRegistryObject> registries = new ArrayList<AnalyzerRegistryObject>();

		switch(scaleGraphic) {
		case HOUR:
			registries = analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate);
			break;
		case DAY:
			registries = analyzerDataService.getDayRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate);
			break;
		default: 
			registries = analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate);
			break;
		}


		analysisDayPojo.fillGraphicForData( registries, scaleGraphic, analysisDate );
	}

	public void updateMeterSelection(BuildingMeterObject buildingMeterObj) {
		analysisDayPojo.changeSerie(buildingMeterObj.getLabelKey());
	}

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate( Date analysisDate ) {
		this.analysisDate = analysisDate;
	}

	public AnalysisGraphicPojo getAnalysisDayPojo() {
		return analysisDayPojo;
	}

	public void setAnalysisDayPojo( AnalysisGraphicPojo analysisDayPojo ) {
		this.analysisDayPojo = analysisDayPojo;
	}

	public AnalysisTypes getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType( AnalysisTypes analysisType ) {
		this.analysisType = analysisType;
	}

	public AnalysisTypes[] getAnalysisTypes() {
		return analysisTypes;
	}

	public void setAnalysisTypes( AnalysisTypes[] analysisTypes ) {
		this.analysisTypes = analysisTypes;
	}

	public ScaleGraphic[] getScalesGraphic() {
		return scalesGraphic;
	}

	public void setScalesGraphic( ScaleGraphic[] scalesGraphic ) {
		this.scalesGraphic = scalesGraphic;
	}

	public ScaleGraphic getScaleGraphic() {
		return scaleGraphic;
	}

	public void setScaleGraphic( ScaleGraphic scaleGraphic ) {
		this.scaleGraphic = scaleGraphic;
	}

	public BuildingObject getBuildingSelected() {
		return buildingSelected;
	}

	public void setBuildingSelected( BuildingObject buildingSelected ) {
		this.buildingSelected = buildingSelected;
	}

	public ClbHomeLoginBean getClbHomeLoginBean() {
		return clbHomeLoginBean;
	}

	public void setClbHomeLoginBean( ClbHomeLoginBean clbHomeLoginBean ) {
		this.clbHomeLoginBean = clbHomeLoginBean;
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
		this.analyzerDataService = analyzerDataService;
	}

	public AnalyzerObject getAnalyzerSelected() {
		return analyzerSelected;
	}

	public void setAnalyzerSelected( AnalyzerObject analyzerSelected ) {
		this.analyzerSelected = analyzerSelected;
	}

	public BuildingObject getTempBuildingSelected() {
		return tempBuildingSelected;
	}

	public void setTempBuildingSelected( BuildingObject tempBuildingSelected ) {
		this.tempBuildingSelected = tempBuildingSelected;
	}

	public AnalyzerObject getTempAnalyzerSelected() {
		return tempAnalyzerSelected;
	}

	public void setTempAnalyzerSelected( AnalyzerObject tempAnalyzerSelected ) {
		this.tempAnalyzerSelected = tempAnalyzerSelected;
	}

	public List<BuildingObject> getBuildingsToSelect() {
		return buildingsToSelect;
	}

	public void setBuildingsToSelect( List<BuildingObject> buildingsToSelect ) {
		this.buildingsToSelect = buildingsToSelect;
	}

	public List<AnalyzerObject> getAnalyzersSelected() {
		return analyzersSelected;
	}

	public void setAnalyzersSelected( List<AnalyzerObject> analyzersSelected ) {
		this.analyzersSelected = analyzersSelected;
	}

	public Hours getHour() {
		return hour;
	}

	public void setHour(Hours hour) {
		this.hour = hour;
	}

	public Hours[] getHoursValues() {
		return hoursValues;
	}

	public void setHoursValues(Hours[] hoursValues) {
		this.hoursValues = hoursValues;
	}

	public Months getMonth() {
		return month;
	}

	public void setMonth(Months month) {
		this.month = month;
	}

	public Months[] getMonthsValues() {
		return monthsValues;
	}

	public void setMonthsValues(Months[] monthsValues) {
		this.monthsValues = monthsValues;
	}
	
	
}

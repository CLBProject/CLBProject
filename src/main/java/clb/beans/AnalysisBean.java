package clb.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import clb.beans.enums.AnalysisTypes;
import clb.beans.enums.ScaleGraphic;
import clb.beans.pojos.AnalysisGraphicPojo;
import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.BuildingMeterObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;

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

    @PostConstruct
    public void init() {
        analysisDate = new Date();
        analysisTypes = AnalysisTypes.values();
        scalesGraphic = ScaleGraphic.values();
        scaleGraphic = ScaleGraphic.HOUR;

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

                                    analysisDayPojo = new AnalysisGraphicPojo( 
                                            analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate),buildingSelected.getBuildingMeters());

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
        switch(scaleGraphic) {
            case HOUR:
                analysisDayPojo.fillGraphicForYearData( analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate), scaleGraphic );
                break;
            case DAY:
                analysisDayPojo.fillGraphicForYearData( analyzerDataService.getDayRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate), scaleGraphic );
                break;
            default:
                analysisDayPojo.fillGraphicForYearData( analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate), scaleGraphic );
                break;
        }
    }

    public void updateMeterSelection(BuildingMeterObject buildingMeterObj) {
        System.out.println( buildingMeterObj );
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


}

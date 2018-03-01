package clb.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import clb.beans.enums.AnalysisTypes;
import clb.beans.enums.ScaleGraphic;
import clb.beans.pojos.AnalysisBarPojo;
import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerObject;
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
    private AnalysisBarPojo analysisBarDayPojo;

    private BuildingObject buildingSelected;
    private DataLoggerObject dataLoggerSelected;
    private AnalyzerObject analyzerSelected;

    private AnalysisTypes analysisType;
    private AnalysisTypes[] analysisTypes;

    private ScaleGraphic scaleGraphic;
    private ScaleGraphic[] scalesGraphic;

    @PostConstruct
    public void init() {
        analysisDate = new Date();
        analysisBarDayPojo = new AnalysisBarPojo();
        analysisTypes = AnalysisTypes.values();
        scalesGraphic = ScaleGraphic.values();
        
        //Set Initial Selected Building, DataLogger and Analyzer
        if(clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings() != null && 
                clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings().size() > 0 ) {
            
            buildingSelected = clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings().get( 0 );
            
            if(buildingSelected.getDataLoggers() != null && buildingSelected.getDataLoggers().size() > 0) {
                
                dataLoggerSelected = buildingSelected.getDataLoggers().get( 0 );
                
                if(dataLoggerSelected.getAnalyzers() != null && dataLoggerSelected.getAnalyzers().size() > 0) {
                    analyzerSelected = dataLoggerSelected.getAnalyzers().get( 0 );
                }
            }
        }
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate( Date analysisDate ) {
        this.analysisDate = analysisDate;
    }

    public AnalysisBarPojo getAnalysisBarDayPojo() {
        return analysisBarDayPojo;
    }

    public void setAnalysisBarDayPojo( AnalysisBarPojo analysisBarDayPojo ) {
        this.analysisBarDayPojo = analysisBarDayPojo;
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

    public DataLoggerObject getDataLoggerSelected() {
        return dataLoggerSelected;
    }

    public void setDataLoggerSelected( DataLoggerObject dataLoggerSelected ) {
        this.dataLoggerSelected = dataLoggerSelected;
    }

    public AnalyzerObject getAnalyzerSelected() {
        return analyzerSelected;
    }

    public void setAnalyzerSelected( AnalyzerObject analyzerSelected ) {
        this.analyzerSelected = analyzerSelected;
    }

    
}

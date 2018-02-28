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
import clb.business.objects.BuildingObject;
import clb.business.objects.UsersystemObject;

@ViewScoped
@ManagedBean
public class AnalysisBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{clbHomeLoginBean}")
    private ClbHomeLoginBean clbHomeLoginBean;
    
    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;
    
    private UsersystemObject userObjectSelected;
    
    private Date analysisDate;
    private AnalysisBarPojo analysisBarDayPojo;
    
    private BuildingObject buildingSelected;
    
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
        
        userObjectSelected = analyzerDataService.getUserData( clbHomeLoginBean.getUserName() );
        
        buildingSelected = userObjectSelected.getBuildings().size() > 0 ? userObjectSelected.getBuildings().get( 0 ) : null;
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

    public UsersystemObject getUserObjectSelected() {
        return userObjectSelected;
    }

    public void setUserObjectSelected( UsersystemObject userObjectSelected ) {
        this.userObjectSelected = userObjectSelected;
    }

    public AnalyzerDataService getAnalyzerDataService() {
        return analyzerDataService;
    }

    public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
        this.analyzerDataService = analyzerDataService;
    }
    
    
}

package clb.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import clb.beans.enums.AnalysisTypes;
import clb.beans.enums.ScaleGraphic;
import clb.beans.pojos.AnalysisBarPojo;

@ViewScoped
@ManagedBean
public class AnalysisBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private Date analysisDate;
    private AnalysisBarPojo analysisBarDayPojo;
    
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
    
    
}

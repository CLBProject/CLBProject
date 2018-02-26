package clb.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class AnalysisBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private Date analysisDate;

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate( Date analysisDate ) {
        this.analysisDate = analysisDate;
    }
    
    
}

package clb.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.MapModel;

import clb.business.AnalyzerDataService;

@ViewScoped
@ManagedBean
public class ClbUserBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private MapModel mapModel;
       
    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;
    
    @ManagedProperty("#{clbHomeLoginBean}")
    private ClbHomeLoginBean clbHomeLoginBean;

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel( MapModel mapModel ) {
        this.mapModel = mapModel;
    }

    public void runUserDataFill() {
        if(clbHomeLoginBean.getUserLoginPojo().getUsername() != null) {
            try {
                analyzerDataService.persistDataForUser( clbHomeLoginBean.getUserLoginPojo().getUsername() );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public AnalyzerDataService getAnalyzerDataService() {
        return analyzerDataService;
    }

    public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
        this.analyzerDataService = analyzerDataService;
    }

    public ClbHomeLoginBean getClbHomeLoginBean() {
        return clbHomeLoginBean;
    }

    public void setClbHomeLoginBean( ClbHomeLoginBean clbHomeLoginBean ) {
        this.clbHomeLoginBean = clbHomeLoginBean;
    }
    
    
}

package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

import clb.business.AnalyzerDataService;

@ViewScoped
@ManagedBean
public class ClbUserBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private MapModel mapModel;

    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;

    @PostConstruct
    public void init() {
        mapModel = new DefaultMapModel();
    }   

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel( MapModel mapModel ) {
        this.mapModel = mapModel;
    }

    public AnalyzerDataService getAnalyzerDataService() {
        return analyzerDataService;
    }

    public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
        this.analyzerDataService = analyzerDataService;
    }

}

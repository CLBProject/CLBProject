package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

import clb.beans.pojos.UsersystemPojo;
import clb.business.AnalyzerDataService;

@ViewScoped
@ManagedBean
public class ClbHomeBean implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private MapModel mapModel;
    
    private UsersystemPojo user;
    
	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;

    @PostConstruct
    public void init() {
    	user = new UsersystemPojo();
        mapModel = new DefaultMapModel();
    }
    
    public void registerUser() {
    	analyzerDataService.persistObject(user.toObject());
    }
    
    public String loginUser() {
        return "clb.xhtml";
    }
    
    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel( MapModel mapModel ) {
        this.mapModel = mapModel;
    }

    public UsersystemPojo getUser() {
        return user;
    }

    public void setUser( UsersystemPojo user ) {
        this.user = user;
    }

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}
    
    
}

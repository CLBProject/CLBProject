package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import clb.beans.pojos.UsersystemPojo;
import clb.business.AnalyzerDataService;

@ViewScoped
@ManagedBean
public class ClbHomeRegisterBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private UsersystemPojo user;

    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;

    @PostConstruct
    public void init() {
        user = new UsersystemPojo();
    }   

    public void registerUser() {
        System.out.println( user.getName() );
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

    public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
        this.analyzerDataService = analyzerDataService;
    }

}

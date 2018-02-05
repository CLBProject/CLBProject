package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import clb.business.AnalyzerDataService;
import clb.business.objects.UsersystemObject;

@SessionScoped
@ManagedBean
public class ClbHomeLoginBean implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String userName;
    private String password;

    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;
    
    private final static String CANT_LOGIN_PARAM = "cantLogin";

    @PostConstruct
    public void init() {
    }
    
    public String loginUser() {

        UsersystemObject userLoggedIn = analyzerDataService.userCanLogin( userName, password );
        RequestContext context = RequestContext.getCurrentInstance();  
        
        if (userLoggedIn == null) {
        //if (userLoggedIn != null) {
            // get Http Session and store username
            HttpSession session = (HttpSession)
                    FacesContext.
                    getCurrentInstance().
                    getExternalContext().
                    getSession(false);
            
            session.setAttribute("username",userName);
            context.addCallbackParam(CANT_LOGIN_PARAM, false);

            return "clb";
        } else {
           
            context.addCallbackParam(CANT_LOGIN_PARAM, true);
            return "index";
        }
    }
    
    public String logout() {
        HttpSession session = (HttpSession)
                FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
        session.invalidate();
        return "index";
     }

    public AnalyzerDataService getAnalyzerDataService() {
        return analyzerDataService;
    }

    public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
        this.analyzerDataService = analyzerDataService;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    
}

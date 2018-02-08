package clb.beans;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import clb.beans.pojos.UsersystemPojo;
import clb.business.AnalyzerDataService;
import clb.business.exceptions.UserExistsException;
import clb.business.exceptions.UserNotPersistedException;
import clb.business.objects.UsersystemObject;

@ViewScoped
@ManagedBean
public class ClbHomeRegisterBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private UsersystemPojo user;

    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;

    private String tokenRegistred;

    private final static String USER_EXISTS_PARAM = "userExists";
    private final static String UNEXPECTED_ERROR_PARAM = "unexpectedError";

    @PostConstruct
    public void init() {
        user = new UsersystemPojo();
    }

    public void registerUserAccount() {

        try {

            UsersystemObject userObj = user.toObject();

            analyzerDataService.registerUser(userObj);

            analyzerDataService.publishEvent(userObj,FacesContext.getCurrentInstance().getExternalContext().getRequestLocale(),
                    "http://localhost:8080" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                    "/pages/registerComplete.xhtml?token=");

        }catch(UserExistsException uee) {
            RequestContext.getCurrentInstance().addCallbackParam( USER_EXISTS_PARAM, true );
        }catch(UserNotPersistedException unpe) {
            RequestContext.getCurrentInstance().addCallbackParam( UNEXPECTED_ERROR_PARAM, true );
        }

    }

    public String registerUser() {
        
        //Token is null
        if(this.tokenRegistred == null) {
            return "badUser";
        }
        
        UsersystemObject userWithToken = analyzerDataService.getUsersystemByToken(this.tokenRegistred);
        if (userWithToken.getToken() == null) {
            //String message = messages.getMessage("auth.message.invalidToken", null, locale);
            //model.addAttribute("message", message);
            return "badUser";
        }

        Calendar cal = Calendar.getInstance();
        if ((userWithToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            //String messageValue = messages.getMessage("auth.message.expired", null, locale)
            //model.addAttribute("message", messageValue);
            return "badUser";
        } 

        user.setEnabled(true); 


        return "clb";
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

    public String getTokenRegistred() {
        return tokenRegistred;
    }

    public void setTokenRegistred( String tokenRegistred ) {
        this.tokenRegistred = tokenRegistred;
    }

}

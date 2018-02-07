package clb.beans;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import clb.beans.pojos.UsersystemPojo;
import clb.business.AnalyzerDataService;
import clb.business.objects.UsersystemObject;

@ViewScoped
@ManagedBean
public class ClbHomeRegisterBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private UsersystemPojo user;

    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;
    
    private String tokenRegistred;

    @PostConstruct
    public void init() {
        user = new UsersystemPojo();
    }
    
    public void registerUserAccount() {
        analyzerDataService.saveObject(user.toObject());
        
        if(user.toObject().getUserid() == null) {
            //Error
        }
        
        analyzerDataService.publishEvent(user.toObject(),FacesContext.getCurrentInstance().getExternalContext().getRequestLocale(),
                FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
    }

    public String registerUser() {
        
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

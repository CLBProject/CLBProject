package clb.beans.management;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import clb.beans.pojos.UserLoginPojo;
import clb.business.UserRegistryService;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;

@SessionScoped
@ManagedBean
public class ClbHomeLoginBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{userRegistryService}")
    private UserRegistryService userRegistryService;
    
    private UserLoginPojo userLoginPojo;

    private final static String CANT_LOGIN_USER_NOT_FOUND_PARAM = "cantLoginUserNotFound";
    private final static String CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM = "cantLoginPasswordDoesntMatch";

    @PostConstruct
    public void init() {
        userLoginPojo = new UserLoginPojo();
    }

    public String loginUser() {

        RequestContext context = RequestContext.getCurrentInstance();  

        try {
            userLoginPojo.setCurrentUser( userRegistryService.validateUserLogin( userLoginPojo.getUsername(), userLoginPojo.getPassword() ));
            return "clb";
        } catch( UserDoesNotExistException e ) {
           
            userLoginPojo.setUsername( null );
            userLoginPojo.setPassword( null );
            userLoginPojo.setCurrentUser( null );
           
            context.addCallbackParam(CANT_LOGIN_USER_NOT_FOUND_PARAM, true);
            return "index";
        } catch( UserDoesNotMatchPasswordLoginException e ) {
            
            userLoginPojo.setUsername( null );
            userLoginPojo.setPassword( null );
            userLoginPojo.setCurrentUser( null );
            
            context.addCallbackParam(CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM, true);
            return "index";
        }
    }

    public String logout() {
        
        userLoginPojo.setUsername( null );
        userLoginPojo.setPassword( null );
        userLoginPojo.setCurrentUser( null );
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    public UserRegistryService getUserRegistryService() {
        return userRegistryService;
    }

    public void setUserRegistryService( UserRegistryService userRegistryService ) {
        this.userRegistryService = userRegistryService;
    }

    public UserLoginPojo getUserLoginPojo() {
        return userLoginPojo;
    }

    public void setUserLoginPojo( UserLoginPojo userLoginPojo ) {
        this.userLoginPojo = userLoginPojo;
    }
    
    
}

package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.context.RequestContext;

import com.sun.istack.NotNull;

import clb.business.UserRegistryService;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;

@SessionScoped
@ManagedBean
public class ClbHomeLoginBean implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @NotNull
    @NotEmpty(message="Username can't be empty")
    private String userName;
    
    @NotNull
    @NotEmpty(message="Password can't be empty")
    private String password;

    @ManagedProperty("#{userRegistryService}")
    private UserRegistryService userRegistryService;

    private final static String CANT_LOGIN_USER_NOT_FOUND_PARAM = "cantLoginUserNotFound";
    private final static String CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM = "cantLoginPasswordDoesntMatch";

    @PostConstruct
    public void init() {
    }

    public String loginUser() {

        RequestContext context = RequestContext.getCurrentInstance();  

        try {
            userRegistryService.validateUserLogin( userName, password );
            return "clb";
        } catch( UserDoesNotExistException e ) {
           
            userName = null;
            password = null;
           
            context.addCallbackParam(CANT_LOGIN_USER_NOT_FOUND_PARAM, true);
            return "index";
        } catch( UserDoesNotMatchPasswordLoginException e ) {
            
            userName = null;
            password = null;
            
            context.addCallbackParam(CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM, true);
            return "index";
        }
    }

    public String logout() {
        
        this.userName = null;
        this.password = null;
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    public UserRegistryService getUserRegistryService() {
        return userRegistryService;
    }

    public void setUserRegistryService( UserRegistryService userRegistryService ) {
        this.userRegistryService = userRegistryService;
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

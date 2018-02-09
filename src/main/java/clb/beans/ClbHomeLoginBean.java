package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import clb.business.UserRegistryService;
import clb.business.exceptions.UserDoesNotExistOnLoginException;

@SessionScoped
@ManagedBean
public class ClbHomeLoginBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;

    @ManagedProperty("#{UserRegistryService}")
    private UserRegistryService userRegistryService;

    private final static String CANT_LOGIN_PARAM = "cantLogin";

    @PostConstruct
    public void init() {
    }

    public String loginUser() {

        RequestContext context = RequestContext.getCurrentInstance();  

        try {
            userRegistryService.validateUserLogin( userName );
            context.addCallbackParam(CANT_LOGIN_PARAM, true);
            return "clb";
        } catch( UserDoesNotExistOnLoginException e ) {

            HttpSession session = (HttpSession)
                    FacesContext.
                    getCurrentInstance().
                    getExternalContext().
                    getSession(false);

            session.setAttribute("username",userName);
            context.addCallbackParam(CANT_LOGIN_PARAM, false);
            return "index";
        }
    }

    public String logout() {
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

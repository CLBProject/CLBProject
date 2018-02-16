package clb.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import clb.beans.pojos.UsersystemPojo;
import clb.business.UserRegistryService;
import clb.business.exceptions.UserDoesNotExistOnLoginException;
import clb.business.exceptions.UserExistsOnRegistryException;
import clb.business.exceptions.UserNotFoundByTokenOnCompleteRegistration;
import clb.business.exceptions.UserNotPersistedException;
import clb.business.exceptions.UserTokenHasExpiredOnCompleteRegistration;
import clb.business.exceptions.UserTokenIsNullOnCompleteRegistrationException;
import clb.business.objects.UsersystemObject;

@ViewScoped
@ManagedBean
public class ClbHomeRegisterBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private UsersystemPojo user;

    @ManagedProperty("#{userRegistryService}")
    private UserRegistryService userRegistryService;
    
    @ManagedProperty("#{clbHomeLoginBean}")
    private ClbHomeLoginBean clbHomeLoginBean;
    
    private String registerResult;

    private final static int SESSION_TIME_MINUTES = 15;
    private final static String USER_EXISTS_PARAM = "userExists";
    private final static String UNEXPECTED_ERROR_PARAM = "unexpectedError";
    private final static String CANT_RECOVER_PASSWORD_NOT_FOUND_PARAM = "cantRecoverPasswordNotFound";

    @PostConstruct
    public void init() {
        
        //If user is logged in redirect
        if(clbHomeLoginBean != null && clbHomeLoginBean.getUserName() != null && !clbHomeLoginBean.getUserName().equals( "" )) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect( "clb.xhtml" );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        }
        
        user = new UsersystemPojo();
    }

    public void registerUserAccount() {

        try {

            userRegistryService.registerUser(user.toObject(), SESSION_TIME_MINUTES);

        }catch(UserExistsOnRegistryException uee) {
            RequestContext.getCurrentInstance().addCallbackParam( USER_EXISTS_PARAM, true );
        }catch(UserNotPersistedException unpe) {
            RequestContext.getCurrentInstance().addCallbackParam( UNEXPECTED_ERROR_PARAM, true );
        }

    }

    public void registerUser() {
        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get( "token" );

        try {
            UsersystemObject userRegistered = userRegistryService.completeUserRegistration( token );
            
            clbHomeLoginBean.setUserName( userRegistered.getUsername() );
            
        } catch( UserTokenIsNullOnCompleteRegistrationException e ) {
            // return badUser
        } catch( UserNotFoundByTokenOnCompleteRegistration e ) {
         // return badUser
        } catch( UserTokenHasExpiredOnCompleteRegistration e ) {
         // return badUser
        }
    }
    
    public void recoverPassword() {
        try {
            userRegistryService.makeNewUserRegistration(user.getUsername());
        } catch( UserDoesNotExistOnLoginException e ) {
            RequestContext.getCurrentInstance().addCallbackParam( CANT_RECOVER_PASSWORD_NOT_FOUND_PARAM, true );
        }
    }
    
    public UsersystemPojo getUser() {
        return user;
    }

    public void setUser( UsersystemPojo user ) {
        this.user = user;
    }

    public UserRegistryService getUserRegistryService() {
        return userRegistryService;
    }

    public void setUserRegistryService( UserRegistryService userRegistryService ) {
        this.userRegistryService = userRegistryService;
    }

    public String getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult( String registerResult ) {
        this.registerResult = registerResult;
    }

    public ClbHomeLoginBean getClbHomeLoginBean() {
        return clbHomeLoginBean;
    }

    public void setClbHomeLoginBean( ClbHomeLoginBean clbHomeLoginBean ) {
        this.clbHomeLoginBean = clbHomeLoginBean;
    }

    
}

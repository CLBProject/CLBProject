package clb.beans.management;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import clb.beans.pojos.UserRegisterPojo;
import clb.business.UserRegistryService;
import clb.business.objects.UsersystemObject;
import clb.global.exceptions.UserCantResendEmailException;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserExistsOnRegistryException;
import clb.global.exceptions.UserNotPersistedException;
import clb.global.exceptions.UserTokenHasExpiredOnCompleteRegistration;
import clb.global.exceptions.UserTokenIsNullOnCompleteRegistrationException;

@ViewScoped
@ManagedBean
public class ClbHomeRegisterBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private UserRegisterPojo user;

    @ManagedProperty("#{userRegistryService}")
    private UserRegistryService userRegistryService;
    
    @ManagedProperty("#{clbHomeLoginBean}")
    private ClbHomeLoginBean clbHomeLoginBean;
    
    private String registerResult;

    private final static int SESSION_TIME_MINUTES = 15;
    private final static int MINUTES_NECESSARY_TO_RESEND_EMAIL = 15;
    private final static String USER_EXISTS_PARAM = "userExists";
    private final static String UNEXPECTED_ERROR_PARAM = "unexpectedError";
    private final static String CANT_RECOVER_PASSWORD_NOT_FOUND_PARAM = "cantRecoverPasswordNotFound";
    
    private final static String USER_NOT_FOUND_ON_RESEND_EMAIL = "userNotFoundResendEmail";
    private final static String TIME_NOT_PASSED_SINCE_LAST_EMAIL = "timeNotPassedLastEmail";
    
    private final static String USER_TOKEN_NOT_FOUND_RESULT = "Token wasnt found for User Registry!";
    private final static String USER_TOKEN_HAS_EXPIRED = "Token has expired for this User!";
    

    @PostConstruct
    public void init() {
        
        //If user is logged in redirect
        if(clbHomeLoginBean != null && clbHomeLoginBean.getUserLoginPojo().getUsername() != null && 
                !clbHomeLoginBean.getUserLoginPojo().getUsername().equals( "" )) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect( "clb.xhtml" );
            } catch( IOException e ) {
                e.printStackTrace();
            }
        }
        
        user = new UserRegisterPojo();
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
    
    public void resendEmail() {
        try {
            userRegistryService.resendEmail(user.getUsername(), MINUTES_NECESSARY_TO_RESEND_EMAIL);
        } catch( UserDoesNotExistException e ) {
            RequestContext.getCurrentInstance().addCallbackParam( USER_NOT_FOUND_ON_RESEND_EMAIL, true );
        } catch( UserCantResendEmailException e ) {
            RequestContext.getCurrentInstance().addCallbackParam( TIME_NOT_PASSED_SINCE_LAST_EMAIL, true );
        }
    }

    public void registerUser() {
        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get( "token" );

        try {
            
            registerResult = null;
            
            UsersystemObject userRegistered = userRegistryService.completeUserRegistration( token);
            
            clbHomeLoginBean.getUserLoginPojo().setUsername( userRegistered.getUsername() );       
            
        } catch( UserTokenIsNullOnCompleteRegistrationException e ) {
            registerResult = USER_TOKEN_NOT_FOUND_RESULT;
        } catch( UserTokenHasExpiredOnCompleteRegistration e ) {
            registerResult = USER_TOKEN_HAS_EXPIRED;
        }
    }
    
    public void recoverPassword() {
        try {
            userRegistryService.makeNewUserRegistration(user.getUsername(), MINUTES_NECESSARY_TO_RESEND_EMAIL);
        } catch( UserDoesNotExistException e ) {
            RequestContext.getCurrentInstance().addCallbackParam( CANT_RECOVER_PASSWORD_NOT_FOUND_PARAM, true );
        } catch( UserCantResendEmailException e ) {
            RequestContext.getCurrentInstance().addCallbackParam( TIME_NOT_PASSED_SINCE_LAST_EMAIL, true );
        }
    }
    
    public UserRegisterPojo getUser() {
        return user;
    }

    public void setUser( UserRegisterPojo user ) {
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

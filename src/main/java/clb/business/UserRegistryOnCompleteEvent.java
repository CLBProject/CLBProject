package clb.business;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import clb.business.objects.UsersystemObject;

public class UserRegistryOnCompleteEvent extends ApplicationEvent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String appUrl;
    private UsersystemObject user;
    private Locale locale;
 
    public UserRegistryOnCompleteEvent(
            UsersystemObject user, Locale locale, String appUrl) {
        super(user);
         
        this.user = user;
        this.appUrl = appUrl;
        this.locale = locale;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl( String appUrl ) {
        this.appUrl = appUrl;
    }

    public UsersystemObject getUser() {
        return user;
    }

    public void setUser( UsersystemObject user ) {
        this.user = user;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale( Locale locale ) {
        this.locale = locale;
    }
    
    
}

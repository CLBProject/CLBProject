package clb.business.objects;

import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String textMsg;
    private String subject;
    private String userName;
 
    public UserEvent(
            String userName, String subject, String textMsg) {
        super(userName);
         
        this.subject = subject;
        this.userName = userName;
        this.textMsg = textMsg;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg( String textMsg ) {
        this.textMsg = textMsg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject( String subject ) {
        this.subject = subject;
    }
    
    
}

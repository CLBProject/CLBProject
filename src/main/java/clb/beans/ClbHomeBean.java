package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

import clb.beans.pojos.UsersystemPojo;

@ViewScoped
@ManagedBean
public class ClbHomeBean implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private MapModel mapModel;
    
    private UsersystemPojo user;

    @PostConstruct
    public void init() {
        mapModel = new DefaultMapModel();
    }
    
    public void registerUser() {
        System.out.println( user.getName() );
    }
    
    public String loginUser() {
        return "clb.xhtml";
    }
    
    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel( MapModel mapModel ) {
        this.mapModel = mapModel;
    }

    public UsersystemPojo getUser() {
        return user;
    }

    public void setUser( UsersystemPojo user ) {
        this.user = user;
    }
    
    
}

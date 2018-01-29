package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

@ViewScoped
@ManagedBean
public class ClbHomeBean implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private MapModel mapModel;

    private String title;

    private double lat;

    private double lng;
    
    private String userNameReg;

    @PostConstruct
    public void init() {
        mapModel = new DefaultMapModel();
    }
    
    public void registerUser() {
        
    }
    
    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel( MapModel mapModel ) {
        this.mapModel = mapModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserNameReg() {
        return userNameReg;
    }

    public void setUserNameReg( String userNameReg ) {
        this.userNameReg = userNameReg;
    }
    
    
}

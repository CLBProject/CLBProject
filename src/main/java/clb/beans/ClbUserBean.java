package clb.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.MapModel;

@ViewScoped
@ManagedBean
public class ClbUserBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private MapModel mapModel;

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel( MapModel mapModel ) {
        this.mapModel = mapModel;
    }

}

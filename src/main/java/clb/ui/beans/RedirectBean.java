package clb.ui.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean
public class RedirectBean implements Serializable{

  
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String redirectTo(String path) {
       return path;
    }
    
}

package clb.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class SimpleBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String testSuccess;
	
	public void action(){
	    testSuccess = "Passed Action!";
	}

    public String getTestSuccess() {
        return testSuccess;
    }


    public void setTestSuccess( String testSuccess ) {
        this.testSuccess = testSuccess;
    }
}

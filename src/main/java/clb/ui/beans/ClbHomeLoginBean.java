package clb.ui.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import clb.business.UserRegistryService;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.global.exceptions.UserIsNotEnabledYet;
import clb.ui.beans.objects.BuildingGui;
import clb.ui.beans.objects.UserSystemGui;

@SessionScoped
@ManagedBean
public class ClbHomeLoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{userRegistryService}")
	private UserRegistryService userRegistryService;

	private String loginUsername;
	private String loginPassword;
	
	private UserSystemGui userUiPojo;

	private final static String CANT_LOGIN_USER_NOT_FOUND_PARAM = "cantLoginUserNotFound";
	private final static String CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM = "cantLoginPasswordDoesntMatch";
	private final static String USER_NOT_ENABLED_YET = "userNotEnabledYet";

	@PostConstruct
	public void init() {
		userUiPojo = new UserSystemGui();
	}

	public String loginUser() {

		RequestContext context = RequestContext.getCurrentInstance();

		try {

			userUiPojo = new UserSystemGui(userRegistryService.validateUserLogin(loginUsername, loginPassword));

			return "clb";
		} catch (UserDoesNotExistException e) {

			userUiPojo.setUsername(null);
			userUiPojo.setPassword(null);
			userUiPojo.clear();

			context.addCallbackParam(CANT_LOGIN_USER_NOT_FOUND_PARAM, true);
			return "index";
		} catch (UserDoesNotMatchPasswordLoginException e) {

			userUiPojo.setUsername(null);
			userUiPojo.setPassword(null);
			userUiPojo.clear();

			context.addCallbackParam(CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM, true);
			return "index";
		} catch (UserIsNotEnabledYet e) {

			userUiPojo.setUsername(null);
			userUiPojo.setPassword(null);
			userUiPojo.clear();

			context.addCallbackParam(USER_NOT_ENABLED_YET, true);
			return "index";
		}
	}

	public String logout() {

		userUiPojo.setUsername(null);
		userUiPojo.setPassword(null);
		userUiPojo.clear();

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index.xhtml?faces-redirect=true";
	}

	public boolean hasUserLoggedIn() {
		return userUiPojo != null && userUiPojo.getUsername() != null && !userUiPojo.getUsername().equals( "" );
	}

	public boolean userHasBuildings() {
		return userUiPojo != null && userUiPojo.getBuildings() != null && userUiPojo.getBuildings().size() > 0;
	}

	
	public UserRegistryService getUserRegistryService() {
		return userRegistryService;
	}

	public void setUserRegistryService(UserRegistryService userRegistryService) {
		this.userRegistryService = userRegistryService;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public List<BuildingGui> getUserBuildings() {
		
		if(userUiPojo.hasBuildings()) {
			return userUiPojo.getBuildings();
		}
		
		return new ArrayList<BuildingGui>();
	}
	
	
}

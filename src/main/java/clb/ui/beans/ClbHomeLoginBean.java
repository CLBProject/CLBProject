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

import clb.business.AnalyzerDataService;
import clb.business.UserRegistryService;
import clb.business.objects.BuildingObject;
import clb.global.exceptions.UserDoesNotExistException;
import clb.global.exceptions.UserDoesNotMatchPasswordLoginException;
import clb.global.exceptions.UserIsNotEnabledYet;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.objects.UsersystemGui;

@SessionScoped
@ManagedBean
public class ClbHomeLoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{userRegistryService}")
	private UserRegistryService userRegistryService;
	
    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;

	private String loginUsername;
	private String loginPassword;
	
	private UsersystemGui authenticatedUser;

	private final static String CANT_LOGIN_USER_NOT_FOUND_PARAM = "cantLoginUserNotFound";
	private final static String CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM = "cantLoginPasswordDoesntMatch";
	private final static String USER_NOT_ENABLED_YET = "userNotEnabledYet";

	@PostConstruct
	public void init() {
		authenticatedUser = new UsersystemGui();
	}

	public String loginUser() {

		RequestContext context = RequestContext.getCurrentInstance();

		try {

			authenticatedUser = new UsersystemGui(userRegistryService.validateUserLogin(loginUsername, loginPassword));

			return "analysis";
		} catch (UserDoesNotExistException e) {

			authenticatedUser.setUsername(null);
			authenticatedUser.setPassword(null);
			authenticatedUser.clear();

			context.addCallbackParam(CANT_LOGIN_USER_NOT_FOUND_PARAM, true);
			return "index";
		} catch (UserDoesNotMatchPasswordLoginException e) {

			authenticatedUser.setUsername(null);
			authenticatedUser.setPassword(null);
			authenticatedUser.clear();

			context.addCallbackParam(CANT_LOGIN_PASSWORD_DOESNT_MATCH_PARAM, true);
			return "index";
		} catch (UserIsNotEnabledYet e) {

			authenticatedUser.setUsername(null);
			authenticatedUser.setPassword(null);
			authenticatedUser.clear();

			context.addCallbackParam(USER_NOT_ENABLED_YET, true);
			return "index";
		}
	}

	public String logout() {

		authenticatedUser.setUsername(null);
		authenticatedUser.setPassword(null);
		authenticatedUser.clear();

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index.xhtml?faces-redirect=true";
	}

	public boolean hasUserLoggedIn() {
		return authenticatedUser != null && authenticatedUser.getUsername() != null && !authenticatedUser.getUsername().equals( "" );
	}

	public boolean userHasBuildings() {
		return authenticatedUser != null && authenticatedUser.getBuildings() != null && authenticatedUser.getBuildings().size() > 0;
	}

	public void addBuildingToUser(BuildingObject building) {
		analyzerDataService.saveBuildingForUser(authenticatedUser.toObject(), building);
		authenticatedUser.addBuilding(new BuildingAnalysisGui(building));
	}

	public void deleteBuildingFromUser(BuildingObject building) {
		analyzerDataService.deleteBuildingForUser(authenticatedUser.toObject(), building);
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

	public List<BuildingAnalysisGui> getUserBuildings() {
		
		if(authenticatedUser.hasBuildings()) {
			return authenticatedUser.getBuildings();
		}
		
		return new ArrayList<BuildingAnalysisGui>();
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}

	public UsersystemGui getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(UsersystemGui authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}



	
}

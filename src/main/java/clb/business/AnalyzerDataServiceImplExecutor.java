package clb.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import clb.business.exceptions.IlegalCommandAppException;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
import clb.business.utils.JsonUtils;
import clb.database.ClbDao;

public class AnalyzerDataServiceImplExecutor implements Runnable{

	private ClbDao clbDao;

	public AnalyzerDataServiceImplExecutor(ClbDao clbDao) {
		this.clbDao = clbDao;
	}

	@Override
	public void run() {

		try(ServerSocket s = new ServerSocket(6006)){
			while(true) {
				Socket clientSocket = s.accept();
				try(Scanner in = new Scanner(clientSocket.getInputStream())){
					
					boolean exit = false;
					
					Map<String,AnalyzerObject> analyzerNames = new HashMap<String,AnalyzerObject>();
					Map<String,DataLoggerObject> dataLoggerNames = new HashMap<String,DataLoggerObject>();
					Map<String,BuildingObject> buildingsNames = new HashMap<String,BuildingObject>();
					 
					while(clientSocket.isConnected() && !exit) {
						String command = in.nextLine();

						//Send Users
						if(command.equals("*getUsersInfo*")) {
							List<JSONObject> jsonToSend = clbDao.getAllUsers().stream()
									.map(UsersystemObject::toJson)
									.collect(Collectors.toList());

							for(JSONObject json: jsonToSend) {
								clientSocket.getOutputStream().write(json.toString().getBytes());
								clientSocket.getOutputStream().flush();
							}	
							
							clientSocket.getOutputStream().write("*end*".getBytes());
						}
						//Persist Data Objects
						else if(command.equals("*persistDataObject*")){
							JSONObject jsonObj = new JSONObject(in.nextLine());
							
							String buildingName = jsonObj.getString("buildingName");
							
							BuildingObject buildingObject = buildingsNames.get(buildingName);
							DataLoggerObject dataLoggerObject = dataLoggerNames.get(buildingName);
							
							if(buildingObject == null) {
								
								dataLoggerObject = new DataLoggerObject();
								dataLoggerObject.setCode(buildingName);
								
								buildingObject = new BuildingObject();
								buildingObject.setName(buildingName);
								buildingObject.addDataLogger(dataLoggerObject);
								
								buildingsNames.put(buildingName,buildingObject);
								dataLoggerNames.put(buildingName,dataLoggerObject);
							}
							
							
							String analyzerCode = jsonObj.getString("itemSn");
							
							AnalyzerObject analyzerObject = analyzerNames.get(analyzerCode);
							
							if(analyzerObject == null) {
								analyzerObject = new AnalyzerObject();
								analyzerObject.setCodeName(analyzerCode);
								analyzerNames.put(analyzerCode,analyzerObject);
								clbDao.saveAnalyzer(analyzerObject);
								dataLoggerObject.addAnalyzer(analyzerObject);
							}
							
							jsonObj.put("analyzerId", analyzerObject.getId());

							AnalyzerRegistryObject analyzerRegistry = JsonUtils.getInstance().toAnalyzerRegistryObject(jsonObj);
							clbDao.saveAnalyzerRegistry( analyzerRegistry);
							analyzerObject.addAnalyzerRegistry(analyzerRegistry.getAnalyzerId());
						}
						else if(command.equals("*exit*")){
							
							analyzerNames.values().stream().forEach(analyzer -> clbDao.saveAnalyzer(analyzer));
							dataLoggerNames.values().stream().forEach(dataLogger -> clbDao.saveDataLogger(dataLogger));
							buildingsNames.values().stream().forEach(building -> {
								clbDao.saveBuilding(building);
								clbDao.getAllUsers().stream().forEach(user -> {
									user.addBuilding(building);
									clbDao.saveUsersystem(user);
								});
							});
							
							analyzerNames = null;
							dataLoggerNames = null;
							buildingsNames = null;
							
							exit = true;
						}
						else throw new IlegalCommandAppException();
					}
				}
				catch(JSONException | IlegalCommandAppException | NoSuchElementException ex){
					ex.printStackTrace();
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}

package clb.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clb.business.exceptions.IlegalCommandAppException;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
import clb.business.utils.JsonUtils;
import clb.database.ClbDao;
import clb.global.BuildingMeterParameterValues;

public class AnalyzerDataServiceImplExecutor implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(AnalyzerDataServiceImplExecutor.class);
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
					
					Map<String,BuildingObject> buildingsNames = new HashMap<String,BuildingObject>();
					AnalyzerObject analyzerObject = null;
					
					boolean exit = false;
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

							clientSocket.getOutputStream().write("*endUsersInfo*".getBytes());
							clientSocket.getOutputStream().flush();
						}
						//Persist Data Objects
						else if(command.equals("*persistDataObject*")){
							JSONObject jsonObj = new JSONObject(in.nextLine());

							String buildingName = jsonObj.getString("buildingName");

							BuildingObject buildingObject = buildingsNames.get(buildingName);
							
							if(buildingObject == null) {
								
								analyzerObject = new AnalyzerObject();
								analyzerObject.setCodeName("Analyzer For Building " + buildingName);
								clbDao.saveAnalyzer(analyzerObject);
								
								buildingObject = saveBuildingStructure(buildingName,analyzerObject);
								buildingsNames.put(buildingName,buildingObject);
							}
							
							jsonObj.put("analyzerId", analyzerObject.getId());

							AnalyzerRegistryObject analyzerRegistry = JsonUtils.getInstance().toAnalyzerRegistryObject(jsonObj);
							clbDao.saveAnalyzerRegistry( analyzerRegistry);
							analyzerObject.addAnalyzerRegistry(analyzerRegistry.getAnalyzerId());
							
							clientSocket.getOutputStream().write("acknowledge*".getBytes());
							clientSocket.getOutputStream().flush();
						}
						else if(command.equals("*getLatestPersistedDate*")) {
							String analyzerCodeName = in.nextLine();
							Long latestDateForAnalyzer = clbDao.getLatestDateForAnalyzer(analyzerCodeName);

							clientSocket.getOutputStream().write(latestDateForAnalyzer.toString() != null ? 
																(latestDateForAnalyzer.toString()+"\n").getBytes() : "\n".getBytes());
							clientSocket.getOutputStream().write("*end*".getBytes());
							clientSocket.getOutputStream().flush();
						}
						else if(command.equals("*exitPersistDataObject*")){

							clbDao.saveAnalyzer(analyzerObject);

							analyzerObject = null;
							buildingsNames = null;

							exit = true;
						}
						else throw new IlegalCommandAppException();
					}
				}
				catch(JSONException | IlegalCommandAppException | NoSuchElementException ex){
					ex.printStackTrace();
					logger.error("Error on Processing Script Commands" , ex.getMessage());
				}
			}
		} 
		catch (IOException e) {
			logger.error("Error on Accepting Connections from Script" , e.getMessage());
		}

	}

	private BuildingObject saveBuildingStructure(String buildingName, AnalyzerObject analyzerObject) {
		DataLoggerObject dataLoggerObject = new DataLoggerObject();
		dataLoggerObject.setCode(buildingName);
		dataLoggerObject.addAnalyzer(analyzerObject);
		clbDao.saveDataLogger(dataLoggerObject);
		
		BuildingObject buildingObject = new BuildingObject();
		buildingObject.setName(buildingName);
		buildingObject.addDataLogger(dataLoggerObject);


		for(BuildingMeterParameterValues buildingMeterParameter: BuildingMeterParameterValues.values()) {
			BuildingMeterObject buildingMeterObject = new BuildingMeterObject();
			buildingMeterObject.setName( buildingMeterParameter.getLabel() );
			buildingMeterObject.setLabelKey( buildingMeterParameter.name() );
			buildingMeterObject.setUnit(buildingMeterParameter.getUnit());

			clbDao.saveBuildingMeter( buildingMeterObject );

			buildingObject.addBuildingMeter(buildingMeterObject);
		}
		clbDao.saveBuilding(buildingObject);
		
		return buildingObject;
	}

}

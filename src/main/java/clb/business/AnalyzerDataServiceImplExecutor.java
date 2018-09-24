package clb.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clb.business.enums.AnalyzerCommand;
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

					AnalyzerObject analyzerObject = null;
					BuildingObject buildingObject = null;

					boolean exit = false;
					while(clientSocket.isConnected() && !exit) {
						AnalyzerCommand command = AnalyzerCommand.getValueOf(in.nextLine());

						if(command == null)
							throw new IlegalCommandAppException();

						switch(command) {
						case GET_USERS_INFO:
							
							//Send Users
							List<JSONObject> jsonToSend = clbDao.getAllUsers().stream()
							.map(UsersystemObject::toJson)
							.collect(Collectors.toList());

							for(JSONObject json: jsonToSend) {
								clientSocket.getOutputStream().write(json.toString().getBytes());
								clientSocket.getOutputStream().flush();
							}	

							clientSocket.getOutputStream().write(AnalyzerCommand.END_USERS_INFO.getValue().getBytes());
							clientSocket.getOutputStream().flush();
							break;
							
						case PERSIST_DATA_OBJECT:
							JSONObject jsonObj = new JSONObject(in.nextLine());

							String buildingName = jsonObj.getString("buildingName");

							if(buildingObject == null || !buildingObject.getName().equals(buildingName)) {

								buildingObject = clbDao.getBuildingByName(buildingName);
								
								analyzerObject = new AnalyzerObject();
								analyzerObject.setCodeName("Analyzer For Building " + buildingName);
								clbDao.saveAnalyzer(analyzerObject);

								buildingObject = saveBuildingStructure(buildingName,analyzerObject);
							}

							jsonObj.put("analyzerId", analyzerObject.getId());

							AnalyzerRegistryObject analyzerRegistry = JsonUtils.getInstance().toAnalyzerRegistryObject(jsonObj);
							clbDao.saveAnalyzerRegistry( analyzerRegistry);
							analyzerObject.addAnalyzerRegistry(analyzerRegistry.getAnalyzerId());

							clientSocket.getOutputStream().write(AnalyzerCommand.ACKNOWLEDGE.getValue().getBytes());
							clientSocket.getOutputStream().flush();
							break;
							
						case GET_LATEST_PERSISTED_DATE:

							String analyzerCodeName = in.nextLine();
							Long latestDateForAnalyzer = clbDao.getLatestDateForAnalyzer(analyzerCodeName);
							
							clientSocket.getOutputStream().write(latestDateForAnalyzer != null ? 
									(latestDateForAnalyzer.toString()+"\n").getBytes() : "\n".getBytes());
							clientSocket.getOutputStream().write(AnalyzerCommand.END.getValue().getBytes());
							clientSocket.getOutputStream().flush();
							break;
							
						case EXIT_PERSIST_DATA_OBJECT:

							clbDao.saveAnalyzer(analyzerObject);

							analyzerObject = null;
							buildingObject = null;

							exit = true;
							break;
							
						default: throw new IlegalCommandAppException();
						}
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

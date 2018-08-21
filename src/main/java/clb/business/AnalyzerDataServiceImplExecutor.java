package clb.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import clb.business.exceptions.IlegalCommandAppException;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.UsersystemObject;
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
							
							JSONObject usersSend = new JSONObject();
							
							final String user = "greenworld@ventosdepoupanca.com";
							final String passwd = "l#_YqMoJe%coJUbF";
							
							usersSend.put("userftp", user);
							usersSend.put("passwordftp", passwd);
							clientSocket.getOutputStream().write(usersSend.toString().getBytes());
							clientSocket.getOutputStream().flush();
							
							clientSocket.getOutputStream().write("*end*".getBytes());
						}
						//Persist Data Objects
						else if(command.equals("*persistDataObject*")){
							JSONObject jsonObj = new JSONObject(in.nextLine());
							
							String analyzerId = jsonObj.getString("itemSn");
							String bulildingName = jsonObj.getString("buildingName");
							
							clbDao.saveAnalyzerRegistry( new AnalyzerRegistryObject(jsonObj));
						}
						else if(command.equals("*exit*")){
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

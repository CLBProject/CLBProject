package clb.business;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.constants.Month;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.MonthAverageObject;
import clb.database.ClbDao;
import clb.database.entities.AnalyzerRegistryEntity;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ClbDao<AnalyzerRegistryEntity> clbDaoAnalyzer;

	@Autowired
	private TaskExecutor taskExecutor;

	@Value(value = "classpath:documents")
	private Resource dataAnalyzerXls;

	public void init(){
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				try(ServerSocket s = new ServerSocket(1234)){
					while(true){
						Socket clientSocket = s.accept();
						try(Scanner in = new Scanner(clientSocket.getInputStream())){
							JSONObject jsonObj = new JSONObject(in.nextLine());
							System.out.println(jsonObj.toString());
						}
						catch(JSONException jsonex){
							jsonex.printStackTrace();
						}
					}
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void destroy(){

	}

	@Override
	@Transactional
	public void fillDatabaseDataWithMoreThenOneYears() throws IOException {
		clbDaoAnalyzer.persistScriptBigData();
		
//		for(File file: dataAnalyzerXls.getFile().listFiles()){
//			clbDaoAnalyzer.updateAnalyzerRegistriesForAnalyzer(file);
//		}
	}

	public ClbDao<AnalyzerRegistryEntity> getClbDaoAnalyzer() {
		return clbDaoAnalyzer;
	}

	public void setClbDaoAnalyzer(ClbDao<AnalyzerRegistryEntity> clbDaoAnalyzer) {
		this.clbDaoAnalyzer = clbDaoAnalyzer;
	}

	public Resource getDataAnalyzerXls() {
		return dataAnalyzerXls;
	}

	public void setDataAnalyzerXls(Resource dataAnalyzerXls) {
		this.dataAnalyzerXls = dataAnalyzerXls;
	}

	public List<AnalyzerRegistryObject> getDataByDay(Date day) {

		if(day == null){
			return new ArrayList<AnalyzerRegistryObject>();
		}

		return new ArrayList<AnalyzerRegistryObject>(clbDaoAnalyzer.getAnalyzerRegistriesByDay(day).stream()
				.map( AnalyzerRegistryObject::new ).collect( Collectors.toList() ));
	}

	@Override
	public List<AnalyzerRegistryObject> getDataByDayAndHours( Date day, String hour ) throws IOException {

		if(day == null || hour == null){
			return new ArrayList<AnalyzerRegistryObject>();
		}

		return new ArrayList<AnalyzerRegistryObject>(clbDaoAnalyzer.getAnalyzerRegistriesByDayAndHour(day,hour).stream()
				.map( AnalyzerRegistryObject::new ).collect( Collectors.toList() ));
	}

	@Override
	public List<MonthAverageObject> getDataByYear(Integer year) {

		if(year == null){
			return new ArrayList<MonthAverageObject>();
		}

		else return clbDaoAnalyzer.getYearMonthAverages(year).stream()
				.map(objectArray -> {
					Object[] object = (Object[]) objectArray;
					return new MonthAverageObject( (Double) object[0], (Double) object[1], (Double) object[2], (Integer) object[3], Month.getMonthById( (Integer)object[4]),null);
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<MonthAverageObject> getDataByYearAndMonths( Integer yearSelected, Month monthSelected ) {
		if(yearSelected == null || monthSelected == null){
			return new ArrayList<MonthAverageObject>();
		}

		else return clbDaoAnalyzer.getYearMonthDaysAverages(yearSelected,monthSelected.getMonth()).stream()
				.map(objectArray -> {
					Object[] object = (Object[]) objectArray;
					return new MonthAverageObject( (Double) object[0], (Double) object[1], (Double) object[2], (Integer) object[3], 
							Month.getMonthById( (Integer)object[4]), (Integer) object[5]);
				})
				.collect(Collectors.toList());
	}


	@Override
	public List<Integer> getRegistryYears() {
		return clbDaoAnalyzer.getRegistryYears();
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

}

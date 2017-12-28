package clb.business;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
import clb.database.UsersystemMongoRepository;
import clb.database.entities.UsersystemEntity;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    @Autowired
    private UsersystemMongoRepository mongoRepository;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value(value = "classpath:documents")
    private Resource dataAnalyzerXls;

    @Transactional
    public void insertMongoData(){

        UsersystemEntity entity = new UsersystemEntity();
        entity.setUsername( "user1" );
        entity.setUserid( "user2@teste.com" );

        mongoRepository.insert( entity );
    }

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
        //clbDaoAnalyzer.persistScriptBigData(dataAnalyzerXls.getFile());
    }

    public Resource getDataAnalyzerXls() {
        return dataAnalyzerXls;
    }

    public void setDataAnalyzerXls(Resource dataAnalyzerXls) {
        this.dataAnalyzerXls = dataAnalyzerXls;
    }

    public List<AnalyzerRegistryObject> getDataByDay(Date day) {
        return new ArrayList<AnalyzerRegistryObject>();
    }

    @Override
    public List<AnalyzerRegistryObject> getDataByDayAndHours( Date day, String hour ) throws IOException {
        return new ArrayList<AnalyzerRegistryObject>();
    }

    @Override
    public List<MonthAverageObject> getDataByYear(Integer year) {
        return new ArrayList<MonthAverageObject>();
    }

    @Override
    public List<MonthAverageObject> getDataByYearAndMonths( Integer yearSelected, Month monthSelected ) {
        return new ArrayList<MonthAverageObject>();
    }
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public UsersystemMongoRepository getMongoRepository() {
        return mongoRepository;
    }

    public void setMongoRepository( UsersystemMongoRepository mongoRepository ) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public List<Integer> getRegistryYears() {
        return new ArrayList<Integer>();
    }


}

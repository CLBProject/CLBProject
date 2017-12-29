package clb.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import clb.business.constants.Month;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.MonthAverageObject;
import clb.database.BuildingsMongoRepository;
import clb.database.UsersystemMongoRepository;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    @Autowired
    private UsersystemMongoRepository usersMongoRepository;
    
    @Autowired
    private BuildingsMongoRepository buildingsMongoRepository;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value(value = "classpath:documents")
    private Resource dataAnalyzerXls;
   

    @Override
    public void persistScriptBigData() throws IOException{
        UsersystemEntity userEntity = new UsersystemEntity();

        userEntity.setUserid( "nobreyeste@hotmail.com" );
        userEntity.setName( "Carlos Nobre" );
        userEntity.setAddress( "No address at this point" );
        userEntity.setUsername( "cnobre" );
        userEntity.setPassword( "123" );

        UsersystemEntity userEntity2 = new UsersystemEntity();

        userEntity2.setUserid( "brunocatela@hotmail.com" );
        userEntity2.setName( "Bruno Catela" );
        userEntity2.setAddress( "No address at this point" );
        userEntity2.setUsername( "bcatela" );
        userEntity2.setPassword( "123" );

        UsersystemEntity userEntity3 = new UsersystemEntity();

        userEntity3.setUserid( "luissantos@hotmail.com" );
        userEntity3.setName( "Luis Santos" );
        userEntity3.setAddress( "No address at this point" );
        userEntity3.setUsername( "lsantos" );
        userEntity3.setPassword( "123" );
        
        List<UsersystemEntity> users = new ArrayList<UsersystemEntity>();
        
        users.add( userEntity );
        users.add( userEntity2 );
        users.add( userEntity3 );
        
        int usersIndex = 0;
        
        for(File file: dataAnalyzerXls.getFile().listFiles()){
            updateAnalyzerRegistriesForAnalyzer(file,users.get( usersIndex ));
            usersIndex = usersIndex +1 == users.size() ? 0 : usersIndex+1;
        }
        
        usersMongoRepository.insert(users);
    }
    
    private void persistDummyAnalyzerRegistries(AnalyzerEntity analyzer, Set<String> dataToExclude){

        Random random = new Random();

        int numberOfYears = 1;
        int startingYear = 2017;
        int lowAl = 200;
        int highAl = 450;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set( startingYear, 0, 1);
        
        System.out.println( "Starting persisting data... ");
        
        for(int a = 0 ;a<numberOfYears; a++){
            int yearDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            for(int l = 0; l < yearDays; l++ ){
                for(int b = 0; b < 24 ; b++){
                    for(int k = 0; k < 60; k++){
                        
                        String currentTime = (b < 10 ? "0"+b : ""+b) + ":" + (k < 10 ? "0"+k : ""+k)+ ":00";
                        Date currentDate = calendar.getTime();
                        
                        if(dataToExclude.contains( calendar.get(Calendar.YEAR) +"/"+calendar.get(Calendar.MONTH) + "/" + 
                        		calendar.get(Calendar.DAY_OF_MONTH) + "_" + currentTime)){
                            continue;
                        }
                        
                        AnalyzerRegistryEntity anaRegObj = new AnalyzerRegistryEntity();
                        anaRegObj.setCurrenttime( currentTime );
                        anaRegObj.setCurrentdate( currentDate ); 

                        anaRegObj.setAl1(lowAl + (highAl - lowAl) * random.nextDouble());
                        anaRegObj.setAl2(lowAl + (highAl - lowAl) * random.nextDouble());
                        anaRegObj.setAl3(lowAl + (highAl - lowAl) * random.nextDouble());

                        analyzer.addAnalyzerRegistry(anaRegObj);
                    }
                }
                calendar.add(Calendar.DATE, 1);
            }
            System.out.println("Persisted Registries from year: " + (a+startingYear) + ", for analyzer: " + analyzer.getAnalyzerid());
        }

    }
    
    
    
    private void updateAnalyzerRegistriesForAnalyzer(File file, UsersystemEntity userEntity) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

        BuildingEntity building = new BuildingEntity();
        building.setName(file.getName().split("\\.")[0]);
        building.setBuildingusername(file.getName().split("\\.")[0]);
        userEntity.addBuilding(building);
        
        
        
        for(int j = 0; j<workbook.getNumberOfSheets();j++){
            
            DataLoggerEntity dl = new DataLoggerEntity();
            dl.setName( "Data Logger "+j );
            dl.setFtpaddress( "ftp://noftp" );
            building.addDataLogger(dl);
            
            AnalyzerEntity ana = new AnalyzerEntity();
            ana.setName( "Analyzer " + j);
            dl.addAnalyzer(ana);
            
            XSSFSheet worksheet = workbook.getSheetAt( j );

            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(new Date());

            Set<String> dataToExclueOnDummy = new HashSet<String>();
            
            for(int i = 2;i<worksheet.getLastRowNum();i++){

                XSSFRow row = worksheet.getRow(i);
                
                if(row.getCell(0) == null){
                    continue;
                }
                
                Date currentRowDate = row.getCell(0).getDateCellValue();
                String currentRowTime = row.getCell(1).getStringCellValue();

                calendar.setTime(currentRowDate);   // assigns calendar to given date 
                
                dataToExclueOnDummy.add( calendar.get(Calendar.YEAR) +"/"+calendar.get(Calendar.MONTH) + "/" + 
                					calendar.get(Calendar.DAY_OF_MONTH) + "_" + currentRowTime );
                
                AnalyzerRegistryEntity analyzerRegistryEntity = new AnalyzerRegistryEntity();
                
                analyzerRegistryEntity.setCurrentdate( currentRowDate );
                analyzerRegistryEntity.setCurrenttime( currentRowTime );
                analyzerRegistryEntity.setAl1(row.getCell(2).getNumericCellValue());
                analyzerRegistryEntity.setAl2(row.getCell(3).getNumericCellValue());
                analyzerRegistryEntity.setAl3(row.getCell(4).getNumericCellValue());
                analyzerRegistryEntity.setHz(row.getCell(5).getNumericCellValue());
                analyzerRegistryEntity.setPfl1(row.getCell(6).getNumericCellValue());
                analyzerRegistryEntity.setPfl2(row.getCell(7).getNumericCellValue());
                analyzerRegistryEntity.setPfl3(row.getCell(8).getNumericCellValue());
                analyzerRegistryEntity.setPfsys(row.getCell(9).getNumericCellValue());
                analyzerRegistryEntity.setVl1l2(row.getCell(10).getNumericCellValue());
                analyzerRegistryEntity.setVl1n(row.getCell(11).getNumericCellValue());
                analyzerRegistryEntity.setVl2l3(row.getCell(12).getNumericCellValue());
                analyzerRegistryEntity.setVl2n(row.getCell(13).getNumericCellValue());
                analyzerRegistryEntity.setVl3l1(row.getCell(14).getNumericCellValue());
                analyzerRegistryEntity.setVl3n(row.getCell(15).getNumericCellValue());
                analyzerRegistryEntity.setVllsys(row.getCell(16).getNumericCellValue());
                analyzerRegistryEntity.setVlnsys(row.getCell(17).getNumericCellValue());
                analyzerRegistryEntity.setKval1(row.getCell(18).getNumericCellValue());
                analyzerRegistryEntity.setKval2(row.getCell(19).getNumericCellValue());
                analyzerRegistryEntity.setKval3(row.getCell(20).getNumericCellValue());
                analyzerRegistryEntity.setKvasys(row.getCell(21).getNumericCellValue());
                analyzerRegistryEntity.setKwl1(row.getCell(22).getNumericCellValue());
                analyzerRegistryEntity.setKwl2(row.getCell(23).getNumericCellValue());
                analyzerRegistryEntity.setKwl3(row.getCell(24).getNumericCellValue());
                analyzerRegistryEntity.setKwsys(row.getCell(25).getNumericCellValue());
                analyzerRegistryEntity.setKvarl1(row.getCell(26).getNumericCellValue());
                analyzerRegistryEntity.setKvarl2(row.getCell(27).getNumericCellValue());
                analyzerRegistryEntity.setKvarl3(row.getCell(28).getNumericCellValue());
                analyzerRegistryEntity.setKvarsys(row.getCell(29).getNumericCellValue());
                ana.addAnalyzerRegistry(analyzerRegistryEntity);
            }
            
            persistDummyAnalyzerRegistries( ana, dataToExclueOnDummy);
        }
        buildingsMongoRepository.insert(building);
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
        return usersMongoRepository;
    }

    public void setMongoRepository( UsersystemMongoRepository mongoRepository ) {
        this.usersMongoRepository = mongoRepository;
    }

    @Override
    public List<Integer> getRegistryYears() {
        return new ArrayList<Integer>();
    }

	public UsersystemMongoRepository getUsersMongoRepository() {
		return usersMongoRepository;
	}

	public void setUsersMongoRepository(UsersystemMongoRepository usersMongoRepository) {
		this.usersMongoRepository = usersMongoRepository;
	}

	public BuildingsMongoRepository getBuildingsMongoRepository() {
		return buildingsMongoRepository;
	}

	public void setBuildingsMongoRepository(BuildingsMongoRepository buildingsMongoRepository) {
		this.buildingsMongoRepository = buildingsMongoRepository;
	}

    
}

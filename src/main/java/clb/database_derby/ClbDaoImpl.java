package clb.database_derby;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clb.database_derby.entities.AnalyzerEntity;
import clb.database_derby.entities.AnalyzerRegistryEntity;
import clb.database_derby.entities.BuildingEntity;
import clb.database_derby.entities.DataLoggerEntity;
import clb.database_derby.entities.UsersystemEntity;


public class ClbDaoImpl<T extends Serializable> implements ClbDao<T>, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    @PersistenceContext(unitName = "clbDatabase")
    protected EntityManager entityManager;

    public void create( T entity ){
        entityManager.persist( entity );
    }

    public T update( T entity ){
        return entityManager.merge( entity );
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager( EntityManager entityManager ) {
        this.entityManager = entityManager;
    }

    @Override
    public void persistData(List<T> data) {
        data.stream().forEach(Entity -> entityManager.persist(Entity));
    }


    /**
     * @return All Analyzer Registries
     */
    @Override
    public List<AnalyzerRegistryEntity> getAllCurrentAnalyzerRegistryData() {
        return entityManager.createNamedQuery("AnalyzerRegistry.findAll",AnalyzerRegistryEntity.class).getResultList();
    }

    @Override
    public void flush() {
        entityManager.flush();
        entityManager.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnalyzerRegistryEntity> getAnalyzerRegistriesByDay(Date date) {
        Query q = entityManager.createNamedQuery("AnalyzerRegistry.findAllByDay",AnalyzerRegistryEntity.class);
        q.setParameter("currentdate", date);

        List<AnalyzerRegistryEntity> resultList = q.getResultList();

        return resultList == null ? new ArrayList<AnalyzerRegistryEntity>() : resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnalyzerRegistryEntity> getAnalyzerRegistriesByDayAndHour(Date date, String hour) {
        Query q = entityManager.createNamedQuery("AnalyzerRegistry.findAllByDayHour",AnalyzerRegistryEntity.class);
        q.setParameter("currentdate", date);
        q.setParameter("currenthour", hour);

        List<AnalyzerRegistryEntity> resultList = q.getResultList();

        return resultList == null ? new ArrayList<AnalyzerRegistryEntity>() : resultList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getRegistryYears() {
        Query q = entityManager.createNativeQuery("select year(currentDate) from ANALYZER_REGISTRY group by year(currentDate)");

        List<Integer> resultList = q.getResultList();

        return resultList == null ? new ArrayList<Integer>() : resultList;
    }

    @Override
    public Collection<?> getYearMonthAverages(Integer year){
        Query q = entityManager.createNativeQuery("select avg(al1), avg(al2), avg(al3), year(currentDate), month(currentDate) "
                + "from analyzer_registry "
                + "where year(currentDate) = ?1 "
                + "group by year(currentDate), month(currentdate)");		
        q.setParameter(1, year);

        return q.getResultList();
    }

    @Override
    public Collection<?> getYearMonthDaysAverages( Integer yearSelected, Integer monthSelected ) {
        Query q = entityManager.createNativeQuery("select avg(al1), avg(al2), avg(al3), year(currentdate), month(currentdate),day(currentdate) "
                + "from clb.analyzer_registry "
                + "where year(currentdate) = ?1 and month(currentdate) = ?2 "
                + "group by year(currentdate),month(currentdate), day(currentdate)");       
        q.setParameter(1, yearSelected);
        q.setParameter(2, monthSelected);

        return q.getResultList();
    }

    @Override
    public void persistScriptBigData(File registryFiles) throws IOException{
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
        
        for(File file: registryFiles.listFiles()){
            updateAnalyzerRegistriesForAnalyzer(file,users.get( usersIndex ));
            usersIndex = usersIndex +1 == users.size() ? 0 : usersIndex+1;
        }
    }
    
    private void updateAnalyzerRegistriesForAnalyzer(File file, UsersystemEntity userEntity) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

        BuildingEntity building = new BuildingEntity();
        building.setName(file.getName().split("\\.")[0]);
        building.setBuildingusername(file.getName().split("\\.")[0]);
        building.setUsersystem(userEntity);
        entityManager.persist( building );
        
        for(int j = 0; j<workbook.getNumberOfSheets();j++){
            
            DataLoggerEntity dl = new DataLoggerEntity();
            dl.setName( "Data Logger "+j );
            dl.setFtpaddress( "ftp://noftp" );
            dl.setBuilding( building );
            
            entityManager.persist( dl);
            
            AnalyzerEntity ana = new AnalyzerEntity();
            ana.setDataLogger( dl );
            ana.setName( "Analyzer " + j);
            
            entityManager.persist( ana);
            
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
                analyzerRegistryEntity.setAnalyzer( ana );
                
                entityManager.persist( analyzerRegistryEntity );
            }
            
            persistDummyAnalyzerRegistries( ana, dataToExclueOnDummy);
            
            flush();
        }

    }

    private void persistDummyAnalyzerRegistries(AnalyzerEntity analyzer, Set<String> dataToExclude){

        Random random = new Random();

        int numberOfYears = 1;
        int startingYear = 2017;
        int lowAl = 200;
        int highAl = 450;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set( startingYear, 0, 1);
        
        System.out.println( "Starting persisting data for Analyzer: " + analyzer.getAnalyzerid());
        
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

                        anaRegObj.setAnalyzer(analyzer);

                        entityManager.merge(anaRegObj);
                        anaRegObj = null;
                    }
                    
                    entityManager.flush();
                    entityManager.clear();
                }
                calendar.add(Calendar.DATE, 1);
            }
            System.out.println("Persisted Registries from year: " + (a+startingYear) + ", for analyzer: " + analyzer.getAnalyzerid());
        }

    }
}

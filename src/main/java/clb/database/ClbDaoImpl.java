package clb.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;


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
        
        List<BuildingEntity> buildings = new ArrayList<BuildingEntity>();

        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setName( "Amanjena Hotel" );
        buildingEntity.setBuildingusername("amanjenaHotel");
        buildingEntity.setUsersystem(userEntity);
        entityManager.persist( buildingEntity );

        BuildingEntity buildingEntity2 = new BuildingEntity();
        buildingEntity2.setName( "AquaMirage Hotel" );
        buildingEntity2.setBuildingusername("aquaMirageHotel");
        buildingEntity2.setUsersystem(userEntity);
        entityManager.persist( buildingEntity2 );

//        BuildingEntity buildingEntity3 = new BuildingEntity();
//        buildingEntity3.setName( "Ritz" );
//        buildingEntity3.setBuildingusername("ritz");
//        buildingEntity3.setUsersystem(userEntity2);
//        entityManager.persist( buildingEntity3 );

        BuildingEntity buildingEntity4 = new BuildingEntity();
        buildingEntity4.setName( "VASP" );
        buildingEntity4.setBuildingusername("vasp");
        buildingEntity4.setUsersystem(userEntity3);
        entityManager.persist( buildingEntity4 );
        
        buildings.add( buildingEntity );
        buildings.add( buildingEntity2 );
       // buildings.add( buildingEntity3 );
        buildings.add( buildingEntity4 );
        
        int buildingIndex = 0;
        
        for(File file: registryFiles.listFiles()){
            updateAnalyzerRegistriesForAnalyzer(file,buildings.get( buildingIndex ));
            buildingIndex = buildingIndex +1 == buildings.size() ? 0 : buildingIndex+1;
        }
    }
    
    private void updateAnalyzerRegistriesForAnalyzer(File file, BuildingEntity building) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

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
            
            persistDummyAnalyzerRegistries( ana );
            
            XSSFSheet worksheet = workbook.getSheetAt( j );

            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(new Date());

            for(int i = 2;i<worksheet.getLastRowNum();i++){

                XSSFRow row = worksheet.getRow(i);

                Date currentRowDate = row.getCell(0).getDateCellValue();
                String currentRowTime = row.getCell(1).getStringCellValue();

                calendar.setTime(currentRowDate);   // assigns calendar to given date 

                Query qBuilding = entityManager.createNativeQuery( "select an.analyzerId from Building b2 "
                        + "inner join Data_Logger dl on b2.buildingId = dl.buildingId "
                        + "inner join Analyzer an on dl.dataLoggerId = an.dataLoggerId "
                        + "where b2.buildingUsername=?1" );

                qBuilding.setParameter(1, file.getName().split( "\\." )[0]);
                BigInteger analyzerId = (BigInteger) qBuilding.getResultList().get( 0 );

                Query q = entityManager.createNamedQuery("AnalyzerRegistry.findSpecificAnalyzerRegistry",AnalyzerRegistryEntity.class);
                q.setParameter("currenttime", currentRowTime);
                q.setParameter("currentdate", currentRowDate);
                q.setParameter("analyzerId", analyzerId.longValue() );
                
                AnalyzerRegistryEntity analyzerRegistryEntity = null;
                
                try{
                	analyzerRegistryEntity = (AnalyzerRegistryEntity)q.getSingleResult();
                }
                catch(Exception e){
                	e.printStackTrace();
                }
                
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
                
                entityManager.merge( analyzerRegistryEntity );
            }
            
            flush();
        }

    }

    private void persistDummyAnalyzerRegistries(AnalyzerEntity analyzer){

        Random random = new Random();

        int numberOfYears = 1;
        int startingYear = 2017;
        int lowAl = 200;
        int highAl = 450;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set( startingYear, 0, 1);
        
        System.out.println( "Starting persisting data for Analyzer: " + analyzer.getAnalyzerid() );
        
        for(int a = 0 ;a<numberOfYears; a++){
            int yearDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            for(int l = 0; l < yearDays; l++ ){
                for(int b = 0; b < 24 ; b++){
                    for(int k = 0; k < 60; k++){

                        AnalyzerRegistryEntity anaRegObj = new AnalyzerRegistryEntity();
                        anaRegObj.setCurrenttime( (b < 10 ? "0"+b : ""+b) + ":" + (k < 10 ? "0"+k : ""+k)+ ":00");
                        anaRegObj.setCurrentdate( calendar.getTime() ); 

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
            System.out.println("Persisted Registries from year: " + a);
        }

    }
}

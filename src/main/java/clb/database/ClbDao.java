package clb.database;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import clb.database.entities.AnalyzerRegistryEntity;

public interface ClbDao<T extends Serializable> {

    void create( T entity );
    
    T update( T entity );
    
    void delete( T entity );

	void persistData(List<T> data);

	List<AnalyzerRegistryEntity> getAllCurrentAnalyzerRegistryData();
	
	List<AnalyzerRegistryEntity> getAnalyzerRegistriesByDay(Date date);
	
	List<AnalyzerRegistryEntity> getAnalyzerRegistriesByDayAndHour(Date date, String hour); 

    void flush();

	List<Integer> getRegistryYears();
	
	Collection<?> getYearMonthAverages(Integer year);

    Collection<?> getYearMonthDaysAverages( Integer yearSelected, Integer monthSelected );

	void persistScriptBigData();

	void updateAnalyzerRegistriesForAnalyzer(File file) throws IOException;
}

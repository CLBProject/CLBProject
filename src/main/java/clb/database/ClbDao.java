package clb.database;

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
	
	List<AnalyzerRegistryEntity> getOnlyLatestCurrentAnalyzerRegistryData(Date sinceDate);

    void flush();

	List<Integer> getRegistryYears();
	
	Collection<?> getYearMonthAverages(Integer year);

	
}

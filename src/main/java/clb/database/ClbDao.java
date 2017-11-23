package clb.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import clb.database.entities.AnalyzerRegistryEntity;

public interface ClbDao<T extends Serializable> {

    public void create( T entity );
    
    public T update( T entity );
    
    public void delete( T entity );

	public void persistData(List<T> data);

	public List<AnalyzerRegistryEntity> getAllCurrentAnalyzerRegistryData();
	
	public List<AnalyzerRegistryEntity> getOnlyLatestCurrentAnalyzerRegistryData(Date sinceDate);

    public void flush();
}

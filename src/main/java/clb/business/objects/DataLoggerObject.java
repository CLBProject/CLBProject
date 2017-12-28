package clb.business.objects;

import clb.database.entities.DataLoggerEntity;

public class DataLoggerObject
{

    private long dataloggerid;

    private String name;

    public DataLoggerObject(){
        
    }

    public DataLoggerObject( DataLoggerEntity dataLogger ) {
        this.dataloggerid = dataLogger.getDataloggerid();
        this.name = dataLogger.getName();
    }
    
    public DataLoggerEntity toEntity() {
        DataLoggerEntity dataLogEntity = new DataLoggerEntity();
        dataLogEntity.setDataloggerid( this.dataloggerid );
        return dataLogEntity;
    }


    public long getDataloggerid() {
        return dataloggerid;
    }


    public void setDataloggerid( long dataloggerid ) {
        this.dataloggerid = dataloggerid;
    }


    public String getName() {
        return name;
    }


    public void setName( String name ) {
        this.name = name;
    }
    
}

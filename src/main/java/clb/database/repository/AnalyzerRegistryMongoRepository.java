package clb.database.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import clb.database.entities.AnalyzerRegistryEntity;

@Repository
public interface AnalyzerRegistryMongoRepository extends MongoRepository<AnalyzerRegistryEntity, String>{

    @Query(value="{ 'buildingusername' : ?0 }")
    List<AnalyzerRegistryEntity> getHourRegistriesFromTimeFrame(String analyzerId, Date timeFrame);

}

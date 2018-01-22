package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.AnalyzerRegistryAverageEntity;

@Repository
public interface AnalyzerRegistryAverageMongoRepository extends MongoRepository<AnalyzerRegistryAverageEntity, String>{
}

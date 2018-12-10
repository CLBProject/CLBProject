package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.AnalyzerMeterEntity;

@Repository
public interface AnalyzerMetersMongoRepository extends MongoRepository<AnalyzerMeterEntity, String>{

}

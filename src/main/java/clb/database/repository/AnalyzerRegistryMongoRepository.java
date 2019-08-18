package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.AnalyzerRegistryEntity;

@Repository
public interface AnalyzerRegistryMongoRepository extends MongoRepository<AnalyzerRegistryEntity, String>{
}

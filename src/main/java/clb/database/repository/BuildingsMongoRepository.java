package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.BuildingEntity;

@Repository
public interface BuildingsMongoRepository extends MongoRepository<BuildingEntity, String>{

}

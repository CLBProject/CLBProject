package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.BuildingMeterEntity;

@Repository
public interface BuildingsMetersMongoRepository extends MongoRepository<BuildingMeterEntity, String>{

}

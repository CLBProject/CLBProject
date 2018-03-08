package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.BuildingMeterParameterEntity;

@Repository
public interface BuildingsMetersParametersMongoRepository extends MongoRepository<BuildingMeterParameterEntity, String>{

}

package clb.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import clb.database.entities.BuildingEntity;

@Repository
public interface BuildingsMongoRepository extends MongoRepository<BuildingEntity, String>{

    @Query(value="{ 'buildingusername' : ?0 }")
    List<BuildingEntity> findBuildingsByUsername(String username);

    @Query(value="{ 'name' : ?0 }")
	BuildingEntity getBuildingByName(String buildingName);
}

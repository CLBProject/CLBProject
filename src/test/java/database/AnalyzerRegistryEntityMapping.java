package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.Test;

import com.mongodb.DBObject;

import clb.database.entities.AnalyzerRegistryEntity;

public class AnalyzerRegistryEntityMapping {

	@Test
	public void testAnalyzerRegistryEntityToDbObject() {
		
		Date currentTime = new Date();
		
		AnalyzerRegistryEntity analyzerEntity = new AnalyzerRegistryEntity();
		analyzerEntity.setVlnsys(2.4);
		analyzerEntity.setVllsys(88.1);
		analyzerEntity.setKwsys(9.5);
		analyzerEntity.setKvarsys(12.4);
		analyzerEntity.setKvasys(33.3);
		analyzerEntity.setPfsys(2.8);
		analyzerEntity.setHz(6.5);
		analyzerEntity.setAsys(18.7);
		analyzerEntity.setAnalyzerId("4");
		analyzerEntity.setCurrenttime(currentTime);
		
		//Dont Exist
		analyzerEntity.setComport(3);
		
		DBObject dbObject = analyzerEntity.toDbObject();
		
		assertEquals(dbObject.get("vlnsys"), analyzerEntity.getVlnsys());
		assertEquals(dbObject.get("vllsys"), analyzerEntity.getVllsys());
		assertEquals(dbObject.get("kwsys"), analyzerEntity.getKwsys());
		assertEquals(dbObject.get("kvarsys"), analyzerEntity.getKvarsys());
		assertEquals(dbObject.get("kvasys"), analyzerEntity.getKvasys());
		assertEquals(dbObject.get("pfsys"), analyzerEntity.getPfsys());
		assertEquals(dbObject.get("hz"), analyzerEntity.getHz());
		assertEquals(dbObject.get("asys"), analyzerEntity.getAsys());
		assertEquals(dbObject.get("analyzerId"), analyzerEntity.getAnalyzerId());
		assertEquals(dbObject.get("currenttime").toString(), analyzerEntity.getCurrenttime().toString());
		assertNotEquals(dbObject.get("comport"),analyzerEntity.getComport());
	}
}

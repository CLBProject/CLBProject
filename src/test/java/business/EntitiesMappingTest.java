package business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;

public class EntitiesMappingTest {

	@Test
	public void testAnalyzerRegistryObjectToEntity() {
		
		Date currentTime = new Date();
		Date epochFormat = new Date();
		Date rfc3339format = new Date();
		
		AnalyzerRegistryObject obj = new AnalyzerRegistryObject();
		obj.setAl1(1.0);
		obj.setAl2(2.0);
		obj.setAl3(3.0);
		obj.setAn(4.0);
		obj.setAnalyzerId("1");
		obj.setAsys(5.0);
		obj.setComport(1);
		obj.setCurrenttime(currentTime);
		obj.setEpochformat(epochFormat);
		obj.setHourmeterkwh(6.0);
		obj.setHourmeterkwhnegative(7.0);
		obj.setHz(8.0);
		obj.setId("12");
		obj.setItemlabel("IL1");
		obj.setKvahl(9.0);
		obj.setKvahl1(10.0);
		obj.setKvahl2(11.0);
		obj.setKvahl3(12.0);
		obj.setKval1(13.0);
		obj.setKval2(14.0);
		obj.setKval3(15.0);
		obj.setKvarh(16.0);
		obj.setKvarl1(17.0);
		obj.setKvarl2(18.0);
		obj.setKvarl3(19.0);
		obj.setKvarsys(20.0);
		obj.setKvasys(21.0);
		obj.setKwh(22.0);
		obj.setKwhl1(23.0);
		obj.setKwhl2(24.0);
		obj.setKwhl3(25.0);
		obj.setKwl1(26.0);
		obj.setKwl2(27.0);
		obj.setKwl3(28.0);
		obj.setKwsys(29.0);
		obj.setModbusid(2L);
		obj.setPfl1(30.0);
		obj.setPfl2(31.0);
		obj.setPfl3(32.0);
		obj.setPfsys(34.0);
		obj.setProducttype("PT1");
		obj.setRecorttype("RT1");
		obj.setRfc3339format(rfc3339format);
		obj.setTemperature(35.0);
		obj.setThdal1(36.0);
		obj.setThdal2(37.0);
		obj.setThdal3(38.0);
		obj.setThdvl1n(39.0);
		obj.setThdvl2n(40.0);
		obj.setThdvl3n(41.0);
		obj.setVadmd(42.0);
		obj.setVardmd(43.0);
		obj.setVl1l2(44.0);
		obj.setVl1n(45.0);
		obj.setVl2l3(46.0);
		obj.setVl2n(47.0);
		obj.setVl3l1(48.0);
		obj.setVl3n(49.0);
		obj.setVllsys(50.0);
		obj.setVlnsys(51.0);
		
		AnalyzerRegistryEntity entity = obj.toEntity();
		
		assertEquals(entity.getAl1(),1.0,0.01);
		assertEquals(entity.getAl2(),2.0,0.01);
		assertEquals(entity.getAl3(),3.0,0.01);
		assertEquals(entity.getAn(),4.0,0.01);
		assertEquals(entity.getAnalyzerId(),"1");
		assertEquals(entity.getAsys(),5.0,0.01);
		assertEquals(entity.getComport(),1);
		assertEquals(entity.getCurrenttime().toString(),currentTime.toString());
		assertEquals(entity.getEpochformat().toString(),epochFormat.toString());
		assertEquals(entity.getHourmeterkwh(),6.0,0.01);
		assertEquals(entity.getHourmeterkwhnegative(),7.0,0.01);
		assertEquals(entity.getHz(),8.0,0.01);
		assertEquals(entity.getId(),"12");
		assertEquals(entity.getItemlabel(),"IL1");
		assertEquals(entity.getKvahl(),9.0,0.01);
		assertEquals(entity.getKvahl1(),10.0,0.01);
		assertEquals(entity.getKvahl2(),11.0,0.01);
		assertEquals(entity.getKvahl3(),12.0,0.01);
		assertEquals(entity.getKval1(),13.0,0.01);
		assertEquals(entity.getKval2(),14.0,0.01);
		assertEquals(entity.getKval3(),15.0,0.01);
		assertEquals(entity.getKvarh(),16.0,0.01);
		assertEquals(entity.getKvarl1(),17.0,0.01);
		assertEquals(entity.getKvarl2(),18.0,0.01);
		assertEquals(entity.getKvarl3(),19.0,0.01);
		assertEquals(entity.getKvarsys(),20.0,0.01);
		assertEquals(entity.getKvasys(),21.0,0.01);
		assertEquals(entity.getKwh(),22.0,0.01);
		assertEquals(entity.getKwhl1(),23.0,0.01);
		assertEquals(entity.getKwhl2(),24.0,0.01);
		assertEquals(entity.getKwhl3(),25.0,0.01);
		assertEquals(entity.getKwl1(),26.0,0.01);
		assertEquals(entity.getKwl2(),27.0,0.01);
		assertEquals(entity.getKwl3(),28.0,0.01);
		assertEquals(entity.getKwsys(),29.0,0.01);
		assertEquals(entity.getModbusid(),2L);
		assertEquals(entity.getPfl1(),30.0,0.01);
		assertEquals(entity.getPfl2(),31.0,0.01);
		assertEquals(entity.getPfl3(),32.0,0.01);
		assertEquals(entity.getPfsys(),34.0,0.01);
		assertEquals(entity.getProducttype(),"PT1");
		assertEquals(entity.getRecorttype(),"RT1");
		assertEquals(entity.getRfc3339format().toString(),rfc3339format.toString());
		assertEquals(entity.getTemperature(),35.0,0.01);
		assertEquals(entity.getThdal1(),36.0,0.01);
		assertEquals(entity.getThdal2(),37.0,0.01);
		assertEquals(entity.getThdal3(),38.0,0.01);
		assertEquals(entity.getThdvl1n(),39.0,0.01);
		assertEquals(entity.getThdvl2n(),40.0,0.01);
		assertEquals(entity.getThdvl3n(),41.0,0.01);
		assertEquals(entity.getVadmd(),42.0,0.01);
		assertEquals(entity.getVardmd(),43.0,0.01);
		assertEquals(entity.getVl1l2(),44.0,0.01);
		assertEquals(entity.getVl1n(),45.0,0.01);
		assertEquals(entity.getVl2l3(),46.0,0.01);
		assertEquals(entity.getVl2n(),47.0,0.01);
		assertEquals(entity.getVl3l1(),48.0,0.01);
		assertEquals(entity.getVl3n(),49.0,0.01);
		assertEquals(entity.getVllsys(),50.0,0.01);
		assertEquals(entity.getVlnsys(),51.0,0.01);
		
		obj.setAn(4.3);
		
		assertNotEquals(entity.getAn(),obj.getAn());
	}

	@Test
	public void testAnalyzerRegistryEntityToObject() {
		
		Date currentTime = new Date();
		Date epochFormat = new Date();
		Date rfc3339format = new Date();
		
		AnalyzerRegistryEntity ent = new AnalyzerRegistryEntity();
		ent.setAl1(1.0);
		ent.setAl2(2.0);
		ent.setAl3(3.0);
		ent.setAn(4.0);
		ent.setAnalyzerId("1");
		ent.setAsys(5.0);
		ent.setComport(1);
		ent.setCurrenttime(currentTime);
		ent.setEpochformat(epochFormat);
		ent.setHourmeterkwh(6.0);
		ent.setHourmeterkwhnegative(7.0);
		ent.setHz(8.0);
		ent.setId("12");
		ent.setItemlabel("IL1");
		ent.setKvahl(9.0);
		ent.setKvahl1(10.0);
		ent.setKvahl2(11.0);
		ent.setKvahl3(12.0);
		ent.setKval1(13.0);
		ent.setKval2(14.0);
		ent.setKval3(15.0);
		ent.setKvarh(16.0);
		ent.setKvarl1(17.0);
		ent.setKvarl2(18.0);
		ent.setKvarl3(19.0);
		ent.setKvarsys(20.0);
		ent.setKvasys(21.0);
		ent.setKwh(22.0);
		ent.setKwhl1(23.0);
		ent.setKwhl2(24.0);
		ent.setKwhl3(25.0);
		ent.setKwl1(26.0);
		ent.setKwl2(27.0);
		ent.setKwl3(28.0);
		ent.setKwsys(29.0);
		ent.setModbusid(2L);
		ent.setPfl1(30.0);
		ent.setPfl2(31.0);
		ent.setPfl3(32.0);
		ent.setPfsys(34.0);
		ent.setProducttype("PT1");
		ent.setRecorttype("RT1");
		ent.setRfc3339format(rfc3339format);
		ent.setTemperature(35.0);
		ent.setThdal1(36.0);
		ent.setThdal2(37.0);
		ent.setThdal3(38.0);
		ent.setThdvl1n(39.0);
		ent.setThdvl2n(40.0);
		ent.setThdvl3n(41.0);
		ent.setVadmd(42.0);
		ent.setVardmd(43.0);
		ent.setVl1l2(44.0);
		ent.setVl1n(45.0);
		ent.setVl2l3(46.0);
		ent.setVl2n(47.0);
		ent.setVl3l1(48.0);
		ent.setVl3n(49.0);
		ent.setVllsys(50.0);
		ent.setVlnsys(51.0);
		
		AnalyzerRegistryObject object = new AnalyzerRegistryObject(ent);
		
		assertEquals(object.getAl1(),1.0,0.01);
		assertEquals(object.getAl2(),2.0,0.01);
		assertEquals(object.getAl3(),3.0,0.01);
		assertEquals(object.getAn(),4.0,0.01);
		assertEquals(object.getAnalyzerId(),"1");
		assertEquals(object.getAsys(),5.0,0.01);
		assertEquals(object.getComport(),1);
		assertEquals(object.getCurrenttime().toString(),currentTime.toString());
		assertEquals(object.getEpochformat().toString(),epochFormat.toString());
		assertEquals(object.getHourmeterkwh(),6.0,0.01);
		assertEquals(object.getHourmeterkwhnegative(),7.0,0.01);
		assertEquals(object.getHz(),8.0,0.01);
		assertEquals(object.getId(),"12");
		assertEquals(object.getItemlabel(),"IL1");
		assertEquals(object.getKvahl(),9.0,0.01);
		assertEquals(object.getKvahl1(),10.0,0.01);
		assertEquals(object.getKvahl2(),11.0,0.01);
		assertEquals(object.getKvahl3(),12.0,0.01);
		assertEquals(object.getKval1(),13.0,0.01);
		assertEquals(object.getKval2(),14.0,0.01);
		assertEquals(object.getKval3(),15.0,0.01);
		assertEquals(object.getKvarh(),16.0,0.01);
		assertEquals(object.getKvarl1(),17.0,0.01);
		assertEquals(object.getKvarl2(),18.0,0.01);
		assertEquals(object.getKvarl3(),19.0,0.01);
		assertEquals(object.getKvarsys(),20.0,0.01);
		assertEquals(object.getKvasys(),21.0,0.01);
		assertEquals(object.getKwh(),22.0,0.01);
		assertEquals(object.getKwhl1(),23.0,0.01);
		assertEquals(object.getKwhl2(),24.0,0.01);
		assertEquals(object.getKwhl3(),25.0,0.01);
		assertEquals(object.getKwl1(),26.0,0.01);
		assertEquals(object.getKwl2(),27.0,0.01);
		assertEquals(object.getKwl3(),28.0,0.01);
		assertEquals(object.getKwsys(),29.0,0.01);
		assertEquals(object.getModbusid(),2L);
		assertEquals(object.getPfl1(),30.0,0.01);
		assertEquals(object.getPfl2(),31.0,0.01);
		assertEquals(object.getPfl3(),32.0,0.01);
		assertEquals(object.getPfsys(),34.0,0.01);
		assertEquals(object.getProducttype(),"PT1");
		assertEquals(object.getRecorttype(),"RT1");
		assertEquals(object.getRfc3339format().toString(),rfc3339format.toString());
		assertEquals(object.getTemperature(),35.0,0.01);
		assertEquals(object.getThdal1(),36.0,0.01);
		assertEquals(object.getThdal2(),37.0,0.01);
		assertEquals(object.getThdal3(),38.0,0.01);
		assertEquals(object.getThdvl1n(),39.0,0.01);
		assertEquals(object.getThdvl2n(),40.0,0.01);
		assertEquals(object.getThdvl3n(),41.0,0.01);
		assertEquals(object.getVadmd(),42.0,0.01);
		assertEquals(object.getVardmd(),43.0,0.01);
		assertEquals(object.getVl1l2(),44.0,0.01);
		assertEquals(object.getVl1n(),45.0,0.01);
		assertEquals(object.getVl2l3(),46.0,0.01);
		assertEquals(object.getVl2n(),47.0,0.01);
		assertEquals(object.getVl3l1(),48.0,0.01);
		assertEquals(object.getVl3n(),49.0,0.01);
		assertEquals(object.getVllsys(),50.0,0.01);
		assertEquals(object.getVlnsys(),51.0,0.01);
		
		ent.setAn(4.3);
		
		assertNotEquals(object.getAn(),ent.getAn());
	}
	
	@Test
	public void testAnalyzerObjectToEntity() {
		
		List<String> analyzerRegistriesIds = new ArrayList<String>();
		analyzerRegistriesIds.add("8");
		analyzerRegistriesIds.add("80");

		AnalyzerObject analyzerObj = new AnalyzerObject();
		analyzerObj.setId("1L");
		analyzerObj.setCodeName("Analyzer Name1");
		analyzerObj.setAnalyzerRegistriesIds(analyzerRegistriesIds);
		
		AnalyzerEntity analyzerEnt = analyzerObj.toEntity();
		
		assertEquals(analyzerEnt.getId(),analyzerObj.getId());
		assertEquals(analyzerEnt.getCodeName(),analyzerObj.getCodeName());
		
		List<String> analyzerAverageRegistriesIdsMapped = analyzerEnt.getAnalyzerRegistriesIds();
		
		assertEquals(analyzerAverageRegistriesIdsMapped.size(), analyzerRegistriesIds.size());
		assertEquals(analyzerAverageRegistriesIdsMapped.get(0),"8");
		assertEquals(analyzerAverageRegistriesIdsMapped.get(1),"80");
	}
	
	@Test
	public void testAnalyzerEntityToObject() {
		
		List<String> analyzerRegistriesIds = new ArrayList<String>();
		analyzerRegistriesIds.add("5");
		analyzerRegistriesIds.add("6");

		AnalyzerEntity analyzerEnt = new AnalyzerEntity();
		analyzerEnt.setId("2L");
		analyzerEnt.setCodeName("Analyzer Name2");
		analyzerEnt.setAnalyzerRegistriesIds(analyzerRegistriesIds);
		
		AnalyzerObject analyzerObj = new AnalyzerObject(analyzerEnt);
		
		assertEquals(analyzerObj.getId(),analyzerEnt.getId());
		assertEquals(analyzerObj.getCodeName(),analyzerEnt.getCodeName());
		
		List<String> analyzerAverageRegistriesIdsMapped = analyzerObj.getAnalyzerRegistriesIds();
		
		assertEquals(analyzerAverageRegistriesIdsMapped.size(), analyzerRegistriesIds.size());
		assertEquals(analyzerAverageRegistriesIdsMapped.get(0),"5");
		assertEquals(analyzerAverageRegistriesIdsMapped.get(1),"6");
	}
	
	@Test
	public void testDataLoggerObjectToEntity() {
		
		BuildingObject bobj = new BuildingObject();
		bobj.setBuildingid("b01");
		bobj.setName("dl1");
		
		DivisionObject mainDivision = new DivisionObject();
		mainDivision.setName("Main Division");
		
		List<AnalyzerObject> analyzers = new ArrayList<AnalyzerObject>();
		
		AnalyzerObject aobj1 = new AnalyzerObject();
		aobj1.setId("1");
		
		AnalyzerObject aobj2 = new AnalyzerObject();
		aobj2.setId("2");
		
		analyzers.add(aobj1);
		analyzers.add(aobj2);
		mainDivision.setAnalyzers(analyzers);
		
		bobj.setMainDivision(mainDivision);
		
		BuildingEntity bEnt = bobj.toEntity();
		
		assertEquals(bobj.getBuildingid(),bEnt.getBuildingid());
		assertEquals(bobj.getName(),bEnt.getName());
		
		List<AnalyzerEntity> analyzersMapped = bEnt.getMainDivision().getAnalyzers();
		
		assertEquals(analyzers.size(), analyzersMapped.size());
		assertEquals(analyzersMapped.get(0).getId(),"1");
		assertEquals(analyzersMapped.get(1).getId(),"2");
	}
}

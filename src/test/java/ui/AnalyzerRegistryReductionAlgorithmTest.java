package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import clb.business.objects.AnalyzerRegistryObject;
import clb.ui.beans.objects.AnalyzerRegistryGui;
import clb.ui.beans.utils.AnalyzerRegistryReductionAlgorithm;
import clb.ui.enums.ScaleGraphic;

public class AnalyzerRegistryReductionAlgorithmTest {

	@Test
	public void testHoursAlgorithm() {

		//Given
		List<AnalyzerRegistryGui> registries = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries2 = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries3 = new ArrayList<AnalyzerRegistryGui>();

		Calendar cal = Calendar.getInstance();

		for(int i=0;i<60;i++) {
			AnalyzerRegistryGui registry = new AnalyzerRegistryGui(new AnalyzerRegistryObject());
			cal.set(Calendar.MINUTE, i);
			registry.setCurrentTime(cal.getTime());
			registries.add(registry);	

			if(i == 3 || i == 15 || i == 27 || i == 48)
				registries2.add(registry);
		}

		//When
		List<AnalyzerRegistryGui> guiRegistries1 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, ScaleGraphic.HOUR);
		List<AnalyzerRegistryGui> guiRegistries2 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries2, ScaleGraphic.HOUR);
		List<AnalyzerRegistryGui> guiRegistries3 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries3, ScaleGraphic.HOUR);

		assertEquals(guiRegistries1.size(),12);
		assertEquals(guiRegistries2.size(),4);
		assertEquals(guiRegistries3.size(),0);

		//Then
		Set<Integer> minutes1 = transformToMinutes(guiRegistries1);
		Set<Integer> minutes2 = transformToMinutes(guiRegistries2);

		List<Integer> minutesPresent = Arrays.asList(new Integer[] {0,5,10,15,20,25,30,35,40,45,50,55});

		assertTrue(minutesPresent.containsAll(minutes1));
		assertTrue(minutesPresent.containsAll(minutes2));

	}

	@Test
	public void testDaysAlgorithm() {
		//Given
		List<AnalyzerRegistryGui> registries = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries2 = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries3 = new ArrayList<AnalyzerRegistryGui>();

		Calendar cal = Calendar.getInstance();

		for(int i=0;i<24;i++) {
			for(int j = 0;j<60;j++) {
				AnalyzerRegistryGui registry = new AnalyzerRegistryGui(new AnalyzerRegistryObject());
				cal.set(Calendar.HOUR_OF_DAY, i);
				cal.set(Calendar.MINUTE, j);

				registry.setCurrentTime(cal.getTime());
				registries.add(registry);	

				if(j == 2 || j == 6 || j == 13 || j == 19)
					registries2.add(registry);
			}
		}

		//When
		List<AnalyzerRegistryGui> guiRegistries1 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, ScaleGraphic.DAY);
		List<AnalyzerRegistryGui> guiRegistries2 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries2, ScaleGraphic.DAY);
		List<AnalyzerRegistryGui> guiRegistries3 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries3, ScaleGraphic.DAY);

		//Then
		assertEquals(guiRegistries1.size(),12*24);
		assertEquals(guiRegistries2.size(),4*24);
		assertEquals(guiRegistries3.size(),0);

		Set<Integer> minutes1 = transformToMinutes(guiRegistries1);
		Set<Integer> minutes2 = transformToMinutes(guiRegistries2);

		List<Integer> minutesPresent = Arrays.asList(new Integer[] {0,5,10,15,20,25,30,35,40,45,50,55});

		assertTrue(minutesPresent.containsAll(minutes1));
		assertTrue(minutesPresent.containsAll(minutes2));
	}

	/*@Test
	public void testWeekAlgorithm() {
		//Given
		List<AnalyzerRegistryGui> registries = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries2 = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries3 = new ArrayList<AnalyzerRegistryGui>();

		Calendar cal = Calendar.getInstance();

		for(int k=1;k<8;k++) {
			for(int i=0;i<24;i++) {
				for(int j = 0;j<60;j++) {
					AnalyzerRegistryGui registry = new AnalyzerRegistryGui(new AnalyzerRegistryObject());
					cal.set(Calendar.DAY_OF_MONTH, k);
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, j);

					registry.setCurrentTime(cal.getTime());
					registries.add(registry);	

					if(k == 1 || k == 3 || k == 5)
						registries2.add(registry);
				}
			}
		}

		//When
		List<AnalyzerRegistryGui> guiRegistries1 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, ScaleGraphic.WEEK);
		List<AnalyzerRegistryGui> guiRegistries2 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries2, ScaleGraphic.WEEK);
		List<AnalyzerRegistryGui> guiRegistries3 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries3, ScaleGraphic.WEEK);
		
		//Then
		assertEquals(guiRegistries1.size(),24*7);
		assertEquals(guiRegistries2.size(),24*3);
		assertEquals(guiRegistries3.size(),0);
		
		Set<Integer> hours1 = transformToHours(guiRegistries1);
		Set<Integer> hours2 = transformToHours(guiRegistries2);

		List<Integer> hoursPresent = Arrays.asList(new Integer[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23});

		assertTrue(hoursPresent.containsAll(hours1));
		assertTrue(hoursPresent.containsAll(hours2));
	}*/

	@Test
	public void testMonthAlgorithm() {
		//Given
		List<AnalyzerRegistryGui> registries = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries2 = new ArrayList<AnalyzerRegistryGui>();
		List<AnalyzerRegistryGui> registries3 = new ArrayList<AnalyzerRegistryGui>();

		Calendar cal = Calendar.getInstance();
		//January
		cal.set(Calendar.MONTH,0);
		int monthMax = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for(int k=1;k<=monthMax;k++) {
			for(int i=0;i<24;i++) {
				for(int j = 0;j<60;j++) {
					AnalyzerRegistryGui registry = new AnalyzerRegistryGui(new AnalyzerRegistryObject());
					cal.set(Calendar.DAY_OF_MONTH, k);
					cal.set(Calendar.HOUR_OF_DAY, i);
					cal.set(Calendar.MINUTE, j);

					registry.setCurrentTime(cal.getTime());
					registries.add(registry);	

					if(k == 1 || k == 3 || k == 5 || k == 11 || k == 23 || k == 30)
						registries2.add(registry);
				}
			}
		}

		//When
		List<AnalyzerRegistryGui> guiRegistries1 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, ScaleGraphic.MONTH);
		List<AnalyzerRegistryGui> guiRegistries2 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries2, ScaleGraphic.MONTH);
		List<AnalyzerRegistryGui> guiRegistries3 = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries3, ScaleGraphic.MONTH);
		
		//Then
		assertEquals(guiRegistries1.size(),31);
		assertEquals(guiRegistries2.size(),6);
		assertEquals(guiRegistries3.size(),0);
		
		Set<Integer> days1 = transformToDays(guiRegistries1);
		Set<Integer> days2 = transformToDays(guiRegistries2);

		List<Integer> daysPresent = Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31});

		assertTrue(daysPresent.containsAll(days1));
		assertTrue(daysPresent.containsAll(days2));
	}

	private Set<Integer> transformToMinutes(List<AnalyzerRegistryGui> guiRegistries){
		return guiRegistries.stream().filter(registry -> {
			final Calendar calTime = Calendar.getInstance();
			calTime.setTime(registry.getCurrentTime());
			return calTime.get(Calendar.MINUTE) % 5 == 0;
		})
				.map(registry -> {
					final Calendar calTime = Calendar.getInstance();
					calTime.setTime(registry.getCurrentTime());
					return calTime.get(Calendar.MINUTE);
				})
				.collect(Collectors.toSet());
	}

	private Set<Integer> transformToHours(List<AnalyzerRegistryGui> guiRegistries){
		return guiRegistries.stream().filter(registry -> {
			final Calendar calTime = Calendar.getInstance();
			calTime.setTime(registry.getCurrentTime());
			return calTime.get(Calendar.MINUTE) == 0 && calTime.get(Calendar.SECOND) == 0 && calTime.get(Calendar.MILLISECOND) == 0;
		})
				.map(registry -> {
					final Calendar calTime = Calendar.getInstance();
					calTime.setTime(registry.getCurrentTime());
					return calTime.get(Calendar.HOUR_OF_DAY);
				})
				.collect(Collectors.toSet());
	}
	
	private Set<Integer> transformToDays(List<AnalyzerRegistryGui> guiRegistries){
		return guiRegistries.stream().filter(registry -> {
			final Calendar calTime = Calendar.getInstance();
			calTime.setTime(registry.getCurrentTime());
			return calTime.get(Calendar.HOUR_OF_DAY) == 0 && calTime.get(Calendar.MINUTE) == 0 && 
						calTime.get(Calendar.SECOND) == 0 && calTime.get(Calendar.MILLISECOND) == 0;
		})
				.map(registry -> {
					final Calendar calTime = Calendar.getInstance();
					calTime.setTime(registry.getCurrentTime());
					return calTime.get(Calendar.DAY_OF_MONTH);
				})
				.collect(Collectors.toSet());
	}
}

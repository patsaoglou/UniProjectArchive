/**
 * 
 */
package filtering;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import metadata.NaiveFileMetadataManager;

/**
 * @author pvassil
 *
 */
public class FilteringEngineTest {
	private static FilteringEngine filteringEngine;
	private static Map<String, List<String>> atomicFilters;
	private static NaiveFileMetadataManager metadataManager;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String sAlias = "simple";
		String sSeparator = ",";
		File sFile = new File("./test/resources/input/simple.csv");
		metadataManager = new NaiveFileMetadataManager(sAlias, sFile, sSeparator);

		atomicFilters = new HashMap<String, List<String>>();
		List<String> countryFilter = new ArrayList<String>();
		countryFilter.add("AUS:Australia");
		atomicFilters.put("LOCATION:Country", countryFilter);

		filteringEngine = new FilteringEngine(atomicFilters, metadataManager);
	}

	/**
	 * Test method for {@link filtering.FilteringEngine#workWithFile()}.
	 */
	@Test
	public final void testWorkWithFileHappyDay() {
		List<String[]> verySimpleResult = filteringEngine.workWithFile();
		assertEquals(15, verySimpleResult.size());
	}

	/**
	 * Test method for {@link filtering.FilteringEngine#workWithFile()}.
	 */
	@Test
	public final void testMultipleCriteriaHappyDay() throws Exception {
		Map<String, List<String>> multiCriteriaAtomicFilters = new HashMap<String, List<String>>(atomicFilters);
		List<String> countryFilter = new ArrayList<String>();
		countryFilter.add("2010");
		countryFilter.add("2011");
		countryFilter.add("2012");
		multiCriteriaAtomicFilters.put("TIME:Year", countryFilter);

		FilteringEngine anotherfilteringEngine = new FilteringEngine(multiCriteriaAtomicFilters, metadataManager);
		List<String[]> verySimpleResult = anotherfilteringEngine.workWithFile();
		assertEquals(3, verySimpleResult.size());
	}
	@Test
	public final void testCheckLineTrueHappyDay() throws Exception {
		Map<String, List<String>> multiCriteriaAtomicFilters = new HashMap<String, List<String>>(atomicFilters);
		List<String> countryFilter= new ArrayList<String>();
		List<String> timeFilter= new ArrayList<String>();
		countryFilter.add("AUT:Austria");
		multiCriteriaAtomicFilters.put("LOCATION:Country", countryFilter);
		timeFilter.add("2010");
		multiCriteriaAtomicFilters.put("TIME:Year", timeFilter);
		FilteringEngine filteringEngine = new FilteringEngine(multiCriteriaAtomicFilters, metadataManager);
		
		String fileLine = "HFTOT:All financing schemes,HCTOT:Current expenditure on health (all functions),HPTOT:All providers,AUT:Austria,2010,4261.055";
		String[] fileLineArray = fileLine.split(","); 
		Boolean verySimpleResult = filteringEngine.checkLine(fileLineArray);
		assertEquals(true, verySimpleResult);
	}
	@Test
	public final void testCheckLineFalseHappyDay() throws Exception {
		Map<String, List<String>> multiCriteriaAtomicFilters = new HashMap<String, List<String>>(atomicFilters);
		List<String> countryFilter= new ArrayList<String>();
		List<String> timeFilter= new ArrayList<String>();
		countryFilter.add("AUT:Austria");
		multiCriteriaAtomicFilters.put("LOCATION:Country", countryFilter);
		timeFilter.add("2010");
		multiCriteriaAtomicFilters.put("TIME:Year", timeFilter);
		FilteringEngine filteringEngine = new FilteringEngine(multiCriteriaAtomicFilters, metadataManager);
		
		String fileLine = "HFTOT:All financing schemes,HCTOT:Current expenditure on health (all functions),HPTOT:All providers,AUT:Austria,2011,4261.055";
		String[] fileLineArray = fileLine.split(","); 
		Boolean verySimpleResult = filteringEngine.checkLine(fileLineArray);
		assertEquals(false, verySimpleResult);
	}
}// end class

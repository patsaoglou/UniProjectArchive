package filtering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import metadata.MetadataManagerInterface;
import metadata.NaiveFileMetadataManager;

public class FilteringEngine implements FilteringEngineInterface {
	private Map<String, List<String>> atomicFilters;
	private MetadataManagerInterface metadataManager;

	public FilteringEngine(Map<String, List<String>> atomicFilters, NaiveFileMetadataManager metadataManager) {
		// TODO Auto-generated constructor stub
		setupFilteringEngine(atomicFilters,metadataManager);		
	}

	@Override
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) {
		// TODO Auto-generated method stub
		if(pAtomicFilters == null || pMetadataManager == null) {
			return -1;
		}			
		this.atomicFilters = pAtomicFilters;
		this.metadataManager = pMetadataManager;
		return 0;
	}

	@Override
	public List<String[]> workWithFile() {
		// TODO Auto-generated method stub
		if(atomicFilters == null || metadataManager == null) {
			return null;
		}
		try {
			List<String[]> results = new ArrayList<String[]>();;
			FileReader reader = new FileReader(metadataManager.getDataFile());
			BufferedReader buffer = new BufferedReader(reader);
			String dataInLine = buffer.readLine();
			String seperator = metadataManager.getSeparator();
			dataInLine = buffer.readLine();

			while(dataInLine!=null) {
				String[] dataInLineArray=dataInLine.split(seperator);
				if(checkLine(dataInLineArray)==true) {
					results.add(dataInLineArray);
				}
				dataInLine = buffer.readLine();
			}
			buffer.close();
			return	results;					
		}catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public Boolean checkLine(String[] dataInLineArray) {
		for(String filterType:atomicFilters.keySet()) {
			Boolean coloumnMeetsAtomicFilter = false;
			int coloumn = metadataManager.getFieldPositions().get(filterType);
			for (String field:atomicFilters.get(filterType)) {
				if (dataInLineArray[coloumn].equals(field)) {	
					coloumnMeetsAtomicFilter = true;
					break;
				}
			}if (coloumnMeetsAtomicFilter == false) {
				return false;
			}
		}
		return true;
	}
}

package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import filtering.FilteringEngine;
import metadata.NaiveFileMetadataManager;

public class StructuredFileManager implements StructuredFileManagerInterface	{
	private NaiveFileMetadataManager fileMetadata;
	private FilteringEngine filteringEngine = new FilteringEngine(null,null);
	List<String[]> results = new ArrayList<String[]>();;

	@Override
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException {
		// TODO Auto-generated method stub
		File registeredFile= new File(pPath);
		fileMetadata = new NaiveFileMetadataManager(pAlias,registeredFile,pSeparator);
 		return fileMetadata.getDataFile();
	}

	@Override
	public String[] getFileColumnNames(String pAlias) {
		// TODO Auto-generated method stub
		String emptyArray[]= {};
		if (fileMetadata == null || pAlias == null) {		
			return emptyArray;
		}else {
			if(fileMetadata.getAlias().equals(pAlias)) {
				return fileMetadata.getColumnNames();
			}else if(!fileMetadata.getAlias().equals(pAlias)) {
				return emptyArray;
			}
		}
		return null;
	}

	@Override
	public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters) {
		// TODO Auto-generated method stub
		filteringEngine.setupFilteringEngine(pAtomicFilters, fileMetadata);
		results = filteringEngine.workWithFile();		
		
		return results;
	}

	@Override
	public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut) {
		// TODO Auto-generated method stub
		List<String> colList;
		int records = 0;
		if(recordList == null || pOut.checkError()) {
			return -1;
		}
		colList = Arrays.asList(fileMetadata.getColumnNames());
		pOut.print(String.join(fileMetadata.getSeparator(), colList)+"\n");
		for(String[] result:recordList) {
			colList = Arrays.asList(result);
			pOut.print(String.join(fileMetadata.getSeparator(), colList)+"\n");
			records++;
		}
		return records;
	}

}

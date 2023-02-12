package metadata;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class NaiveFileMetadataManager implements MetadataManagerInterface {
	private String alias;
	private File file; 
	private String separator;
	private Map<String, Integer> fieldPositions = new HashMap<String, Integer>();
	private String[] columnNames;

	public NaiveFileMetadataManager(String pAlias, File pFile, String pSeparator) {
		// TODO Auto-generated constructor stub
		this.alias=pAlias;
		this.file=pFile;
		this.separator=pSeparator;	
	}

	public Map<String, Integer> getFieldPositions() {
		// TODO Auto-generated method stub
		try {			
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			String fieldsLine = buffer.readLine();
			columnNames = fieldsLine.split(separator);
			int fieldPosition = 0;
			for(String field: columnNames) {
				fieldPositions.put(field, fieldPosition);
				fieldPosition++;
			}
			buffer.close();
			return fieldPositions;			
		}catch (IOException e) {
            e.printStackTrace();
        }
		return null;				
	}

	public File getDataFile() {
		// TODO Auto-generated method stub
		return file;
	}

	public String getSeparator() {
		// TODO Auto-generated method stub
		return separator;
	}

	public String getAlias() {
		// TODO Auto-generated method stub
		return alias;
	}

	public String[] getColumnNames() {
		// TODO Auto-generated method stub
		if(columnNames == null) {
			getFieldPositions();
			return columnNames;
		}else {		
			return columnNames;
		}
	}

}

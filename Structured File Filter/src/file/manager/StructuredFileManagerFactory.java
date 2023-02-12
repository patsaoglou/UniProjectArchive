package file.manager;

public class StructuredFileManagerFactory  {

	public StructuredFileManagerInterface createStructuredFileManager() {
		StructuredFileManagerInterface fileManager = new StructuredFileManager();
		return fileManager;
	}

}

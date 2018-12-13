package documentRetrieval;

import java.io.File;
import java.io.FileFilter;


public class FileSelector implements FileFilter {
		public boolean accept(File pathToFile) {
			boolean flag=false;	
			if(pathToFile.getName().endsWith(".txt") || pathToFile.getName().endsWith(".html") || pathToFile.getName().endsWith(".html"))
				{
				flag=true;
				}	
				return flag;
				//return pathToFile.getName().endsWith(".txt");
			}
}

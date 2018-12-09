package documentRetrieval;

import java.io.File;
import java.io.FileFilter;


public class FileSelector implements FileFilter {
		public boolean accept(File pathToFile) {
			
				return pathToFile.getName().endsWith(".txt");
		}
}

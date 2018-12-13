package documentRetrieval;

import java.io.*;
import java.nio.file.Paths;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class Indexer {
	
	private IndexWriter Iwriter;
	//String pathtoData;
	public Indexer(String pathToIndex) throws IOException
	{
		
		IndexWriterConfig indexWriterConfig= new IndexWriterConfig(new StandardAnalyzer());
		Directory indexDirectory=FSDirectory.open(Paths.get(pathToIndex));
		Iwriter=new IndexWriter(indexDirectory,indexWriterConfig);
	}
	void close() throws CorruptIndexException, IOException	
	{
		Iwriter.close();
	}
	// Creating the Document for Lucene to understand with fields and returning to it to the caller
	private Document getDocument(File newfile) throws IOException {
		Document document;
		document = new Document();
		TextField content;
		Field fileName;
		Field filePath;
		if(newfile.getName().endsWith(".htm") || newfile.getName().endsWith(".html"))
		{
			
			System.out.println("File type Html : "+newfile.getCanonicalPath());
			File newHtmltoTxtFile = htmlParser.htmltotxt(newfile);
			getDocument(newHtmltoTxtFile);
									
		}
     		content = new TextField(GlobalConstants.CONTENTS,new FileReader(newfile));
			fileName = new StringField(GlobalConstants.FILE_NAME,newfile.getName(),Field.Store.YES);
			filePath = new StringField(GlobalConstants.FILE_PATH,newfile.getCanonicalPath(),Field.Store.YES);
			document.add(content);
			document.add(fileName);
			document.add(filePath);
		return document;
	}
	private void fileIndexer(File file) throws IOException{
		System.out.println("Indexing File :"+file.getCanonicalPath());
		Document doc=getDocument(file);
		Iwriter.addDocument(doc);
	}
		
	public int createIndexDirectory(String dataDirPath,	FileFilter filter) throws IOException{
		//pathtoData = dataDirPath;
		File[] files = new File(dataDirPath).listFiles();
		for(File fileIterator : files) {
			if(!fileIterator.isHidden() && fileIterator.exists() && fileIterator.canRead() && filter.accept(fileIterator)) {
				fileIndexer(fileIterator);
			}
			if(fileIterator.isDirectory())
			{
				createIndexDirectory(fileIterator.getAbsolutePath(), filter);
			}
		}
		return Iwriter.numDocs();
	}

	
}

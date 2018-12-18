package documentRetrieval;

import java.io.IOException;	

import java.nio.file.Path;

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
// import org.tartarus.snowball.ext.PorterStemmer;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.document.Document;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	// Setting up objects for visible use in Class
	IndexSearcher newIndexSearcher;
	QueryParser newQueryParser;
	static Query newQuery;
	EnglishAnalyzer newAnalyzer = new EnglishAnalyzer();   // automatically stems the Query with PorterStemmer
 
 
 	// setting up constructor wit a path to index
 	public Searcher(Path pathToIDX) throws IOException
 	{
 		IndexReader idxDir = DirectoryReader.open(FSDirectory.open(pathToIDX));
 		newIndexSearcher = new IndexSearcher(idxDir);
 		if(InputModulator.rankflag == 1) {
 			newIndexSearcher.setSimilarity(new BM25Similarity());
 		}
 		newQueryParser = new QueryParser(GlobalConstants.CONTENTS,newAnalyzer);
 	}
 	
 	// Searching the Document
 	public TopDocs search(String searchQuery) throws IOException
 	{
 		//String sample=null;
 		try {
 			newQuery = newQueryParser.parse(searchQuery);
 			/*  the below Returns the sink of the instance of the EnglishAnalyzer using the protected createComponents() 
 			 method. Can be used to verify stemming , stop words */ 
 			// System.out.println(newAnalyzer.tokenStream(sample, searchQuery));
 			}
		catch (ParseException e) { e.printStackTrace(); }
 		return newIndexSearcher.search(newQuery, GlobalConstants.MAX_SEARCH);
 	}	
 	
 	
 	// Scoring the document
 	public Document documentGetter(ScoreDoc newScore) throws CorruptIndexException, IOException
 	{
 		return newIndexSearcher.doc(newScore.doc);
 	}
 	    
}

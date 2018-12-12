/**
 * 
 * /**
 * @author Kirann Bhavaraju
 *
 */

package documentRetrieval;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.nio.file.Path;

//import java.util.*;
@SuppressWarnings("serial")
class InvalidInputException extends Exception
{
	public String toString()
	{
		//System.err.println("Please enter VS/OK as third Argument for ranking Scheme. Type -h or -- help fpr more details");
		//System.exit(1);
		return "Please enter VS/OK as third Argument for ranking Scheme. Type -h or -- help for more information";
	}
}

// Throw exceptions at command line 
// Throw help Request
public class InputModulator {
	static Path indexPathEntered=null;
	static Path docsPathEntered=null;
	public static void setRankingScheme(String identifier)  throws InvalidInputException
	{
		if(identifier.contentEquals("VS"))
		{
			// Ranking Scheme code goes here
		}
		else if (identifier.contentEquals("OK"))
		{
			// Ranking Scheme code goes here
		}
		else
		{
			throw new InvalidInputException();
		}
	}
	
	
	public static void indexCreator() throws IOException
	{
		Indexer newIndexer= new Indexer(indexPathEntered.toString());
		int numberofIndexedFiles=0;
		Long timeatStart = System.currentTimeMillis();
		numberofIndexedFiles = newIndexer.createIndexDirectory(docsPathEntered.toString(), new FileSelector());
		Long timeatEnd = System.currentTimeMillis();
		newIndexer.close();
		System.out.println(+numberofIndexedFiles+"Files Indexed, Time Taken : "+(timeatEnd-timeatStart)+"milli seconds");	
	}
	
	public static void searcher(String searchQueryEntered) throws IOException, ParseException
	{
		Searcher newSearcher = new Searcher(indexPathEntered);
		Long timeatStart = System.currentTimeMillis();
		TopDocs noOfHits = newSearcher.search(searchQueryEntered);
		Long timeatEnd = System.currentTimeMillis();
		System.out.println(+noOfHits.totalHits+"documents Searched, Time Taken : "+(timeatEnd-timeatStart)+"milli seconds");
		
		for(ScoreDoc scorer : noOfHits.scoreDocs)
		{
			Document document = newSearcher.documentGetter(scorer);
			System.out.println("File Found "+document.get(GlobalConstants.FILE_PATH));
		}
	}
	
	
	public static void main(String[] args) throws InvalidInputException {
		
		for(int index=0; index<args.length; index++) {
			if(args[index].contentEquals("--help") || args[index].contentEquals("-h"))
				{
				System.out.println("DocumentRetrieval System Versioning 1.0");
				System.out.println("Argument 1 : Path to documents Forlder");
				System.out.println("Argument 2 : Path to the index Folder");
				System.out.println("Argument 3 : Set Ranking Scheme, Vector Space Mode (VS) or OKAPI25 (OK)");
				System.out.println("Argument 4 : Search Query");
				System.out.println("java -jar IR P.jar [path to document folder] [path to index folder] [VS/OK] [query]");
				System.exit(1);
				}
		}

		indexPathEntered = Paths.get(args[0]) ;
		docsPathEntered= Paths.get(args[1]);
		setRankingScheme(args[2]);
		String query=null;
		for(int index=3; index<args.length; index++)
		{
			query = query + " " + args[index];
		}
		//System.out.printf(" %s \n %s \n %s \n %s",indexPathEntered, docsPathEntered, args[2], query);	
		try { 
			InputModulator.indexCreator(); } catch(IOException e) { e.printStackTrace(); }
		try {
			InputModulator.searcher(query); } catch (IOException e) {e.printStackTrace(); } 
											  catch(ParseException e) { e.printStackTrace(); }
		}
	}


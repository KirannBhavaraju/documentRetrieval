/**
 * 
 * /**
 * @author Kirann Bhavaraju
 *
 */

package documentRetrieval;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;


//import java.util.*;
@SuppressWarnings("serial")
class InvalidInputException extends Exception
{
	public String toString()
	{
		//System.err.println("Please enter VS/OK as third Argument for ranking Scheme. Type -h or -- help for more details");
		//System.exit(1);
		return "Please enter VS/OK as third Argument for ranking Scheme. Type -h or -- help for more information";
	}
}

// Throw exceptions at command line 
// Throw help Request
public class InputModulator {
	static Path indexPathEntered=null;
	static Path docsPathEntered=null;
	public static int rankflag = 0;
	
	public static void setRankingScheme(String identifier)  throws InvalidInputException
	{
		if(identifier.contentEquals("VS"))
		{
			System.out.println("The Ranking Scheme has been set to Vector Space");
			System.out.println("\n");
		}
		else if (identifier.contentEquals("OK"))
		{
			rankflag =1;
			System.out.println("The Ranking Scheme has been set to Okapi Best Match 25");
			System.out.println("\n");
		}
		else
		{
			throw new InvalidInputException();
		}
	}
	
	public static char indexDirectoryCheck() throws IOException
	{
		char flag = '\0';
		if(Files.list(Paths.get(indexPathEntered.toString())).findAny().isPresent())
		{
			System.out.printf("\n \n ------- \n");
			System.out.println("Index Directory is not empty");
			System.out.printf("\n \n ------- \n");
			System.out.println("Press Y to use the existing Index and skip indexing");
			System.out.println("Press E to empty the existing index Directory and start new Indexing");
			System.out.println("\n");
			System.out.println("Waiting for input -- ");
			Scanner newScanner = new Scanner(System.in);
			flag = newScanner.next().charAt(0);
			newScanner.close();
			if (flag == 'E' || flag == 'e')
			{
				File directory = new File(indexPathEntered.toString());
				File[] files = directory.listFiles();
				for(File afile : files)
				{
					 afile.delete();
				}
			}
			else if (flag == 'y' || flag == 'Y')
			{
				return flag;			
			}
			else
			{
				System.out.println("Please Re-run the application with valid input, type in --help or -h for help");
				System.exit(1);
			}
		}
		return flag;
	}
	
	public static void indexCreator() throws IOException
	{
		Indexer newIndexer= new Indexer(indexPathEntered.toString());
		int numberofIndexedFiles=0;
		Long timeatStart = System.currentTimeMillis();
		numberofIndexedFiles = newIndexer.createIndexDirectory(docsPathEntered.toString(), new FileSelector());
		Long timeatEnd = System.currentTimeMillis();
		newIndexer.close();
		System.out.println("\n");
		System.out.println(+numberofIndexedFiles+"Files Indexed, Time Taken : "+(timeatEnd-timeatStart)+"milli seconds");
		System.out.println("\n");
	}
	
	public static void searcher(String searchQueryEntered) throws IOException, ParseException
	{
		Searcher newSearcher = new Searcher(indexPathEntered);
		Long timeatStart = System.currentTimeMillis();
		TopDocs noOfHits = newSearcher.search(searchQueryEntered);
		Long timeatEnd = System.currentTimeMillis();
		System.out.println("\n");
		System.out.println(+noOfHits.totalHits+"documents Searched, Time Taken : "+(timeatEnd-timeatStart)+"milli seconds");
		System.out.println("\n");
		int n=0;
		for(ScoreDoc scorer : noOfHits.scoreDocs)
		{
			n += 1;
			Document document = newSearcher.documentGetter(scorer);
			System.out.println("File Found : ");
			System.out.println("Printing Details");
			System.out.println("Name : " +document.get(GlobalConstants.FILE_NAME));
			System.out.println("Path : " +document.get(GlobalConstants.FILE_PATH));
			if(document.get(GlobalConstants.FILE_PATH).endsWith(".html"))
			{
				String temp = document.get(GlobalConstants.FILE_PATH);
				htmlParser.printTitle(temp);
			}
			Float score = scorer.score;
			int DocId = scorer.doc;
			System.out.println("Rank : " +n);
			System.out.println("Relevance Score : " +score);
			System.out.println("Document Id : " +DocId);
			System.out.println("\n");
		}
	}
	
	
	public static void main(String[] args) throws InvalidInputException , IOException {
		
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
		
		char indexflag = indexDirectoryCheck();    // might throw IO Exception which is un-handled hence, modificatiion to the main
		if(indexflag != 'Y' && indexflag != 'y')
		{
		try { 
			InputModulator.indexCreator(); } catch(IOException e) { e.printStackTrace(); }
		}
		try {
			InputModulator.searcher(query); } catch (IOException e) {e.printStackTrace(); } 
											  catch(ParseException e) { e.printStackTrace(); }
		}
	}


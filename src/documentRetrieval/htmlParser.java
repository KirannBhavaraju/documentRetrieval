package documentRetrieval;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import java.io.BufferedWriter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;


public class htmlParser {
	
	public static void printTitle(String searchedfilePath) throws IOException
	{
		//System.out.println(searchedfilePath);
		File tempFile = new File(searchedfilePath);
		Document jdoctemp = Jsoup.parse(tempFile, "UTF-8");
		System.out.println("Title : "+jdoctemp.title());
	}
	
	//private static final String UTF16;
	private static String createFileName(Path pathEntered, File file)
	{
		String name=null;
		name = pathEntered.toString() + "\\.\\" +file.getName().toString()+".txt" ;
		//System.out.println(Paths.get(name).toString());
		return Paths.get(name).toString();
	}
	public static File htmltotxt(File file) throws IOException
	{
		// creating temp variables for filename 
				Path docsPath = InputModulator.docsPathEntered;
				String filename = createFileName(docsPath, file);
		
		Document newHtlmDoc = Jsoup.parse(file,"UTF-8");
		Elements titles = newHtlmDoc.getElementsByTag("title");
		
		//Elements paragraphs = newHtlmDoc.getElementsByTag("p");
		Elements body = newHtlmDoc.getElementsByTag("body");
		BufferedWriter newWriter;
		try
		{
			newWriter = new BufferedWriter(new FileWriter(filename));
			//newWriter.write(newHtlmDoc.toString()); // use for writing the enire Html page
			/* for(Element para: paragraphs)			// used Explicitly to write the para Element
			{
				newWriter.write(para.text());
			}*/
			for (Element title : titles)             // used to explicitly write the title to document
			{
				newWriter.write(title.text());
			}
			for (Element b : body)             // used to explicitly write the title to document
			{
				newWriter.write(b.text());
			}
			newWriter.close();
		}
		catch ( IOException e )
		{
			System.out.println("Exception while parsing html as txt");
			e.printStackTrace();
		}
		
		return new File(filename);
	}
}

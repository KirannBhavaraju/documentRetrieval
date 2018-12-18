# document-Retrieval

PA Custom (command-line based) Information Retrieval system using Apache 
Lucene 7.5.0 and Java. Lucene is 
an open source search library that provides standard functionality for analyzing, indexing, 
and searching text-based documents. The following criteria have to be met by your 
Information Retrieval system: 
Done • Using Lucene, parse and index Plain Text and HTML documents that a given folder 
and its subfolders contain. List all parsed files. 
Done • Consider the English language and use a stemmer for it (e.g. Porter Stemmer). 
Done • Select an available search index or create a new one (if not available in the chosen 
directory). 
Done • Make possible for the user to choose the ranking model, Vector Space Model (VS) 
or Okapi BM25 (OK). 
Done • Print a ranked list of relevant articles given a search query. The output should 
contain the 10 most relevant documents with their rank, relevance score, file name 
resp. title (if it is a HTML-file containing title information) and the path. 
Done • Search multiple fields concurrently (multifield search): not only search the 
document’s text (body tag), but also its title. 
Done The program should be written in a way that it is runnable without any corrections or 
modifications of the java runtime environment or source code! Create a jar-File named 
IR P.jar. It should process the input: 

java -jar IR P.jar [path to document folder] [path to index folder] [VS/OK] [query] 

Help Section
DocumentRetrieval System Versioning 1.0
Argument 1 : Path to documents Forlder Please Enter as String in Double-Quotes
Argument 2 : Path to the index Folder Please Enter as String in Double-Quotes
Argument 3 : Set Ranking Scheme, Vector Space Mode (VS) or OKAPI25 (OK)
Argument 4 : Search Query
run as : java -jar IR P.jar [path to document folder] [path to index folder] [VS/OK] [query]

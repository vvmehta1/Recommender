package com.support.docs;

import java.io.File;
import java.util.HashMap;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.recommender.common.model.Keyword_Format;

//@Configuration
//@PropertySource("classpath:location.properties")
public class Preprocessing {

	
	
	public static HashMap<String, Keyword_Format> setupPreprocesses(PropertiesFile propertyfile) throws Exception{
		// TODO Auto-generated method stub
		
		
		PropertiesFile filelocations = propertyfile;
		// Preprocessing Started
		System.out.println("Preprocessing Started.");
		
		// Step 1: Read csv file to generate list of documents
		//Read_generateCorpus.readCSV_getCORPUS(filelocations);
		
		System.out.println("Step: 1 generate corpus done.");
		
		// Step 2: Append Concepts
		//AppendConcepts.appendConcept(filelocations);
		System.out.println("Step: 2 Appending concepts done.");
		
		// Step 3: Stem all the Files
		try{
			//Stem_All_Files_under_Dir.stem_all_files_in_directory(filelocations);
        }
        catch(Throwable e)
        {
        	e.printStackTrace();
        }
		
		System.out.println("Step: 3 Stemming done.");
		
		// Step 4: Filter into separate question & answers
		// Potential place to store in db
		//FilterStemmed.filter_docs(filelocations);
		System.out.println("Step: 4 Filtering done.");
		
		//Generate concepts from text
		
		
		
		// Step 5: Create Index Code
		//CallIndexFiles_Code.setupIndex(filelocations);
		System.out.println("Step: 5 Indexing (Code) done.");
		
		// Step 6: Create Index Text
		//CallIndexFiles_Text.setupIndex(filelocations);
		System.out.println("Step: 6 Indexing (Text) done.");
		
		// Pre-calculate document Norm: Code 
		
		IndexReader t = IndexReader.open(FSDirectory.open(new File(Preprocessing.class.getClassLoader().getResource(filelocations.getBaseUrl()+filelocations.getIndexC()).toURI())));
		Recommender.precalculate_doc_norm(t, "code");
		System.out.println("Code document norm calculation complete");
		
		// Pre-calculate Document Norm: Text 
		IndexReader s = IndexReader.open(FSDirectory.open(new File(Preprocessing.class.getClassLoader().getResource(filelocations.getBaseUrl()+filelocations.getIndexT()).toURI())));
		Recommender.precalculate_doc_norm(s, "text");
		System.out.println("Text document norm calculation complete");
		
		// Generate Keywords for each document
		HashMap<String, Keyword_Format> toController = KeywordExtract.GetConceptsForDocuments(filelocations);
		System.out.println("Concepts Extracted from Documents");
		
		//Preprocessing Completed. 
		System.out.println("Preprocessing completed. ");
		
		
		return toController;
	
	}


}

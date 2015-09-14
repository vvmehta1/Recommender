package com.support.docs;

import java.io.File;
import java.util.HashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.store.FSDirectory;

import com.recommender.common.model.Recommended_Questions;
import com.stemming.support.SnowballStemmer;



public class Recommender {
	
	static double[] docsText;
	static double[] docsCode;

	public static Recommended_Questions[] getResults(String selectIndex, String userinput, PropertiesFile file) throws Exception {
		// TODO Auto-generated method stub
		//readCSV_generatecorups();
		IndexReader r=null;
		//IndexReader r = IndexReader.open(FSDirectory.open(new File("C:\\Users\\Vishal\\Desktop\\IR Thesis\\new Dataset\\Index_Que\\")));
		//IndexReader r = IndexReader.open(FSDirectory.open(new File("C:\\Users\\Vishal\\Desktop\\IR Thesis\\new Dataset\\Index_Ans\\")));
		if(selectIndex.equals("code"))
			r = IndexReader.open(FSDirectory.open(new File(Recommender.class.getClassLoader().getResource(file.getBaseUrl()+file.getIndexC()+"\\").toURI())));
		else if(selectIndex.equals("text"))
			r = IndexReader.open(FSDirectory.open(new File(Recommender.class.getClassLoader().getResource(file.getBaseUrl()+file.getIndexT()+"\\").toURI())));
		else{
			System.out.println("Error in Index Selection");
			return null;
		}
		
		//IndexReader r = IndexReader.open(FSDirectory.open(new File("index")));
		System.out.println("The number of documents in this index is: " + r.maxDoc());
		//String isbn = r.document(12).getFieldable("ISBN").toString();
		//System.out.println(isbn);
		///*
		//String code = r.document(12).getFieldable("CODE").toString();
		//System.out.println(code);
		Recommended_Questions[] results_controller;
		results_controller = TF_IDF(r, selectIndex, userinput);
		return results_controller;
		
	}
	public static void precalculate_doc_norm(IndexReader r, String indexselection) throws Exception{
		
		double[] docs = new double[r.maxDoc()];
		
		TermEnum allterms = r.terms();
		
		while(allterms.next())
		{
			//Code to find all the docs containing this term
			Term one_term = null;
			if(indexselection.equals("code"))
				one_term = new Term("CONCEPTS", allterms.term().text());
			else if(indexselection.equals("text"))
				one_term = new Term("contents", allterms.term().text());
			
			//System.out.println("Number of documents with the word "+ allterms.term().text() +" is: " + r.docFreq(one_term));
			if(r.docFreq(one_term) > 0)
			{
				TermDocs all_docs_containing_term = r.termDocs(one_term);
				
				while(all_docs_containing_term.next())
				{
					Document d = r.document(all_docs_containing_term.doc());
					//System.out.println(d.get("path"));
					//System.out.println(d.get("ISBN"));
					String Isbn = d.get("ISBN");
					//String Isbn = r.document(all_docs_containing_term.doc()).getFieldable("ISBN").toString();
					int isbn = Integer.parseInt(Isbn);
					//System.out.println(d.get("ISBN"));
					
					//all_docs_containing_term.
					//System.out.println("Document number ["+tdd.doc()+"] contains the term "+ t.term().text()+" ; "+ tdd.freq() + " time(s).");
					//docs[tdd.doc()] = docs[tdd.doc()] + (tdd.freq()*tdd.freq());//TF
					int total_docs = r.maxDoc();
					docs[isbn] = docs[isbn] + (all_docs_containing_term.freq()* (Math.log(total_docs/r.docFreq(one_term))))*(all_docs_containing_term.freq()* (Math.log(total_docs/r.docFreq(one_term))));// TF-IDF
					
				
				}
			}
				
		}
		
		int v;
		for (v=0; v<r.maxDoc(); v++)
		{
			docs[v]=Math.sqrt(docs[v]);
			//System.out.println("Total Square: " + docs[v]);
		}
		System.out.println(docs.length);
		if(indexselection.equals("code"))
			docsCode = docs;
		else if(indexselection.equals("text"))
			docsText = docs;
		
		//return docs;
		
	}
	public static Recommended_Questions[] TF_IDF(IndexReader r, String indexselection, String userquery) throws Exception{
		
		//double[] docs = new double[r.maxDoc()];
		
		//docs = precalculate_doc_norm(r);
		
		//Scanner sc = new Scanner(System.in);
		String str = userquery;
		//Take value of Code Priority in Results
		//Scanner inputC = new Scanner(System.in);
		//String inputCval = "";
		//int CodePriority = 0;
		//System.out.print("Enter the Code Similarity Priority Value (0 to 100): ");
		//inputCval = inputC.nextLine();
		
		try{
			//CodePriority = Integer.parseInt(inputCval);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Bad Input. Re-enter Code Similarity Value");
			System.exit(1);
			
		}
		//inputC.close();
		
		//System.out.print("query> ");
		//while(!(str = sc.nextLine()).equals("quit")) // This
		//{
			int noofQterms=0;
			String[] terms = str.split("\\s+");
			for(String wordd : terms)
			{
				noofQterms++;
			}
			double q = Math.sqrt((double)noofQterms);
			System.out.println("\nThe value of Q is: "+ q );
			HashMap<Integer,Double> results = new HashMap<Integer,Double>();
			for(String word : terms)
			{
				Class stemmmClass = Class.forName("com.stemming.support.englishStemmer");
				SnowballStemmer stemmerr = (SnowballStemmer) stemmmClass.newInstance();
			    stemmerr.setCurrent(word);
			    stemmerr.stem();
			    word = stemmerr.getCurrent();
			    System.out.println("Stemmed word is: "+word);
				System.out.println("\nSearching for Word: "+word+"\n");
				
				Term term = null;
				if(indexselection.equals("code"))
					term = new Term("CONCEPTS", word);
				else if(indexselection.equals("text"))
					term = new Term("contents", word);
				else
				{
					System.out.println("Error in Index selection");
					return null;
				}
				
				TermDocs docs_with_term = r.termDocs(term);
				System.out.println("Number of documents with the word "+ word +" is: " + r.docFreq(term));
				
				while(docs_with_term.next())
				{
					// New Logic here Using Hashmap
					
					// Check if key is present in Hashmap
					Document retrieve_doc = r.document(docs_with_term.doc()); //Extract key from here; not for time being
					
					String Isbn = retrieve_doc.get("ISBN");
					
					//String codestat = retrieve_doc.get("CODE");
					//codestat = codestat.trim();
					
					
					/*int codesim = 0;
					if(CodePriority == 50)
					{
						codesim = 0;
					}
					else if (CodePriority <50)
					{
						if(codestat.equals("NULL"))
						{
							codesim = 1;
						}
						else if (codestat.equals("PRESENT"))
						{
							codesim = 0;
						}
					}
					else if (CodePriority >50)
					{
						if(codestat.equals("NULL"))
						{
							codesim = 0;
						}
						else if (codestat.equals("PRESENT"))
						{
							codesim = 1;
						}
					}*/
					
					if(results.containsKey(docs_with_term.doc())) // If Key is Present
					{
						Double similarity = ((Double)results.get(docs_with_term.doc())).doubleValue();
						if(indexselection.equals("code"))
							similarity += ((docs_with_term.freq() * Math.log(r.maxDoc()/r.docFreq(term)))/ ( q * docsCode[Integer.parseInt(Isbn)]));//IDF  + (CodePriority/100)*codesim
						else if(indexselection.equals("text"))
							similarity += ((docs_with_term.freq() * Math.log(r.maxDoc()/r.docFreq(term)))/ ( q * docsText[Integer.parseInt(Isbn)]));
						//similarity += ((docs_with_term.freq() * Math.log(r.maxDoc()/r.docFreq(term)))/ ( q * docs[Integer.parseInt(Isbn)]));//IDF  + (CodePriority/100)*codesim
						results.put(docs_with_term.doc(),similarity);
					}
					else
					{
						Double similarity = 0.0;
						if(indexselection.equals("code"))
							similarity = ((docs_with_term.freq() * Math.log(r.maxDoc()/r.docFreq(term)))/ ( q * docsCode[Integer.parseInt(Isbn)]));
						else if(indexselection.equals("text"))
							similarity = ((docs_with_term.freq() * Math.log(r.maxDoc()/r.docFreq(term)))/ ( q * docsText[Integer.parseInt(Isbn)]));
						
						//Double similarity = ((docs_with_term.freq() * Math.log(r.maxDoc()/r.docFreq(term)))/ ( q * docs[Integer.parseInt(Isbn)]));//IDF  + (CodePriority/100)*codesim
						results.put(docs_with_term.doc(),similarity);
					}
					

				}
			}
			// Display Results here -- Change this 
			double[][] displayresults = new double[results.size()][2];
			
			
		      int values = 0;
		      for (Integer key : results.keySet()) {
		    	  displayresults[values][0] = key;
		    	  displayresults[values][1] = results.get(key);
		    	  values++;
		      }
		      
		      // Remember to empty the hashmap and results array after doing this operation
		      results.clear();
		      // Now sort the results
		    //Code to sort here
		      displayresults = sort_results(displayresults, values);
		      Recommended_Questions[] sendview;
		      sendview = generateResultList(displayresults, values, r);
		      display_results(displayresults, values, r);
		      
			//System.out.print("query> ");
		//}// This
		//sc.close();
		      return sendview;
	
	}
	public static Recommended_Questions[] generateResultList(double[][] displayresults, int values, IndexReader r) throws Exception{
		int count;
		if(displayresults.length > 20)
			count = 20;
		else
			count = displayresults.length;
		
		Recommended_Questions [] result = new Recommended_Questions[count];
		
		for(int p=0; p<count; p++)
		{
	  		Double doc_no = displayresults[p][0];
	  		int doc_num = doc_no.intValue();
	  		Document retrieve_doc = r.document(doc_num); 
	  		String Filename = retrieve_doc.get("path");
	  		Recommended_Questions result_store = new Recommended_Questions();
	  		result_store.setQuestion_no(Filename);
	  		result_store.setSimilarity(displayresults[p][1]);
	  		result[p] = result_store;
			//System.out.println(Filename+" has Similarity of : "+displayresults[p][1]+ " Code: "+retrieve_doc.get("CODE"));
			//System.out.println("Code: "+retrieve_doc.get("CODE"));
		}
		
		
		return result;
		
	}
	
	public static void display_results(double[][] displayresults, int values, IndexReader r) throws Exception{
		
		System.out.println("Showing Top 20 Results: ");
	      if(values < 20)
	      {
	      for(int p=0; p<values; p++)
				{
	    	  		Double doc_no = displayresults[p][0];
	    	  		int doc_num = doc_no.intValue();
	    	  		Document retrieve_doc = r.document(doc_num); 
	    	  		String Filename = retrieve_doc.get("path");
					System.out.println(Filename+" has Similarity of : "+displayresults[p][1]+ " Code: "+retrieve_doc.get("CODE"));
					//System.out.println("Code: "+retrieve_doc.get("CODE"));
				}
	      }
	      else
	      {
	    	  for(int p=0; p<20; p++)
				{
	    	  		Double doc_no = displayresults[p][0];
	    	  		int doc_num = doc_no.intValue();
	    	  		Document retrieve_doc = r.document(doc_num); 
	    	  		String Filename = retrieve_doc.get("path");
					System.out.println(Filename+" has Similarity of : "+displayresults[p][1]+" Code: "+retrieve_doc.get("CODE"));
					//System.out.println("Code: "+retrieve_doc.get("CODE"));
				}
	      }
	      
	}
	
	
	public static double[][] sort_results(double[][] displayresults, int values){
		
		for(int vi=0; vi < values; vi++)
		{
			for (int vm=vi+1; vm<values; vm++)
			{
				if(displayresults[vi][1] < displayresults[vm][1])
				{
					//swapbothelements
					double temp_docno,temp_similarity;
					temp_docno=displayresults[vi][0];
					temp_similarity=displayresults[vi][1];
					displayresults[vi][0]=displayresults[vm][0];
					displayresults[vi][1]=displayresults[vm][1];
					displayresults[vm][0]=temp_docno;
					displayresults[vm][1]=temp_similarity;
				}
			}
		}
		
		return displayresults;
		
		
	}
	
}

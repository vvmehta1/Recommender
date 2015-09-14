package com.support.docs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

import com.recommender.common.model.Keyword_Format;
import com.stemming.support.SnowballStemmer;

public class KeywordExtract {
	
	static HashMap<String, Double> stopwords; 
	static HashMap<String, Keyword_Format> documentwords;
	static Vector<String> javaWords;
	
	static HashMap<String, Double> keywordsextract;
	
	public static HashMap<String, Keyword_Format> GetConceptsForDocuments(PropertiesFile file) throws Exception{
		// TODO Auto-generated method stub
		final String docsPath = file.getBaseUrl()+file.getQue()+"\\";
		javaWords = javaGlossary();
		stopwords = addwords();
		keywordsextract = generateKeywords(docsPath, file);
		documentwords = getDocumentConcepts(docsPath);
		
		return documentwords;
	}
	
	
	static HashMap<String, Double> addwords()
	{
		HashMap<String, Double> commonwords = new HashMap<String, Double>();
		commonwords.put("you", 1.0);
		commonwords.put("have", 1.0);
		commonwords.put("the", 1.0);
		commonwords.put("are", 1.0);
		commonwords.put("when", 1.0);
		commonwords.put("want", 1.0);
		commonwords.put("way", 1.0);
		commonwords.put("can", 1.0);
		commonwords.put("what", 1.0);
		commonwords.put("how", 1.0);
		commonwords.put("use", 1.0);
		// get use
		commonwords.put("here", 1.0);
		commonwords.put("java", 1.0);
		
		
		return commonwords;
	}
	
	/**
	 * Code to write to file has been commented to speed up execution. 
	 * Uncomment for analysis 
	 * @throws Exception
	 */
	static HashMap<String, Keyword_Format> getDocumentConcepts(String docsPath) throws Exception{
		
		HashMap<String, Keyword_Format> docwords = new HashMap<String, Keyword_Format>();
		
		BufferedReader br = null;
		final File docDir = new File(KeywordExtract.class.getClassLoader().getResource(docsPath).toURI());
	    if (!docDir.exists() || !docDir.canRead()) {
	    	
	      System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
	      System.exit(1);
	    }
	    
	    //File file = new File("C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\DocInfo.txt");
	    
	    if (docDir.canRead()) {
		      if (docDir.isDirectory()) {
		    	  
		    	  String[] files = docDir.list();
		    	  
		    	  if (files != null) {
		    		  
		    		  /*if (!file.exists()) {
      		    		file.createNewFile();
      		    	}
		        	FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
      				BufferedWriter bw = new BufferedWriter(fw);*/
      				
      				StringBuffer input = new StringBuffer();
			          for (int i = 0; i < files.length; i++) {
			        		
			        	  //bw.write("\n "+ files[i]+ ": ");
			            
			        	  HashMap<String, Double> dockeywords = new HashMap<String,Double>();
			        	  String keyword = null;
			        	  String displayword1=null,displayword2=null,displayword3=null;
			        	  double keycount = 0.0;
			        	  double displaycount1 = 0.0;
			        	  double displaycount2 = 0.0;
			        	  double displaycount3 = 0.0;
			        	  
			        	  try {
			        	  
			        	  String sCurrentLine;
			        	  
			        	  br = new BufferedReader(new FileReader(new File(KeywordExtract.class.getClassLoader().getResource(docsPath+files[i]).toURI())));
			        	  
			        	 // Logic here
			        	  while ((sCurrentLine = br.readLine()) != null) {
				  				
			        		  String[] keywordtext = sCurrentLine.split(",");
			        		  if(keywordtext.length > 0)
			        		  {
			        			  
			        			  
			        			  String extractwords = keywordtext[2];
			        			  
			        			  for(int itr = 0; itr < extractwords.length(); itr++)
			        			  {
			        				  char ch = (char) extractwords.charAt(itr);
			        				  if (Character.isWhitespace((char) ch)) {
			        				  		if (input.length() > 0) {
			        				  			
			        				  				String checkword = input.toString().trim();
			        				  				if(keywordsextract.containsKey(checkword))
			      			        			  {
			      			        				  if(dockeywords.containsKey(checkword))
			      			        				  {
			      			        					  double count = dockeywords.get(checkword);
			      			        					  count++;
			      			        					  if(javaWords.contains(checkword))
			      			        					  {
			      			        						count++;
			      			        					  }
			      			        					  dockeywords.put(checkword, count);
			      			        					  
			      			        				  }
			      			        				  else
			      			        				{
			      			        					if(javaWords.contains(checkword))
			      			        					{
			      			        						dockeywords.put(checkword, 2.0);
			      			        					}
			      			        					else
			      			        					  dockeywords.put(checkword, 1.0);
			      			        				}
			      			        			  }
			        				  				
			        				  				
			        				  				input.delete(0, input.length());
			        				  		    
			        				  			
			        				  		}
			        				  	    } else {
			        				  	    		input.append(Character.toLowerCase(ch));
			        				  	    }
			        				  
			        			  }
			        			  
			        			  // below code
			        			
			        			  
			        		  }
			        	  }
			        	 
			        	  
			        	  
			        	  }
			        	  catch (IOException e) {
			      			e.printStackTrace();
			      		} finally {
			      			try {
			      				if (br != null)br.close();
			      			} catch (IOException ex) {
			      				ex.printStackTrace();
			      			}
			      		}  
			        	 
			        	  for(String name : dockeywords.keySet())
			        	  {
			        		  //bw.write(" W: "+ name + " , C: " + dockeywords.get(name)+ " -->");
			        		  double current = dockeywords.get(name) * keywordsextract.get(name);
			        		  if(current > keycount)
			        		  {
			        			  displayword3 = displayword2;
			        			  displaycount3 = displaycount2;
			        			  
			        			  displayword2 = displayword1;
			        			  displaycount2 = displaycount1;
			        			  
			        			  displayword1 = keyword;
			        			  displaycount1 = keycount;
			        			  
			        			  keycount = current;
			        			  keyword = name; 
			        		  }
			        		  else if(current > displaycount1)
			        		  {
			        			  displayword3 = displayword2;
			        			  displaycount3 = displaycount2;
			        			  
			        			  displayword2 = displayword1;
			        			  displaycount2 = displaycount1;
			        			  
			        			  displayword1 = name;
			        			  displaycount1 = current;
			        			  
			        		  }
			        		  else if(current > displaycount2)
			        		  {
			        			  displayword3 = displayword2;
			        			  displaycount3 = displaycount2;
			        			  
			        			  displayword2 = name;
			        			  displaycount2 = current;
			        		  }
			        		  else if(current > displaycount3)
			        		  {
			        			  displayword3 = name;
			        			  displaycount3 = current;
			        		  }
			        	  }
			        	  //bw.write(" W: "+ keyword + " , C: " + keycount+ " -->");
			        	  // Put inside map here
			        	  Keyword_Format putword = new Keyword_Format();
			        	  putword.setWord1(keyword);
			        	  putword.setKeycount1(keycount);
			        	  
			        	  if(displayword1 != null)
			        		  putword.setKeyword1(displayword1);
			        	  if(displayword2 != null)
			        		  putword.setKeyword2(displayword2); 
			        	  if(displayword3 != null)
			        		  putword.setKeyword3(displayword3); 
			        	  docwords.put(files[i], putword);
			        	  
			        	  
			        	  keyword = null;
			        	  keycount = 0.0;
			        	  displayword1 = null;
			        	  displayword2 = null;
			        	  displayword3 = null;
			        	  dockeywords.clear();
			        	  
			          }
		    		 
			          //bw.close();
			          
			        }
		    	  else
		    		  System.out.println("No Files in Directory");
		    	  
		    	  
		      }
		      else
		    	  System.out.println("Is Not a Directory");
	    }
	    else
	    	System.out.println("Cannot Read Directory");
	    
	    System.out.println("Completed Exceution");
	    return docwords;
	    
	}
	
	
	
	
	static HashMap<String, Double> generateKeywords(String docsPath, PropertiesFile file) throws Exception
	{
		HashMap<String, Double> keywords = new HashMap<String, Double>();
		
		
		BufferedReader br = null;
		final File docDir = new File(KeywordExtract.class.getClassLoader().getResource(docsPath).toURI());
	    if (!docDir.exists() || !docDir.canRead()) {
	      System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
	      System.exit(1);
	    }
	    
	    
	    if (docDir.canRead()) {
		      if (docDir.isDirectory()) {
		    	  
		    	  String[] files = docDir.list();
		    	  
		    	  if (files != null) {
			          for (int i = 0; i < files.length; i++) {  
			        	  
			        	  try {
			        	  
			        	  String sCurrentLine;
			        	  
			        	  br = new BufferedReader(new FileReader(new File(KeywordExtract.class.getClassLoader().getResource(docsPath+files[i]).toURI())));
			        	  
			        	  StringBuffer input = new StringBuffer();
			        	  
			        	  
			        	  
			        	  
			        	  while ((sCurrentLine = br.readLine()) != null) {
			  				
			        		  String[] keywordtext = sCurrentLine.split(",");
			        		  if(keywordtext.length > 0)
			        		  {
			        			  
			        			  
			        			  String extractwords = keywordtext[2];
			        			  
			        			  for(int itr = 0; itr < extractwords.length(); itr++)
			        			  {
			        				  char ch = (char) extractwords.charAt(itr);
			        				  if (Character.isWhitespace((char) ch)) {
			        				  		if (input.length() > 2) {
			        				  			  
			        				  				String checkword = input.toString().trim();
			        				  				
			        				  				if( keywords.containsKey(checkword))
			  			        				  {
			  			        					  double count = keywords.get(checkword);
			  			        					  count++;
			  			        					  keywords.put(checkword, count);
			  			        				  }
			  			        				  else if(!stopwords.containsKey(checkword))
			  			        					  keywords.put(checkword, 1.0);
			        				  				
			        				  				
			        				  				input.delete(0, input.length());
			        				  		    
			        				  			
			        				  		}
			        				  		else if(input.length() > 0)
			        				  			input.delete(0, input.length());
			        				  		
			        				  	    } else {
			        				  	    		input.append(Character.toLowerCase(ch));
			        				  	    }
			        				  
			        			  }
			        			  
			        			 
			        		  }
			        		  
			  			}
			   
			        	  
			        	  }
			        	  catch (IOException e) {
			      			e.printStackTrace();
			      		} finally {
			      			try {
			      				if (br != null)br.close();
			      			} catch (IOException ex) {
			      				ex.printStackTrace();
			      			}
			      		}  
			          }
			          //System.out.println(files.length);
		        	  //System.out.println(iter);
			          
			          
			        }
		    	  else
		    		  System.out.println("No Files in Directory");
		    	  
		    	  
		      }
		      else
		    	  System.out.println("Is Not a Directory");
	    }
	    else
	    	System.out.println("Cannot Read Directory");
	    
	    
	    try {
	    	
	    	/*File file = new File("C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\Keywords.txt");
	    	if (!file.exists()) {
	    		file.createNewFile();
	    	}
	    	FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);*/
			//C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\Index T\\
	    	IndexReader r = IndexReader.open(FSDirectory.open(new File(KeywordExtract.class.getClassLoader().getResource(file.getBaseUrl()+file.getIndexT()).toURI())));
			 int check=0;
	    	Iterator it = keywords.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<String, Double> pair = (Map.Entry<String, Double>)it.next();
	            //System.out.println(pair.getKey() + " = " + pair.getValue());
	            String word = pair.getKey();
	           
	            Term term = new Term("contents", word);
	            double count = pair.getValue();
	            
	            if(r.docFreq(term) != 0)
	            	{
	            		//count = count/r.docFreq(term);
	            		double idf = Math.log(3723.0/r.docFreq(term)); // Keep check of hardcoded constant
	            		//count = idf;
	            		double ifidf = count * idf;
	            		/*if(ifidf > 10.0)
	            			bw.write("\n"+word + " Count = " + count+" Idf: " + idf + " TfIdf: "+ifidf+"\n");
	            		else
	            			it.remove();*/
	            		if(ifidf < 10.0)
	            		{
	            			it.remove();
		            		check++;
	            		}
	            			
	            	}
	            else
	            	it.remove();
	            
	            //r.docFreq(term)
	            //it.remove(); // avoids a ConcurrentModificationException
	        }
	        System.out.println(check);
	        //bw.close();
	    	System.out.println("Done");
	    	
	    	
	    }catch (IOException e) {
			e.printStackTrace();
		}
	    return keywords;
	}
	static Vector<String> javaGlossary()
	{
		
		
			Vector<String> javawords = new Vector<String>();
			javawords.add("abstract");
			javawords.add("class");
			javawords.add("method");
			javawords.add("awt");
			javawords.add("access");
			javawords.add("control");
			javawords.add("acid");
			javawords.add("parameter");
			javawords.add("list");
			javawords.add("api");
			javawords.add("applet");
			javawords.add("argument");
			javawords.add("array");
			javawords.add("ascii");
			javawords.add("atomic");
			javawords.add("authentication");
			javawords.add("authorization");
			javawords.add("autoboxing");
			javawords.add("bean");
			javawords.add("binary");
			javawords.add("operator");
			javawords.add("bit");
			javawords.add("bitwise");
			javawords.add("block");
			javawords.add("bool");
			javawords.add("boolean");
			javawords.add("break");
			javawords.add("byte");
			javawords.add("bytecode");
			javawords.add("case");
			javawords.add("casting");
			javawords.add("catch");
			javawords.add("char");
			javawords.add("class");
			javawords.add("method");
			javawords.add("variable");
			javawords.add("classpath");
			javawords.add("client");
			javawords.add("codebase");
			javawords.add("comment");
			javawords.add("commit");
			javawords.add("compilation");
			javawords.add("compiler");
			javawords.add("unit");
			javawords.add("compositing");
			javawords.add("constructor");
			javawords.add("const");
			javawords.add("continue");
			javawords.add("conversational");
			javawords.add("state");
			javawords.add("corba");
			javawords.add("core");
			javawords.add("class");
			javawords.add("packages");
			javawords.add("credentials");
			javawords.add("critical");
			javawords.add("section");
			javawords.add("declaration");
			javawords.add("default");
			javawords.add("definition");
			javawords.add("delegation");
			javawords.add("deprecation");
			javawords.add("derived");
			javawords.add("distributed");
			javawords.add("application");
			javawords.add("do");
			javawords.add("dom");
			javawords.add("double");
			javawords.add("precision");
			javawords.add("dtd");
			javawords.add("else");
			javawords.add("embedded");
			javawords.add("java");
			javawords.add("technology");
			javawords.add("encapsulation");
			javawords.add("enum");
			javawords.add("enumerated");
			javawords.add("type");
			javawords.add("exception");
			javawords.add("handler");
			javawords.add("content");
			javawords.add("executable");
			javawords.add("extends");
			javawords.add("final");
			javawords.add("finally");
			javawords.add("float");
			javawords.add("for");
			javawords.add("ftp");
			javawords.add("parameter");
			javawords.add("list");
			javawords.add("garbage");
			javawords.add("collection");
			javawords.add("generic");
			javawords.add("goto");
			javawords.add("group");
			javawords.add("gui");
			javawords.add("hexadecimal");
			javawords.add("hierarchy");
			javawords.add("html");
			javawords.add("http");
			javawords.add("https");
			javawords.add("idl");
			javawords.add("identifier");
			javawords.add("iiop");
			javawords.add("impersonation");
			javawords.add("if");
			javawords.add("implements");
			javawords.add("import");
			javawords.add("inheritance");
			javawords.add("instance");
			javawords.add("instance");
			javawords.add("hierarchy");
			javawords.add("method");
			javawords.add("variable");
			javawords.add("instanceof");
			javawords.add("int");
			javawords.add("interface");
			javawords.add("internet");
			javawords.add("interpreter");
			javawords.add("ip");
			javawords.add("jain");
			javawords.add("jar");
			javawords.add("interface");
			javawords.add("rmi");
			javawords.add("jvm");
			javawords.add("virtual");
			javawords.add("machine");
			javawords.add("javabean");
			javawords.add("javascript");
			javawords.add("jdbc");
			javawords.add("jdk");
			javawords.add("jfc");
			javawords.add("jndi");
			javawords.add("keyword");
			javawords.add("lexical");
			javawords.add("linker");
			javawords.add("literal");
			javawords.add("variable");
			javawords.add("local");
			javawords.add("long");
			javawords.add("member");
			javawords.add("method");
			javawords.add("multithreaded");
			javawords.add("native");
			javawords.add("new");
			javawords.add("null");
			javawords.add("object");
			javawords.add("design");
			javawords.add("oriented");
			javawords.add("octal");
			javawords.add("packages");
			javawords.add("orb");
			javawords.add("principal");
			javawords.add("ots");
			javawords.add("overloading");
			javawords.add("overriding");
			javawords.add("package");
			javawords.add("peer");
			javawords.add("persistence");
			javawords.add("pixel");
			javawords.add("key");
			javawords.add("primary");
			javawords.add("primitive");
			javawords.add("poa");
			javawords.add("principal");
			javawords.add("private");
			javawords.add("privilege");
			javawords.add("process");
			javawords.add("property");
			javawords.add("profiles");
			javawords.add("protected");
			javawords.add("public");
			javawords.add("raster");
			javawords.add("realm");
			javawords.add("reference");
			javawords.add("return");
			javawords.add("rollback");
			javawords.add("root");
			javawords.add("rmi");
			javawords.add("rpc");
			javawords.add("runtime");
			javawords.add("system");
			javawords.add("sandbox");
			javawords.add("sax");
			javawords.add("scope");
			javawords.add("ssl");
			javawords.add("attributes");
			javawords.add("security");
			javawords.add("context");
			javawords.add("domain");
			javawords.add("policy");
			javawords.add("serialization");
			javawords.add("security");
			javawords.add("serialization");
			javawords.add("short");
			javawords.add("precision");
			javawords.add("single");
			javawords.add("soap");
			javawords.add("sql");
			javawords.add("static");
			javawords.add("field");
			javawords.add("method");
			javawords.add("stream");
			javawords.add("subarray");
			javawords.add("subclass");
			javawords.add("subtype");
			javawords.add("superclass");
			javawords.add("super");
			javawords.add("supertype");
			javawords.add("switch");
			javawords.add("swing");
			javawords.add("synchronized");
			javawords.add("tcp");
			javawords.add("ip");
			javawords.add("tcpip");
			javawords.add("client");
			javawords.add("thin");
			javawords.add("thread");
			javawords.add("this");
			javawords.add("throws");
			javawords.add("throw");
			javawords.add("transaction");
			javawords.add("isolation");
			javawords.add("level");
			javawords.add("manager");
			javawords.add("transient");
			javawords.add("try");
			javawords.add("type");
			javawords.add("unicode");
			javawords.add("uri");
			javawords.add("url");
			javawords.add("urn");
			javawords.add("variable");
			javawords.add("machine");
			javawords.add("virtual");
			javawords.add("void");
			javawords.add("volatile");
			javawords.add("server");
			javawords.add("web");
			javawords.add("webserver");
			javawords.add("while");
			javawords.add("readable");
			javawords.add("files");
			javawords.add("world");
			javawords.add("wrapper");
			javawords.add("www");
			javawords.add("xml");
			
			Vector<String> finalwords = new Vector<String>();
			// Do the stemming here
			for(String get : javawords)
			{
				String stemedword;
				try{
				    	Class stemmmClass = Class.forName("com.stemming.support.englishStemmer");
				    	SnowballStemmer stemmerr = (SnowballStemmer) stemmmClass.newInstance();
				    	stemmerr.setCurrent(get);
				    	stemmerr.stem();
				    	stemedword = stemmerr.getCurrent();
				    	finalwords.add(stemedword);
				    }
				    catch(Exception e)
				    {
				    	e.printStackTrace();
				    }
			}
			javawords.clear();
			return finalwords;
	}
	
}

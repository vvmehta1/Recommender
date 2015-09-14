
package com.support.docs;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;

import com.stemming.support.SnowballStemmer;

public class Stem_All_Files_under_Dir {
    /*public static void main(String [] args) {
    	
        try{
        	stem_all_files_in_directory();
        }
        catch(Throwable e)
        {
        	e.printStackTrace();
        }
        
        System.out.println("Done");
        
        
        }*/
    
    
    
    
    private static void usage()
    {
        System.err.println("Usage: TestApp <algorithm> <input file> [-o <output file>]");
    }

   
    
    static void stem_all_files_in_directory(PropertiesFile file) throws Throwable
    {
    	//final static String docsPath = "C:\\Users\\Vishal\\Desktop\\IR Thesis\\new Dataset\\NoCodeAnswers";
    	//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\Documents"
    	final String docsPath = file.getBaseUrl()+file.getDocs();
        //int no_of_indexed_docs = 0;
    	
    	
    	String [] argss = new String[4];
    	argss[0] = "english";
    	argss[2] = "-o";
    	
    final File docDir = new File(docsPath);
    if (!docDir.exists() || !docDir.canRead()) {
	      System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
	      System.exit(1);
	    }
	
    if (docDir.canRead()) {
    	if (docDir.isDirectory()) {
    		String[] files = docDir.list();
	        // an IO error could occur
    		if (files != null) {
    		System.out.println("No of Files: "+files.length);
  	          for (int no_files = 0; no_files < files.length; no_files++) {
  	            //indexDocs(writer, new File(file, files[i]));
  	        	  File f = new File(files[no_files]);
  	        argss[1] = file.getBaseUrl()+file.getDocs()+"\\"+f.getPath();
  	        argss[3] = file.getBaseUrl()+file.getStemDocs()+"\\"+f.getName();
  	        	  
  	      if (argss.length < 2) {
              usage();
              return;
          }

  	Class stemClass = Class.forName("com.stemming.support." +
  					argss[0] + "Stemmer");
          SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

  	Reader reader;
  	reader = new InputStreamReader(new FileInputStream(argss[1]));
  	reader = new BufferedReader(reader);

  	StringBuffer input = new StringBuffer();

          OutputStream outstream;

  	if (argss.length > 2) {
              if (argss.length >= 4 && argss[2].equals("-o")) {
                  outstream = new FileOutputStream(argss[3]);
              } else {
                  usage();
                  return;
              }
  	} else {
  	    outstream = System.out;
  	}
  	Writer output = new OutputStreamWriter(outstream);
  	output = new BufferedWriter(output);

  	int repeat = 1;
  	if (argss.length > 4) {
  	    repeat = Integer.parseInt(argss[4]);
  	}

  	Object [] emptyArgs = new Object[0];
  	int character;
  	
  	boolean stemming = true;
  	while ((character = reader.read()) != -1) {
  	    char ch = (char) character;
  	    if (Character.isWhitespace((char) ch)) {
  		if (input.length() > 0) {
  			if(stemming)
  			{
  				String checkword = input.toString().trim();
  				if(checkword.equals("stopstemming"))
  				{
  					stemming = false;
  					input.delete(0, input.length());
  					continue;
  				}
  			
  				
  			
  				stemmer.setCurrent(input.toString());
  				for (int i = repeat; i != 0; i--) {
  					stemmer.stem();
  					}
  				output.write(" "+stemmer.getCurrent()+" ");
  				//output.write('\n'); I want to preserve the csv format of the file
  				input.delete(0, input.length());
  		    
  			}
  			else
  			{
  				String checkword = input.toString().trim();
  				if(checkword.equals("startstemming"))
  				{
  					stemming = true;
  					input.delete(0, input.length());
  					continue;
  				}
  				output.write(" "+input.toString()+" ");
  				input.delete(0, input.length());
  			}
  		}
  	    } else {
  	    	if(stemming)
  			{
  	    		if (Character.isLetter(ch) || ch == ',')
  	    			input.append(Character.toLowerCase(ch));
  			}
  	    	else
  	    		input.append(Character.toLowerCase(ch));
  	    }
  	}
  	output.flush();
  	        	  
  	          }
  	        }
    		
    		
    		
    	}
    }
    	
    }
    
}

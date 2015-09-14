package com.support.docs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppendConcepts {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		appendConcept();

	}*/
	
	public static void appendConcept(PropertiesFile file){
		//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\stackoverflow_concepts.csv"
		String csvFile = file.getBaseUrl()+file.getStackoverflow_concepts_file();
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String prevFile=null;
		FileWriter fw =null;
		String filename = null;
		
		String current;
		
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {

		        // use comma as separator
				String[] country = line.split(cvsSplitBy);
				
				current = country[0];
				
				if(prevFile == null)
				{
					//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\Documents\\"+
					filename = file.getBaseUrl()+file.getDocs()+"\\"+country[0]+".txt";
				}
				else if(current.equals(prevFile))
					{
						fw.write(" "+country[1]+" ");
						prevFile=filename;
						continue;
					}
				else
				{
					fw.close();
					filename = file.getBaseUrl()+file.getDocs()+"\\"+country[0]+".txt";
				}


					fw = new FileWriter(filename,true); //the true will append the new data
	
					fw.write(" "+country[1]+" "); //" %TYPEE% "+
				
					prevFile=current;
				
				//fw.close();
				}
				
		//System.out.println("Done");
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		//System.out.println("Done");
	  }
	

}

	
	




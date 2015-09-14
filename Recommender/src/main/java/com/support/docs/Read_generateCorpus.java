package com.support.docs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Read_generateCorpus {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		readCSV_getCORPUS();

	}*/
	
	public static void readCSV_getCORPUS(PropertiesFile file){
		
		
		String csvFile = file.getBaseUrl()+file.getConceptcount_file();
		//String conceptsFile = "C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\stackoverflow_concepts.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int no_of_docs = 0;
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {
			        // use comma as separator
				String[] country = line.split(cvsSplitBy);
				
				
				OutputStream outstream;
				String outputname = null;
				
				int length = country.length;
				if(length < 4)
					continue;

				
				//"C:\\Users\\Vishal\\Desktop\\IR Thesis\\analyze\\Documents\\"
				outputname = file.getBaseUrl()+file.getDocs()+country[0]+".txt";
						
					
					outstream = new FileOutputStream(outputname);
					
					Writer output = new OutputStreamWriter(outstream);
					output = new BufferedWriter(output);
					
					output.write(country[2]+" , "); //" %TYPEE% "+
					
					if(length > 4)
					{
						if(country[4].equals(" ") || country[4].equals(null) || country[4].equals(""))
							output.write("NULL, "); //" %CODE% "+
						else
						{
							output.write(" stopstemming ");
							output.write(" "+ country[4] + " "); //" %CODE% " +
							output.write(" startstemming ,");
						}
					}
					else
						output.write("NULL, "); //" %CODE% "+
					
					
					//output.write(country[1]+" , "); //" %Concept Count% "+
					output.write(country[3]+" , "); //" %TEXT% "+
					
					output.flush();
					output.close();

					no_of_docs++;
				}
				
		System.out.println("Number of Documents: "+no_of_docs);
	 
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

	
	



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import edu.smu.tspell.wordnet.*;

public class TextReader {
	public static void main(String[ ] args) throws IOException {
		
	
		
		System.setProperty("wordnet.database.dir", "dict");
	    String file_name= "Sports.txt";
	    String file_name1= "Travel.txt";
	    String file_name2= "Finance.txt";
	    String file_name3= "Jobs.txt";
	    String file_name4= "Geography.txt";
	    try
	    {
	    	String[] sprt,tra,fin,jo,geo;
	    	CategorizeReader file = new CategorizeReader();
	    	String[] sport = file.OpenFile(file_name);
	    	String[] travel = file.OpenFile(file_name1);
	    	String[] finance = file.OpenFile(file_name2);
	    	String[] jobs = file.OpenFile(file_name3);
	    	String[] geography = file.OpenFile(file_name4);
	    	
	    	TextReader tr1=new TextReader();
	    	sprt=tr1.sportswnetret(sport);
	    	tra=tr1.sportswnetret(travel);
	    	fin=tr1.sportswnetret(finance);
	    	jo=tr1.sportswnetret(jobs);
	    	geo=tr1.sportswnetret(geography);
	    	HashMap<String,Integer> hs1=new HashMap<String,Integer>();
	    	HashMap<String,Integer> hs2=new HashMap<String,Integer>();
	    	HashMap<String,Integer> hs3=new HashMap<String,Integer>();
	    	HashMap<String,Integer> hs4=new HashMap<String,Integer>();
	    	HashMap<String,Integer> hs5=new HashMap<String,Integer>();
	    	
	    	tr1.puthasset(sprt,hs1);
	    	tr1.puthasset(tra,hs2);
	    	tr1.puthasset(fin,hs3);
	    	tr1.puthasset(jo,hs4);
	    	tr1.puthasset(geo,hs5);
	    	
	    	tr1.write2file(hs1, "sprt.txt");
	    	tr1.write2file(hs2, "trv.txt");
	    	tr1.write2file(hs3, "fin.txt");
	    	tr1.write2file(hs1, "joe.txt");
	    	tr1.write2file(hs1, "geog.txt");
	    }
	    catch(IOException e)
	    {
	    	System.out.println(e.getMessage());
	    }
	}
	
	private void puthasset(String[] strar, HashMap hs1) {
		// TODO Auto-generated method stub
		for(int i=0;i<strar.length;i++)
		{
			if (!hs1.containsKey(strar[i])) {
				hs1.put(strar[i], 1);
            } else {
            	hs1.put(strar[i], (Integer) hs1.get(strar[i]) + 1);
            }
		}
		
	}

	public String[] sportswnetret(String[] s1) throws IOException
	{
		String def="";
		String[] s2,s4;
		for(int j=0;j<s1.length;j++)
		{
		String x=s1[j],s3;
		def=def.concat(s1[j])+" ";
		Synset nounSynset = null; 
		NounSynset[] hyponyms; 
		WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		Synset[] synsets = database.getSynsets(x); 
		for (int i = 0; i < synsets.length; i++) {
		    nounSynset = (Synset)(synsets[i]); 
		    s2=nounSynset.getUsageExamples();
		    s3=nounSynset.getDefinition();
		    def=" "+def.concat(s3)+" ";
		    for(int k=0;k<s2.length;k++)
		    {
		    def=" "+def.concat(s2[k])+" ";
		
		    }
		    }
		}
		def=def.replaceAll("\"", "");
		
		//System.out.println(def);
		s4=stopwordsremoval(def);
		return s4;
}
	public void write2file(HashMap<String,Integer> hs2, String filename) throws IOException
	{
		 File file1 = new File(filename);
 	    if (!file1.exists()) {
				file1.createNewFile();
			}
 	    FileWriter fw = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for ( String key : hs2.keySet() ) {
			  
				
				bw.write(key);
				bw.newLine();
				}
			bw.close();
	}
	public String[] stopwordsremoval(String str) throws IOException
	{
		str=str.toLowerCase();
		int numberOfLines;
		

		String[] splitarray;
		str=str.replaceAll("[\\n\\t\\d]+","");
		splitarray=str.split("\\s+");
		
		for(int k=0;k<splitarray.length;k++)
		{
			
		splitarray[k]=splitarray[k].replaceAll("[^a-zA-Z]","");
		}
		FileReader fr = new FileReader("stopwords");
		BufferedReader textReader = new BufferedReader(fr);

		numberOfLines = readLines();
		String[ ] textData = new String[numberOfLines];
		
		int i,j;
		
		
		
		
		for (i=0; i < numberOfLines; i++)
		{

			textData[ i ] = textReader.readLine();
			//System.out.println(textData[i]);
			
			for(j=0;j<splitarray.length;j++)
			{
				
				if(splitarray[j].equals(textData[i]))
				{
					splitarray[j]="";
				}
				
		
				//System.out.println(splitarray[j]);
			}
			
		}
		
		int count=0;
		for(int abs=0;abs<splitarray.length;abs++)
		{
			if(!splitarray[abs].isEmpty())
			{
				count++;
			}
		}
		
		String[] strtemp=new String[count];
		count=0;
		for(int abs=0;abs<splitarray.length;abs++)
		{
			if(!splitarray[abs].isEmpty())
			{
				strtemp[count]=splitarray[abs];
				count++;
			}
		}
		/*for(int x=0;x<splitarray.length;x++)
		{
			
			splitarray[x]=splitarray[x].replaceAll("[\\n\\t\\d]+","");
			splitarray[x]=splitarray[x].trim();
		}*/
		textReader.close( );
		fr.close();
		return strtemp;
	}
	public int readLines() throws IOException
	{
		FileReader file_to_read = new FileReader("stopwords");
		BufferedReader bf = new BufferedReader(file_to_read);
		
		String aLine;
		int numberofLines=0;
		while ( ( aLine = bf.readLine( ) ) != null ) {
			numberofLines++;
			}
	bf.close();
	return numberofLines;
	}
	
	
}


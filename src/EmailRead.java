import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.smu.tspell.wordnet.*;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;


public class EmailRead {
	/*Contains ham words */
	TreeMap<String, Integer> hamwords;
	/*Contains spam words */
	TreeMap<String, Integer> spamwords;
	TreeMap<String, Integer> testwords;
	TreeMap<String, Integer> testlemwords;
	double gspamprob=0,ghamprob=0;
	double gspamlemprob=0,ghamlemprob=0;

	public static void main(String args[]) throws IOException
	{
	
	
		System.setProperty("wordnet.database.dir", "dict");
		String s1="",sample="",spam_string="",s3="",s5="",s6 ="";
		String[] s2,s4,s7;
		EmailRead er1=new EmailRead();
		sample=er1.readham();
		s1=er1.tagremover(sample);
		s2=er1.stopwordsremoval(s1);
		er1.puthamtomaps(s2);
		er1.hamwritetofile();
		spam_string=er1.readspam();
		s3=er1.tagremover(spam_string);
		s4=er1.stopwordsremoval(s3);
		er1.putspamtomaps(s4);
		er1.spamwritetofile();
		/*s5=er1.readtestfile();
		s6=er1.tagremover(s5);
		s7=er1.stopwordsremoval(s6);
		er1.puttesttomaps(s7);
		er1.calculateprob();*/
	}
	
	
	 private void hamwritetofile() throws IOException {
		 File file1 = new File("hamfile2.txt");
 	    if (!file1.exists()) {
				file1.createNewFile();
			}
 	    FileWriter fw = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(Map.Entry<String, Integer> entry:hamwords.entrySet()){
	        	
	        	
	            bw.write(entry.getKey());
	            bw.newLine();
	            bw.write(entry.getValue().toString());
	            bw.newLine();
	        }
			
			bw.close();
			fw.close();
		
	}
	 
	 private void spamwritetofile() throws IOException {
		 File file2 = new File("spamfile2.txt");
 	    if (!file2.exists()) {
				file2.createNewFile();
			}
 	    FileWriter fw = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(Map.Entry<String, Integer> entry:spamwords.entrySet()){
	        	
	        	
	            bw.write(entry.getKey());
	            bw.newLine();
	            bw.write(entry.getValue().toString());
	            bw.newLine();
	        }
			
			bw.close();
			fw.close();
	}


	/*String readtestfile() throws IOException {
		// TODO Auto-generated method stub
		File folder = new File("C:/Users/Jay Buddhdev/Desktop/Project/test");
		  File[] listOfFiles = folder.listFiles();
		  String s=" ";int counter= 0;
		  
			  	for (File file : listOfFiles) 
			  	{
			  		if (file.isFile()) 
			  		{
		         // System.out.println(file.getName());
			  			FileReader fr = new FileReader(file);
			  			BufferedReader br = new BufferedReader(fr);
			  			while (br.ready())
			  				{
			  				//counter++;
			  				s += br.readLine() + "\n";
			  				}
			  			
			  			br.close();
			  			
			  		}
			  		break;
			  	}
		  	
		  	return s;

	}*/

	void calculateprob () throws IOException {
		// TODO Auto-generated method stub
		//double totalspam = spamwords.size();
		//double totalham = hamwords.size();
		gspamprob=0;ghamprob=0;
		
		for (Map.Entry<String,Integer> entry : testwords.entrySet()) {
			/*System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());*/
			
			 FileInputStream fstream = new FileInputStream("hamfile2.txt");
			   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			   
			   FileInputStream fstream1 = new FileInputStream("spamfile2.txt");
			   BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
			   String word =entry.getKey();
			   int wordcount = entry.getValue();
	
	     	/*for (int i = 0;i< s7.length;i++){*/
	     	
			   
			   double hamcount = 0,spamcount = 0;
			   
			  
			   
			   int i,j;
			   double numberOfLines,numberOfLines1;
			   numberOfLines = readLiness("hamfile2.txt");
			   String a="",b="";
			   //System.out.println("word check "+word);
				for (i=0; i < numberOfLines; i=i+2)
				{
					//if(i%2==0)
					//{	
					a=br.readLine();
					//a.replaceAll("\\s","");
					//System.out.println("Line read: "+a);
					if(word.equals(a))
					{
						hamcount=Double.parseDouble(br.readLine());
						//System.out.println("hamcount "+hamcount+"word "+ word);
					}
					
					else
					{
						br.readLine();
					}
					
					//}
				}
				
				
				
				
				numberOfLines1 = readLiness("spamfile2.txt");
				for (j=0; j < numberOfLines1; j=j+2)
				{
					//if(j%2==0)
					//{	
					b=br1.readLine();
					//b.replaceAll("\\s","");
					//System.out.println("Line read: "+b);
					if(word.equals(b))
					{
						spamcount=Double.parseDouble(br1.readLine());
						//System.out.println("spamcount "+spamcount+"word "+ word);
					
					}
					else
					{
						br1.readLine();
					}
					//}
				}	
			/*if (hamwords.containsKey(word)){
				hamcount=hamwords.get(word);
				
				
				}
		
				if (spamwords.containsKey(word)){
					spamcount = spamwords.get(word);
			
				}
				*/
				/*System.out.println(" spamcount "+ spamcount);
				System.out.println(" hamcount "+ hamcount);
				System.out.println(" totalham "+ (numberOfLines/2));
				System.out.println(" totalspam "+ (numberOfLines1/2));*/
				if(spamcount!=0 && hamcount != 0)
				{		
					
				double prob = ((spamcount/(numberOfLines1/2))/((spamcount/(numberOfLines1/2))+(hamcount/(numberOfLines/2))));
				/*System.out.println("prob "+prob);*/
				if(hamcount>spamcount)
				{
					ghamprob+=wordcount*(1-prob);
				}
				
				else if(hamcount<spamcount)
				{
					gspamprob+=wordcount*prob;
				}
				
				else
				{
					gspamprob+=wordcount*prob;
					ghamprob+=wordcount*(1-prob);
				}
		}
				
				if(spamcount!=0 && hamcount == 0)
				{
					gspamprob+=wordcount*1;
				}
				else if(spamcount==0 && hamcount != 0)
				{
					ghamprob+=wordcount*1;
				}
				fstream.close();
				br.close();
				fstream1.close();
				br1.close();
		}
		
		
		System.out.println();
		System.out.println("Global Ham Probability of the test e-mail is: "+ghamprob);
		System.out.println("Global Spam Probability of the test e-mail is:"+gspamprob);
		System.out.println();
		if(gspamprob>ghamprob)
		System.out.println("MAIL IS A SPAM, Think before you open");
		else
			System.out.println("MAIL IS A HAM, no worries ");
	}
		
		public double readLiness(String path) throws IOException
		{
			FileReader file_to_read = new FileReader(path);
			BufferedReader bf = new BufferedReader(file_to_read);
			
			String aLine;
			int numberofLines=0;
			while ( ( aLine = bf.readLine( ) ) != null ) {
				numberofLines++;
				}
		bf.close();
		return numberofLines;
		}
		
	
	void puttesttomaps(String[] s2) {
		testwords=new TreeMap<String,Integer>();
		
		for(int i=0;i<s2.length;i++)
		{
			if(!s2[i].isEmpty())
			{
				
			
			if (!testwords.containsKey(s2[i])) {
				testwords.put(s2[i], 1);
            } else {
            	testwords.put(s2[i], (Integer) testwords.get(s2[i]) + 1);
            }
			}
		}
		
		/*for (Map.Entry<String,Integer> entry : testwords.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}*/

	}

	
	public String bestmatchfinder()
	{
		Set<Entry<String, Integer>> set = testwords.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        System.setProperty("wordnet.database.dir", "dict");
        WordNetDatabase database1 = WordNetDatabase.getFileInstance(); 
        String temp="";
        int store;
        for(Map.Entry<String, Integer> entry:list){
        	temp=entry.getKey().toString();
        	store=entry.getValue();
        	Synset[] synsets = database1.getSynsets(temp,SynsetType.NOUN);
            //System.out.println(entry.getKey()+" - "+entry.getValue()+" times");
        	if(synsets.length!=0)
				{
        		if(temp.length()<5)
        		continue;
        		else
        		return temp;
        		
				}
        	else
        		continue;
        }
        return "Nothing Found in Wordnet";
		
		
	}
	void puthamtomaps(String[] s2) {
		hamwords=new TreeMap<String,Integer>();
		 String s=" ",x="";
		  WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		for(int i=0;i<s2.length;i++)
		{
			if(!s2[i].isEmpty())
			{
				Synset[] synsets = database.getSynsets(s2[i]);
  				
  				if(synsets.length!=0)
  				{
  					System.out.println(s2[i]);
			if (!hamwords.containsKey(s2[i])) {
				hamwords.put(s2[i], 1);
            } else {
            	hamwords.put(s2[i], (Integer) hamwords.get(s2[i]) + 1);
            }
			}
		}
		}
		
		/*for (Map.Entry<String,Integer> entry : hamwords.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}*/

	}
	
	void putspamtomaps(String[] s3) {
		spamwords=new TreeMap<String,Integer>();
		String s=" ",x="";
		  WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		for(int i=0;i<s3.length;i++)
		{
			if(!s3[i].isEmpty())
			{
				Synset[] synsets = database.getSynsets(s3[i]);
  				
  				if(synsets.length!=0)
  				{
  					System.out.println(s3[i]);
			if(!s3[i].isEmpty())
			{
				
			
			if (!spamwords.containsKey(s3[i])) {
				spamwords.put(s3[i], 1);
            } else {
            	spamwords.put(s3[i], (Integer) spamwords.get(s3[i]) + 1);
            }
			}
		}
			}
		}
  				
		
		/*for (Map.Entry<String,Integer> entry : spamwords.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}*/

	}

	public String readham()throws IOException
	{
	File folder = new File("20021010_hard_ham/hard_ham");
	  File[] listOfFiles = folder.listFiles();
	 
		String s="",x="";
		  	for (File file : listOfFiles) 
		  	{
		  		if (file.isFile()) 
		  		{
	         // System.out.println(file.getName());
		  			FileReader fr = new FileReader(file);
		  			BufferedReader br = new BufferedReader(fr);
		  			while (br.ready())
		  				{
		  				x=br.readLine().trim();
		  				
		  			
		  				s += x + "\n";
		  				}
		  			br.close();
		  			fr.close();
		  		}
		  	}
	  	
	  	return s;
		}
	
	public String readspam()throws IOException
	{
	File folder = new File("20021010_spam/spam");
	  File[] listOfFiles = folder.listFiles();
	  String s1=" ",y="";
	 
		  	for (File file : listOfFiles) 
		  	{
		  		if (file.isFile()) 
		  		{
	         // System.out.println(file.getName());
		  			FileReader fr = new FileReader(file);
		  			BufferedReader br = new BufferedReader(fr);
		  			while (br.ready())
		  				{
		  				y=br.readLine().trim();
		  				
		  				
		  				s1 += y + "\n";

		  				}
		  			br.close();
		  			fr.close();
		  		}
		  	}
	  	
	  	return s1;
		}
	
	public String tagremover(String s1) 
	{
		String s2="";
		s2=new HtmlToPlainText().getPlainText(Jsoup.parse(s1)); 
		s2=s2.replaceAll("\\<.*?\\>", "");
		//return Jsoup.parse(s1).text();
		return s2;
	}
	 
	public String[] stopwordsremoval(String str) throws IOException
	{
		str=str.toLowerCase();
		int numberOfLines;
		

		String[] splitarray=new String[str.length()];
		str=str.replaceAll("[\\n\\t\\d]+"," ");
		splitarray=str.split("\\s+");
		
		for(int k=0;k<splitarray.length;k++)
		{
		if(splitarray[k].contains("'"))
			continue;
		else	
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
		
		textReader.close( );
		return splitarray;
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


	public void putlemtest2map(List<String> list) {
		testlemwords=new TreeMap<String,Integer>();
		String temp="";
		// TODO Auto-generated method stub
		for(int i = 0; i < list.size(); i++) {
            temp=list.get(i).toString();
            
          
    			if (!testlemwords.containsKey(temp)) {
    				testlemwords.put(temp, 1);
                } else {
                	testlemwords.put(temp, (Integer) testlemwords.get(temp) + 1);
                }
    		
		}
	}
	
	public String spamorham()
	
	{
		if(gspamprob>ghamprob)
			return("spam");
		else if(gspamprob<ghamprob)
			return("ham");
		else
			return null;
	}
	
	void calculateproblem () throws IOException {
		// TODO Auto-generated method stub
		//double totalspam = spamwords.size();
		//double totalham = hamwords.size();
		
		gspamlemprob=0;ghamlemprob=0;
		for (Map.Entry<String,Integer> entry : testlemwords.entrySet()) {
			/*System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());*/
			
			 FileInputStream fstream = new FileInputStream("hamfile2.txt");
			   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			   
			   FileInputStream fstream1 = new FileInputStream("spamfile2.txt");
			   BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
			   String word =entry.getKey();
			   int wordcount = entry.getValue();
	
	     	/*for (int i = 0;i< s7.length;i++){*/
	     	
			   
			   double hamlemcount = 0,spamlemcount = 0;
			   
			  
			   
			   int i,j;
			   double numberOfLines,numberOfLines1;
			   numberOfLines = readLiness("hamfile2.txt");
			   String a="",b="";
			   //System.out.println("word check "+word);
				for (i=0; i < numberOfLines; i=i+2)
				{
					//if(i%2==0)
					//{	
					a=br.readLine();
					//a.replaceAll("\\s","");
					//System.out.println("Line read: "+a);
					if(word.equals(a))
					{
						hamlemcount=Double.parseDouble(br.readLine());
						//System.out.println("hamcount "+hamcount+"word "+ word);
					}
					
					else
					{
						br.readLine();
					}
					
					//}
				}
				
				
				
				
				numberOfLines1 = readLiness("spamfile2.txt");
				for (j=0; j < numberOfLines1; j=j+2)
				{
					//if(j%2==0)
					//{	
					b=br1.readLine();
					//b.replaceAll("\\s","");
					//System.out.println("Line read: "+b);
					if(word.equals(b))
					{
						spamlemcount=Double.parseDouble(br1.readLine());
						//System.out.println("spamcount "+spamcount+"word "+ word);
					
					}
					else
					{
						br1.readLine();
					}
					//}
				}	
			/*if (hamwords.containsKey(word)){
				hamcount=hamwords.get(word);
				
				
				}
		
				if (spamwords.containsKey(word)){
					spamcount = spamwords.get(word);
			
				}
				*/
				/*System.out.println(" spamcount "+ spamcount);
				System.out.println(" hamcount "+ hamcount);
				System.out.println(" totalham "+ (numberOfLines/2));
				System.out.println(" totalspam "+ (numberOfLines1/2));*/
				if(spamlemcount!=0 && hamlemcount != 0)
				{		
					
				double prob = ((spamlemcount/(numberOfLines1/2))/((spamlemcount/(numberOfLines1/2))+(hamlemcount/(numberOfLines/2))));
				/*System.out.println("prob "+prob);*/
				if(hamlemcount>spamlemcount)
				{
					ghamlemprob+=wordcount*(1-prob);
				}
				
				else if(hamlemcount<spamlemcount)
				{
					gspamlemprob+=wordcount*prob;
				}
				
				else
				{
					gspamlemprob+=wordcount*prob;
					ghamlemprob+=wordcount*(1-prob);
				}
		}
				
				if(spamlemcount!=0 && hamlemcount == 0)
				{
					gspamlemprob+=wordcount*1;
				}
				else if(spamlemcount==0 && hamlemcount != 0)
				{
					ghamlemprob+=wordcount*1;
				}
				fstream.close();
				br.close();
				fstream1.close();
				br1.close();
		}
		
		
		System.out.println();
		System.out.println("Global Ham Probability of the LEMMATIZED test e-mail is "+ghamlemprob);
		System.out.println("Global Spam Probability of the LEMMATIZED test e-mail is "+gspamlemprob);
		System.out.println();
		if(gspamlemprob>ghamlemprob)
			System.out.println("MAIL IS A SPAM, Think before you open");
		else
			System.out.println("MAIL IS A HAM, no worries ");
		
	}
	}


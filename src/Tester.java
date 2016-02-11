import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.parser.lexparser.Item;




public class Tester {

	public static void main(String[] args) throws IOException {
		
		


		
		List<String> list;
		StanfordLemmatizer stanfordLemma= new StanfordLemmatizer();
		// TODO Auto-generated method stub
		/*String s1="",sample="",spam_string="",s3="",s5="",s6 ="";
		String[] s2,s4,s7;*/
		EmailRead er1=new EmailRead();
		Tester tr1=new Tester();
		/*sample=er1.readham();
		s1=er1.tagremover(sample);
		s2=er1.stopwordsremoval(s1);
		er1.puthamtomaps(s2);
		spam_string=er1.readspam();
		s3=er1.tagremover(spam_string);
		s4=er1.stopwordsremoval(s3);
		er1.putspamtomaps(s4);*/
		String s5="",s6="";
				String[] s7;
		s5=tr1.readtestfile();
		
		
		
		s6=er1.tagremover(s5);
		String sz=s6;
		sz=sz.replaceAll("[^\\w\\s]","");
		//sz=sz.replaceAll("[^A-Za-z//s]+","");
		
		list= stanfordLemma.lemmatize(sz);
		
		
		
		
		
		s7=er1.stopwordsremoval(s6);
		
		
		er1.puttesttomaps(s7);
		String concept=er1.bestmatchfinder();
		System.out.println("*********************************************************************************");
		System.out.println("*********************************************************************************");
		System.out.println("WELCOME TO THE SPAM IDENTIFYING AND CATEGORIZATION SYSTEM");
		System.out.println("*********************************************************************************");
		System.out.println("*********************************************************************************");
		System.out.println("The concept of the test email is: "+ concept);
		
		er1.calculateprob();
		
		er1.putlemtest2map(list);
		er1.calculateproblem();
		
		int trcount=tr1.matchfinder(s7,"trv.txt");
		int spcount=tr1.matchfinder(s7,"sprt.txt");
		int fincount=tr1.matchfinder(s7,"fin.txt");
		int jobcount=tr1.matchfinder(s7,"joe.txt");
		int geocount=tr1.matchfinder(s7,"geog.txt");
		int sizetester=s7.length;
		tr1.categoryprinter(trcount,spcount,fincount,jobcount,geocount,sizetester);
		//System.out.println("trcount"+trcount+"spcount"+spcount+"fincount"+fincount+"sizetester"+sizetester+"jobcount"+jobcount+"geocount"+geocount);
	}

	private void categoryprinter(int trcount, int spcount, int fincount,int jobcount,int geocount, int sizetester) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("The amount of data related to Travel is: "+((trcount*100)/sizetester)+"%");
		System.out.println("The amount of data related to Sports is: "+((spcount*100)/sizetester)+"%");
		System.out.println("The amount of data related to Finance is: "+((fincount*100)/sizetester)+"%");
		System.out.println("The amount of data related to Jobs/Occupation is: "+((jobcount*100)/sizetester)+"%");
		System.out.println("The amount of data related to Geography is: "+((geocount*100)/sizetester)+"%");
		System.out.println();
		
		int[] a=new int[5];
		int max=0;
		String name="";
		a[0]=trcount;
		a[1]=spcount;
		a[2]=fincount;
		a[3]=jobcount;
		a[4]=geocount;
		System.out.println();
		
		for(int y=0;y<5;y++)
		{
			if(a[y]>max)
				max=a[y];
			
		}
		if(max==trcount)
			System.out.println("E-mail is mostly related to TRAVEL");
		
		else if(max==trcount)
		System.out.println("E-mail is mostly related to TRAVEL");
		
		else if(max==spcount)
			System.out.println("E-mail is mostly related to SPORTS");
		
		else if(max==fincount)
			System.out.println("E-mail is mostly related to FINANCE");
		
		else 
			System.out.println("E-mail is mostly related to GEOGRAPHY");
		
	}

	String readtestfile() throws IOException {
		// TODO Auto-generated method stub
		File folder = new File("test");
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

	}
	public int matchfinder(String[] fi,String filename) throws IOException
	{
		int count=0;
		FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String aLine;
			int numberofLines=0;
			while ( ( aLine = br.readLine( ) ) != null ) {
				numberofLines++;
				}
		br.close();
		fr.close();
		
		
		
		String temp="";
		for(int y=0;y<fi.length;y++)
		{
			FileReader fr1 = new FileReader(filename);
			BufferedReader br1 = new BufferedReader(fr1);
			for(int a=0;a<numberofLines;a++)
			{
				
			temp=br1.readLine().trim();
			//System.out.println(temp);
			if(!temp.isEmpty()) 
			{
			if(fi[y].equals(temp))
					{
				count++;
					}
			}
			}
			br1.close();
			fr1.close();
			
		}
		return count;
		
	}
}

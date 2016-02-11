import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class AccuracyTester {
	
	static double falsecount=0;
	static double truecount=0;
	
	public static void main(String[] args) throws IOException
	{
	HashMap<String,String> hm=new HashMap<String,String>();
	
	for(int i=101;i<131;i++)
	{
		hm.put(i+".txt","spam");
	}
	
	for(int j=201;j<231;j++)
	{
		hm.put(j+".txt","ham");
	}
	AccuracyTester at= new AccuracyTester();
	EmailRead erx=new EmailRead();
	
	String temp1="",temp2="",temp3="";
	String[] tempar={};
	for(int i=101;i<131;i++)
	{
		temp1=at.readtestfileforacc(i);
		temp2=erx.tagremover(temp1);
		tempar=erx.stopwordsremoval(temp2);
		erx.puttesttomaps(tempar);
		erx.calculateprob();
		temp3=erx.spamorham();
		
		if(temp3.equals("spam"))
		{
		truecount++;	
		}
		else
			falsecount++;
	
	}
	String temp11="",temp21="",temp31="";
	String[] tempar1={};
	for(int j=201;j<231;j++)
	{
		temp11=at.readtestfileforacc(j);
		temp21=erx.tagremover(temp11);
		tempar1=erx.stopwordsremoval(temp21);
		erx.puttesttomaps(tempar1);
		erx.calculateprob();
		temp31=erx.spamorham();
		
		if(temp31.equals("ham"))
		{
		truecount++;	
		}
		else
			falsecount++;
	
	}
	System.out.println();
	System.out.println("The accuracy of our system is: "+ (truecount*100)/(truecount+falsecount)+"%");
	}
	
	String readtestfileforacc(int i) throws IOException {
		// TODO Auto-generated method stub
		String s="";
			  			FileReader fr = new FileReader("TestMails/"+i+".txt");
			  			BufferedReader br = new BufferedReader(fr);
			  			while (br.ready())
			  				{
			  				//counter++;
			  				s += br.readLine() + "\n";
			  				}
			  			
			  			br.close();
			  			
			  			return s;

	}
	
	

}

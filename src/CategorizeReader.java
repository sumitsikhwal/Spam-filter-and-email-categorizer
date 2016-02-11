import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CategorizeReader  {
	private String path;
	/*public  CategorizeReader(String file_path)
	{
		path=file_path;
	}*/

public String[] OpenFile(String file_path) throws IOException
{
	path=file_path;
	FileReader fr = new FileReader(path);
	BufferedReader textReader = new BufferedReader(fr);

	int numberOfLines = readLines();
	String[ ] textData = new String[numberOfLines];
	
	int i;

	for (i=0; i < numberOfLines; i++)
	{
	textData[ i ] = textReader.readLine();
	}
	
	textReader.close( );
	return textData;
	
}
	public int readLines() throws IOException
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

}

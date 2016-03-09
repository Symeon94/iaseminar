import java.io.*;
import java.util.*;

public class SequenceGenerator
{
	public static void main(String[] args) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter(new File(args[0])));
			for(int i = 0 ; i < Integer.parseInt(args[1]) ; i++) {
				// length [15;29]
				int up = 15 + (int)(Math.random()*29);
				for(int j = 0 ; j < up ; j++) {
					write.write("" + ((int)(Math.random()*8) + 1));
					if(j != up-1)
						write.write(" -1 ");
				}
				write.newLine();
			}
			write.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

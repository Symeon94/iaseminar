import java.io.*;
import java.util.*;

public class Main
{
	public static void main(String[] args) {
		LinkedList<Sequence> seq = readDatabase(args[0]);
		MaxSP.maxsp(seq, seq, (int)(seq.size() * Integer.parseInt(args[1])/100.0), new Sequence());
	}

	public static LinkedList<Sequence> readDatabase(String path) {
		LinkedList<Sequence> seq = new LinkedList<Sequence>();
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(new File(path)));
			String data = null;
			while((data = file.readLine()) != null) {
				seq.add(new Sequence(data));
			}
			file.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return seq;
	}
}

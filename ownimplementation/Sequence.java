import java.util.*;

public class Sequence
{
	private LinkedList<Integer> seq;
	
	public Sequence() {
		this.seq = new LinkedList<Integer>();
	}
	
	public Sequence(String seq) {
		this();
		String[] data = seq.split(" ");
		for(String txt : data)
			this.seq.add(Integer.parseInt(txt));
	}
	
	public Sequence(LinkedList<Integer> seq) {
		this.seq = seq;
	}
	
	public boolean isIn(int i) {
		for(Integer in : seq) 
			if(in == i)
				return true;
		return false;
	}
	
	public int getItem(int i) {
		return seq.get(i);
	}
	
	public int getSize() {
		return seq.size();
	}
	
	public void append(Integer i) {
		seq.add(i);
	}

	/**
	 * Remove all items until you find i 
	 * Example ABCD removeTo(B) => CD
	 */
	public void removeTo(int i) {
		// TODO
	}
	
	public LinkedList<Integer> getSequence() {
		return seq;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append('<');
		for(Integer i : seq) {
			if(buffer.length() != 1)
				buffer.append(' ');
			buffer.append(i);
		}
		buffer.append('>');
		return buffer.toString();
	}
	
	public Sequence project(int item) {
		Sequence s = new Sequence();
		boolean found = false;
		// TODO AMELIORER
		for(int i : seq) {
			if(!found) {
				if(i == item) {
					found = true;
				}
			}
			else
				s.append(i);
		}
		return s;
	}

	public Sequence copy() {
		// TODO Check the reference
		// TODO NOT SAGE
		return new Sequence((LinkedList<Integer>)this.seq.clone());
	}
	
	public boolean contains(Sequence s) {
		// TODO
	}
}
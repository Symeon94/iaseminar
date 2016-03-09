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

	public Sequence(int firstItem) {
		this();
		seq.add(firstItem);
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
		if(s.getSize() > this.getSize())
			return false;
		for(ListIterator<Integer> it = (ListIterator<Integer>)this.seq.iterator() ; it.hasNext() ; ) {
			int item = it.next();
			if(item == s.getSequence().get(0)) {
				// Can we iterate
				ListIterator<Integer> newIt = this.seq.listIterator(it.nextIndex());
				if(contains(s, newIt))
					return true;
			}
		}
		return false;
	}

	private boolean contains(Sequence s, ListIterator<Integer> it) {
		Iterator<Integer> it2 = s.getSequence().iterator();
		if(!it2.hasNext())
			return true;
		it2.next();

		while(it2.hasNext()) {
			if(!it.hasNext())
				return false;
			if(it2.next() != it.next())
				return false;
		}
		return true;
	}
}

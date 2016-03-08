import java.io.*;
import java.util.*;

public class MaxSP
{	
	/**
	 * @param minsup minimum support in terms of line count
	 */
	public static void maxsp(List<Sequence> db, int minsup, Sequence p) {
		int largestSupport;
		// Scan DB
		HashMap<Integer, Integer> support = scan(db);
		for(Integer item : support.keySet()) {
			// If item support >= minsup
			if(support.get(item) >= minsup) {
				// Concatenate
				Sequence ps = p.copy();
				ps.append(item);
				System.out.println(ps);
				// Project Database with item i
				List<Sequence> dbi = project(db, item);
				// Recursive call
				maxsp(dbi, minsup, ps);
			}
		}
	}	
	
	private static HashMap<Integer, Integer> scan(List<Sequence> db) {
		HashMap<Integer, Integer> support = new HashMap<Integer, Integer>();
		LinkedList<Integer> alreadySeen = new LinkedList<Integer>();
		for(Sequence sq : db) {
			alreadySeen.clear();
			// Count for a sequence all items that you see
			for(Integer i : sq.getSequence()) {
				if(!alreadySeen.contains(i))
					alreadySeen.add(i);
			}
			// Add to support
			for(Integer i : alreadySeen) {
				Integer val = support.get(i);
				if(val == null)
					support.put(i, 1);
				else
					support.put(i, val+1);
			}
		}
		return support;
	}
	
	private static List<Sequence> project(List<Sequence> db, int item) {
		List<Sequence> projected_db = new LinkedList<Sequence>();
		for(Sequence s : db) {
			projected_db.add(s.project(item));
		}
		return projected_db;
	}
}
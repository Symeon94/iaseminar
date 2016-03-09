import java.io.*;
import java.util.*;

public class MaxSP
{
	/**
	 * @param minsup minimum support in terms of line count
	 */
	public static int maxsp(LinkedList<Sequence> originalDb, LinkedList<Sequence> db, int minsup, Sequence p) {
		int largestSupport = 0;
		// Scan DB
		HashMap<Integer, Integer> support = scan(db);
		for(Integer item : support.keySet()) {
			// If item support >= minsup
			int itemSupport = support.get(item);
			if(itemSupport >= minsup) {
				// Concatenate
				Sequence ps = p.copy();
				ps.append(item);
				// Project Database with item i
				LinkedList<Sequence> dbi = project(db, item);
				int maxSupport = maxsp(originalDb, dbi, minsup, ps);
				if(maxSupport < minsup) {
					if(!hasMaximalBackextension(originalDb, ps.copy(), minsup)) {
						System.out.println(ps);
					}
				}
				// TODO GET SUPPORT SEQUENCE
				int curSupport = MaxSP.getSupport(db, item);
				if(curSupport > largestSupport)
					largestSupport = curSupport;
				// MBE TODO CHECK iTEMSUPPORT
				/*if(!hasMaximalBackextension(db, item, minsup)) {
					// Recursive call
					int maxSupport = maxsp(dbi, minsup, ps);
					if(maxSupport < minsup) {
						System.out.println(ps);
					}
					// Find the last item support in the DB
					int curSupport = MaxSP.getSupport(db, item);
					if(curSupport > largestSupport)
						largestSupport = curSupport;
				}*/
			}
		}
		return largestSupport;
	}

	public static void prefixSpan(LinkedList<Sequence> db, int minsup, Sequence p) {
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
				LinkedList<Sequence> dbi = project(db, item);
				// Recursive call
				prefixSpan(dbi, minsup, ps);
			}
		}
	}

	private static int getSupport(LinkedList<Sequence> db, Sequence s) {
		int sup = 0;
		for(Sequence t : db) {
			if(t.contains(s))
				sup ++;
		}
		return sup;
	}

	private static int getSupport(LinkedList<Sequence> db, int item) {
		return getSupport(db, new Sequence(item));
	}

	private static HashMap<Integer, Integer> scan(LinkedList<Sequence> db) {
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

	private static LinkedList<Sequence> project(LinkedList<Sequence> db, int item) {
		LinkedList<Sequence> projected_db = new LinkedList<Sequence>();
		for(Sequence s : db) {
			projected_db.add(s.project(item));
		}
		return projected_db;
	}

	private static LinkedList<Sequence> project(LinkedList<Sequence> db, Sequence s) {
		Sequence copy = s.copy();
		LinkedList<Sequence> res = db;
		while(copy.getSize() > 0) {
			res = project(res, copy.getSequence().pop());
		}
		return res;
	}

	/**
	 * Recursive function, starts with i = size-1 to 0
	 * @post Sequence s WILL be modified
	 */
	private static boolean hasMaximalBackextension(LinkedList<Sequence> db, Sequence s, int minsup) {
		// Get last item
		int lastItem = s.getSequence().pollLast();
		// Find LLn
		LinkedList<Sequence> newDb = new LinkedList<Sequence>();
		for(Sequence seq : db)
			newDb.add(lastInLast(seq, lastItem));
		// Project P_{n-1} on the DB
		LinkedList<Sequence> projectedDb = project(newDb, s);
		// Calculate support
		HashMap<Integer, Integer> support = scan(projectedDb);
		// If we find max period return true
		for(Integer key : support.keySet()) {
			if(support.get(key) >= minsup)
				return true;
		}
		// If a sequence to see recall maxback extension
		if(s.getSize() > 0)
			return hasMaximalBackextension(newDb, s, minsup);
		return false;
	}

	/**
	 * Find Last in Last element and remove the other
	 * Example: ABCDCDAC with item D ==> ABCDC
	 */
	private static Sequence lastInLast(Sequence s, int item) {
		Sequence copy = s.copy();
		boolean stop = false;
		while(!stop) {
			if(copy.getSize() == 0)
				stop = true;
			else if(copy.getSequence().pollLast() == item)
				stop = true;
		}
		return copy;
	}
}

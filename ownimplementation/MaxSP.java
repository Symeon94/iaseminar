import java.io.*;
import java.util.*;

public class MaxSP
{
	/**
	 * @param minsup minimum support in terms of line count
	 */
	public static int maxsp(LinkedList<Sequence> db, int minsup, Sequence p) {
		int largestSupport = 0;
		// Scan DB
		HashMap<Integer, Integer> support = scan(db);
		for(Integer item : support.keySet()) {
			// If item support >= minsup
			int itemSupport = support.get(item);
			if(itemSupport >= minsup) {
				// MBE TODO CHECK iTEMSUPPORT
				if(!hasMaximalBackextension(db, item, itemSupport)) {
					// Concatenate
					Sequence ps = p.copy();
					ps.append(item);
					// Project Database with item i
					LinkedList<Sequence> dbi = project(db, item);
					// Recursive call
					int maxSupport = maxsp(dbi, minsup, ps);
					if(maxSupport < minsup) {
						System.out.println(ps);
					}
					// Find the last item support in the DB
					int curSupport = MaxSP.getSupport(db, item);
					if(curSupport > largestSupport)
						largestSupport = curSupport;
				}
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

	private static boolean hasMaximalBackextension(LinkedList<Sequence> db, int item, int minsup) {
		// TODO don't create a new sequence
		LinkedList<Sequence> reverseSequence = new LinkedList<Sequence>();
		// Stop when we found an MBE
		for(Sequence s : db) {
			Sequence sCopy = s.copy();
			boolean stop = false;
			while(!stop) {
				if(sCopy.getSize() == 0)
					stop = true;
				else if(sCopy.getSequence().pollLast() == item) {
					stop = true;
					reverseSequence.add(sCopy);
				}
			}
		}
		/* TODO for(Sequence sss : db)
			System.out.println("B" + item + sss);
		for(Sequence sss : reverseSequence)
			System.out.println("A" + item + sss);*/
		// Get support for periods
		HashMap<Integer, Integer> support = scan(reverseSequence);
		for(Integer ite : support.keySet()) {
			// TODO System.out.println(ite + " -> Sup: " + support.get(ite));
			// If item support >= minsup
			if(support.get(ite) >= minsup)
				return true;
		}
		return false;
	}
}

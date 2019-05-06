import java.util.ArrayList;
import java.util.Collections;

class Chain {

	private ArrayList<String> words;
	private ArrayList<ArrayList<Integer>> counterMap; // counts number of times each word goes to the next

	private String[] bakedWords;
	private double[][] bakedProbabilities;

	int lastIndex;

	public Chain () {

		words = new ArrayList ();
		counterMap = new ArrayList ();

	}

	public void add (String text) {

		text = abcOnly (text.toLowerCase ()).replaceAll ("\n", " ");

		String[] words = text.split (" ");

		for (String word : words)
			addWord (word.trim ());

	}

	private static String abcOnly (String text) {

		StringBuilder b = new StringBuilder ();

		for (char c : text.toCharArray ()) {

			if (!(Character.isAlphabetic (c) || Character.isDigit (c) || Character.isWhitespace (c))) continue;

			b.append (c);

		}

		return b.toString ();

	}

	private static String replace (String text, String... stuff) {

		for (int i = 0; i < stuff.length; i++)
			text = text.replaceAll (stuff[i], stuff[++i]);

		return text;

	}

	public void addWord (String word) {

		int index = words.indexOf (word);

		if (index == -1) {

			for (ArrayList<Integer> node : counterMap)
				node.add (0);

			index = words.size ();

			counterMap.add (new ArrayList (Collections.nCopies (index + 1, 0)));

			words.add (word);

		}

		counterMap.get (lastIndex).set (index, counterMap.get (lastIndex).get (index) + 1);

		lastIndex = index;

	}

	public void bake () {

		bakedWords = words.toArray (new String[words.size ()]);

		bakedProbabilities = new double[counterMap.size ()][];

		for (int x = 0; x < counterMap.size (); x++) {

			ArrayList<Integer> ar = counterMap.get (x);

			double[] percents = new double[ar.size ()];

			int sum = 0;

			for (int count : ar) sum += count;

			if (sum != 0)
				for (int i = 0; i < percents.length; i++) percents[i] = (double) ar.get (i) / sum;
			else
				for (int i = 0; i < percents.length; i++) percents[i] = 0;

			bakedProbabilities[x] = percents;

		}

	}

	public String generate (int length, String start) {

		int currIndex = words.indexOf (start);

		StringBuilder b = new StringBuilder ();

		for (int i = 0; i < length; i++) {

			b.append (bakedWords[currIndex]);
			b.append (' ');

			currIndex = getWeightedIndex (bakedProbabilities[currIndex]);

		}

		b.deleteCharAt (b.length () - 1);

		return b.toString ();

	}

	public static int getWeightedIndex (double[] probabilities) {

		double rand = Math.random ();

		int chosen = -1;

		try {
			for (double sum = 0; sum <= rand; )
				sum += probabilities[++chosen]; // runs on each loop instead of after
		}

		catch (ArrayIndexOutOfBoundsException e) { return 0; }

		return chosen;

	}

}
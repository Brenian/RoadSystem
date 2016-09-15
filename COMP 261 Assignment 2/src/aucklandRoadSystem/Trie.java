package aucklandRoadSystem;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trie {

	private TrieChar root;

	public Trie() {
		root = new TrieChar();
	}

	public void initialiseTrie(ArrayList<Road> roadList) {
		ArrayList<Road> roadNames = roadList;
		Set<String> nameSet = new HashSet<String>();
		for (Road road : roadNames) {
			nameSet.add(road.getLabel());
		}
		for (String s : nameSet) {
			this.add(s);
		}
	}

	public void add(String word) {
		root.addWord(word.toLowerCase());
	}

	public boolean contains(String s) {
		TrieChar currentNode = root;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (currentNode.containsKey(c))
				currentNode = currentNode.getNode(c);
			else
				return false;
		}
		return true;
	}

	public List<String> getWords(String prefix) {
		// Find the node which represents the last letter of the prefix.
		TrieChar lastNode = root;
		for (int i = 0; i < prefix.length(); i++) {
			lastNode = lastNode.getNode(prefix.charAt(i));

			// If no node matches, then no words exists, return empty list
			if (lastNode == null)
				return new ArrayList<String>();
		}
		// Return the words with the final node as a prefix
		return lastNode.getWords();
	}

}
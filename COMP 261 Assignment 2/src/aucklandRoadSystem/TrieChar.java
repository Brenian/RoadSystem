package aucklandRoadSystem;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class TrieChar{
    public TrieChar parent;
    public TrieChar[] children;
    public boolean isLeaf;
    public boolean isWord; // Is the last char of a word
    public char character; //character the node represents

    public TrieChar()
    {
        children = new TrieChar[94];
        isLeaf = true;
        isWord = false;
    }

    public TrieChar(char character){
        this();
        this.character = character;
    }

    protected void addWord(String s){
        isLeaf = false;
        int pos = s.charAt(0) - ' ';
        if (children[pos] == null){
            children[pos] = new TrieChar(s.charAt(0));
            children[pos].parent = this;
        }
        if (s.length() > 1){
            children[pos].addWord(s.substring(1));
        }
        else{
            children[pos].isWord = true;
        }
    }
    
    protected TrieChar getNode(char c){
        return children[c-' '];
    }
    
    public boolean containsKey(char c){
        List<Character> followers = new ArrayList<Character>();
        for (TrieChar x : children) {
            if (x != null) {
                char y = x.character;
                followers.add(y);
            }
        }
        return (followers.contains(c));
    }
    
    protected List<String> getWords() {
        List<String> list = new ArrayList<String>();
        
        // add word
        if (isWord) {
            list.add(toString());
        }
        if (!isLeaf) {
            // add children's words
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    list.addAll(children[i].getWords());
                }
            }
        }
        return list;
    }

    public String toString(){
        if (parent == null){
            return "";
        }
        else{
            return parent.toString() + new String(new char[]{character});
        }
    }
}
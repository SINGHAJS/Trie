package Trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ajit Singh
 * 
 */
public class Trie implements TrieADT {
    private TrieNode root;
    private int numElements;

    public Trie() {
        this.root = new TrieNode('a');
        this.numElements = 0;
    }

    /**
     * this method takes a string, converts it to an char array and adds those
     * chars to the trie tree.
     * 
     * @param element type String
     * @return boolean, false if the element was not added or true if the element
     *         was added to the trie.
     */
    @Override
    public boolean add(String element) {
        TrieNode currentNode = this.root;

        // checking null input
        if (element == null) {
            return false;
        }

        // converting string to char array
        char[] charArr = element.toCharArray();

        // iterating over the array of chars,
        // if the currentNode (root) does not have a char at at the current index,
        // it will create that new TrieNode with that char. Otherwise,
        // if the root does contain have child with the same char, it will then move
        // forward to that char.
        for (int i = 0; i < charArr.length; i++) {
            if (currentNode.children.get(charArr[i]) == null) {
                currentNode.children.put(charArr[i], new TrieNode(charArr[i]));
                this.numElements++;
            }

            if (currentNode.children.get(charArr[i]) != null) {
                currentNode = currentNode.children.get(charArr[i]);
            }
        }

        // that last TrieNode with the last character will have its isWord variable
        // set to true since it is the end of a word.
        currentNode.isWord = true;
        return true;
    }

    /**
     * this method takes a string, converts it to an char array and then iterates of
     * the array. Throughout the iteration, if at point the currentNode map does not
     * contain that character, it will return false. If they exist, it will continue
     * to set the currentNode to that character key value. Once the iteration is
     * over, if that end currentNode's isWord value is true, then it will set that
     * currentNode's isWord value to false.
     * 
     * @param element type String
     * @return false if the value does not exist and return true if and only if the
     *         value exists and its last character's isWord value is true.
     * 
     */
    @Override
    public boolean remove(String element) {
        if (element == null) {
            return false;
        }

        TrieNode currentNode = this.root;
        char[] charArr = element.toCharArray();

        for (int i = 0; i < charArr.length; i++) {
            if (!currentNode.children.containsKey(charArr[i])) {
                // returning false since not all the given characters exist in the trie
                return false;
            } else if (currentNode.children.containsKey(charArr[i])) {
                // setting the currentNode to that character key value node
                currentNode = currentNode.children.get(charArr[i]);
            }
        }

        // setting the isWord value to false
        if (currentNode.isWord) {
            currentNode.isWord = false;
            return true;
        }

        return false;
    }

    /**
     * this method takes a string, converts it to an char array and checks if the
     * tree has those characters.
     * 
     * @param element type String
     * @return boolean, false if the tree does not have that character and true if
     *         and only if the tree contains all the characters and the last
     *         character's isWord variable is set to true.
     */
    @Override
    public boolean contains(String element) {
        TrieNode currentNode = this.root;

        // checking null input
        if (element == null) {
            return false;
        }

        // converting string to char array
        char[] charArr = element.toCharArray();

        // iterating of the currentNode (root), if the currentNode does not have the
        // charArr[i] character of the element, then it will return false. If the root
        // does, the currentNode will be set to the TrieNode containing that char.
        for (int i = 0; i < charArr.length; i++) {
            if (!currentNode.children.containsKey(charArr[i])) {
                return false;
            } else if (currentNode.children.containsKey(charArr[i])) {
                currentNode = currentNode.children.get(charArr[i]);
            }
        }

        // checking if the last character of the string has its isWord variable set to
        // true. If it is true, then return true. Otherwise, return false.
        if (currentNode.isWord) {
            return true;
        }

        return false;
    }

    /**
     * this method iterates over the given prefix and passes the last node of the
     * prefix to the helper method. The helper method then checks of the given node
     * is a word, if it is a word it then sets the currentNode's isWord value to
     * false.
     * 
     * @param prefix type String
     * @return boolean, false the a character from the prefix does not exist in the
     *         trie and true if and only if all children of the given prefix have
     *         been removed.
     */
    @Override
    public boolean removeAll(String prefix) {
        if (prefix == null) {
            return false;
        }

        TrieNode currentNode = this.root;
        char[] charArr = prefix.toCharArray();

        for (int i = 0; i < charArr.length; i++) {
            if (!currentNode.children.containsKey(charArr[i])) {
                return false;
            }
            currentNode = currentNode.children.get(charArr[i]);
        }

        removeAll(currentNode);

        return true;
    }

    /* helper method */
    private void removeAll(TrieNode currentNode) {
        if (currentNode.isWord) {
            currentNode.isWord = false;
        }

        // iterating over the children values
        for (TrieNode node : currentNode.children.values()) {
            removeAll(node); // removing the nodes
        }
    }

    /**
     * this method iterates over the given prefix and checks if the trie contains
     * the nodes with the given prefix.
     * 
     * @param prefix type String
     * @return boolean, false if the prefix does not exist, and true if the prefix
     *         given exists.
     */
    @Override
    public boolean startsWith(String prefix) {
        if (prefix == null) {
            return false;
        }

        TrieNode currentNode = this.root;
        char[] charArr = prefix.toCharArray();

        for (int i = 0; i < charArr.length; i++) {
            if (!currentNode.children.containsKey(charArr[i])) {
                return false;
            }

            if (currentNode.children.containsKey(charArr[charArr.length - 1])) {
                return true;
            }
            currentNode = currentNode.children.get(charArr[i]);
        }

        return false;
    }

    /**
     * this method first iterates of the prefix until the end, it then passes the
     * currentNode to the suggestions() method which then checks if that node is a
     * word. If that node is a word then it is added to the set. It then iterates
     * over the children of that node and of each child the suggestion() recursion
     * is used.
     * 
     * @param prefix type String
     * @return Set<String>
     */
    @Override
    public Set<String> suggestions(String prefix) {
        if (prefix == null) {
            return null;
        }

        TrieNode currentNode = this.root;
        Set<String> suggestions = new HashSet<>(); // set to store the prefix words
        char[] charArr = prefix.toCharArray();

        // iterating over the array
        for (int i = 0; i < charArr.length; i++) {
            if (!currentNode.children.containsKey(charArr[i])) {
                // returning null since there are not such character key value node
                return null;
            } else if (currentNode.children.containsKey(charArr[i])) {
                // setting the currentNode to that character key value node
                currentNode = currentNode.children.get(charArr[i]);
            }
        }

        // passing the last node of prefix character (currentNode) to the helper method
        suggestions(currentNode, prefix, suggestions);
        return suggestions;
    }

    /* helper method */
    private void suggestions(TrieNode currentNode, String prefix, Set<String> suggestions) {
        // checks if the given node is a word, if so it adds the word to the set
        if (currentNode.isWord) {
            suggestions.add(prefix);
        }

        // iterating over the children of the currentNode
        for (TrieNode node : currentNode.children.values()) {
            // add the characters of the node to the prefix
            prefix += node.element;
            suggestions(node, prefix, suggestions);
        }
    }

    // private TrieNode inner class
    private class TrieNode {
        private boolean isWord;
        private Character element;
        private Map<Character, TrieNode> children;

        public TrieNode(char element) {
            this.isWord = false;
            this.element = element;
            this.children = new HashMap<Character, TrieNode>();
        }

        @Override
        public String toString() {
            return "[" + this.element + "]";
        }
    }

    @Override
    public String toString() {
        return recursiveString(root, 0);
    }

    private String recursiveString(TrieNode current, int level) {
        String levelString = "";
        if (current.children.size() > 0) {
            Set<Character> chars = current.children.keySet();
            String tabs = "";
            for (int i = 0; i < level; i++)
                tabs += "\t";

            for (Character c : chars) {
                TrieNode child = current.children.get(c);
                levelString += tabs + " [" + c + "]";
                if (child.element != null)
                    levelString += " >> " + child.element;
                levelString += "\n";
                levelString += recursiveString(child, level + 1);
            }
        }
        return levelString;
    }

    public static void main(String[] args) {
        System.out.println("============= trie test =============");
        Trie trie = new Trie();

        /******************** testing add() method ********************/
        trie.add("dog");
        // trie.add("doge");
        // trie.add("dragon");

        trie.add("badger");
        trie.add("bear");

        trie.add("eagle");
        trie.add("elephant");

        trie.add("snake");
        trie.add("shark");
        trie.add("salamander");
        trie.add("seahorse");

        trie.add("lion");
        trie.add("leopard");

        trie.add("tiger");

        trie.add("panda");

        trie.add("jaguar");
        trie.add("jellyfish");

        // trie.add(null);

        /******************** testing contains() method ********************/

        // System.out.println("contains dog: " + trie.contains("dog"));
        // System.out.println("contains badger: " + trie.contains("badger"));
        // System.out.println("contains bear: " + trie.contains("bear"));
        // System.out.println("contains eagle: " + trie.contains("eagle"));
        // System.out.println("contains elephant: " + trie.contains("elephant"));
        // System.out.println("contains snake: " + trie.contains("snake"));
        // System.out.println("contains shark: " + trie.contains("shark"));
        // System.out.println("contains salamander: " + trie.contains("salamander"));
        // System.out.println("contains seahorse: " + trie.contains("seahorse"));
        // System.out.println("contains lion: " + trie.contains("seahorse"));
        // System.out.println("contains leopard: " + trie.contains("leopard"));
        // System.out.println("contains tiger: " + trie.contains("tiger"));
        // System.out.println("contains panda: " + trie.contains("panda"));
        // System.out.println("contains jaguar: " + trie.contains("jaguar"));
        // System.out.println("contains jellyfish: " + trie.contains("jellyfish"));
        // System.out.println("contains null: " + trie.contains(null));

        /******************** testing remove() method ********************/
        // System.out.println("contains dog: " + trie.contains("dog"));
        // System.out.println("is dog removed: " + trie.remove("dog"));
        // System.out.println("contains dog: " + trie.contains("dog"));

        // System.out.println("is dogman removed: " + trie.remove("dogman"));
        // System.out.println("contains dog: " + trie.contains("dogman"));

        // System.out.println(trie);

        /******************** testing removeAll() method ********************/
        // System.out.println(trie.removeAll("e"));
        // System.out.println(trie.contains("eagle"));
        // System.out.println(trie.contains("elephant"));

        // System.out.println(trie.removeAll("z")); // no word with z character exists

        /******************** testing suggestions() method ********************/
        // System.out.println(trie.suggestions("e"));
        // System.out.println(trie.suggestions("s"));
        // System.out.println(trie.suggestions("d"));

        /******************** testing startsWith() method ********************/
        // System.out.println(trie.startsWith("do"));
        // System.out.println(trie.startsWith("pan"));
        // System.out.println(trie.startsWith("afd")); // does not exist in trie

        System.out.println(trie);
        System.out.println("=====================================");

    }
}
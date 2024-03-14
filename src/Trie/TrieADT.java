package Trie;

import java.util.Set;

/**
 * @author Ajit Singh
 * 
 */

public interface TrieADT {
    public boolean add(String element);

    public boolean remove(String element);

    public boolean contains(String element);

    public boolean removeAll(String prefix);

    public boolean startsWith(String prefix);

    public Set<String> suggestions(String prefix);
}

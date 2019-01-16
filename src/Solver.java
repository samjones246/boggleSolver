import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver {
    String[][] board;
    List<String> dictionary;
    int size;
    Solver() throws IOException{
        loadDictionary();
    }
    public List<String> solve(String[][] board){
        this.board = board;
        size = board[0].length;
        List<String> solution = new ArrayList<>();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                List<String> possible = new ArrayList<>();
                List<String> words = new ArrayList<>();
                List<Pair<Integer>> visited = new ArrayList<>();
                possible.addAll(dictionary);
                possible.removeAll(solution);
                String initial = board[i][j];
                solution.addAll(findWords(words, possible, visited, initial, new Pair<>(i, j)));
            }
        }
        solution.sort(Comparator.comparingInt(String::length));
        Set<String> t = new LinkedHashSet<>();
        t.addAll(solution);
        solution.clear();
        solution.addAll(t);
        return solution;
    }
    private void loadDictionary() throws IOException{
        dictionary = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
        String line;
        while((line=reader.readLine())!=null){
            dictionary.add(line);
        }
    }
    private List<String> findWords(List<String> words, List<String> possible, List<Pair<Integer>> visited, String word, Pair<Integer> pos){
        visited.add(pos);
        possible.removeIf(s -> s.length() < word.length() || !(s.substring(0, word.length()).equals(word)));
        if(possible.isEmpty()){
            visited.remove(pos);
            return words;
        }else{
            for (String possibleWord : possible){
                if(word.equals(possibleWord)){
                    words.add(word);
                    break;
                }
            }
            for (Pair<Integer> child : getChildren(pos)){
                if(!visited.contains(child)) {
                    int row = child.getA();
                    int column = child.getB();
                    String nextLetter = board[row][column];
                    List<String> _possible = new ArrayList<>();
                    _possible.addAll(possible);
                    findWords(words, _possible, visited,word + nextLetter, child);
                }
            }
            visited.remove(pos);
            return words;
        }
    }
    private List<Pair<Integer>> getChildren(Pair<Integer> pos){
        List<Pair<Integer>> children = new ArrayList<>();
        int row = pos.getA();
        int column = pos.getB();
        if(row<size-1)
            children.add(new Pair<>(row + 1, column));
        if(row>0)
            children.add(new Pair<>(row - 1, column));
        if(column<size-1)
            children.add(new Pair<>(row, column + 1));
        if(column>0)
            children.add(new Pair<>(row, column - 1));
        if(row>0&&column>0)
            children.add(new Pair<>(row - 1, column - 1));
        if(row>0&&column<size-1)
            children.add(new Pair<>(row - 1, column + 1));
        if(row<size-1&&column>0)
            children.add(new Pair<>(row + 1, column - 1));
        if(row<size-1&&column<size-1)
            children.add(new Pair<>(row + 1, column + 1));
        return children;
    }

}

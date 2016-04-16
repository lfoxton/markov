package markov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarkovWord {

    private static final Random RANDOM = new Random();

    public String word;
    public List<MarkovWord> followingWords;

    public MarkovWord(String word) {
        this.word = word;
        followingWords = new ArrayList<MarkovWord>();
    }

    public void addFollowingWord(MarkovWord markovWord) {
        followingWords.add(markovWord);
    }

    public MarkovWord getNextMarkovWord() {
        if (followingWords.size() == 0) {
            return null;
        } else {
            int index =  RANDOM.nextInt(followingWords.size());
            return followingWords.get(index);
        }
    }

    public String getWord() {
        return word;
    }
}

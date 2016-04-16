package markov.model;

import java.util.HashMap;

public class MarkovText {

    public HashMap<String, MarkovWord> markovWords;
    String lastAddedWord;

    public MarkovText() {
        markovWords = new HashMap<String, MarkovWord>();
        lastAddedWord = null;
    }

    public void addText(String text) {

        for(String word: text.split("\\s+")) {

            // Add the current word to the set
            MarkovWord markovWord = markovWords.get(word);
            if (markovWord == null) {
                markovWord = new MarkovWord(word);
                markovWords.put(word, markovWord);
            }

            // Add this word as a follower to the predecessor
            if (lastAddedWord != null) {
                markovWords.get(lastAddedWord).addFollowingWord(markovWord);
            }
            lastAddedWord = word;
        }
    }

    public String generateSentence(String startingWord, int depth) {

        StringBuilder sb = new StringBuilder();

        // Return nothing if input params are not valid
        MarkovWord currentMarkovWord = markovWords.get(startingWord);
        if (currentMarkovWord == null || depth < 1) {
            return null;
        }

        // Iterate down picking the probable next word 'depth' times (or until we land on the last word)
        for (int i = 0; i < depth; i++) {
            sb.append(currentMarkovWord.getWord()).append(" ");
            currentMarkovWord = currentMarkovWord.getNextMarkovWord();
            if (currentMarkovWord == null) {
                break;
            }
        }
        return sb.toString().trim();
    }

}

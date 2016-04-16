package markov.model;

import markov.model.MarkovText;
import markov.model.MarkovWord;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.theInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@RunWith(SpringJUnit4ClassRunner.class)
public class MarkovTextTest {

    private static final String SOURCE_STRING = "Mary had a little lamb,\n"+
        "His fleece was white as snow,\n"+
        "And everywhere that Mary went,\n"+
        "The lamb was sure to go.\n"+
        "\n"+
        "He followed her to school one day,\n"+
        "Which was against the rule,\n"+
        "It made the children laugh and play\n"+
        "To see a lamb at school.\n"+
        "\n"+
        "And so the teacher turned it out,\n"+
        "But still it lingered near,\n"+
        "And waited patiently about,\n"+
        "Till Mary did appear.\n"+
        "\n"+
        "\"Why does the lamb love Mary so?\"\n"+
        "The eager children cry.\n"+
        "\"Why, Mary loves the lamb, you know,\"\n"+
        "The teacher did reply.";

    private MarkovText markovText;

    @Before
    public void setUp() throws Exception {
        markovText = new MarkovText();
        markovText.addText(SOURCE_STRING);
    }

    @Test
    public void generateTextShouldReturnFirstWordWithDepth1() throws Exception {
        assertThat(markovText.generateSentence("Mary", 1), equalTo("Mary"));
    }

    @Test
    public void generateTextShouldReturnNullWithDepth0() throws Exception {
        assertThat(markovText.generateSentence("Mary", 0), equalTo(null));
    }

    @Test
    public void generateTextShouldReturnNullWithDepthBadSeedWord() throws Exception {
        assertThat(markovText.generateSentence("hakuna matata", 0), equalTo(null));
    }

    @Test
    public void generateTextShouldNeverReturnMoreWordsThanGivenDepth() throws Exception {
        assertThat(markovText.generateSentence("lamb", 10).split(" ").length, lessThanOrEqualTo(10));
    }

    @Test
    public void markovTextShouldReuseMemoryAllocationForWords() throws Exception {
        MarkovWord mary = markovText.markovWords.get("Mary");
        MarkovWord otherMary = markovText.markovWords.get("Till").getNextMarkovWord();
        assertThat(mary, theInstance(otherMary));
    }

    @Test
    public void addTextShouldSplitOnAllWhiteSpaceCharacters() {
        markovText = new MarkovText();
        markovText.addText("some\ttext separated\nby   \t\n   whitespace");
        assertThat(markovText.markovWords.size(), equalTo(5));
    }

    @Test
    public void addTextMultipleTimesShouldAddFollowingWordsToMarkovWords() {
        markovText = new MarkovText();
        markovText.addText("some text");
        markovText.addText("some more text");

        MarkovWord some = markovText.markovWords.get("some");
        MarkovWord more = markovText.markovWords.get("more");
        MarkovWord text = markovText.markovWords.get("text");

        assertThat(some.followingWords.size(), equalTo(2));
        assert(some.followingWords.contains(more));
        assert(some.followingWords.contains(text));

        assertThat(more.followingWords.size(), equalTo(1));
        assert(more.followingWords.contains(text));

        assertThat(text.followingWords.size(), equalTo(1));
        assert(text.followingWords.contains(some));
    }
}

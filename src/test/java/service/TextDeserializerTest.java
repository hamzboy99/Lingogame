package service;

import bep.lingogame.service.WordRefiner;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@VisibleForTesting
@DisplayName("WordRefiner")
public class TextDeserializerTest {
    private static String textFileLocation = "src/test/resources/deserializetext.txt";
    private static List<String> words = Arrays.asList("mand", "manco", "mandir", "mandala", "mandfles", "tussenin", "bedrijfsgoed", "tussenjaar", "tussenkop", "tussen-n", "bedrijfs-pc-netwerk");

    public TextDeserializerTest() {
    }

    @Test
    @DisplayName("Deserialize")
    public void testDeserialize() throws FileNotFoundException {
        final WordRefiner wordRefiner = new WordRefiner();
        final List<String> deserializedText = wordRefiner.refine(textFileLocation);
        assertEquals(words, deserializedText);
    }

    @Test
    @DisplayName("Can't find file")
    public void testFileNotFoundException() {
        final WordRefiner wordRefiner = new WordRefiner();
        assertThrows(FileNotFoundException.class, () -> wordRefiner.refine("text.txt"));
    }
}
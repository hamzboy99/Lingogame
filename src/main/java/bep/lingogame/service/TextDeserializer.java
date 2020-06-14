package bep.lingogame.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class TextDeserializer implements FileDeserializerInterface {

    public TextDeserializer() {
    }

    @Override
    public final List<String> deserialize(final String file) throws FileNotFoundException {
        final ArrayList<String> words = new ArrayList<>();

        final File myObj = new File(file);
        final Scanner myReader = new Scanner(myObj);
        try {
            while (myReader.hasNextLine()) {
                final String data = myReader.nextLine();
                words.add(data);
            }
        } finally {
            myReader.close();
        }
        return words;
    }
}
package bep.lingogame.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class WordRefiner implements WordRefinerInterface {

    public WordRefiner() {
    }

    @Override
    public final List<String> refine(final String file) throws FileNotFoundException {
        final ArrayList<String> allwords = new ArrayList<>();

        final File myObj = new File(file);
        final Scanner myReader = new Scanner(myObj);
        try {
            while (myReader.hasNextLine()) {
                final String data = myReader.nextLine();
                allwords.add(data);
            }
        } finally {
            myReader.close();
        }
        return allwords;
    }
}
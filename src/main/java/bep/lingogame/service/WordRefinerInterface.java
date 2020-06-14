package bep.lingogame.service;

import java.io.FileNotFoundException;
import java.util.List;

public interface WordRefinerInterface {
    List<String> refine(String file) throws FileNotFoundException;
}

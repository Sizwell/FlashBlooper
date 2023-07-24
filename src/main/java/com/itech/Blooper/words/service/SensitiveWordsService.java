package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SensitiveWordsService {

    private final SensitiveWordsRepository sensitiveWordsRepository;

    @Autowired
    public SensitiveWordsService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    public List<SensitiveWords> getSensitiveWords()
    {
        return List.of(
                new SensitiveWords("This is getting real")
        );
    }

    Files files;
    public void saveWordsFromFile(String filePath)
    {
        try {
            Stream<String> lines = files.lines(Paths.get(filePath));
            lines.forEach(this::saveWords);
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void saveWords(String word)
    {
        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWords.setWords(word);
        sensitiveWordsRepository.save(sensitiveWords);
    }

    public static void processAndWriteToFile(String inputFilePath, String outputFilePath) {
        List<String> modifiedStrings = readAndProcessFile(inputFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Write each modified string to the output file
            for (String str : modifiedStrings) {
                writer.write(str);
                writer.newLine();
            }
            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAndProcessFile(String filePath) {
        List<String> modifiedStrings = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read the entire content of the file into a single string
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
            }
            String fileContent = contentBuilder.toString();

            // Extract the array of quoted strings from the content
            int startIndex = fileContent.indexOf('[');
            int endIndex = fileContent.lastIndexOf(']');
            String arrayContent = fileContent.substring(startIndex + 1, endIndex);

            // Split the array content into individual quoted strings
            String[] quotedStrings = arrayContent.split(",");

            // Remove the square brackets and quotes from each string and add to the new list
            for (String str : quotedStrings) {
                modifiedStrings.add(str.trim().replaceAll("[\"\\s]", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modifiedStrings;
    }
}

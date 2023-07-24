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
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class SensitiveWordsService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(SensitiveWordsService.class.getName());

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

    public void processAndWriteToFile(String inputFilePath) {
        List<String> modifiedStrings = readAndProcessFile(inputFilePath);

        // Save the modified strings to the database
        for (String str : modifiedStrings) {
            SensitiveWords sensitiveWords = new SensitiveWords();
            sensitiveWords.setWords(str);
            sensitiveWordsRepository.save(sensitiveWords);
        }
    }

    public List<String> readAndProcessFile(String filePath) {
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

    public void addNewWord(SensitiveWords sensitiveWords)
    {
        Optional<SensitiveWords> sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(sensitiveWords.getWords());
        if (sensitiveWordsOptional.isPresent())
        {
            logger.warning("Word already exists...");
        }
        else {
            sensitiveWordsRepository.save(sensitiveWords);
            logger.info("Word added to Database...");
        }
    }

}

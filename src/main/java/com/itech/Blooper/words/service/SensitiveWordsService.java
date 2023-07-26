package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

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
        List<SensitiveWords> retrieveAll = sensitiveWordsRepository.findAll();
        if (retrieveAll.isEmpty())
        {
            logger.info("Database contains no Sensitive Words");
        }
        return sensitiveWordsRepository.findAll();
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

    Optional<SensitiveWords> sensitiveWordsOptional;
    public void addNewWord(String newWord)
    {
        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(sensitiveWords.getWords());
        if (sensitiveWordsOptional.isPresent())
        {
            logger.warning("Word already exists...");
        }
        else {
            sensitiveWords.setWords(newWord.toUpperCase());
            sensitiveWordsRepository.save(sensitiveWords);
            logger.fine("Word added to Database...");
        }
    }
}

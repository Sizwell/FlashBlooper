package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UploadWordsService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(UploadWordsService.class.getName());

    @Autowired
    public UploadWordsService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    public void processFile(String inputFilePath) {
        List<String> modifiedStrings = readAndProcessFile(inputFilePath);

        Optional<SensitiveWords> sensitiveWordsOptional;

        // Save the modified list to the database
        for (String userInput : modifiedStrings) {
            SensitiveWords sensitiveWords = new SensitiveWords();
            String str = userInput.replaceAll("\\s+", "");
            sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(str);
            if (sensitiveWordsOptional.isPresent())
            {
                logger.info("Word " + "'" + str + "'" + " already exists in Database. Skipping...");
            } else {
                sensitiveWords.setWords(str);
                sensitiveWordsRepository.save(sensitiveWords);
            }
        }
        logger.fine("Successfully Uploaded Sensitive words to the Database");
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

}

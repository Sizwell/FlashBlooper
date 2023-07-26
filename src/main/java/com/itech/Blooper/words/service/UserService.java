package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    public List<String> userRequest(String userInput)
    {
        Optional<SensitiveWords> sensitiveWordsOptional;

        sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(userInput);
        if (sensitiveWordsOptional.isEmpty())
        {
            logger.info("Word not found!");
        }
        logger.info("Checking input...");

        List<String> input = Arrays.asList(userInput.split("\\s+"));
        List<String> output = new ArrayList<>();
        String response = null;

        int numberOfWords = 0;
        while (numberOfWords < input.size()) {
            logger.info("User input word count: " + input.size());
            sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(input.get(numberOfWords));

            if (sensitiveWordsOptional.isPresent())
            {
                int characterCount = 0;
                String sensitiveWord = input.get(numberOfWords);
                for (int i = 0; i < sensitiveWord.length(); i++) {
                    if (input.get(numberOfWords).charAt(characterCount) != ' ')
                    {
                        characterCount ++;
                    }
                }

                String bloop = "*".repeat(characterCount);

                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).equals(sensitiveWord))
                    {
                        input.set(i, bloop);
                        characterCount ++;
                    }
                }

                response = String.join(" ", input);
                logger.info(response);

            }
            numberOfWords ++;
        }
        output.add(response);
        return output;
    }
}

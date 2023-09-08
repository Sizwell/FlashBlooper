package com.itech.Blooper.user.service;

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
public class UserRequestService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(UserRequestService.class.getName());

    @Autowired
    public UserRequestService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    public List<String> userRequest(String userInput)
    {
        Optional<SensitiveWords> sensitiveWordsOptional;

        List<String> input = Arrays.asList(userInput.split("\\s+"));
        List<String> output = new ArrayList<>();
        String response = null;

        int numberOfWords = 0;
        logger.info("User input word count: " + input.size());
        while (numberOfWords < input.size()) {

            String words = input.get(numberOfWords);
            sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(words);

            if (sensitiveWordsOptional.isPresent())
            {
                int characterCount = 0;
                for (int i = 0; i < words.length(); i++) {
                    if (input.get(numberOfWords).charAt(characterCount) != ' ')
                    {
                        characterCount ++;
                    }
                }

                String bloop = "*".repeat(characterCount);

                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).equals(words))
                    {
                        input.set(i, bloop);
                        characterCount ++;
                    }
                }



            }
            response = String.join(" ", input);
            numberOfWords ++;
        }
        output.add(response);
        return output;
    }
}

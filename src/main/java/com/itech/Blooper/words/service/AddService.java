package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AddService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(AddService.class.getName());

    @Autowired
    public AddService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    Optional<SensitiveWords> sensitiveWordsOptional;
    public SensitiveWords addNewWord(String userRequest)
    {
        /*
        Remove any white spaces between strings before processing
         */
        String newWord = userRequest.replaceAll("\\s+", "");

        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(newWord);
        if (sensitiveWordsOptional.isPresent())
        {
            logger.warning("Word " + "'" + newWord + "'" + " already exists in Database...");
        }
        else {
            sensitiveWords.setWords(newWord.toUpperCase());
            sensitiveWordsRepository.save(sensitiveWords);
            logger.fine("Word added to Database...");
        }
        return sensitiveWords;
    }

}

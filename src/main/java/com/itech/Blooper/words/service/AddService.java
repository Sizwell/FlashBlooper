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

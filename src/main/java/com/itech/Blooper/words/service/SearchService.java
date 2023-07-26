package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SearchService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(SearchService.class.getName());

    @Autowired
    public SearchService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }
    Optional<SensitiveWords> sensitiveWordsOptional;

    public List<SensitiveWords> searchWord(String searchWord)
    {
        sensitiveWordsOptional = sensitiveWordsRepository.findSensitiveWordsByWords(searchWord);
        if (sensitiveWordsOptional.isEmpty())
        {
            logger.info("Word not found!");
        }
        return sensitiveWordsRepository.findByWords(searchWord);
    }
}

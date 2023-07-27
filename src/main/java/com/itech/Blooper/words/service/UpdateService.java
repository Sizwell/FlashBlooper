package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class UpdateService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(UpdateService.class.getName());

    @Autowired
    public UpdateService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    @Transactional
    public void updateWord(Long id, String searchWord)
    {
        SensitiveWords sensitiveWords;
        sensitiveWords = sensitiveWordsRepository
                .findById(id)
                .orElseThrow(()-> new IllegalStateException("Word with ID " + "'" +  id + "'" + " does not exist"));

        //Check if the search word is not null, word has characters and is not equal to word already in DB
        if (searchWord != null && searchWord.length() > 0 && !Objects.equals(sensitiveWords.getWords(), searchWord))
        {
            sensitiveWords.setWords(searchWord);
            sensitiveWordsRepository.save(sensitiveWords);
            logger.fine("Word successfully Updated.");
        }
    }
}

package com.itech.Blooper.words.service;

import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

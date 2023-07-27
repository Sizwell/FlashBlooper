package com.itech.Blooper.words.service;

import com.itech.Blooper.words.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class DeleteService {

    private final SensitiveWordsRepository sensitiveWordsRepository;
    private static final Logger logger = Logger.getLogger(DeleteService.class.getName());

    @Autowired
    public DeleteService(SensitiveWordsRepository sensitiveWordsRepository) {
        this.sensitiveWordsRepository = sensitiveWordsRepository;
    }

    public void deleteWord(Long id)
    {
        boolean exists = sensitiveWordsRepository.existsById(id);
        if (!exists)
        {
            logger.info("Word with ID " + "'" + id + "'" + " does not exist.");
        } else {
            logger.warning("Deleting Word with ID " + "'" + id + "'");
            sensitiveWordsRepository.deleteById(id);
        }
    }
}

package com.itech.Blooper.words.controller;

import com.itech.Blooper.words.service.SensitiveWordsService;
import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SensitiveWordsController {

    private final SensitiveWordsService sensitiveWordsService;

    @Autowired
    public SensitiveWordsController(SensitiveWordsService sensitiveWordsService) {
        this.sensitiveWordsService = sensitiveWordsService;
    }

    @GetMapping("/home")
    public List<SensitiveWords> getWords()
    {
        return sensitiveWordsService.getSensitiveWords();
    }

    @PostMapping("/save_processed_words")
    public void saveFromFile()
    {
        String filePath = "C:\\Users\\SizweNcikana\\IdeaProjects\\flash\\Bloop\\src\\main\\resources\\data\\sql_sensitive_list.txt";
        sensitiveWordsService.processAndWriteToFile(filePath);
    }
}

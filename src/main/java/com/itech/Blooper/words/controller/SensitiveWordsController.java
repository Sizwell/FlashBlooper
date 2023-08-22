package com.itech.Blooper.words.controller;

import com.itech.Blooper.words.service.*;
import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SensitiveWordsController {

    private final SensitiveWordsService sensitiveWordsService;
    private final UserInputService userInputService;
    private final DeleteService deleteService;
    private final UpdateService updateService;
    private final SearchService searchService;
    private final AddService addService;
    private final UploadWordsService uploadWordsService;

    @Autowired
    public SensitiveWordsController(
            SensitiveWordsService sensitiveWordsService,
            UserInputService userInputService,
            DeleteService deleteService,
            UpdateService updateService,
            SearchService searchService,
            AddService addService, UploadWordsService uploadWordsService)
    {
        this.sensitiveWordsService = sensitiveWordsService;
        this.userInputService = userInputService;
        this.deleteService = deleteService;
        this.updateService = updateService;
        this.searchService = searchService;
        this.addService = addService;
        this.uploadWordsService = uploadWordsService;
    }

    SensitiveWords sensitiveWords;
    @GetMapping("/all_words")
    public List<SensitiveWords> getWords()
    {
        return sensitiveWordsService.getSensitiveWords();
    }

    @PostMapping("/upload_words")
    public ResponseEntity<String> saveFromFile()
    {
        String filePath = "src/main/resources/data/sql_sensitive_list.txt";
        try {
            uploadWordsService.processFile(filePath);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded Sensitive words to  Database");
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading words \nReason: " + e.getMessage());
        }

    }

    @PostMapping("/add_new_word")
    public ResponseEntity <String> addWord(@RequestParam("word") String newWord)
    {
        try {
            sensitiveWords = addService.addNewWord(newWord.toUpperCase());
            return new ResponseEntity<>("New Word saved to Database with ID " +
                    sensitiveWords.getId(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating Word \nReason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search_word")
    public List<SensitiveWords> returnOneWord(@RequestParam("word") String searchWord)
    {
        return searchService.searchWord(searchWord.toUpperCase());
    }

    @PutMapping("/update_word")
    public void updateWord(@RequestBody SensitiveWords sensitiveWords)
    {
        updateService.updateWord(sensitiveWords.getId(), sensitiveWords.getWords().toUpperCase());
    }

    @DeleteMapping("/delete_word/{id}")
    public void deleteWordById(@PathVariable("id") Long id)
    {
        deleteService.deleteWord(id);
    }

    @PostMapping("/process_user_input")
    public List<String> userInput(@RequestParam("words") String userInput)
    {
        return userInputService.userRequest(userInput.toUpperCase());
    }
}

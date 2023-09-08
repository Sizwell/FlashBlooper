package com.itech.Blooper.words.controller;

import com.itech.Blooper.exception.WordNotFoundException;
import com.itech.Blooper.words.service.*;
import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v2/sensitiveWords")
public class SensitiveWordsController {

    private final SensitiveWordsService sensitiveWordsService;
    private final DeleteService deleteService;
    private final UpdateService updateService;
    private final SearchService searchService;
    private final AddService addService;
    private final UploadWordsService uploadWordsService;

    @Autowired
    public SensitiveWordsController(
            SensitiveWordsService sensitiveWordsService,
            DeleteService deleteService,
            UpdateService updateService,
            SearchService searchService,
            AddService addService, UploadWordsService uploadWordsService)
    {
        this.sensitiveWordsService = sensitiveWordsService;
        this.deleteService = deleteService;
        this.updateService = updateService;
        this.searchService = searchService;
        this.addService = addService;
        this.uploadWordsService = uploadWordsService;
    }

    SensitiveWords sensitiveWords;
    @GetMapping("/allWords")
    public List<SensitiveWords> getWords()
    {
        return sensitiveWordsService.getSensitiveWords();
    }

    @PostMapping("/upload")
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

    @PostMapping("/word")
    public ResponseEntity <String> addWord(@RequestParam("word") String newWord)
    {
        try {
            sensitiveWords = addService.addNewWord(newWord.toUpperCase());
            return new ResponseEntity<>("New Word saved to Database with ID " +
                    sensitiveWords.getId(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating Word \nReason: " +
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/word/search")
    public ResponseEntity<List<SensitiveWords>> returnOneWord(@RequestParam("word") String searchWord)
    {
        try {
            List<SensitiveWords> words = searchService.searchWord(searchWord.toUpperCase());
            return new ResponseEntity<>(words, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/word")
    public ResponseEntity<String> updateWord(@RequestBody SensitiveWords sensitiveWords)
    {
        try {

            updateService.updateWord(sensitiveWords.getId(), sensitiveWords.getWords().toUpperCase());
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated word...");
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred \nReason: " +
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/word/{id}")
    public ResponseEntity<String> deleteWordById(@PathVariable("id") Long id)
    {
        try {
            deleteService.deleteWord(id);
            return new ResponseEntity<>("Word with ID " + "'" + id + "'" + " deleted", HttpStatus.OK);
        }
        catch (WordNotFoundException wnf) {
            return new ResponseEntity<>(wnf.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Unable to delete Word with ID "  + "'" + id + "'" + ". \nReason: " +
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

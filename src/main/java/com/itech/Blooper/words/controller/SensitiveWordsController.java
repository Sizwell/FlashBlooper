package com.itech.Blooper.words.controller;

import com.itech.Blooper.words.service.*;
import com.itech.Blooper.words.entity.SensitiveWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SensitiveWordsController {

    private final SensitiveWordsService sensitiveWordsService;
    private final UserService userService;
    private final DeleteService deleteService;
    private final UpdateService updateService;
    private final SearchService searchService;
    private final AddService addService;

    @Autowired
    public SensitiveWordsController(
            SensitiveWordsService sensitiveWordsService,
            UserService userService,
            DeleteService deleteService,
            UpdateService updateService,
            SearchService searchService,
            AddService addService)
    {
        this.sensitiveWordsService = sensitiveWordsService;
        this.userService = userService;
        this.deleteService = deleteService;
        this.updateService = updateService;
        this.searchService = searchService;
        this.addService = addService;
    }

    @GetMapping("/all_words")
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

    @PostMapping("/add_new_word")
    public void addWord(@RequestParam("word") String newWord)
    {
        addService.addNewWord(newWord.toUpperCase());
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

    @DeleteMapping("/delete_word_by_id/{id}")
    public void deleteWordById(@PathVariable("id") Long id)
    {
        deleteService.deleteWord(id);
    }

    @PostMapping("/process_input")
    public List<String> userInput(@RequestParam("words") String userInput)
    {
        return userService.userRequest(userInput.toUpperCase());
    }
}

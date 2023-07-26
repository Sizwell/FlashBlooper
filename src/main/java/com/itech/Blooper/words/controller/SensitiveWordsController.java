package com.itech.Blooper.words.controller;

import com.itech.Blooper.words.service.SensitiveWordsService;
import com.itech.Blooper.words.entity.SensitiveWords;
import com.itech.Blooper.words.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SensitiveWordsController {

    private final SensitiveWordsService sensitiveWordsService;
    private final UserService userService;

    @Autowired
    public SensitiveWordsController(SensitiveWordsService sensitiveWordsService, UserService userService) {
        this.sensitiveWordsService = sensitiveWordsService;
        this.userService = userService;
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
        sensitiveWordsService.addNewWord(newWord.toUpperCase());
    }

    @GetMapping("/search_word")
    public List<SensitiveWords> returnOneWord(@RequestParam("word") String searchWord)
    {
        return sensitiveWordsService.searchWord(searchWord.toUpperCase());
    }

    @PutMapping("/update_word")
    public void updateWord(@RequestBody SensitiveWords sensitiveWords)
    {
        sensitiveWordsService.updateWord(sensitiveWords.getId(), sensitiveWords.getWords());
    }

    @DeleteMapping("/delete_word_by_id/{id}")
    public void deleteWordById(@PathVariable("id") Long id)
    {
        sensitiveWordsService.deleteWord(id);
    }

    @DeleteMapping("/search_and_delete_word")
    public void deleteWord(@RequestParam("word") String searchWord)
    {
        sensitiveWordsService.searchAndDelete(searchWord.toUpperCase());
    }

    @PostMapping("/process_input")
    public List<String> userInput(@RequestParam("words") String userInput)
    {
        return userService.userRequest(userInput.toUpperCase());
    }
}

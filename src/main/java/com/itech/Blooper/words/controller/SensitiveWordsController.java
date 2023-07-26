package com.itech.Blooper.words.controller;

import com.itech.Blooper.words.service.DeleteService;
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
    private final DeleteService deleteService;

    @Autowired
    public SensitiveWordsController(SensitiveWordsService sensitiveWordsService, UserService userService, DeleteService deleteService) {
        this.sensitiveWordsService = sensitiveWordsService;
        this.userService = userService;
        this.deleteService = deleteService;
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
        deleteService.deleteWord(id);
    }

    @PostMapping("/process_input")
    public List<String> userInput(@RequestParam("words") String userInput)
    {
        return userService.userRequest(userInput.toUpperCase());
    }
}

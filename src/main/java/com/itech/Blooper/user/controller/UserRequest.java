package com.itech.Blooper.user.controller;

import com.itech.Blooper.user.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v2")
public class UserRequest {

    private final UserRequestService userRequestService;

    @Autowired
    public UserRequest(UserRequestService userRequestService) {
        this.userRequestService = userRequestService;
    }

    @PostMapping("/user_request")
    public List<String> userInput(@RequestParam("words") String userInput)
    {
        return userRequestService.userRequest(userInput.toUpperCase());
    }
}

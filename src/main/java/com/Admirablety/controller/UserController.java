package com.Admirablety.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Admirablety.model.Rating;
import com.Admirablety.model.User;
import com.Admirablety.service.TweetService;
import com.Admirablety.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;
    
    private void setTweetCounts(List<User> users, Model model) {
        HashMap<String,Integer> tweetCounts = new HashMap<>();
        for (User user : users) {
            List<Rating> tweets = tweetService.findAllByUser(user);
            tweetCounts.put(user.getUsername(), tweets.size());
        }
        model.addAttribute("tweetCounts", tweetCounts);
    }
    
    @GetMapping(value = "/users/{username}")
    	public String displayUser(@PathVariable(value="username") String username, Model model) {	
    	    User user = userService.findByUsername(username);
    	    List<Rating> tweets = tweetService.findAllByUser(user);
    	    model.addAttribute("tweetList", tweets);
    	    model.addAttribute("user", user);
    	    return "user";
    	}
    
    @GetMapping(value = "/users")
    public String displayUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        setTweetCounts(users, model);
        return "users";
    }
    
}

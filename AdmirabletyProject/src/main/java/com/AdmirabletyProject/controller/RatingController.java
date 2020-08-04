package com.AdmirabletyProject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.AdmirabletyProject.model.Rating;
import com.AdmirabletyProject.model.User;
import com.AdmirabletyProject.service.RatingService;
import com.AdmirabletyProject.service.UserService;

@Controller
public class RatingController {
    @Autowired
    private UserService userService;
	
    @Autowired
    private RatingService tweetService;
    
    @GetMapping(value= {"/tweets", "/"})
    public String displayFeed(Model model){
        List<Rating> tweets = tweetService.findAll();
        model.addAttribute("tweetList", tweets);
        return "feed";  //looks for a file called "feed", which is feed.html
    }
    
    @GetMapping(value = "/tweets/new")
    public String displayTweetForm(Model model) {
        model.addAttribute("tweet", new Rating());
        return "newTweet";
    }
    
    @PostMapping(value = "/tweets")
    public String submitTweetForm(@Valid Rating tweet, BindingResult bindingResult, Model model) {
        User user = userService.getLoggedInUser();
        if (!bindingResult.hasErrors()) {
            tweet.setUser(user);
            tweetService.save(tweet);
            model.addAttribute("successMessage", "Tweet successfully created!");
            model.addAttribute("tweet", new Rating());
        }
        return "newTweet";
    }
}

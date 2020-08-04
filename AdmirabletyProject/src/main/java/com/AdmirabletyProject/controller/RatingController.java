package com.AdmirabletyProject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private RatingService ratingService;
    
    @GetMapping(value= {"/tweets", "/"})
    public String displayFeed(Model model){
        List<Rating> tweets = ratingService.findAll();
        model.addAttribute("tweetList", tweets);
        return "feed"; 
    }
    
    @GetMapping(value = "/tweets/new")
    public String displayTweetForm(Model model) {
        model.addAttribute("tweet", new Rating());
        return "newTweet";
    }
    
    @PostMapping(value = "/ratings")
    public String submitRating(@Valid Rating rating, BindingResult bindingResult, Model model) {
        User rater = userService.getLoggedInUser();
        if (!bindingResult.hasErrors()) {
            rating.setRater(rater);
            ratingService.save(rating);
            model.addAttribute("successMessage", "Rating assigned");
            model.addAttribute("rating", new Rating());
        }
        return "newRating";
    }
    
    @PostMapping(value = "/rate/{username}")
    public String rate(@Valid Rating rating, @PathVariable(value="username") String username, 
                         HttpServletRequest request) {
    	User rater = userService.getLoggedInUser();
    	User subject = userService.findByUsername(username);
    	List<User> trackers = subject.getTrackers();
    	trackers.add(rater);
    	subject.setTrackers(trackers);
        userService.save(subject);
        ratingService.save(rating);
        return "redirect:" + request.getHeader("Referer");

    }
}

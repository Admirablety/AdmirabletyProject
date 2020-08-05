package com.AdmirabletyApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AdmirabletyApp.model.Rating;
import com.AdmirabletyApp.model.User;
import com.AdmirabletyApp.service.UserService;



@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/users/{username}")
	public String displayUser(@PathVariable(value="username") String username, Model model) {	
	    User user = userService.findByUsername(username);

	    User loggedInUser = userService.getLoggedInUser();
        List<User> tracking = loggedInUser.getTracking();
        boolean isTracking = false;
        for (User trackedUser : tracking) {
            if (trackedUser.getUsername().equals(username)) {
                isTracking = true;
            }
        }
        model.addAttribute("tracking", isTracking);
        
        boolean isSelfPage = loggedInUser.getUsername().equals(username);
        model.addAttribute("isSelfPage", isSelfPage);
	    
	    model.addAttribute("user", user);
	    return "user";
	}

	// List of users logged-in user is tracking, else a list of all users
	@GetMapping(value = "/users")
	public String getUsers(@RequestParam(value="filter", required=false) 
		String filter, Model model) {
		List<User> users = new ArrayList<User>();
		User loggedInUser = userService.getLoggedInUser();
		List<User> usersTracking = loggedInUser.getTracking();
		if (filter == null) {
		    filter = "all";
		}
		if (filter.equalsIgnoreCase("tracking")) {
		    users = usersTracking;
		    model.addAttribute("filter", "tracking");
		}
		else {
		    users = userService.findAll();
		    model.addAttribute("filter", "all");
		}
		model.addAttribute("users", users);
		SetTrackingStatus(users, usersTracking, model);
		return "users";
	}
	
	
	private void SetTrackingStatus(List<User> users, List<User> usersTracking, Model model) {
	    HashMap<String,Boolean> trackingStatus = new HashMap<>();
	    String username = userService.getLoggedInUser().getUsername();
	    for (User user : users) {
	        if(usersTracking.contains(user)) {
	            trackingStatus.put(user.getUsername(), true);
	        }else if (!user.getUsername().equals(username)) {
	            trackingStatus.put(user.getUsername(), false);
	    	}
	    }
	    model.addAttribute("trackingStatus", trackingStatus);
	}
    
    // to rate a user
    @PostMapping(value = "/rate/{username}/{stars}")
    public String rate(String subjectUsername, Integer stars, HttpServletRequest request) {
    	User rater = userService.getLoggedInUser();
    	User subject = userService.findByUsername(subjectUsername);
    	Rating newRating = new Rating();
    	newRating.rater = rater;
    	newRating.stars_given = stars;		
    	ArrayList<Rating> tempAL = subject.getRatingsRecieved();
    	tempAL.add(newRating);
    	subject.setRatingsRecieved(tempAL);
        return "redirect:" + request.getHeader("Referer");

    }
    

    
    /*
    //Get method for the last five ratings given to the user as the subject
    @GetMapping
    public String getSubjectsLastFive(User subject, Model model) {
    	List<Rating> ratings = ratingService.findLastFiveForSubject(subject);
    	model.addAttribute("subjectsRatingList",ratings); 
        return "subjectsLastFive";
    }
    
  //Get method for the last five ratings given by the user as the rater
    @GetMapping
    public String getRatersLastFive(User rater, Model model) {
    	List<Rating> ratings = ratingService.findLastFiveForRater(rater);
    	model.addAttribute("ratersRatingList",ratings); 
        return "ratersLastFive";
    }
    */

    
    
    
    
    
    /*
    
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
    
    */
    
}
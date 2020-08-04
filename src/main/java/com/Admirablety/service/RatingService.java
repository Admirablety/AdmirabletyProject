package com.Admirablety.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Admirablety.model.Rating;
import com.Admirablety.model.User;
import com.Admirablety.repository.RatingRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository tweetRepository;
    
    // method to recalculate the weighted average rating of a user every time there is a new or updated rating for that User

    public List<Rating> findAll() {
        List<Rating> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
        return tweets;
    }
	
    public List<Rating> findAllByUser(User user) {
        List<Rating> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
        return tweets;
    }
	
    public List<Rating> findAllByUsers(List<User> users){
        List<Rating> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
        return tweets;
    }
	
    public void save(Rating tweet) {
        tweetRepository.save(tweet);
    }
}

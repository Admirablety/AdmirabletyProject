package com.AdmirabletyProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AdmirabletyProject.model.Rating;
import com.AdmirabletyProject.model.User;
import com.AdmirabletyProject.repository.RatingRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository tweetRepository;

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

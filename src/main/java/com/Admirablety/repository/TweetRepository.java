package com.Admirablety.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Admirablety.model.Rating;
import com.Admirablety.model.User;

@Repository
public interface TweetRepository extends CrudRepository<Rating, Long> {
    List<Rating> findAllByOrderByCreatedAtDesc();
    List<Rating> findAllByUserOrderByCreatedAtDesc(User user);
    List<Rating> findAllByUserInOrderByCreatedAtDesc(List<User> users);
}
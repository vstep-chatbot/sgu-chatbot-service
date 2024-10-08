package com.sguchatbot.backend.repository;

import com.sguchatbot.backend.entity.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<Score, String> {
}

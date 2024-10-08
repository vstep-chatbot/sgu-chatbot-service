package com.sguchatbot.backend.repository;

import com.sguchatbot.backend.entity.Contestant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContestantRepository extends MongoRepository<Contestant, String> {
}

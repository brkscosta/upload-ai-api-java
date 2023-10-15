package com.jsancosta.uploadai.repositories;

import com.jsancosta.uploadai.models.prompt.Prompt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IPromptRepository extends MongoRepository<Prompt, String> { }

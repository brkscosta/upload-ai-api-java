package com.jsancosta.uploadai.services.dataaccess;

import com.jsancosta.uploadai.models.prompt.IPromptDAO;
import com.jsancosta.uploadai.models.prompt.Prompt;
import com.jsancosta.uploadai.repositories.IPromptRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("prompt")
public class PromptMongoDBDataAccessService implements IPromptDAO {

    private final IPromptRepository promptRepository;

    public PromptMongoDBDataAccessService(IPromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    @Override
    public List<Prompt> lisAllPrompts() {
        return promptRepository.findAll();
    }

    @Override
    public Optional<Prompt> findPromptById(String id) {
        return promptRepository.findById(id);
    }

    @Override
    public void addPrompt(Prompt prompt) {
        this.promptRepository.save(prompt);
    }

    @Override
    public List<Prompt> searchByTitle(String title) {
        return this.promptRepository.findAll();
    }

}

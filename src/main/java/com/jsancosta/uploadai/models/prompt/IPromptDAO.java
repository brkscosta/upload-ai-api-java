package com.jsancosta.uploadai.models.prompt;

import com.jsancosta.uploadai.models.prompt.Prompt;
import com.jsancosta.uploadai.models.prompt.PromptDTO;

import java.util.List;
import java.util.Optional;

public interface IPromptDAO {
    List<Prompt> lisAllPrompts();

    Optional<Prompt> findPromptById(String id);

    void addPrompt(Prompt prompt);

    List<Prompt>  searchByTitle(String title);
}


package com.jsancosta.uploadai.services;

import com.jsancosta.uploadai.exceptions.ResourceNotFoundException;
import com.jsancosta.uploadai.models.prompt.Prompt;
import com.jsancosta.uploadai.models.prompt.PromptDTO;
import com.jsancosta.uploadai.models.prompt.PromptDTOMapper;
import com.jsancosta.uploadai.models.prompt.IPromptDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromptService {
    private final IPromptDAO promptDAO;
    private final PromptDTOMapper promptDTOMapper;

    @Autowired
    public PromptService(@Qualifier("prompt") IPromptDAO promptDAO) {
        this.promptDAO = promptDAO;
        this.promptDTOMapper = new PromptDTOMapper();
    }

    public PromptDTO getPrompt(String id) {
        return this.promptDAO.findPromptById(id)
                .map(this.promptDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("prompt with id [%s] not found".formatted(id)));
    }

    public void registerPrompt(Prompt prompt) {
        this.promptDAO.addPrompt(prompt);
    }

    public List<PromptDTO> searchByTitle(String title) {
        if (title.isEmpty())
            return new ArrayList<>();

        List<Prompt> prompts = this.promptDAO.searchByTitle(title);
        List<PromptDTO> promptDTOS = new ArrayList<>(prompts.size());

        prompts.forEach(prompt -> {
            String srcPromptTitle = prompt.getTitle().toLowerCase().trim();
            if (srcPromptTitle.contains(title.toLowerCase().trim()))
                promptDTOS.add(new PromptDTO(prompt.getTitle(), prompt.getTemplate()));
        });

        return promptDTOS;
    }

    public List<PromptDTO> getPrompts() {
        List<Prompt> prompts = this.promptDAO.lisAllPrompts();
        List<PromptDTO> promptDTOS = new ArrayList<>(prompts.size());

        prompts.forEach( prompt -> promptDTOS.add(new PromptDTO(prompt.getTitle(), prompt.getTemplate())));

        return promptDTOS;
    }
}

package com.jsancosta.uploadai.controllers;

import com.jsancosta.uploadai.models.prompt.Prompt;
import com.jsancosta.uploadai.models.prompt.PromptDTO;
import com.jsancosta.uploadai.services.PromptService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("api/v1/prompts")
public class PromptController {
    private final PromptService promptService;

    @Autowired
    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping("/{promptId}")
    public PromptDTO getPrompt(@PathVariable String promptId) {
        return this.promptService.getPrompt(promptId);
    }

    @GetMapping("/{title}/find")
    public List<PromptDTO> searchPromptByTitle(@NotNull @PathVariable String title) {
        if (title.trim().isEmpty())
            return new ArrayList<>();

        return this.promptService.searchByTitle(title);
    }

    @GetMapping("/")
    public List<PromptDTO> getAllPrompts() {
        return this.promptService.getPrompts();
    }

    @PostMapping("/")
    public void registerPrompt(@RequestBody Prompt prompt) {
        this.promptService.registerPrompt(prompt);
    }
}

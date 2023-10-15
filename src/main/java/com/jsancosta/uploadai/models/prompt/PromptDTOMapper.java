package com.jsancosta.uploadai.models.prompt;

import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class PromptDTOMapper implements Function<Prompt, PromptDTO> {
    @Override
    public PromptDTO apply(Prompt prompt) {
        return new PromptDTO(
                prompt.getTitle(),
                prompt.getTemplate()
        );
    }
}

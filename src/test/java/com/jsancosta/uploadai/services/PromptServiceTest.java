package com.jsancosta.uploadai.services;

import com.jsancosta.uploadai.models.prompt.Prompt;
import com.jsancosta.uploadai.models.prompt.PromptDTO;
import com.jsancosta.uploadai.models.prompt.PromptDTOMapper;
import com.jsancosta.uploadai.models.prompt.IPromptDAO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PromptServiceTest {

    @InjectMocks
    private PromptService promptService;

    @Mock
    private IPromptDAO promptDAO;

    private final String fakePromptId = "fake-id";
    private final String fakeTitle = "Fake Title";
    private final String fakeTemplate = "fake template";

    @Test
    void getPrompt() {
        Optional<Prompt> prompt = Optional.of(new Prompt(this.fakePromptId, this.fakeTitle, this.fakeTemplate));

        Mockito.when(this.promptDAO.findPromptById(fakePromptId)).thenReturn(prompt);
        PromptDTO actual = this.promptService.getPrompt(fakePromptId);
        assertEquals(fakeTitle, actual.title());
        assertEquals(fakeTemplate, actual.template());
    }

    @Test
    void searchByTitle() {
        List<Prompt> prompts = new ArrayList<>();
        prompts.add(new Prompt(this.fakePromptId, "YouTube Title", this.fakeTemplate));
        prompts.add(new Prompt(this.fakePromptId + "2", "prompt", this.fakeTemplate));
        prompts.add(new Prompt(this.fakePromptId + "3", this.fakeTitle + "2", this.fakeTemplate));
        prompts.add(new Prompt(this.fakePromptId + "3", this.fakeTitle + "3", this.fakeTemplate));

        Mockito.when(this.promptDAO.searchByTitle("YouTube")).thenReturn(prompts);

        List<PromptDTO> promptDTOS = this.promptService.searchByTitle("YouTube");
        List<PromptDTO> expected = new ArrayList<>();
        expected.add(new PromptDTO("YouTube Title", this.fakeTemplate));

        assertArrayEquals(expected.toArray(), promptDTOS.toArray());

        Mockito.when(this.promptDAO.searchByTitle(this.fakeTitle)).thenReturn(prompts);

        List<PromptDTO> promptDTOS2 = this.promptService.searchByTitle(this.fakeTitle);
        List<PromptDTO> expected2 = new ArrayList<>();
        expected2.add(new PromptDTO(this.fakeTitle + "2", this.fakeTemplate));
        expected2.add(new PromptDTO(this.fakeTitle + "3", this.fakeTemplate));

        assertArrayEquals(expected2.toArray(), promptDTOS2.toArray());

        Mockito.when(this.promptDAO.searchByTitle("")).thenReturn(prompts);

        List<PromptDTO> promptDTOS3 = this.promptService.searchByTitle("");
        List<PromptDTO> expected3 = new ArrayList<>();

        assertArrayEquals(expected3.toArray(), promptDTOS3.toArray());
    }
}

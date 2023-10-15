package com.jsancosta.uploadai.services;

import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.audio.TranscriptionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Configuration
@ConfigurationProperties(prefix = "openai.config")
public class OpenAiServiceWrapperService implements IOpenAiWrapperService {

    private String openAiKey;
    private String model;
    private String language;
    private String responseFormat;

    private OpenAiService openAiService;

    @Override
    public String getTranscription(String prompt, File audio) {
        CreateTranscriptionRequest transcriptionRequest = new CreateTranscriptionRequest();
        transcriptionRequest.setModel(this.getModel());
        transcriptionRequest.setLanguage(this.getLanguage());
        transcriptionRequest.setResponseFormat(this.getResponseFormat());
        transcriptionRequest.setTemperature(0.0);
        transcriptionRequest.setPrompt(prompt);
        TranscriptionResult transcriptionResult = this.openAiService().createTranscription(transcriptionRequest, audio);

        return transcriptionResult.getText();
    }

    public String getKey() {
        return openAiKey;
    }

    public void setKey(String openAiKey) {
        this.openAiKey = openAiKey;
    }

    public String getModel() {
        return this.model;
    }


    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private OpenAiService openAiService() {
        if (this.openAiService == null)
            this.openAiService = new OpenAiService(this.getKey());

        return this.openAiService;
    }
}

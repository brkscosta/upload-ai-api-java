package com.jsancosta.uploadai.services;

import java.io.File;

public interface IOpenAiWrapperService {

    String getTranscription(String prompt, File audio);
}

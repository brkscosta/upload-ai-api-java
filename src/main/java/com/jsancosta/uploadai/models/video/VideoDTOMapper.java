package com.jsancosta.uploadai.models.video;


import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VideoDTOMapper implements Function<Video, VideoDTO> {

    @Override
    public VideoDTO apply(Video video) {
        return new VideoDTO(
                video.getTitle(),
                video.getTranscription(),
                video.getCreatedAt()
        );
    }
}
package com.jsancosta.uploadai.services;

import com.jsancosta.uploadai.models.video.IVideoDAO;
import com.jsancosta.uploadai.models.video.Video;
import com.jsancosta.uploadai.s3.S3Bucket;
import com.jsancosta.uploadai.s3.S3Service;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VideoServiceTest {

    @InjectMocks
    VideoService videoService;

    @Mock
    private IVideoDAO videoDAO;

    @Mock
    private IOpenAiWrapperService aiWrapperService;

    @Mock
    private S3Service fakeS3Service;

    @Mock
    private S3Bucket fakeS3Bucket;

    @Test
    void setVideoTranscription() {
        String fakeVideoId = "fake-videoId";
        String fakePrompt = "fake-prompt";
        String fakeVideoTitle = "fake video title";
        String fakeBucketName = "test-bk";
        String expectedTranscription = "I'm a test transcription";
        byte[] testArrayByte = {1, 3, 4};

        Optional<Video> video = Optional.of(new Video(fakeVideoTitle, "", new Date()));

        Mockito.when(this.videoDAO.findVideoById(fakeVideoId)).thenReturn(video);
        Mockito.when(this.fakeS3Bucket.getName()).thenReturn(fakeBucketName);
        Mockito.when(this.fakeS3Service.getObject(fakeBucketName, video.get().getTitle())).thenReturn(testArrayByte);
        Mockito.when(this.aiWrapperService.getTranscription(Mockito.any(), Mockito.any())).thenReturn(expectedTranscription);

        String videoTranscription = this.videoService.setVideoTranscription(fakeVideoId, fakePrompt);

        verify(this.videoDAO).updateVideo(video.get());

        assertEquals(expectedTranscription, videoTranscription);
    }
}

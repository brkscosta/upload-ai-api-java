package com.jsancosta.uploadai.utils;

import com.jsancosta.uploadai.models.video.Video;
import com.jsancosta.uploadai.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UtilsTests {
    @Test
    void copyNonNullProperties() {
        Video newVideo = new Video(null, "Random transcription", null);
        Video video = new Video("Title", "Random transcription", new Date());

        String[] newVideoNullProps = Utils.getNullPropertiesNames(newVideo);
        String[] expected = {"createdAt", "id", "title"};
        Assertions.assertArrayEquals(expected, newVideoNullProps);
    }
}

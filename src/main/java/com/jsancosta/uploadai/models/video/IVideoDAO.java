package com.jsancosta.uploadai.models.video;

import com.jsancosta.uploadai.models.video.Video;

import java.util.List;
import java.util.Optional;

public interface IVideoDAO {
    List<Video> selectAllVideos();
    Optional<Video> findVideoById(String id);
    void updateVideo(Video video);
    void addVideo(Video video);
    void deleteVideoById(String videoId);
    void deleteVideoByTitle(Video video);
}

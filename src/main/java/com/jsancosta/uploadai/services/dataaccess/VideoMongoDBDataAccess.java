package com.jsancosta.uploadai.services.dataaccess;

import com.jsancosta.uploadai.models.video.IVideoDAO;
import com.jsancosta.uploadai.models.video.Video;
import com.jsancosta.uploadai.repositories.IVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("video")
public class VideoMongoDBDataAccess implements IVideoDAO {


    private final MongoTemplate mongoTemplate;

    private final IVideoRepository videoRepository;

    @Autowired
    public VideoMongoDBDataAccess(IVideoRepository videoRepository, MongoTemplate mongoTemplate) {
        this.videoRepository = videoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Video> selectAllVideos() {
        return this.videoRepository.findAll();
    }

    @Override
    public Optional<Video> findVideoById(String id) {
        return this.videoRepository.findById(id);
    }

    @Override
    public void updateVideo(Video video) {
        this.videoRepository.save(video);
    }

    @Override
    public void addVideo(Video video) {
        this.videoRepository.save(video);
    }

    @Override
    public void deleteVideoById(String videoId) {
        this.videoRepository.deleteById(videoId);
    }

    @Override
    public void deleteVideoByTitle(Video video) {
        this.videoRepository.delete(video);
    }
}

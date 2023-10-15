package com.jsancosta.uploadai.repositories;

import com.jsancosta.uploadai.models.video.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IVideoRepository extends MongoRepository<Video, String> { }

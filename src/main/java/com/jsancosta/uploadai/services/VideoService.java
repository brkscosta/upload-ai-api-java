package com.jsancosta.uploadai.services;

import com.jsancosta.uploadai.exceptions.ResourceNotFoundException;
import com.jsancosta.uploadai.models.video.Video;
import com.jsancosta.uploadai.models.video.VideoDTO;
import com.jsancosta.uploadai.models.video.VideoDTOMapper;
import com.jsancosta.uploadai.s3.S3Bucket;
import com.jsancosta.uploadai.s3.S3Service;
import com.jsancosta.uploadai.models.video.IVideoDAO;
import com.jsancosta.uploadai.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class VideoService {
    private final S3Service s3Service;
    private final S3Bucket s3Bucket;
    private final IVideoDAO videoDAO;
    private final VideoDTOMapper videoDTOMapper;
    private final IOpenAiWrapperService openAiServiceWrapper;

    @Autowired
    public VideoService(S3Service s3Service, S3Bucket s3Bucket, @Qualifier("video") IVideoDAO videoDAO,
                        VideoDTOMapper videoDTOMapper, IOpenAiWrapperService openAiServiceWrapper) {
        this.s3Service = s3Service;
        this.s3Bucket = s3Bucket;
        this.videoDAO = videoDAO;
        this.videoDTOMapper = videoDTOMapper;
        this.openAiServiceWrapper = openAiServiceWrapper;
    }

    public VideoDTO getVideo(String id) {
        return this.videoDAO.findVideoById(id)
                .map(this.videoDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("video with id [%s] not found".formatted(id))
                );
    }

    public List<VideoDTO> getVideos() {
        List<Video> videos = this.videoDAO.selectAllVideos();
        List<VideoDTO> videoDTOS = new ArrayList<>();
        videos.forEach(video -> videoDTOS.add(
                new VideoDTO(video.getTitle(), video.getTranscription(), video.getCreatedAt())
        ));

        return videoDTOS;
    }

    public void registerVideo(String fileUploadName, byte[] file) {
        this.s3Service.putObject("upload-ai", fileUploadName, file);
        this.videoDAO.addVideo(new Video(fileUploadName, "", new Date()));
    }

    public String setVideoTranscription(String videoId, String prompt) {
        Video video = this.videoDAO.findVideoById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video with id " + videoId + " not found"));

        byte[] rawAudio = this.s3Service.getObject(s3Bucket.getName(), video.getTitle());
        File tempAudioFile = null;

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(rawAudio)) {

            tempAudioFile = File.createTempFile("audio", ".mp3");
            tempAudioFile.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(tempAudioFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
            }

            String transcription = this.openAiServiceWrapper.getTranscription(prompt, tempAudioFile);
            // String transcription = "Bla bla blealas aseafas asdfasdla asdfas";

            Video newVideo = new Video(null, transcription, null);

            Utils.copyNonNullProperties(newVideo, video);

            this.videoDAO.updateVideo(video);

            return transcription;
        } catch (IOException e) {
            throw new RuntimeException("Failed to process video transcription", e);
        } finally {
            if (tempAudioFile != null && !tempAudioFile.delete()) {
                System.err.println("Failed to delete temporary audio file: " + tempAudioFile.getAbsolutePath());
            }
        }
    }

    public void removeVideoById(String videoId) {

        Optional<Video> video = this.videoDAO.findVideoById(videoId);

        if (video.isEmpty())
            return;

        this.s3Service.removeObject(s3Bucket.getName(), video.get().getTitle());

        this.videoDAO.deleteVideoById(videoId);
    }

    public boolean removeVideoByTitle(String title) {
        Video target = null;

        for (Video video : this.videoDAO.selectAllVideos()) {
            if (Objects.equals(video.getTitle(), title))
                target = video;
        }

        if (target == null) {
            return false;
        }

        this.s3Service.removeObject(s3Bucket.getName(), target.getTitle());
        this.videoDAO.deleteVideoByTitle(target);

        return true;
    }
}

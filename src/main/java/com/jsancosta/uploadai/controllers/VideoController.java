package com.jsancosta.uploadai.controllers;

import com.jsancosta.uploadai.models.video.VideoDTO;
import com.jsancosta.uploadai.response.ResponseHandler;
import com.jsancosta.uploadai.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController()
@RequestMapping("api/v1/videos")
public class VideoController {

    static private final Integer MAX_FILE_SIZE =  1_048_576 * 25; // 25mb

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/{videoId}")
    public VideoDTO getVideo(@PathVariable String videoId) {
        return this.videoService.getVideo(videoId);
    }

    @PostMapping("/")
    public ResponseEntity<?> registerVideo(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            return ResponseHandler.generateResponse("File is not valid", HttpStatus.NO_CONTENT, null);
        }

        if (file.getSize() > MAX_FILE_SIZE) { // 30 MB em bytes
            return ResponseHandler.generateResponse("File size exceeds the allowed limit", HttpStatus.INSUFFICIENT_STORAGE, null);
        }

        String[] fileNameParts = Objects.requireNonNull(file.getResource().getFilename()).split("\\.");
        String fileBaseName = fileNameParts[0];
        String extension = fileNameParts[1];

        if (!extension.equals("mp3")) {
            return ResponseHandler.generateResponse("File format is not acceptable", HttpStatus.BAD_REQUEST, null);
        }

        try {
            String fileUploadName = "%s-%s.%s".formatted(fileBaseName, UUID.randomUUID(), extension);
            this.videoService.registerVideo(fileUploadName, file.getResource().getContentAsByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseHandler.generateResponse("Video added", HttpStatus.CREATED, null);
    }

    @PostMapping("/{videoId}/transcription")
    public ResponseEntity<?> createVideoTranscription(@RequestBody String prompt, @PathVariable String videoId) {

        VideoDTO video = this.videoService.getVideo(videoId);

        if (video == null) {
            return ResponseHandler.generateResponse("Video not found", HttpStatus.NOT_FOUND, null);
        }

        String videoTranscription = this.videoService.setVideoTranscription(videoId, prompt);

        return ResponseHandler.generateResponse("Transcription updated", HttpStatus.OK, videoTranscription);
    }

    @GetMapping("/")
    public List<VideoDTO> getVideos() {
        return this.videoService.getVideos();
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<?> removeVideoById(@PathVariable String videoId) {
        VideoDTO video = this.videoService.getVideo(videoId);

        if (video == null) {
            return ResponseHandler.generateResponse("Video not found", HttpStatus.NOT_FOUND, null);
        }

        this.videoService.removeVideoById(videoId);

        return ResponseHandler.generateResponse("Video removed", HttpStatus.OK, null);
    }

    @DeleteMapping("/{title}/title")
    public ResponseEntity<?> removeVideoByTitle(@PathVariable String title) {
        boolean isRemoved = this.videoService.removeVideoByTitle(title);

        return ResponseHandler.generateResponse("Video removed", HttpStatus.OK, isRemoved);
    }
}

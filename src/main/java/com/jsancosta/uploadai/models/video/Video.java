package com.jsancosta.uploadai.models.video;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Video")
public class Video {

    @Id
    private String id;
    private String title;
    private String transcription;
    @CreatedDate
    private Date created_at;

    public Video(String title, String transcription, Date created_at) {
        this.title = title;
        this.transcription = transcription;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (!id.equals(video.id)) return false;
        if (!title.equals(video.title)) return false;
        if (!transcription.equals(video.transcription)) return false;
        return created_at.equals(video.created_at);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + transcription.hashCode();
        result = 31 * result + created_at.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", transcription='" + transcription + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}

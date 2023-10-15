package com.jsancosta.uploadai.models.prompt;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Prompt")
public class Prompt {

    @Id()
    private String id;
    private String title;
    private String template;

    public Prompt(String id, String title, String template) {
        this.id = id;
        this.title = title;
        this.template = template;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prompt prompt = (Prompt) o;

        if (!id.equals(prompt.id)) return false;
        if (!title.equals(prompt.title)) return false;
        return template.equals(prompt.template);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + template.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Prompt{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", template='" + template + '\'' +
                '}';
    }
}

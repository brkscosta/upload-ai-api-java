package com.jsancosta.uploadai.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.bucket")
public class S3Bucket {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String video) {
        this.name = video;
    }
}


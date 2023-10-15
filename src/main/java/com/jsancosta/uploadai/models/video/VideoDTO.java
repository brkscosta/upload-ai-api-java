package com.jsancosta.uploadai.models.video;

import java.util.Date;

public record VideoDTO (
        String title,
        String transcription,
        Date created_at
) { }

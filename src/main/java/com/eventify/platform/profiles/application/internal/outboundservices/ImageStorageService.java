package com.eventify.platform.profiles.application.internal.outboundservices;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    UploadedImage uploadAlbumImage(Long profileId, MultipartFile file);

    UploadedImage uploadProfileImage(Long profileId, MultipartFile file);
}

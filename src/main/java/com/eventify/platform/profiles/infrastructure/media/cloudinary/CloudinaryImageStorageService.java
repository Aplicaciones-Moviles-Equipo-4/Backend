package com.eventify.platform.profiles.infrastructure.media.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eventify.platform.profiles.application.internal.outboundservices.ImageStorageService;
import com.eventify.platform.profiles.application.internal.outboundservices.UploadedImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageStorageService implements ImageStorageService {

    private final Cloudinary cloudinary;
    private final String albumFolder;
    private final boolean configured;

    public CloudinaryImageStorageService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret,
            @Value("${cloudinary.album-folder}") String albumFolder) {
        this.configured = StringUtils.hasText(cloudName)
                && StringUtils.hasText(apiKey)
                && StringUtils.hasText(apiSecret);
        this.albumFolder = albumFolder;
        this.cloudinary = configured
                ? new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret,
                        "secure", true))
                : null;
    }

    @Override
    public UploadedImage uploadAlbumImage(Long profileId, MultipartFile file) {
        return uploadImage("%s/%d".formatted(albumFolder, profileId), file);
    }

    @Override
    public UploadedImage uploadProfileImage(Long profileId, MultipartFile file) {
        return uploadImage("eventify/profiles/%d".formatted(profileId), file);
    }

    private UploadedImage uploadImage(String folder, MultipartFile file) {
        if (!configured) {
            throw new IllegalStateException("Cloudinary is not configured");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "image"));
            return new UploadedImage(
                    String.valueOf(result.get("url")),
                    String.valueOf(result.get("secure_url")),
                    String.valueOf(result.get("public_id")));
        } catch (IOException exception) {
            throw new IllegalStateException("Could not upload image", exception);
        }
    }
}

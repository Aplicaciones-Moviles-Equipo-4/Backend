package com.eventify.platform.profiles.application.internal.queryservices;

import com.eventify.platform.profiles.domain.model.aggregates.Album;
import com.eventify.platform.profiles.domain.model.queries.GetAlbumByIdQuery;
import com.eventify.platform.profiles.domain.model.queries.GetAlbumsByProfileIdQuery;
import com.eventify.platform.profiles.domain.services.AlbumQueryService;
import com.eventify.platform.profiles.infrastructure.persistence.jpa.repositories.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of album query service.
 */
@Service
public class AlbumQueryServiceImpl implements AlbumQueryService {

    private final AlbumRepository albumRepository;

    public AlbumQueryServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> handle(GetAlbumsByProfileIdQuery query) {
        return albumRepository.findByProfile_Id(query.profileId());
    }

    @Override
    public Optional<Album> handle(GetAlbumByIdQuery query) {
        return albumRepository.findByIdWithPhotos(query.albumId());
    }
}

package com.eventify.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.eventify.platform.profiles.domain.model.aggregates.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for album aggregate.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * Find albums by profile id.
     *
     * @param profileId profile identifier
     * @return list of albums
     */
    @Query("select distinct album from Album album left join fetch album.photos where album.profile.id = :profileId")
    List<Album> findByProfile_Id(Long profileId);

    @Query("select album from Album album left join fetch album.photos where album.id = :albumId")
    Optional<Album> findByIdWithPhotos(Long albumId);
}

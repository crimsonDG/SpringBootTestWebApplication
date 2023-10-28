package com.music.song.repository;

import com.music.song.domain.SongEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SongRepository extends CrudRepository<SongEntity, String> {

    Page<SongEntity> findAll(Pageable pageable);

    @NonNull List<SongEntity> findAll();

    @NonNull Optional<SongEntity> findById(@NonNull String s);

    Page<SongEntity> findAllByName(Pageable pageable, String name);

    Page<SongEntity> findAllByArtist(Pageable pageable, String artist);

    Page<SongEntity> findAllByYear(Pageable pageable, int year);

}

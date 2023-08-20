package com.music.repository;

import com.music.domain.MusicEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MusicRepository extends CrudRepository<MusicEntity, String> {

    Page<MusicEntity> findAll(Pageable pageable);

    @NonNull List<MusicEntity> findAll();

    @NonNull Optional<MusicEntity> findById(@NonNull String s);

    Page<MusicEntity> findAllByName(Pageable pageable, String name);

    Page<MusicEntity> findAllByArtist(Pageable pageable, String artist);

    Page<MusicEntity> findAllByYear(Pageable pageable, int year);

}

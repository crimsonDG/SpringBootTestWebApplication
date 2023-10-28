package com.music.label.repository;

import com.music.label.domain.LabelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, String> {

    Page<LabelEntity> findAll(Pageable pageable);

    Optional<LabelEntity> findById(String s);

    Optional<LabelEntity> findByName(String name);

}

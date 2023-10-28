package com.music.service;

import com.music.label.domain.LabelEntity;
import com.music.label.model.LabelDto;
import com.music.label.repository.LabelRepository;
import com.music.song.service.SongService;
import com.redis.config.CustomPage;
import com.redis.model.SongDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final ModelMapper modelMapper;

    private final SongService songService;
    private final LabelRepository labelRepository;

    public LabelDto findLabelById(String id) {
        return labelConverter(labelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)));
    }

    public LabelDto findLabelByName(String name) {
        return labelConverter(labelRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + name)));
    }

    public CustomPage<LabelDto> findAllLabels(int page, int size) {
        return new CustomPage<>(addField(labelRepository.findAll(PageRequest.of(page, size))));
    }

    private LabelDto labelConverter(LabelEntity labelFromDB) {
        LabelDto currentLabel = modelMapper.map(labelFromDB, LabelDto.class);
        currentLabel.setSongs(getCurrentSongs(labelFromDB.getSongs()));
        return currentLabel;
    }

    private Page<LabelDto> addField(Page<LabelEntity> labelEntities) {
        List<LabelEntity> labels = labelEntities.stream().toList();
        List<LabelDto> currentLabel = new ArrayList<>();
        for (LabelEntity labelEntity : labels) {
            currentLabel.add(labelConverter(labelRepository.findById(labelEntity.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + labelEntity.getId()))));
        }
        return new PageImpl<>(currentLabel);
    }

    private List<SongDto> getCurrentSongs(List<String> id) {
        return id != null
                ? id.stream()
                .map(songService::findSongById)
                .collect(Collectors.toList())
                : null;
    }

    private LabelEntity buildLabel(LabelDto label) {
        LabelEntity labelEntity = new LabelEntity();
        labelEntity.setId(label.getId());
        labelEntity.setName(label.getName());

        if (label.getSongs() != null) {
            labelEntity.setSongs(label.getSongs().stream().map(SongDto::getId).toList());
        }

        return labelEntity;
    }

    @Transactional
    public boolean updateLabel(LabelDto label, String id) {

        LabelDto currentLabel = findLabelById(id);

        if (currentLabel == null) {
            return false;
        }

        currentLabel.setName(label.getName());

        labelRepository.save(buildLabel(currentLabel));
        return true;
    }

    @Transactional
    public boolean saveLabel(LabelDto baseLabelDto) {
        baseLabelDto.setId(UUID.randomUUID().toString());
        Optional<LabelEntity> labelFromDB = labelRepository.findById(baseLabelDto.getId());
        if (labelFromDB.isPresent()) {
            return false;
        }

        labelRepository.save(buildLabel(baseLabelDto));

        return true;
    }

    @Transactional
    public void deleteLabel(String id) {
        Optional<LabelEntity> labelFromDB = labelRepository.findById(id);
        if (labelFromDB.isEmpty()) {
            return;
        }
        labelRepository.delete(labelFromDB.get());
    }

    @Transactional
    public String addSongs(LabelDto currentLabel, String songId) {
        SongDto musicDto = songService.findSongById(songId);

        if (currentLabel.getSongs() == null) {
            currentLabel.setSongs(Collections.singletonList(musicDto));
        } else if (!currentLabel.getSongs().contains(musicDto)) {
            currentLabel.getSongs().add(musicDto);
        } else {
            return String.format("\"%s\" is already in %s's library!", musicDto.getName(), currentLabel.getName());
        }
        labelRepository.save(buildLabel(currentLabel));
        return String.format("%s gets \"%s\" to his library!", currentLabel.getName(), musicDto.getName());
    }

    @Transactional
    public String deleteSong(LabelDto currentLabel, String songId) {
        SongDto song = songService.findSongById(songId);

        if (currentLabel.getSongs() != null && currentLabel.getSongs().remove(song)) {
            labelRepository.save(buildLabel(currentLabel));
            return String.format("\"%s\" is removed from %s's library!", song.getName(), currentLabel.getName());
        }
        return String.format("\"%s\" was not found in the library!", song.getName());
    }

}

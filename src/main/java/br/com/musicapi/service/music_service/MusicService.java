package br.com.musicapi.service.music_service;

import br.com.musicapi.models.MusicModel;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {

    List<MusicModel> findAllMusics();

    void addMusic(MusicModel newMusic);

    void deleteMusic(Long musicId);

    void editMusic(MusicModel editedMusic, Long musicId);

    List<MusicModel> searchMusics(String musicName);

    List<MusicModel> findMusicsByType(String musicType);

    List<MusicModel> findMusicsByAuthor(String author);

    void uploadMusicFile(MultipartFile musicFile, Long musicId) throws Exception;

    FileSystemResource downloadMusicFile(Long musicId) throws Exception;



}

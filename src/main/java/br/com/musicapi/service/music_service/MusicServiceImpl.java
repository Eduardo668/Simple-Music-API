package br.com.musicapi.service.music_service;

import br.com.musicapi.models.MusicModel;
import br.com.musicapi.repository.MusicRepository;
import br.com.musicapi.service.music_service.edit_music_strategy.EditMusicWithoutAuthor;
import br.com.musicapi.service.music_service.edit_music_strategy.EditMusicWithoutMusicPath;
import br.com.musicapi.service.music_service.edit_music_strategy.EditMusicWithoutName;
import br.com.musicapi.service.music_service.edit_music_strategy.EditMusicWithoutType;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;
    @Autowired
    private EditMusicWithoutName editMusicWithoutName;
    @Autowired
    private EditMusicWithoutAuthor editMusicWithoutAuthor;
    @Autowired
    private EditMusicWithoutType editMusicWithoutType;
    @Autowired
    private EditMusicWithoutMusicPath editMusicWithoutMusicPath;

    private Path currentDir = Paths.get("").toAbsolutePath();
    private String fileSystemDir = currentDir.normalize().toString();
    private String musicFilesDir = fileSystemDir + "/musicFiles";
    private File pathAsFile = new File(musicFilesDir);

    @Override
    public List<MusicModel> findAllMusics() {
        return musicRepository.findAll();
    }

    @Override
    public void addMusic(MusicModel newMusic) {
        try {
            musicRepository.save(newMusic);
        } catch (Exception error){
            throw new RuntimeException("An error occurred while trying to add new music: ", error);
        }
    }

    @Override
    public void deleteMusic(Long musicId) {
        try{
            Optional<MusicModel> musicData = musicRepository.findById(musicId);
            if (musicData.isEmpty()){
                throw new RuntimeException("This music doenst exists!");
            }

            if (musicData.get().getMusicPath() != null){
                Path musicPath = Paths.get(musicData.get().getMusicPath());
                Files.delete(musicPath);
            }

            musicRepository.delete(musicData.get());
        } catch (Exception error){
            throw new RuntimeException("An error occurred while trying to delete a music: ", error);
        }
    }

    @Override
    public void editMusic(MusicModel editedMusic, Long musicId) {
        try{
            Optional<MusicModel> musicData = musicRepository.findById(musicId);
            if (musicData.isEmpty()){
                throw new RuntimeException("This music doenst exists!");
            }
            editMusicWithoutName.editMusic(musicData, editedMusic);
            editMusicWithoutAuthor.editMusic(musicData, editedMusic);
            editMusicWithoutType.editMusic(musicData, editedMusic);
            editMusicWithoutMusicPath.editMusic(musicData, editedMusic);

            musicRepository.save(musicData.get());

        } catch (Exception error){
            throw new RuntimeException("An error occurred while trying to edit a music: ", error);
        }
    }

    @Override
    public List<MusicModel> searchMusics(String musicName) {
        return musicRepository.findByNameStartsWithIgnoreCase(musicName);
    }

    @Override
    public List<MusicModel> findMusicsByType(String musicType) {
        return musicRepository.findByTypeIgnoreCase(musicType);
    }

    @Override
    public List<MusicModel> findMusicsByAuthor(String author) {
        return musicRepository.findByAuthorIgnoreCase(author);
    }

    @Override
    public void uploadMusicFile(MultipartFile musicFile, Long musicId) throws Exception{
        Optional<MusicModel> musicData = musicRepository.findById(musicId);
        if (musicData.isEmpty()){
            throw new RuntimeException("This music doesnt exists!");
        }

        if(!Files.exists(Paths.get(musicFilesDir))){
            pathAsFile.mkdir();
        }

        Path newMusicFile = Paths.get(musicFilesDir, new Date().getTime()+ "-" + musicFile.getOriginalFilename());

        Files.createDirectories(newMusicFile.getParent());
        Files.write(newMusicFile, musicFile.getBytes());

        musicData.get().setMusicPath(newMusicFile.toAbsolutePath().toString());
        editMusic(musicData.get(), musicId);


    }

    @Override
    public FileSystemResource downloadMusicFile(Long musicId) throws Exception {
        Optional<MusicModel> musicData = musicRepository.findById(musicId);
        String musicPath = musicData.get().getMusicPath();
        return new FileSystemResource(Paths.get(musicPath));
    }

}

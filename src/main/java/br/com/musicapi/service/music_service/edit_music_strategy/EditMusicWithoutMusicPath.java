package br.com.musicapi.service.music_service.edit_music_strategy;

import br.com.musicapi.models.MusicModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditMusicWithoutMusicPath implements EditMusicStrategy {
    @Override
    public void editMusic(Optional<MusicModel> musicData, MusicModel editedMusic) {
        if (editedMusic.getMusicPath() == null || editedMusic.getMusicPath() == ""){
            musicData.get().setMusicPath(musicData.get().getMusicPath());
        } else {
            musicData.get().setMusicPath(editedMusic.getMusicPath());
        }
    }
}

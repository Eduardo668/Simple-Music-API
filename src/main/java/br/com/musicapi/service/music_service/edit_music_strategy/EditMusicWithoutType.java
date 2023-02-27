package br.com.musicapi.service.music_service.edit_music_strategy;

import br.com.musicapi.models.MusicModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditMusicWithoutType implements EditMusicStrategy {

    @Override
    public void editMusic(Optional<MusicModel> musicData, MusicModel editedMusic) {
        if (editedMusic.getType() == null || editedMusic.getType() == ""){
            musicData.get().setType(musicData.get().getType());
        } else {
            musicData.get().setType(editedMusic.getType());
        }
    }
}

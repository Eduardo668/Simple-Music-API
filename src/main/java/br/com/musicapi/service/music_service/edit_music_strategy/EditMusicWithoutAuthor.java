package br.com.musicapi.service.music_service.edit_music_strategy;

import br.com.musicapi.models.MusicModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditMusicWithoutAuthor implements EditMusicStrategy {

    @Override
    public void editMusic(Optional<MusicModel> musicData, MusicModel editedMusic) {
        if (editedMusic.getAuthor() == null || editedMusic.getAuthor() == ""){
            musicData.get().setAuthor(musicData.get().getAuthor());
        } else {
            musicData.get().setAuthor(editedMusic.getAuthor());
        }
    }
}

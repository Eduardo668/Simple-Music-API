package br.com.musicapi.service.music_service.edit_music_strategy;

import br.com.musicapi.models.MusicModel;

import java.util.Optional;

public interface EditMusicStrategy {

    void editMusic(Optional<MusicModel> musicData, MusicModel editedMusic);

}

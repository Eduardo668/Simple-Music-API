package br.com.musicapi.music_service;

import br.com.musicapi.models.MusicModel;
import br.com.musicapi.repository.MusicRepository;
import br.com.musicapi.service.music_service.MusicServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MusicServiceImplTest {

    private MusicServiceImpl underTest;

    @Mock
    private MusicRepository musicRepository;

    @BeforeEach
    void setUp() {
        underTest = new MusicServiceImpl(musicRepository);
    }


    @Test
    void canFindAllMusics() {
        // When
        underTest.findAllMusics();
        //Then
        verify(musicRepository).findAll();
    }

    @Test
    void canAddMusic() {
        //Given
        MusicModel music = new MusicModel();
        music.setName("Music Test");
        music.setAuthor("Author Test");
        music.setType("Type Test");

        // When
        underTest.addMusic(music);

        //then
        ArgumentCaptor<MusicModel> argumentCaptor = ArgumentCaptor.forClass(MusicModel.class);
        verify(musicRepository).save(argumentCaptor.capture());

        MusicModel capturedMusic = argumentCaptor.getValue();

        assertThat(music).isEqualTo(capturedMusic);

    }

    @Test
    void canDeleteMusic() {
        //Given
        MusicModel music = new MusicModel();
        music.setId(1L);
        music.setName("Music Test");
        music.setAuthor("Author Test");
        music.setType("Type Test");

        Optional<MusicModel> musicOpt = Optional.of(music);

        given(musicRepository.findById(music.getId())).willReturn(musicOpt);

        //When
        underTest.deleteMusic(music.getId());

        ArgumentCaptor<MusicModel> argumentCaptor = ArgumentCaptor.forClass(MusicModel.class);
        verify(musicRepository).delete(argumentCaptor.capture());

        MusicModel capturedMusic = argumentCaptor.getValue();

        //Then
        assertThat(music).isEqualTo(capturedMusic);


    }

    @Test
    @Disabled
    void canEditMusic() {

    }

    @Test
    void canSearchMusics() {
        //Given
        String musicName = "Music";

        //When
        underTest.searchMusics(musicName);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(musicRepository).findByNameStartsWithIgnoreCase(argumentCaptor.capture());

        String capturedMusicName = argumentCaptor.getValue();

        //Then
        assertThat(musicName).isEqualTo(capturedMusicName);

    }

    @Test
    void canFindMusicsByType() {
        //Given
        String musicType = "Type";

        //When
        underTest.findMusicsByType(musicType);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(musicRepository).findByTypeIgnoreCase(argumentCaptor.capture());

        String capturedMusicType = argumentCaptor.getValue();

        //Then
        assertThat(musicType).isEqualTo(capturedMusicType);


    }

    @Test
    void canFindMusicsByAuthor() {
        //Given
        String musicAuthor = "Author";

        //When
        underTest.findMusicsByAuthor(musicAuthor);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(musicRepository).findByAuthorIgnoreCase(argumentCaptor.capture());

        String capturedMusicAuthor = argumentCaptor.getValue();

        //Then
        assertThat(musicAuthor).isEqualTo(capturedMusicAuthor);
    }

    @Test
    @Disabled
    void uploadMusicFile() {

    }

    @Test
    @Disabled
    void downloadMusicFile() {
    }
}
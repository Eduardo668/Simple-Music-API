package br.com.musicapi.controller;

import br.com.musicapi.models.MusicModel;
import br.com.musicapi.service.music_service.MusicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicServiceImpl musicService;

    @GetMapping("/findAll")
    public ResponseEntity<List<MusicModel>> findAllMusics(){
        return ResponseEntity.ok().body(musicService.findAllMusics());
    }

    @GetMapping("/search/musicName={musicName}")
    public ResponseEntity<List<MusicModel>> searchMusics(@PathVariable String musicName){
        return ResponseEntity.ok().body(musicService.searchMusics(musicName));
    }

    @GetMapping("/search/musicType={musicType}")
    public ResponseEntity<List<MusicModel>> searchMusicsByType(@PathVariable String musicType){
        return ResponseEntity.ok().body(musicService.findMusicsByType(musicType));
    }

    @GetMapping("/search/author={author}")
    public ResponseEntity<List<MusicModel>> findMusicsByAuthor(@PathVariable String author){
        return ResponseEntity.ok().body(musicService.findMusicsByAuthor(author));
    }

    @PostMapping("/add")
    public void addMusic(@RequestBody MusicModel newMusic){
        musicService.addMusic(newMusic);
    }

    @PutMapping("/edit/musicId={musicId}")
    public void editMusic(@PathVariable Long musicId, @RequestBody MusicModel editedMusic){
        musicService.editMusic(editedMusic, musicId);
    }

    @DeleteMapping("/delete/musicId={musicId}")
    public void deleteMusic(@PathVariable Long musicId){
        musicService.deleteMusic(musicId);
    }

    @PostMapping("/upload/musicId={musicId}")
    public void addMusicFile(@RequestParam MultipartFile musicFile, @PathVariable Long musicId) throws Exception {
        System.out.println(musicFile.getName());
        musicService.uploadMusicFile(musicFile,musicId);
    }

    @GetMapping(value = "/download/musicId={musicId}", produces = "audio/mp3")
    public ResponseEntity<FileSystemResource> downloadMusic(@PathVariable Long musicId) throws Exception{
        return ResponseEntity.ok().body(musicService.downloadMusicFile(musicId));
    }
}

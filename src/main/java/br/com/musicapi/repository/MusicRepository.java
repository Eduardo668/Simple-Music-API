package br.com.musicapi.repository;

import br.com.musicapi.models.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<MusicModel, Long> {

    List<MusicModel> findByTypeIgnoreCase(String type);

    List<MusicModel> findByNameStartsWithIgnoreCase(String name);

    List<MusicModel> findByAuthorIgnoreCase(String author);


}

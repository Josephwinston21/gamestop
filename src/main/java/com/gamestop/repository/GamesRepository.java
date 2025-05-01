package com.gamestop.repository;

import com.gamestop.model.Game;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamesRepository{

    void save (Game theGame);

    void delete (Game theGame);

    Game findById (int theId);

    List<Game> findAll();
}

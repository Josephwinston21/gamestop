package com.gamestop.controller;

import com.gamestop.model.Game;
import com.gamestop.repository.GamesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameController {

    private GamesRepository gamesRepository;

    @Autowired
    public GameController(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int theId) {
      Game foundGame =  gamesRepository.findById(theId);
      if (foundGame != null) {
          return ResponseEntity.ok(foundGame);
      }else {
          return ResponseEntity.notFound().build();
      }
    }

    @DeleteMapping("/games/delete/{theId}")
    public ResponseEntity<Void> deleteGame(@PathVariable int theId) {
        Game foundGame =  gamesRepository.findById(theId);
        if (foundGame != null) {
            gamesRepository.delete(foundGame);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/games")
    public ResponseEntity<String> saveGame(@Valid @RequestBody Game theGame, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(errorMessage);
        }
       try {
           Game findGame = gamesRepository.findById(theGame.getId());
           if (findGame != null) {
               return ResponseEntity.status(HttpStatus.CONFLICT).body("Gane with the ID " + theGame.getId() + "already exists");
           } else {
               gamesRepository.save(theGame);
               return ResponseEntity.status(HttpStatus.CREATED).body("Game saved successfully with ID " + theGame.getId());
           }
       }catch (ObjectOptimisticLockingFailureException e) {
           throw new RuntimeException("The game has been updated by another transaction. Please try again.");
       }
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> gamesList = gamesRepository.findAll();
        if (gamesList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gamesList);
    }

}

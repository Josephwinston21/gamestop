package com.gamestop.repository;

import com.gamestop.model.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GamesRepositoryImpl implements GamesRepository{

    private EntityManager entityManager;

    @Autowired
    public GamesRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Game theGame) {
        entityManager.merge(theGame);
    }

    @Override
    @Transactional
    public void delete(Game theGame) {
        Game gameToDelete = findById(theGame.getId());
        if(gameToDelete!=null) {
            entityManager.remove(gameToDelete);
        } else {
            throw new RuntimeException("Game with ID "+ theGame.getId() + " not found");
        }
    }

    @Override
    public Game findById(int theId) {
       Game game = entityManager.find(Game.class,theId);
       return game;
    }

    @Override
    public List<Game> findAll() {
        TypedQuery<Game> theQuery = entityManager.createQuery("FROM Game", Game.class);
         List<Game> gamesList = theQuery.getResultList();
         return gamesList;
    }

}

package com.intuit.playerdb.service;

import com.intuit.playerdb.exceptions.EntityType;
import com.intuit.playerdb.exceptions.ExceptionType;
import com.intuit.playerdb.exceptions.PlayerDBCustomException;
import com.intuit.playerdb.logging.SimpleLogger;
import com.intuit.playerdb.model.Player;
import com.intuit.playerdb.repository.PlayerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.intuit.playerdb.exceptions.EntityType.*;
import static com.intuit.playerdb.exceptions.ExceptionType.*;

@Service
public class PlayerService implements IPlayerService {
    private final PlayerRepository repository;
    protected SimpleLogger logger = new SimpleLogger(LoggerFactory.getLogger(getClass()));

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns a Player entity
     *
     * @param playerID
     * @return Player
     */
    @Override
    public Player fetchPlayerById(String playerID){
        Optional<Player> player = repository.findById(playerID);
        if(player.isPresent()) return player.get();
        else throw exception(PLAYER, ENTITY_NOT_FOUND, playerID);
    }

    /**
     * Returns all Player entities
     *
     * @return List<Player>
     */
    @Override
    public List<Player> fetchAllPlayers() {
        return repository.findAll();
    }

    /**
     * Returns all Player entities in pages
     *
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> fetchPlayersPaginated(int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Player> pagePlayer;
        pagePlayer = repository.findAll(paging);
        List<Player> players = pagePlayer.getContent();
        return constructResponseObject(pagePlayer, players);
    }

    private Map<String, Object> constructResponseObject(Page<Player> pagePlayer, List<Player> players) {
        Map<String, Object> response = new HashMap<>();
        response.put("players", players);
        response.put("currentPage", pagePlayer.getNumber());
        response.put("totalItems", pagePlayer.getTotalElements());
        response.put("totalPages", pagePlayer.getTotalPages());
        return response;
    }

    /**
     * Persists a Player entity
     *
     * @param player
     * @return void
     */
    @Override
    public void savePlayer(Player player){
        try {
            repository.save(player);
        }catch (IllegalStateException ex){
            throw exception(PLAYER, ENTITY_BAD_REQUEST, player.getPlayerID());
        }
    }

    /**
     * Increment a Player's weight
     *
     * @param playerID
     * @return void
     */
    @Override
    public void incrementWeight(String playerID){
        Player player = fetchPlayerById(playerID);
        player.setWeight(player.getWeight()+1);
        savePlayer(player);
    }

    /**
     * Increment a Player's height
     *
     * @param playerID
     * @return void
     */
    @Override
    public void incrementHeight(String playerID){
        Player player = fetchPlayerById(playerID);
        player.setHeight(player.getHeight()+1);
        savePlayer(player);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return PlayerDBCustomException.throwException(entityType, exceptionType, args);
    }

}


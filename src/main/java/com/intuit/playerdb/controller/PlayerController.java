package com.intuit.playerdb.controller;

import com.intuit.playerdb.dto.response.Response;
import com.intuit.playerdb.logging.SimpleLogger;
import com.intuit.playerdb.model.Player;
import com.intuit.playerdb.service.PlayerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PlayerController {
    protected SimpleLogger LOGGER = new SimpleLogger(LoggerFactory.getLogger(getClass()));

    @Autowired
    private PlayerService service;

    /**
     * GET API to fetch all players
     * Returns an entity of type Response
     * @return Response
     */
    @GetMapping(value = "/api/players")
    public Response getPlayers(){
        return Response.ok().setPayload(service.fetchAllPlayers());
    }

    /**
     * GET API to fetch all players in paginated way
     * Returns an entity of type Response
     * @param page
     * @param size
     * @return ResponseEntity
     */
    @GetMapping(value = "/api/players/paginated")
    public ResponseEntity<Map<String, Object>> getPlayersPaginated(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size){
        Pageable paging = PageRequest.of(page, size);

        Page<Player> pagePlayer;
        pagePlayer = service.fetchPlayersPaginated(paging);
        List<Player> players = pagePlayer.getContent();
        Map<String, Object> response = constructResponseObject(pagePlayer, players);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
     * GET API to fetch a players by ID
     * Returns an entity of type Response
     * @param playerID
     * @return Response
     */
    @GetMapping(value = "/api/players/{playerID}")
    public Response getPlayerById(@PathVariable String playerID){
        Player player = service.fetchPlayerById(playerID);
        return Response.ok().setPayload(player);
    }

    /**
     * PUT API to increment a player's weight
     * Returns an entity of type Response
     * @param playerID
     * @return Response
     */
    @PutMapping(value = "/api/players/{playerID}/weight")
    public Response incrementPlayerWeight(@PathVariable String playerID){
        service.incrementWeight(playerID);
        return Response.entityUpdated();
    }

    /**
     * PUT API to increment a player's height
     * Returns an entity of type Response
     * @param playerID
     * @return Response
     */
    @PutMapping(value = "/api/players/{playerID}/height")
    public Response incrementPlayerHeight(@PathVariable String playerID){
        service.incrementHeight(playerID);
        return Response.entityUpdated();
    }


}

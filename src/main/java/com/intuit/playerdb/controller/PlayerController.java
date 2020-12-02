package com.intuit.playerdb.controller;

import com.intuit.playerdb.dto.response.Response;
import com.intuit.playerdb.logging.SimpleLogger;
import com.intuit.playerdb.service.PlayerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @return Response
     */
    @GetMapping(value = "/api/players/paginated")
    public Response getPlayersPaginated(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size){
        Map<String, Object> payload = service.fetchPlayersPaginated(page, size);
        return Response.ok().setPayload(payload);
    }

    /**
     * GET API to fetch a players by ID
     * Returns an entity of type Response
     * @param playerID
     * @return Response
     */
    @GetMapping(value = "/api/players/{playerID}")
    public Response getPlayerById(@PathVariable String playerID){
        return Response.ok().setPayload(service.fetchPlayerById(playerID));
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

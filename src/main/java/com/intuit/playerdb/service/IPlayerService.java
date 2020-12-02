package com.intuit.playerdb.service;

import com.intuit.playerdb.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPlayerService {
     Player fetchPlayerById(String playerID);
     List<Player> fetchAllPlayers();
     Map<String, Object> fetchPlayersPaginated(int page, int size);
     void incrementWeight(String playerID);
     void incrementHeight(String playerID);
     void savePlayer(Player player);
}

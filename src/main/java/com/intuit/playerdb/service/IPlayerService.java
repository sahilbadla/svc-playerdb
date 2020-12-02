package com.intuit.playerdb.service;

import com.intuit.playerdb.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPlayerService {
     Player fetchPlayerById(String playerID);
     List<Player> fetchAllPlayers();
     Page<Player> fetchPlayersPaginated(Pageable paging);
     void incrementWeight(String playerID);
     void incrementHeight(String playerID);
     void savePlayer(Player player);
}

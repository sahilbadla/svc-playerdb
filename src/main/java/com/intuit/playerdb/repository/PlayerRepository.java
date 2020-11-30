package com.intuit.playerdb.repository;

import com.intuit.playerdb.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, String> {

}

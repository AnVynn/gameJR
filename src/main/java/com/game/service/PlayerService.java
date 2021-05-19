package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface PlayerService {
    Page<Player> getAllPlayers(Specification<Player> playerSpecification, Pageable pageable);

    Long getCountPlayers(Specification<Player> playerSpecification);

    Player createPlayer(Player player);

    Player getPlayerById(Long id);

    Player updatePlayer(Long id, Player player);

    void deletePlayer(Long id);

    boolean isExistsById(Long id);

    boolean isIdValid(Long id);

    boolean isDateValid(Player player);
}

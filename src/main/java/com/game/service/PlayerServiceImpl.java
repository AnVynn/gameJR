package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Page<Player> getAllPlayers(Specification<Player> playerSpecification, Pageable pageable) {
        return playerRepository.findAll(playerSpecification, pageable);
    }

    @Override
    public Long getCountPlayers(Specification<Player> playerSpecification) {
        return playerRepository.count(playerSpecification);
    }

    @Override
    public Player createPlayer(Player player) {
        if (player.isBanned() == null) {
            player.setBanned(false);
        }

        int level = resultLevelPlayer(player);
        int untilNextLevel = resultUntilNextLevelPlayer(player, level);
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);

        return playerRepository.save(player);
    }

    @Override
    public Player getPlayerById(Long id) {
        isIdValid(id);
        isExistsById(id);
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public Player updatePlayer(Long id, Player player) {
        Player updatePlayer = getPlayerById(id);

        if (player.getName() != null) {
            updatePlayer.setName(player.getName());
        }

        if (player.getTitle() != null) {
            updatePlayer.setTitle(player.getTitle());
        }

        if (player.getRace() != null) {
            updatePlayer.setRace(player.getRace());
        }

        if (player.getProfession() != null) {
            updatePlayer.setProfession(player.getProfession());
        }

        if (player.getBirthday() != null) {
            updatePlayer.setBirthday(player.getBirthday());
        }

        if (player.isBanned() != null) {
            updatePlayer.setBanned(player.isBanned());
        }

        if (player.getExperience() != null && player.getExperience() != 0) {
            int level = resultLevelPlayer(player);
            int untilNextLevel = resultUntilNextLevelPlayer(player, level);
            updatePlayer.setLevel(level);
            updatePlayer.setUntilNextLevel(untilNextLevel);
            updatePlayer.setExperience(player.getExperience());
        }

        return playerRepository.save(updatePlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        isIdValid(id);
        isExistsById(id);
        playerRepository.deleteById(id);
    }

    @Override
    public boolean isExistsById(Long id) {
        return playerRepository.existsById(id);
    }

    @Override
    public boolean isIdValid(Long id) {
        return id > 0;
    }


    public int resultLevelPlayer(Player player) {
        return (int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100;
    }

    public int resultUntilNextLevelPlayer(Player player, int level) {
        return 50 * (level + 1) * (level + 2) - player.getExperience();
    }

    @Override
    public boolean isDateValid(Player player) {
        return player.getBirthday().after(new Date(946587600000L)) && player.getBirthday().before(new Date(32503582800000L));
    }
}

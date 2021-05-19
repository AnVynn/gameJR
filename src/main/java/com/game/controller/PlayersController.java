package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.SpecificationPlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayersController {
    private PlayerService playerService;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    //вернуть список игроков
    public ResponseEntity<List<Player>> getPlayersList(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String title,
                                                       @RequestParam(required = false) Race race,
                                                       @RequestParam(required = false) Profession profession,
                                                       @RequestParam(required = false) Long after,
                                                       @RequestParam(required = false) Long before,
                                                       @RequestParam(required = false) Boolean banned,
                                                       @RequestParam(required = false) Integer minExperience,
                                                       @RequestParam(required = false) Integer maxExperience,
                                                       @RequestParam(required = false) Integer minLevel,
                                                       @RequestParam(required = false) Integer maxLevel,
                                                       @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                                       @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                       @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        List<Player> playerList = playerService.getAllPlayers(Specification.where(SpecificationPlayer.findByName(name).
                and(SpecificationPlayer.findByTitle(title)).and(SpecificationPlayer.findByRace(race)).
                and(SpecificationPlayer.findByProfession(profession)).and(SpecificationPlayer.findByAfter(after)).
                and(SpecificationPlayer.findByBefore(before)).and(SpecificationPlayer.findByBanned(banned)).
                and(SpecificationPlayer.findByMinExperience(minExperience)).
                and(SpecificationPlayer.findByMaxExperience(maxExperience)).
                and(SpecificationPlayer.findByMinLevel(minLevel)).
                and(SpecificationPlayer.findByMaxLevel(maxLevel))), pageable).getContent();
        return new ResponseEntity<>(playerList, HttpStatus.OK);
    }

    @GetMapping("/players/count")
    //вернуть к-во игроков
    public ResponseEntity<Long> getPlayersCount(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String title,
                                                @RequestParam(required = false) Race race,
                                                @RequestParam(required = false) Profession profession,
                                                @RequestParam(required = false) Long after,
                                                @RequestParam(required = false) Long before,
                                                @RequestParam(required = false) Boolean banned,
                                                @RequestParam(required = false) Integer minExperience,
                                                @RequestParam(required = false) Integer maxExperience,
                                                @RequestParam(required = false) Integer minLevel,
                                                @RequestParam(required = false) Integer maxLevel) {
        Long countPlayers = playerService.getCountPlayers(Specification.where(SpecificationPlayer.findByName(name)).
                and(SpecificationPlayer.findByTitle(title)).and(SpecificationPlayer.findByRace(race)).
                and(SpecificationPlayer.findByProfession(profession)).and(SpecificationPlayer.findByAfter(after)).
                and(SpecificationPlayer.findByBefore(before)).and(SpecificationPlayer.findByBanned(banned)).
                and(SpecificationPlayer.findByMinExperience(minExperience)).and(SpecificationPlayer.findByMaxExperience(maxExperience)).
                and(SpecificationPlayer.findByMinLevel(minLevel)).and(SpecificationPlayer.findByMaxLevel(maxLevel)));
        return new ResponseEntity<>(countPlayers, HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    //вернуть игрока по id
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        if (!playerService.isIdValid(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = playerService.getPlayerById(id);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("/players")
    //создаем игрока
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (player.getName() == null || player.getTitle() == null || player.getRace() == null
                || player.getProfession() == null || player.getExperience() == null
                || player.getBirthday() == null || player.getName().length() > 12
                || player.getTitle().length() > 30 || player.getName().isEmpty()
                || player.getExperience() < 0 || player.getExperience() > 10000000
                || player.getBirthday().getTime() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!playerService.isDateValid(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerService.createPlayer(player), HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    //обновляем игрока
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player1) {
        if (!playerService.isIdValid(id)) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (playerService.getPlayerById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player player = playerService.updatePlayer(id, player1);

        if (player.getName() == null || player.getName().isEmpty() || player.getName().length() > 12 ||
                player.getTitle() == null || player.getTitle().isEmpty() || player.getTitle().length() > 30
                || player.getRace() == null || player.getProfession() == null
                || player.getBirthday() == null || player.getBirthday().getTime() < 0 || !playerService.isDateValid(player)
                || player.isBanned() == null || player.getExperience() == null || player.getExperience() < 0
                || player.getExperience() > 10000000) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerService.updatePlayer(id, player), HttpStatus.OK);
    }
    @DeleteMapping("/players/{id}")
    //удаление игрока по id
    public ResponseEntity<Player> deletePlayerById(@PathVariable Long id) {
        if (!playerService.isIdValid(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Player player = playerService.getPlayerById(id);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerService.deletePlayer(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

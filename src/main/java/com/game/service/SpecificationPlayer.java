package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class SpecificationPlayer {
    public static Specification<Player> findByName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Player> findByTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Player> findByRace(Race race) {
        return (root, query, criteriaBuilder) -> race == null ? null : criteriaBuilder.equal(root.get("race"), race);
    }

    public static Specification<Player> findByProfession(Profession profession) {
        return (root, query, criteriaBuilder) -> profession == null ? null : criteriaBuilder.equal(root.get("profession"), profession);
    }

    public static Specification<Player> findByAfter(Long after) {
        return (root, query, criteriaBuilder) -> after == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
    }

    public static Specification<Player> findByBefore(Long before) {
        return (root, query, criteriaBuilder) -> before == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before));
    }

    public static Specification<Player> findByBanned(Boolean banned) {
        return (root, query, criteriaBuilder) -> banned == null ? null : banned ? criteriaBuilder.isTrue(root.get("banned")) : criteriaBuilder.isFalse(root.get("banned"));
    }

    public static Specification<Player> findByMinExperience(Integer minExperience) {
        return (root, query, criteriaBuilder) -> minExperience == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience);
    }

    public static Specification<Player> findByMaxExperience(Integer maxExperience) {
        return (root, query, criteriaBuilder) -> maxExperience == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience);
    }

    public static Specification<Player> findByMinLevel(Integer minLevel) {
        return (root, query, criteriaBuilder) -> minLevel == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel);
    }

    public static Specification<Player> findByMaxLevel(Integer maxLevel) {
        return (root, query, criteriaBuilder) -> maxLevel == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel);
    }
}


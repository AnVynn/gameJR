package com.game.entity;

public enum Race {
    HUMAN("Human"),
    DWARF("Dwarf"),
    ELF("Elf"),
    GIANT("Giant"),
    ORC("Orc"),
    TROLL("Troll"),
    HOBBIT("Hobbit");

    private final String race;

    Race(String race) {
        this.race = race;
    }

    public String getRace() {
        return race;
    }
}
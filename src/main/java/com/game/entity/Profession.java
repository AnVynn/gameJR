package com.game.entity;

public enum Profession {
    WARRIOR("Warrior"),
    ROGUE("Rogue"),
    SORCERER("Sorcerer"),
    CLERIC("Cleric"),
    PALADIN("Paladin"),
    NAZGUL("Nazgul"),
    WARLOCK("Warlock"),
    DRUID("Druid");

    private final String profession;

    Profession(String profession) {
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }
}

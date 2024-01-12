package org.example.apps.mctg.enums;

public enum CreatureType {
    GOBLIN("Goblin"),
    DRAGON("Dragon"),
    ORK("Ork"),
    WIZARD("Wizard"),
    KRAKEN("Kraken"),
    KNIGHT("Knight"),
    ELF("Elf"),
    NO_CREATURE("No creature");
    private final String type;
    CreatureType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}

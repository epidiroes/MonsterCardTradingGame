package org.example.apps.mctg.entity;

import org.example.apps.mctg.enums.CreatureType;
import org.example.apps.mctg.enums.ElementType;

public class Card {
    private String id;
    private String user_id;
    private String name;
    private int damage;

    public Card() {}

    public Card(String id, String user_id, String name, int damage) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.damage = damage;
    }

    public String toString() {
        return "Id: " + this.getId() + ", Name: " + this.getName() + ", Damage: " + this.getDamage() + "\n";
    }

    public boolean isSpell() {
        return this.getName().endsWith("Spell");
    }

    public boolean hasElementType() {
        ElementType type = this.elementType();
        return type.equals(ElementType.WATER) || type.equals(ElementType.FIRE) || type.equals(ElementType.REGULAR);
    }

    public ElementType elementType() {
        String name = this.getName();
        if (name.startsWith("Water")) {
            return ElementType.WATER;
        } else if (name.startsWith("Fire")) {
            return ElementType.FIRE;
        } else if (name.startsWith("Regular")) {
            return ElementType.REGULAR;
        } else {
            return ElementType.NO_TYPE;
        }
    }

    public CreatureType creatureType() {
        String name = this.getName();
        if (name.endsWith("Goblin")) {
            return CreatureType.GOBLIN;
        } else if (name.endsWith("Ork")) {
            return CreatureType.ORK;
        } else if (name.endsWith("Dragon")) {
            return CreatureType.DRAGON;
        } else if (name.endsWith("Wizard")) {
            return CreatureType.WIZARD;
        } else if (name.endsWith("Kraken")) {
            return CreatureType.KRAKEN;
        } else if (name.endsWith("Knight")) {
            return CreatureType.KNIGHT;
        } else if (name.endsWith("Elf")) {
            return CreatureType.ELF;
        } else {
            return CreatureType.NO_CREATURE;
        }
    }

    public double getDamageMultiplier(Card enemy) {
        ElementType ownType = this.elementType();
        ElementType enemyType = enemy.elementType();
        if (
                ownType.equals(ElementType.WATER) && enemyType.equals(ElementType.FIRE) ||
                ownType.equals(ElementType.FIRE) && enemyType.equals(ElementType.REGULAR) ||
                ownType.equals(ElementType.REGULAR) && enemyType.equals(ElementType.WATER)
        ) {
            return 2;
        } else if (
                ownType.equals(ElementType.FIRE) && enemyType.equals(ElementType.WATER) ||
                ownType.equals(ElementType.REGULAR) && enemyType.equals(ElementType.FIRE) ||
                ownType.equals(ElementType.WATER) && enemyType.equals(ElementType.REGULAR)
        ) {
            return 0.5;
        } else {
            return 1;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

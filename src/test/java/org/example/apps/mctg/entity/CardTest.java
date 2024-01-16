package org.example.apps.mctg.entity;

import org.example.apps.mctg.enums.CreatureType;
import org.example.apps.mctg.enums.ElementType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void whenCallingToString_getPlainString() {
        // Arrange
        Card card = new Card("1234", "user-id", "Dragon", 40);

        // Act
        String plainString = card.toString();

        // Assert
        assertEquals(plainString, "Id: 1234, Name: Dragon, Damage: 40\n");
    }

    @Test
    public void whenCallingIsSpell_getCorrectBoolean() {
        // Arrange
        Card dragon = new Card("1234", "user-id", "Dragon", 40);
        Card spell = new Card("2345", "other-user-id", "FireSpell", 30);

        // Act
        boolean isSpellDragon = dragon.isSpell();
        boolean isSpellSpell = spell.isSpell();

        // Assert
        assertFalse(isSpellDragon);
        assertTrue(isSpellSpell);
    }

    @Test
    public void whenCallingHasElementType_getCorrectBoolean() {
        // Arrange
        Card dragon = new Card("1234", "user-id", "Dragon", 40);
        Card kraken = new Card("1234", "user-id", "Kraken", 20);

        Card regularSpell = new Card("2345", "other-user-id", "RegularSpell", 30);
        Card fireSpell = new Card("1234", "user-id", "FireSpell", 20);
        Card waterElf = new Card("1234", "user-id", "WaterElf", 20);
        Card regularGoblin = new Card("1234", "user-id", "RegularGoblin", 20);

        // Act
        boolean hasElementTypeDragon = dragon.hasElementType();
        boolean hasElementTypeKraken = kraken.hasElementType();

        boolean hasElementTypeRegularSpell = regularSpell.hasElementType();
        boolean hasElementTypeFireSpell = fireSpell.hasElementType();
        boolean hasElementTypeWaterElf = waterElf.hasElementType();
        boolean hasElementTypeRegularGoblin = regularGoblin.hasElementType();

        // Assert
        assertFalse(hasElementTypeDragon);
        assertFalse(hasElementTypeKraken);

        assertTrue(hasElementTypeRegularSpell);
        assertTrue(hasElementTypeFireSpell);
        assertTrue(hasElementTypeWaterElf);
        assertTrue(hasElementTypeRegularGoblin);
    }

    @Test
    public void whenCallingElementType_getCorrectElementType() {
        // Arrange
        Card fireElf = new Card("1234", "user-id", "FireElf", 20);
        Card waterElf = new Card("1234", "user-id", "WaterElf", 20);
        Card regularElf = new Card("1234", "user-id", "RegularElf", 20);
        Card noElement = new Card("1234", "user-id", "Dragon", 20);

        // Act
        ElementType fireElfElement = fireElf.elementType();
        ElementType waterElfElement = waterElf.elementType();
        ElementType regularElfElement = regularElf.elementType();
        ElementType noElementElement = noElement.elementType();

        // Assert
        assertEquals(fireElfElement, ElementType.FIRE);
        assertEquals(waterElfElement, ElementType.WATER);
        assertEquals(regularElfElement, ElementType.REGULAR);
        assertEquals(noElementElement, ElementType.NO_TYPE);
    }

    @Test
    public void whenCallingCreatureType_getCorrectCreatureType() {
        // Arrange
        Card waterGoblin = new Card("1234", "user-id", "WaterGoblin", 20);
        Card ork = new Card("1234", "user-id", "Ork", 20);
        Card dragon = new Card("1234", "user-id", "Dragon", 20);
        Card wizard = new Card("1234", "user-id", "Wizard", 20);
        Card kraken = new Card("1234", "user-id", "Kraken", 20);
        Card knight = new Card("1234", "user-id", "Knight", 20);
        Card fireElf = new Card("1234", "user-id", "FireElf", 20);

        // Act
        CreatureType waterGoblinType = waterGoblin.creatureType();
        CreatureType orkType = ork.creatureType();
        CreatureType dragonType = dragon.creatureType();
        CreatureType wizardType = wizard.creatureType();
        CreatureType krakenType = kraken.creatureType();
        CreatureType knightType = knight.creatureType();
        CreatureType fireElfType = fireElf.creatureType();

        // Assert
        assertEquals(waterGoblinType, CreatureType.GOBLIN);
        assertEquals(orkType, CreatureType.ORK);
        assertEquals(dragonType, CreatureType.DRAGON);
        assertEquals(wizardType, CreatureType.WIZARD);
        assertEquals(krakenType, CreatureType.KRAKEN);
        assertEquals(knightType, CreatureType.KNIGHT);
        assertEquals(fireElfType, CreatureType.ELF);
    }

    @Test
    public void whenCallingGetDamageMultiplier_getCorrectMultiplier() {
        // Arrange
        Card waterSpell = new Card("1234", "user-id", "WaterSpell", 20);
        Card waterOrk = new Card("1234", "user-id", "WaterOrk", 20);
        Card fireSpell = new Card("1234", "user-id", "FireSpell", 20);
        Card fireElf = new Card("1234", "user-id", "FireElf", 20);
        Card regularSpell = new Card("1234", "user-id", "RegularSpell", 20);
        Card regularGoblin = new Card("1234", "user-id", "RegularGoblin", 20);

        // Act
        double waterSpellWaterOrk = waterSpell.getDamageMultiplier(waterOrk);
        double waterSpellFireSpell = waterSpell.getDamageMultiplier(fireSpell);
        double waterOrkRegularSpell = waterOrk.getDamageMultiplier(regularSpell);
        double fireSpellWaterSpell = fireSpell.getDamageMultiplier(waterSpell);
        double fireSpellRegularGoblin = fireSpell.getDamageMultiplier(regularGoblin);
        double fireElfFireSpell = fireElf.getDamageMultiplier(fireSpell);
        double regularSpellWaterSpell = regularSpell.getDamageMultiplier(waterSpell);
        double regularSpellFireElf = regularSpell.getDamageMultiplier(fireElf);
        double regularGoblinRegularSpell = regularGoblin.getDamageMultiplier(regularSpell);

        // Assert
        assertEquals(waterSpellWaterOrk, 1);
        assertEquals(waterSpellFireSpell, 2);
        assertEquals(waterOrkRegularSpell, 0.5);
        assertEquals(fireSpellWaterSpell, 0.5);
        assertEquals(fireSpellRegularGoblin, 2);
        assertEquals(fireElfFireSpell, 1);
        assertEquals(regularSpellWaterSpell, 2);
        assertEquals(regularSpellFireElf, 0.5);
        assertEquals(regularGoblinRegularSpell, 1);
    }
}